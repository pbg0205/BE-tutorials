package com.example.springcore.spel;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class CarPark {

	private final List<Car> cars = new ArrayList<>();

	public void add(Car car) {
		cars.add(car);
	}

}
