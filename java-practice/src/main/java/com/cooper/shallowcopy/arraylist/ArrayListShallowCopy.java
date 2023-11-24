package com.cooper.shallowcopy.arraylist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArrayListShallowCopy {
	public static void main(String[] args) {
		sample01();
		sample02();
	}

	private static void sample01() {
		List<Person> people = List.of(
			new Person("cooper", 123),
			new Person("anna", 234),
			new Person("sally", 345)
		);

		List<Person> peopleCopy = new ArrayList<>(people);
		peopleCopy.removeLast();
		peopleCopy.add(new Person("smith", 111));
		peopleCopy.add(new Person("panda", 333));

		/**
		 * 얕은 복사(구조적 공유)
		 * - 원소들의 데이터 참조만 복사하는 구조
		 * - 구조적 공유 : 두 중첩되니 데이터 구조에서 안쪽 데이터가 같은 데이터를 참조
		 */

		// [Person{name='cooper', price=123}, Person{name='anna', price=234}, Person{name='sally', price=345}]
		System.out.println(people);

		// [Person{name='cooper', price=123}, Person{name='anna', price=234}, Person{name='smith', price=111}]
		System.out.println(peopleCopy);
	}

	private static void sample02() {
		List<Person> people = List.of(
			new Person("cooper", 123),
			new Person("anna", 234),
			new Person("sally", 345)
		);

		Collections.unmodifiableCollection(people);

		List<Person> peopleCopy = new ArrayList<>(people);
		peopleCopy.removeLast();
		peopleCopy.add(new Person("smith", 111));
		peopleCopy.add(new Person("panda", 333));

		System.out.println(people);
		System.out.println(peopleCopy);
	}
}
