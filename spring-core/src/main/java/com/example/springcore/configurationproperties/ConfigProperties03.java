package com.example.springcore.configurationproperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Component
@ToString
@ConfigurationProperties("mail3")
@Validated
public class ConfigProperties03 {

    private String hostname;
    @PositiveOrZero
    private int port;
    @Pattern(regexp = "([0-9a-zA-Z_.+-]+)@[0-9a-zA-Z_.+-]+\\.[0-9a-zA-Z]+")
    private String from;
    private List<String> defaultRecipients;
    private Map<String, String> additionalHeaders;
    private Credentials credentials;

}
