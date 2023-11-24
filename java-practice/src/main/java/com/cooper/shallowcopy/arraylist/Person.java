package com.cooper.shallowcopy.arraylist;

class Person {
	private String name;
	private int price;

	public Person(String name, int price) {
		this.name = name;
		this.price = price;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Person{" +
			"name='" + name + '\'' +
			", price=" + price +
			'}';
	}
}
