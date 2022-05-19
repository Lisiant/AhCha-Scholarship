package com.example.ahchascholarship

import android.icu.text.SimpleDateFormat
import java.lang.Exception
import java.text.ParseException
import java.util.*
import kotlin.collections.ArrayList

class ScholarshipDataParser {
	companion object {

		val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
		val DEPT_8: String = "제한없음"
		val DEPT_7: String = "공학계열"
		val DEPT_6: String = "교육계열"
		val DEPT_5: String = "사회계열"
		val DEPT_4: String = "예체능계열"
		val DEPT_3: String = "의약계열"
		val DEPT_2: String = "인문계열"
		val DEPT_1: String = "자연계열"
		val DEPT_0: String = "특정학과"
		val YEAR_12: String = "제한없음"
		val YEAR_11: String = "대학신입생"
		val YEAR_10: String = "대학2학기"
		val YEAR_9: String = "대학3학기"
		val YEAR_8: String = "대학4학기"
		val YEAR_7: String = "대학5학기"
		val YEAR_6: String = "대학6학기"
		val YEAR_5: String = "대학7힉기"
		val YEAR_4: String = "대학8학기이상"
		val YEAR_3: String = "석사신입생"
		val YEAR_2: String = "석사2학기이상"
		val YEAR_1: String = "박사과정"
		val YEAR_0: String = "연령제한"
		val SCHOOLCAT_9: String = "제한없음"
		val SCHOOLCAT_8: String = "4년제(5~6년제포함)"
		val SCHOOLCAT_7: String = "전문대(2~3년제)"
		val SCHOOLCAT_6: String = "특정대학"
		val SCHOOLCAT_5: String = "해외대학"
		val SCHOOLCAT_4: String = "원격대학"
		val SCHOOLCAT_3: String = "학점은행제 대학"
		val SCHOOLCAT_2: String = "기술대학"
		val SCHOOLCAT_1: String = "전문대학원"
		val SCHOOLCAT_0: String = "일반대학원"
		val S_CAT1_1: String = "장학금"
		val S_CAT1_0: String = "학자금"
		val S_CAT2_5: String = "기타"
		val S_CAT2_4: String = "성적우수"
		val S_CAT2_3: String = "소득구분"
		val S_CAT2_2: String = "장애인"
		val S_CAT2_1: String = "지역연고"
		val S_CAT2_0: String = "특기자"
		val F_CAT_5: String = "관계부처"
		val F_CAT_4: String = "대학교"
		val F_CAT_3: String = "민간(기업)"
		val F_CAT_2: String = "민간(기타)"
		val F_CAT_1: String = "지자체"
		val F_CAT_0: String = "한국장학재단"
		val BIT_13 = 0x2000
		val BIT_12 = 0x1000
		val BIT_11 = 0x800
		val BIT_10 = 0x400
		val BIT_9 = 0x200
		val BIT_8 = 0x100
		val BIT_7 = 0x80
		val BIT_6 = 0x40
		val BIT_5 = 0x20
		val BIT_4 = 0x10
		val BIT_3 = 0x8
		val BIT_2 = 0x4
		val BIT_1 = 0x2
		val BIT_0 = 0x1
	}
	public fun stringToDate(dateString: String): StartEndDate? {
		val splitData = dateString.split("~")
		var ret: StartEndDate
		if (splitData.size < 2)
			return null
		try {
			if (splitData[1].trim().split("-")[0].trim().toInt() < 2022)
				return null
		} catch (e: Exception) {
			return null
		}
		try {
			ret = StartEndDate(
				dateFormat.parse(splitData[0].trim().removePrefix("-").trim()),
				dateFormat.parse(splitData[1].trim())
			)
		} catch (e: ParseException) {
			return null
		}
		return ret
	}

	public fun encodeSCat1(dataString: String): Int {
		var ret = 0
		if (dataString.contains(S_CAT1_1))
			ret = ret.or(BIT_0)
		return ret
	}

	public fun encodeSCat2(dataString: String): Int {
		var ret = 0
		if (dataString.contains(S_CAT2_5))
			ret = ret.or(BIT_5)
		if (dataString.contains(S_CAT2_4))
			ret = ret.or(BIT_4)
		if (dataString.contains(S_CAT2_3))
			ret = ret.or(BIT_3)
		if (dataString.contains(S_CAT2_2))
			ret = ret.or(BIT_2)
		if (dataString.contains(S_CAT2_1))
			ret = ret.or(BIT_1)
		if (dataString.contains(S_CAT2_0))
			ret = ret.or(BIT_0)
		return ret
	}

