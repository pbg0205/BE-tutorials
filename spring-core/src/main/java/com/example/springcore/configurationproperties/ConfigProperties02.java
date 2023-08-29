package com.example.springcore.configurationproperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@ToString
@Validated
public class ConfigProperties02 {

    @NotBlank
    private String hostName;
    @PositiveOrZero
    private int port;
    @NotBlank
    private String from;

}
