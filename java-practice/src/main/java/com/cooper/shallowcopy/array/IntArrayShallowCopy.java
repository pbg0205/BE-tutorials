package com.cooper.shallowcopy.array;

import java.util.Arrays;

public class IntArrayShallowCopy {
	public static void main(String[] args) {
		int[] numbers = new int[] {1, 2, 3};
		int[] numbersCopy = Arrays.copyOf(numbers, numbers.length);

		numbersCopy[2] = 4;

		for (int number : numbers) {
			System.out.print(number + " ");
		}

		System.out.println();


		for (int number : numbersCopy) {
			System.out.print(number + " ");
		}
	}
}
