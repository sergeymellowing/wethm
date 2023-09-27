package com.mellowingfactory.wethm

import aws.smithy.kotlin.runtime.util.length
import com.mellowingfactory.wethm.utils.getMonthList
import org.junit.Test

class DateUtilsUnitTest {
    @Test
    fun testCalendarMonthList(){
        val list = getMonthList()
        println(list.length)
        list.forEach {
            println(it)
        }
    }
}