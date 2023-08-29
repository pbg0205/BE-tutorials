package com.example.springcore.configurationproperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

@Component
@ConfigurationProperties(prefix = "mail")
@Getter
@Setter
@ToString
@Validated
public class ConfigProperties01 {

    @NotBlank
    private String hostName;
    @PositiveOrZero
    private int port;
    @Pattern(regexp = "[0-9a-zA-Z]{4,}")
    private String from;

}
