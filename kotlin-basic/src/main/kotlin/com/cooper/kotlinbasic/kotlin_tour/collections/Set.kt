package com.cooper.kotlinbasic.kotlin_tour.collections

/**
 * - https://kotlinlang.org/docs/kotlin-tour-collections.html#set
 */
fun main() {
    readOnlySet()
    mutableSet()
}

private fun readOnlySet() {
    // Read-only set
    val readOnlyFruit = setOf("apple", "banana", "cherry", "cherry")

    // Mutable set with explicit type declaration
    val fruit: MutableSet<String> = mutableSetOf("apple", "banana", "cherry", "cherry")

    // 1. 원소들 출력
    println(readOnlyFruit) // [apple, banana, cherry]

    // 2. 원소 존재 여부 확인
    println("banana" in readOnlyFruit) // true
}

private fun mutableSet() {
    val fruit: MutableSet<String> = mutableSetOf("apple", "banana", "cherry", "cherry")

    // 1. read-only set 변환 (fruit 에 원소 추가, 삭제시 값 변경)
    val fruitLocked: Set<String> = fruit

    // 2. 원소 추가 & 삭제
    fruit.add("dragonfruit")    // Add "dragonfruit" to the set
    println(fruit)              // [apple, banana, cherry, dragonfruit]
    fruit.remove("dragonfruit") // Remove "dragonfruit" from the set
    println(fruit)              // [apple, banana, cherry]
}
