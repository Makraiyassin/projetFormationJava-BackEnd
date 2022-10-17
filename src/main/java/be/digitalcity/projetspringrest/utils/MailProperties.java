package be.digitalcity.projetspringrest.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties("spring.mail")
public class MailProperties {
    private String username;
    private String password;
    private String host;
    private int port;
}
