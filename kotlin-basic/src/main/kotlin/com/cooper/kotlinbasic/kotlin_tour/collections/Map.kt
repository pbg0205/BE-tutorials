package com.cooper.kotlinbasic.kotlin_tour.collections

/**
 * https://kotlinlang.org/docs/kotlin-tour-collections.html#map
 */
fun main() {
    readOnlyMap()
    mutableMap()
}

private fun readOnlyMap() {
    // Read-only map
    val readOnlyJuiceMenu = mapOf("apple" to 100, "kiwi" to 190, "orange" to 100)
    println(readOnlyJuiceMenu) // {apple=100, kiwi=190, orange=100}

    // 1. map 원소(value) 접근 방법
    println("The value of apple juice is: ${readOnlyJuiceMenu["apple"]}")

    // 2. map 원소 갯수 조회
    println("This map has ${readOnlyJuiceMenu.count()} key-value pairs")

    // 3. map 에 존재하는 키 여부 확인
    println(readOnlyJuiceMenu.containsKey("kiwi")) // kiwi

    // 4. map 내부 key 목록 조회 & value 목록 조회
    println(readOnlyJuiceMenu.keys) // [apple, kiwi, orange]
    println(readOnlyJuiceMenu.values) // [100, 190, 100]

    // 5. key, value 목록 내 값 존재 확인
    println("orange" in readOnlyJuiceMenu.keys) // true
    println(200 in readOnlyJuiceMenu.values) // false
}

private fun mutableMap() {
    // Mutable map with explicit type declaration
    val juiceMenu: MutableMap<String, Int> = mutableMapOf("apple" to 100, "kiwi" to 190, "orange" to 100)

    // 1. read-only map 변환 (fruit 에 원소 추가, 삭제시 값 변경)
    val juiceMenuLocked: Map<String, Int> = juiceMenu
    println(juiceMenu)  // {apple=100, kiwi=190, orange=100}

    // 2. 원소 추가 & 삭제
    juiceMenu.put("coconut", 150) // Add key "coconut" with value 150 to the map
    println(juiceMenu) // {apple=100, kiwi=190, orange=100, coconut=150}
    juiceMenu.remove("orange")    // Remove key "orange" from the map
    println(juiceMenu)
}
