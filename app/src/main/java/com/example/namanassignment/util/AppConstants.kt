package com.example.namanassignment.util

import java.lang.Exception
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

public class AppConstants {

    companion object{
        const val COMMENT = "comment"

        @Throws(ParseException::class)
        public fun getFormattedDate(date: String): String? {
            return try {
                val d: Date = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date)
                val cal: Calendar = Calendar.getInstance()
                cal.time = d
                SimpleDateFormat("MMMM yyyy").format(cal.time)
            }catch (e:Exception){
                date
            }
        }
    }
}