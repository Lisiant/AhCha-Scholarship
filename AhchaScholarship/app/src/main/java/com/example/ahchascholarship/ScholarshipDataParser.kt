package com.example.ahchascholarship

import android.icu.text.SimpleDateFormat
import java.lang.Exception
import java.text.ParseException
import java.util.*
import kotlin.collections.ArrayList

class ScholarshipDataParser {
	companion object {
		val DEPT_4 = "관계부처"
		val DEPT_3 = "대학교"
		val DEPT_2 = "민간"
		val DEPT_1 = "지자체"
		val DEPT_0 = "한국장학재단"
		val YEAR_12 = "제한없음"
		val YEAR_11 = "대학신입생"
		val YEAR_10 = "대학2학기"
		val YEAR_9 = "대학3학기"
		val YEAR_8 = "대학4학기"
		val YEAR_7 = "대학5학기"
		val YEAR_6 = "대학6학기"
		val YEAR_5 = "대학7힉기"
		val YEAR_4 = "대학8학기이상"
		val YEAR_3 = "석사신입생"
		val YEAR_2 = "석사2학기이상"
		val YEAR_1 = "박사과정"
		val YEAR_0 = "연령제한"
		val SCHOOLCAT_9 = "제한없음"
		val SCHOOLCAT_8 = "4년제(5~6년제포함)"
		val SCHOOLCAT_7 = "전문대(2~3년제)"
		val SCHOOLCAT_6 = "특정대학"
		val SCHOOLCAT_5 = "해외대학"
		val SCHOOLCAT_4 = "원격대학"
		val SCHOOLCAT_3 = "학점은행제 대학"
		val SCHOOLCAT_2 = "기술대학"
		val SCHOOLCAT_1 = "전문대학원"
		val SCHOOLCAT_0 = "일반대학원"
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

	val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
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
		if (dataString.contains(DEPT_0))
			ret = ret.or(BIT_0)
		if (dataString.contains(DEPT_1))
			ret = ret.or(BIT_1)
		if (dataString.contains(DEPT_2))
			ret = ret.or(BIT_2)
		if (dataString.contains(DEPT_3))
			ret = ret.or(BIT_3)
		if (dataString.contains(DEPT_4))
			ret = ret.or(BIT_4)
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
		return if (binaryData.and(BIT_0) == 1)
			S_CAT1_1
		else
			(S_CAT1_0)
	}

	public fun decodeSCat2(binaryData: Int): String {

		return if (binaryData.and(BIT_5) == 1)
			(S_CAT2_5)
		else {
			if (binaryData.and(BIT_4) == 1)
				(S_CAT2_4)
			else {
				if (binaryData.and(BIT_3) == 1)
					(S_CAT2_3)
				else {
					if (binaryData.and(BIT_2) == 1)
						(S_CAT2_2)
					else {
						if (binaryData.and(BIT_1) == 1)
							(S_CAT2_1)
						else
							return (S_CAT2_0)
					}
				}
			}
		}
	}


	public fun decodeFCat(binaryData: Int) :String{

		return if (binaryData.and(BIT_5) == 1)
			(F_CAT_5)
		else {
			if (binaryData.and(BIT_4) == 1)
				(F_CAT_4)
			else {
				if (binaryData.and(BIT_3) == 1)
					(F_CAT_3)
				else {
					if (binaryData.and(BIT_2) == 1)
						(F_CAT_2)
					else {
						if (binaryData.and(BIT_1) == 1)
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
		if (binaryData.and(BIT_9) == 1)
			ret.add(SCHOOLCAT_9)
		if (binaryData.and(BIT_8) == 1)
			ret.add(SCHOOLCAT_8)
		if (binaryData.and(BIT_7) == 1)
			ret.add(SCHOOLCAT_7)
		if (binaryData.and(BIT_6) == 1)
			ret.add(SCHOOLCAT_6)
		if (binaryData.and(BIT_5) == 1)
			ret.add(SCHOOLCAT_5)
		if (binaryData.and(BIT_4) == 1)
			ret.add(SCHOOLCAT_4)
		if (binaryData.and(BIT_3) == 1)
			ret.add(SCHOOLCAT_3)
		if (binaryData.and(BIT_2) == 1)
			ret.add(SCHOOLCAT_2)
		if (binaryData.and(BIT_1) == 1)
			ret.add(SCHOOLCAT_1)
		if (binaryData.and(BIT_0) == 1)
			ret.add(SCHOOLCAT_0)
		return ret
	}

	public fun decodeDepartment(binaryData: Int) :ArrayList<String>{
		var ret = ArrayList<String>()
		if (binaryData.and(BIT_4) == 1)
			ret.add(DEPT_4)
		if (binaryData.and(BIT_3) == 1)
			ret.add(DEPT_3)
		if (binaryData.and(BIT_2) == 1)
			ret.add(DEPT_2)
		if (binaryData.and(BIT_1) == 1)
			ret.add(DEPT_1)
		if (binaryData.and(BIT_0) == 1)
			ret.add(DEPT_0)
		return ret
	}

	public fun decodeYear(binaryData: Int) :ArrayList<String>{
		var ret = ArrayList<String>()
		if (binaryData.and(BIT_12) == 1)
			ret.add(YEAR_12)
		if (binaryData.and(BIT_11) == 1)
			ret.add(YEAR_11)
		if (binaryData.and(BIT_10) == 1)
			ret.add(YEAR_10)
		if (binaryData.and(BIT_9) == 1)
			ret.add(YEAR_9)
		if (binaryData.and(BIT_8) == 1)
			ret.add(YEAR_8)
		if (binaryData.and(BIT_7) == 1)
			ret.add(YEAR_7)
		if (binaryData.and(BIT_6) == 1)
			ret.add(YEAR_6)
		if (binaryData.and(BIT_5) == 1)
			ret.add(YEAR_5)
		if (binaryData.and(BIT_4) == 1)
			ret.add(YEAR_4)
		if (binaryData.and(BIT_3) == 1)
			ret.add(YEAR_3)
		if (binaryData.and(BIT_2) == 1)
			ret.add(YEAR_2)
		if (binaryData.and(BIT_1) == 1)
			ret.add(YEAR_1)
		if (binaryData.and(BIT_0) == 1)
			ret.add(YEAR_0)
		return ret
	}
}
//
// * 운영기관구분
// 5 자리 2진수 숫자로 각 자리 0은 false, 1은 true
// 10,000: 관계부처
// 1,000: 대학교
// 100: 민간
// 10: 지자체
// 1: 한국장학제단
//
// * 상품구분
// 1자리 2진수 숫자
// 1: 장학금
// 0: 학자금
//
// * 학자금유형구분
// 6자리 2진수 숫자
// 100,000: 기타
// 10,000: 성적우수
// 1,000: 소득구분
// 100: 장애인
// 10: 지역연고
// 1: 특기자