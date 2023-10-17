package com.example.springcore.spel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter // spel 적용을 위해 setter 필요
@ToString
@AllArgsConstructor
public class Car {

	private String make;
	private String model;
	private int yearOfProduction;

}
