package com.sdxxtop.guardianapp.aakt

/**
 * @author :  lwb
 * Date: 2020/1/9
 * Desc:
 */
fun main() {
    var list = listOf<Int>(1, 2, 3, 4)

    if (list.none { it > 10 }) {
        println("没有大于10的值")
    }

    method3()
}

fun mrthod(str: () -> String): String {

    return "sss"
}

fun method2(): String {
    return "sss"
}

fun method3() {
    val a = 1
    try {
        println(a)
    } catch (ex: NumberFormatException) {
        null
    } finally {
        println("final invoked")
    }
}