package com.cooper.shallowcopy.array;

import java.util.Arrays;

public class StringArrayShallowCopy {
	public static void main(String[] args) {
		String[] names = new String[] {"cooper", "anna", "jack"};
		String[] namesCopy = Arrays.copyOf(names, names.length);

		namesCopy[2] = "panda";

		for (String name : names) {
			System.out.print(name + " ");
		}

		System.out.println();


		for (String name : namesCopy) {
			System.out.print(name + " ");
		}
	}
}
