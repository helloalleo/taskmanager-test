package com.workingbit.taskmanager.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Created by Aleksey Popryaduhin on 08:57 11/06/2017.
 */
@Component
@PropertySource("classpath:aws.properties")
public class AWSProperties {

  private final Environment env;

  @Value("${CLIENT_ID}")
  private @Getter
  String clientId;

  @Value("${REGION}")
  private @Getter
  String region;

  @Value("${APP_CLIENT_ID}")
  private @Getter
  String appClientId;

  @Value("${APP_CLIENT_SECRET}")
  private @Getter
  String appClientSecret;


  @Value("${USER_TABLE}")
  private @Getter
  String userTable;

  @Value("${READ_CAPACITY_UNITS}")
  private @Getter
  Long readCapacityUnits;

  @Value("${WRITE_CAPACITY_UNITS}")
  private @Getter
  Long writeCapacityUnits;


  @Autowired
  public AWSProperties(Environment env) {
    this.env = env;
  }

}
