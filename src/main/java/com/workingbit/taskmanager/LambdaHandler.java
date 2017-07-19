package com.workingbit.taskmanager;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.internal.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.internal.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;


public class LambdaHandler implements RequestHandler<AwsProxyRequest, AwsProxyResponse> {
    private static SpringLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;

    private static SpringLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> getHandler() {
        if (null == handler) {
            try {
                SpringApplication application = new SpringApplication(TaskManagerApplication.class);
                application.setWebEnvironment(false);
                application.setBannerMode(Banner.Mode.OFF);
                ConfigurableWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
                appContext.setParent(application.run());

                handler = SpringLambdaContainerHandler.getAwsProxyHandler(appContext);
            } catch (ContainerInitializationException e) {
                e.printStackTrace();
            }
        }
        return handler;
    }

    public AwsProxyResponse handleRequest(AwsProxyRequest awsProxyRequest, Context context) {
        if (null == awsProxyRequest.getResource()) {
            AwsProxyResponse awsProxyResponse = new AwsProxyResponse();
            awsProxyResponse.setBody("null");
            return awsProxyResponse;
        }
        return getHandler().proxy(awsProxyRequest, context);
    }
}