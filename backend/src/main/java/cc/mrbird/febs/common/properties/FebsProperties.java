package cc.mrbird.febs.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Component
@Configuration
@ConfigurationProperties(prefix = "febs")
public class FebsProperties {

    private ShiroProperties shiro = new ShiroProperties();

    private boolean openAopLog = true;

    private SwaggerProperties swagger = new SwaggerProperties();

    private String uploadPath;

    private String baseUrl;


    private String sendMsg;

//    private int openSms;
//    private String in0;
//    private String in1;
//    private String in2;
//    private String in3;
}