	public fun encodeFCat(dataString: String): Int {
		var ret = 0
		if (dataString.contains(F_CAT_5))
			ret = ret.or(BIT_5)
		if (dataString.contains(F_CAT_4))
			ret = ret.or(BIT_4)
		if (dataString.contains(F_CAT_3))
			ret = ret.or(BIT_3)
		if (dataString.contains(F_CAT_2))
			ret = ret.or(BIT_2)
		if (dataString.contains(F_CAT_1))
			ret = ret.or(BIT_1)
		if (dataString.contains(F_CAT_0))
			ret = ret.or(BIT_0)
		return ret
	}

	public fun encodeSchoolCat(dataString: String): Int {
		var ret = 0
		if (dataString.contains(SCHOOLCAT_9))
			ret = ret.or(0x3FF)
		if (dataString.contains(SCHOOLCAT_8))
			ret = ret.or(BIT_8)
		if (dataString.contains(SCHOOLCAT_7))
			ret = ret.or(BIT_7)
		if (dataString.contains(SCHOOLCAT_6))
			ret = ret.or(BIT_6)
		if (dataString.contains(SCHOOLCAT_5))
			ret = ret.or(BIT_5)
		if (dataString.contains(SCHOOLCAT_4))
			ret = ret.or(BIT_4)
		if (dataString.contains(SCHOOLCAT_3))
			ret = ret.or(BIT_3)
		if (dataString.contains(SCHOOLCAT_2))
			ret = ret.or(BIT_2)
		if (dataString.contains(SCHOOLCAT_1))
			ret = ret.or(BIT_1)
		if (dataString.contains(SCHOOLCAT_0))
			ret = ret.or(BIT_0)
		return ret
	}

	public fun encodeDepartment(dataString: String): Int {
		var ret = 0
		if (dataString.contains(DEPT_8))
			ret = ret.or(0x1FF)
		if (dataString.contains(DEPT_7))
			ret = ret.or(BIT_7)
		if (dataString.contains(DEPT_6))
			ret = ret.or(BIT_6)
		if (dataString.contains(DEPT_5))
			ret = ret.or(BIT_5)
		if (dataString.contains(DEPT_4))
			ret = ret.or(BIT_4)
		if (dataString.contains(DEPT_3))
			ret = ret.or(BIT_3)
		if (dataString.contains(DEPT_2))
			ret = ret.or(BIT_2)
		if (dataString.contains(DEPT_1))
			ret = ret.or(BIT_1)
		if (dataString.contains(DEPT_0))
			ret = ret.or(BIT_0)
		return ret
	}

	public fun encodeYear(dataString: String): Int {
		var ret = 0
		if (dataString.contains(YEAR_12))
			ret = ret.or(0x1FFF)
		if (dataString.contains(YEAR_11))
			ret = ret.or(BIT_11)
		if (dataString.contains(YEAR_10))
			ret = ret.or(BIT_10)
		if (dataString.contains(YEAR_9))
			ret = ret.or(BIT_9)
		if (dataString.contains(YEAR_8))
			ret = ret.or(BIT_8)
		if (dataString.contains(YEAR_7))
			ret = ret.or(BIT_7)
		if (dataString.contains(YEAR_6))
			ret = ret.or(BIT_6)
		if (dataString.contains(YEAR_5))
			ret = ret.or(BIT_5)
		if (dataString.contains(YEAR_4))
			ret = ret.or(BIT_4)
		if (dataString.contains(YEAR_3))
			ret = ret.or(BIT_3)
		if (dataString.contains(YEAR_2))
			ret = ret.or(BIT_2)
		if (dataString.contains(YEAR_1))
			ret = ret.or(BIT_1)
		if (dataString.contains(YEAR_0))
			ret = ret.or(BIT_0)
		return ret
	}

	public fun decodeSCat1(binaryData: Int): String {
		return if (binaryData.and(BIT_0) == BIT_0)
			S_CAT1_1
		else
			(S_CAT1_0)
	}

	public fun decodeSCat2(binaryData: Int): String {

		return if (binaryData.and(BIT_5) == BIT_5)
			(S_CAT2_5)
		else {
			if (binaryData.and(BIT_4) == BIT_4)
				(S_CAT2_4)
			else {
				if (binaryData.and(BIT_3) == BIT_3)
					(S_CAT2_3)
				else {
					if (binaryData.and(BIT_2) == BIT_2)
						(S_CAT2_2)
					else {
						if (binaryData.and(BIT_1) == BIT_1)
							(S_CAT2_1)
						else
							return (S_CAT2_0)
					}
				}
			}
		}
	}


