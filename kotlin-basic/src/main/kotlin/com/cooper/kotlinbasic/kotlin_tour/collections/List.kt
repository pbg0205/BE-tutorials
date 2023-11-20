package com.cooper.kotlinbasic.kotlin_tour.collections

/**
 * - https://kotlinlang.org/docs/kotlin-tour-collections.html#list
 */
fun main() {
    readOnlyList()
    mutableList()
}

private fun readOnlyList() {
    // Read only list
    val readOnlyShapes = listOf("triangle", "square", "circle")
    println(readOnlyShapes) // [triangle, square, circle]

    // 1. 첫번째 원소 추출
    println("The first item in the list is: ${readOnlyShapes[0]}")
    println("The first item in the list is: ${readOnlyShapes.first()}")

    // 2. 마지막 원소 추출
    println("The last item in the list is: ${readOnlyShapes.last()}")

    // 3. 원소 갯수 추출
    println("This list has ${readOnlyShapes.count()} items")

    // 4. 해당 값이 포함되어 있는지 확인
    println("circle" in readOnlyShapes) // true
}

private fun mutableList() {
    // Mutable list with explicit type declaration
    val shapes: MutableList<String> = mutableListOf("triangle", "square", "circle")

    val shapesLocked: List<String> = shapes
    shapes.add("pentagon") // shapesLocked 로 변환하더라도 shapes 에 추가하면 값이 추가됨
    shapes.remove("pentagon") // shapesLocked 로 변환하더라도 shapes 에 추가하면 값이 삭제됨

    println(shapes) // [triangle, square, circle]
    println(shapesLocked) // [triangle, square, circle]
}


