package com.workingbit.taskmanager;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.internal.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.internal.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

public class LambdaHandler implements RequestHandler<AwsProxyRequest, AwsProxyResponse> {
    private static SpringLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;

    private static SpringLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> getHandler() {
        if (null == handler) {
            try {
                handler = SpringLambdaContainerHandler.getAwsProxyHandler(TaskManagerApplication.class);
                System.out.println(SpringLambdaContainerHandler.getContainerConfig().getServiceBasePath());
//                handler.activateSpringProfiles("dev");


//                addPath("/var/task");
//                addPath("/var/task/lib");

                ClassLoader cl = ClassLoader.getSystemClassLoader();

                URL[] urls = ((URLClassLoader)cl).getURLs();

                for(URL url: urls){
                    System.out.println("CLASSPATH: " + url.getFile());
                }

                displayIt(new File("/var"));

            } catch (ContainerInitializationException e) {
                e.printStackTrace();
            }
        }
        return handler;
    }

    public static void addPath(String s) {
        File f = new File(s);
        URI u = f.toURI();
        URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        Class<URLClassLoader> urlClass = URLClassLoader.class;
        Method method = null;
        try {
            method = urlClass.getDeclaredMethod("addURL", new Class[]{URL.class});
            method.setAccessible(true);
            method.invoke(urlClassLoader, new Object[]{u.toURL()});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void displayIt(File node){

        System.out.println(node.getAbsoluteFile());

        if(node.isDirectory()){
            String[] subNote = node.list();
            if (subNote != null) {
                for (String filename : subNote) {
                    displayIt(new File(node, filename));
                }
            }
        }
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