	public fun decodeFCat(binaryData: Int) :String{

		return if (binaryData.and(BIT_5) == BIT_5)
			(F_CAT_5)
		else {
			if (binaryData.and(BIT_4) == BIT_4)
				(F_CAT_4)
			else {
				if (binaryData.and(BIT_3) == BIT_3)
					(F_CAT_3)
				else {
					if (binaryData.and(BIT_2) == BIT_2)
						(F_CAT_2)
					else {
						if (binaryData.and(BIT_1) == BIT_1)
							(F_CAT_1)
						else
							return (F_CAT_0)
					}
				}
			}
		}
	}

	public fun decodeSchoolCat(binaryData: Int) :ArrayList<String>{
		var ret = ArrayList<String>()
		if (binaryData.and(BIT_9) == BIT_9)
			ret.add(SCHOOLCAT_9)
		if (binaryData.and(BIT_8) == BIT_8)
			ret.add(SCHOOLCAT_8)
		if (binaryData.and(BIT_7) == BIT_7)
			ret.add(SCHOOLCAT_7)
		if (binaryData.and(BIT_6) == BIT_6)
			ret.add(SCHOOLCAT_6)
		if (binaryData.and(BIT_5) == BIT_5)
			ret.add(SCHOOLCAT_5)
		if (binaryData.and(BIT_4) == BIT_4)
			ret.add(SCHOOLCAT_4)
		if (binaryData.and(BIT_3) == BIT_3)
			ret.add(SCHOOLCAT_3)
		if (binaryData.and(BIT_2) == BIT_2)
			ret.add(SCHOOLCAT_2)
		if (binaryData.and(BIT_1) == BIT_1)
			ret.add(SCHOOLCAT_1)
		if (binaryData.and(BIT_0) == BIT_0)
			ret.add(SCHOOLCAT_0)
		return ret
	}

	public fun decodeDepartment(binaryData: Int) :ArrayList<String>{
		var ret = ArrayList<String>()
		if (binaryData.and(BIT_8) == BIT_8)
			ret.add(DEPT_8)
		if (binaryData.and(BIT_7) == BIT_7)
			ret.add(DEPT_7)
		if (binaryData.and(BIT_6) == BIT_6)
			ret.add(DEPT_6)
		if (binaryData.and(BIT_5) == BIT_5)
			ret.add(DEPT_5)
		if (binaryData.and(BIT_4) == BIT_4)
			ret.add(DEPT_4)
		if (binaryData.and(BIT_3) == BIT_3)
			ret.add(DEPT_3)
		if (binaryData.and(BIT_2) == BIT_2)
			ret.add(DEPT_2)
		if (binaryData.and(BIT_1) == BIT_1)
			ret.add(DEPT_1)
		if (binaryData.and(BIT_0) == BIT_0)
			ret.add(DEPT_0)
		return ret
	}

	public fun decodeYear(binaryData: Int) :ArrayList<String>{
		val ret = ArrayList<String>()
		if (binaryData.and(BIT_12) == BIT_12)
			ret.add(YEAR_12)
		if (binaryData.and(BIT_11) == BIT_11)
			ret.add(YEAR_11)
		if (binaryData.and(BIT_10) == BIT_10)
			ret.add(YEAR_10)
		if (binaryData.and(BIT_9) == BIT_9)
			ret.add(YEAR_9)
		if (binaryData.and(BIT_8) == BIT_8)
			ret.add(YEAR_8)
		if (binaryData.and(BIT_7) == BIT_7)
			ret.add(YEAR_7)
		if (binaryData.and(BIT_6) == BIT_6)
			ret.add(YEAR_6)
		if (binaryData.and(BIT_5) == BIT_5)
			ret.add(YEAR_5)
		if (binaryData.and(BIT_4) == BIT_4)
			ret.add(YEAR_4)
		if (binaryData.and(BIT_3) == BIT_3)
			ret.add(YEAR_3)
		if (binaryData.and(BIT_2) == BIT_2)
			ret.add(YEAR_2)
		if (binaryData.and(BIT_1) == BIT_1)
			ret.add(YEAR_1)
		if (binaryData.and(BIT_0) == BIT_0)
			ret.add(YEAR_0)
		return ret
	}
}