package com.cooper.springasync.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown=true)
@Getter
@Setter
@ToString
public class User {

    private String name;
    private String blog;

}
