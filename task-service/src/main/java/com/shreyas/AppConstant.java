package com.shreyas;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class AppConstant {
    private final Environment environment;

    public AppConstant(Environment environment) {
        this.environment = environment;
    }

    public String GetTaskUserServiceUrl(){
        return environment.getProperty("SPRING_APP_USER_TASK_SERVICE_URL");
    }

    public boolean RunStartupFile() {
        return Boolean.parseBoolean(environment.getProperty("SPRING_APP_RUN_STARTUP_FILE"));
    }

    public String GetApplicationURL() {
        return environment.getProperty("SPRING_APP_API_URL");
    }

    public Boolean IsMailServiceEnabled(){
        return Boolean.parseBoolean(environment.getProperty("MAIL_SERVICE_ENABLED"));
    }

    public String GetAWSAccessKey() {
        return environment.getProperty("AWS_ACCESS_KEY_ID");
    }

    public String GetAWSSecretAccessKey() {
        return environment.getProperty("AWS_SECRET_ACCESS_KEY");
    }

    public String GetAWSS3Region() {
        return environment.getProperty("AWS_S3_REGION");
    }

    public boolean AWSServiceEnabled() {
        return Boolean.parseBoolean(environment.getProperty("AWS_SERVICE_ENABLED"));
    }

    public String AWSS3BucketName() {
        return environment.getProperty("AWS_S3_BUCKET_NAME");
    }

    public static final String CORRELATION_ID = "Correlation-Id";
    public static final String Authorization_Header = "Authorization";
    public static final String Bearer = "Bearer";
}
