package com.example.ahchascholarship

import android.icu.text.SimpleDateFormat
import org.apache.commons.lang3.ObjectUtils
import java.lang.Exception
import java.text.ParseException
import java.util.*
import kotlin.collections.ArrayList

class OutdoorDataParser {
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

        val S_CAT_4: String = "인턴"
        val S_CAT_3: String = "채용"
        val S_CAT_2: String = "공모전"
        val S_CAT_1: String = "봉사"
        val S_CAT_0: String = "동아리"

        val F_CAT_5: String = "관계부처"
        val F_CAT_4: String = "공공기업"
        val F_CAT_3: String = "지자체"
        val F_CAT_2: String = "정부기관"
        val F_CAT_1: String = "공공재단"
        val F_CAT_0: String = "민간"

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

    public fun stringToDate(dateString: String): Date? {
        val date:Date
        try {
            date = dateFormat.parse(dateString.trim())
        } catch (e: ParseException) {
            return null
        }
        return date
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

    public fun encodeSCat(dataString: String): Int {
        var ret = 0
        if (dataString.contains(S_CAT_4))
            ret = ret.or(BIT_4)
        if (dataString.contains(S_CAT_3))
            ret = ret.or(BIT_3)
        if (dataString.contains(S_CAT_2))
            ret = ret.or(BIT_2)
        if (dataString.contains(S_CAT_1))
            ret = ret.or(BIT_1)
        if (dataString.contains(S_CAT_0))
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

    public fun decodeSCat(binaryData: Int): String {

        return if (binaryData.and(BIT_4) == BIT_4)
                (S_CAT_4)
            else {
                if (binaryData.and(BIT_3) == BIT_3)
                    (S_CAT_3)
                else {
                    if (binaryData.and(BIT_2) == BIT_2)
                        (S_CAT_2)
                    else {
                        if (binaryData.and(BIT_1) == BIT_1)
                            (S_CAT_1)
                        else
                            return (S_CAT_0)
                    }
                }
            }
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

    fun calculateDateBetween(start:String, end:String):Long
    {
        var ret :Long= 0
        ret = ((dateFormat.parse(end).getTime()/(3600*24*1000) - dateFormat.parse(start).getTime()/(3600*24*1000)))
        return ret
    }
}