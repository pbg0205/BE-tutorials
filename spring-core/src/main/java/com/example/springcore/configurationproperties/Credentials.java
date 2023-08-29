package com.example.springcore.configurationproperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@ToString
public class Credentials {
    private String authMethod;
    private String username;
    private String password;
}
