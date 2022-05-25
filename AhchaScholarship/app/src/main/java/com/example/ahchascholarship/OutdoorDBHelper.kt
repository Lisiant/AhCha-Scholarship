package com.example.ahchascholarship

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

/* =================Table==================== */
//
//-----------outdoorMain----------
// -
// - 0 번호  integer primary-key
// - 1 활동이름
// - 2 운영기관명
// - 3 운영기관종류
// - 4 활동종류
// - 5 활동내용계열
// - 6 신청시작
// - 7 신청마감
// - 8 사이트링크
// - 9 상세

// * (3)운영기관구분
// 6 자리 2진수 숫자로 각 자리 0은 false, 1은 true
// 100,000: 관계부처
// 10,000: 공공기업
// 1,000: 지자체
// 100: 정부기관
// 10: 공공재단
// 1: 민간
//

//* (4)활동종류 구분
//5자리 2진수 숫자로 각 자리에 0은 false, 1은 true
// 10,000: 인턴
// 1,000: 채용
// 100: 공모전
// 10: 봉사
// 1: 동아리


// * (5)활동내용계열 구분
// 9자리 2진수 숫자로 각 자리에 0은 false, 1은 true
//
// 100,000,000: 제한없음
// 10,000,000: 공학계열
// 1,000,000: 교육계열
// 100,000: 사회계열
// 10,000: 예체능계열
// 1,000: 의약계열
// 100: 인문계열
// 10: 자연계열
// 1: 특정학과 -> 제한없음과 같은 지위를 줘야하나?
//

class OutdoorDBHelper (val context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object{
        val DB_NAME = "outdoor.db"
        val DB_VERSION = 1
        val TABLE_NAME_MAIN = "outdoorMain"

        val SNO = "SNO"
        val S_NAME = "활동이름"
        val FOUNDATION = "운영기관명"
        val FOUNDATION_CAT = "FOUNDATIONCAT" // 복호화 함수: ScholarshipDataParser().decodeFCat
        val S_CAT = "활동종류" // 복호화 함수: ScholarshipDataParser().decodeSCat
        val DEPARTMENT = "활동내용계열" // 복호화 함수: ScholarshipDataParser().decodeDepartment
        val DATE_START = "신청시작"
        val DATE_END = "신청마감"
        val SITE_LINK = "사이트링크"
        val DETAIL = "상세"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        val drop_table_main = "drop table ${TABLE_NAME_MAIN};"  //처음 생성시 table 없어서 에러 뜸
        val create_table_main = "create table if not exists ${OutdoorDBHelper.TABLE_NAME_MAIN}("+
                "${SNO} integer primary key," +
                "${S_NAME} text," +
                "${FOUNDATION} text," +
                "${FOUNDATION_CAT} INTEGER," +
                "${S_CAT} INTEGER," +
                "${DEPARTMENT} INTEGER,"+
                "${DATE_START} text," +
                "${DATE_END} text, " +
                "${SITE_LINK} text," +
                "${DETAIL} text);"
        db!!.execSQL(drop_table_main)
        db!!.execSQL(create_table_main)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVirsion: Int) {
        val drop_table = "drop table if exists ${TABLE_NAME_MAIN};"
        db!!.execSQL(drop_table)
        onCreate(db)
    }
    fun insertData(outdoorActivityData:OutdoorActivityData):Boolean{
        val values = ContentValues()
        var flag = false
        val db = writableDatabase
        values.put(SNO, outdoorActivityData.번호)
        values.put(S_NAME, outdoorActivityData.활동이름)
        values.put(FOUNDATION, outdoorActivityData.운영기관명)
        values.put(FOUNDATION_CAT, outdoorActivityData.운영기관종류)
        values.put(S_CAT, outdoorActivityData.활동종류)
        values.put(DEPARTMENT, outdoorActivityData.활동내용계열)
        values.put(DATE_START, outdoorActivityData.신청시작)
        values.put(DATE_END, outdoorActivityData.신청마감)
        values.put(SITE_LINK, outdoorActivityData.사이트링크)
        values.put(DETAIL, outdoorActivityData.상세)
        flag = db.insert(TABLE_NAME_MAIN, "", values)>0
        db.close()
        return flag
    }

    fun deleteScholarship(sno: String): Boolean {
        val strsql = "select * from ${TABLE_NAME_MAIN} where ${SNO} = '$sno'"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        val flag = cursor.count!=0
        if(flag){
            cursor.moveToFirst()
            db.delete(TABLE_NAME_MAIN, "${SNO}=?", arrayOf(sno)) // ? 에 pid값 들을 하나씩 넘겨줌
        }
        cursor.close()
        db.close()
        return flag
    }

    fun findScholarship(name: String): ArrayList<OutdoorActivityData> { // 특정 문자열로 시작하는 제품들 나열
        val strsql = "select * from ${TABLE_NAME_MAIN} where ${S_NAME} like '$name%'"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        val ret = ArrayList<OutdoorActivityData>()
        cursor.moveToFirst()
        while(!cursor.isAfterLast){
                ret.add(
                    OutdoorActivityData(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getInt(4),
                        cursor.getInt(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9)
                    )
                )
            cursor.moveToNext()
        }
        cursor.close()
        db.close()
        return ret
    }


    fun getAllRecord():ArrayList<OutdoorActivityData>{
        val strSql = "select * from ${TABLE_NAME_MAIN}"
        val db = readableDatabase
        val cursor = db.rawQuery(strSql, null)
        val ret = ArrayList<OutdoorActivityData>()
        cursor.moveToFirst()
        while(!cursor.isAfterLast){
            ret.add(
                OutdoorActivityData(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getInt(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getString(9)
                )
            )
            cursor.moveToNext()
        }
        cursor.close()
        db.close()
        return ret
    }

    fun catSelector(fCatBit: Int, sCatBit: Int, departmentBit: Int): ArrayList<OutdoorActivityData> { // 특정 문자열로 시작하는 제품들 나열
        val strsql = "select * from ${TABLE_NAME_MAIN} where ${FOUNDATION_CAT} & $fCatBit==$fCatBit and ${S_CAT} & $sCatBit == $sCatBit and ${DEPARTMENT} & $departmentBit == $departmentBit"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        val ret = ArrayList<OutdoorActivityData>()
        cursor.moveToFirst()
        while(!cursor.isAfterLast){
            ret.add(
                OutdoorActivityData(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getInt(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getString(9)
                )
            )
            cursor.moveToNext()
        }
        cursor.close()
        db.close()
        return ret
    }
}