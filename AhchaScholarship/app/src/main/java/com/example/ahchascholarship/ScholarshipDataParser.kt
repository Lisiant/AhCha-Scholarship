package com.example.ahchascholarship

import android.icu.text.SimpleDateFormat
import java.lang.Exception
import java.text.ParseException
import java.util.*

class ScholarshipDataParser {
	val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
	public fun stringToDate(dateString:String): StartEndDate?{
		val splitData = dateString.split("~")
		var ret:StartEndDate
		if(splitData.size<2)
			return null
		try{
			if(splitData[1].trim().split("-")[0].trim().toInt() < 2022)
				return null
		} catch (e: Exception){
			return null
		}
		try{
			ret = StartEndDate(
				dateFormat.parse(splitData[0].trim().removePrefix("-").trim()),
				dateFormat.parse(splitData[1].trim()))
		} catch (e: ParseException){
			return null
		}
		return ret
	}

}