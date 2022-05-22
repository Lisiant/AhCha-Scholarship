package com.example.ahchascholarship

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Color
import android.view.Gravity
import android.widget.TableRow
import android.widget.TextView
import androidx.core.database.getStringOrNull
import java.lang.Math.random

/* =================Table==================== */
//
//-----------scholarshipMain----------
// -
// - 0 번호  integer primary-key
// - 1 운영기관명
// - 2 상품명
// - 3 운영기관구분
// - 4 상품구분
// - 5 학자금유형구분
// - 6 대학구분
// - 7 학년구분
// - 8 학과구분
// - 9 성적기준
// - 10 소득기준
// - 11 지원금액
// - 12 특정자격
// - 13 지역거주여부
// - 14 신청시작
// - 15 신청마감
// - 16 선발방법
// - 17 선발인원
// - 18 자격제한
// - 19 추천필요여부
// - 20 제출서류
// - 21 관심여부
//
// * 운영기관구분
// 5 자리 2진수 숫자로 각 자리 0은 false, 1은 true
// 100,000: 관계부처
// 10,000: 대학교
// 1,000: 민간
// 100: 민간
// 10: 지자체
// 1: 한국장학재단
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
//
// * 대학구분
// 10자리 2진수 숫자로 각 자리에 0은 false, 1은 true
// 1,000,000,000: 제한없음
// 100,000,000: 4년제(5~6년제 포함)
// 10,000,000: 전문대(2~3년제)
// 1,000,000: 특정대학 -> 제한없음과 같은 지위를 줘야하나?
// 100,000: 해외대학
// 10,000: 원격대학
// 1,000: 학점은행제 대학
// 100: 기술대학
// 10: 전문대학원
// 1: 일반대학원
//
// * 학년구분
// 13자리 2진수 숫자로 각 자리에 0은 false, 1은 true
// 1,000,000,000,000: 제한없음
// 100,000,000,000: 대학신입생
// 10,000,000,000: 대학2학기
// 1,000,000,000: 대학3학기
// 100,000,000: 대학4학기
// 10,000,000: 대학5학기
// 1,000,000: 대학6학기
// 100,000: 대학7학기
// 10,000: 대학8학기이상
// 1,000: 석사신입생
// 100: 석사2학기이상
// 10: 박사과정
// 1: 연령제한 -> 제한없음과 같은 지위를 줘야하나?
//
//
// * 학과구분
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


class ScholarshipDBHelper (val context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
	companion object{ // 변수를 스테틱으로 만들어버려
		val DB_NAME = "scholarship.db"
		val DB_VERSION = 1
		val TABLE_NAME_MAIN = "scholarshipMain"

		val SNO = "SNO"
		val FOUNDATION = "운영기관명"
		val S_NAME = "상품명"
		val FOUNDATION_CAT = "FOUNDATIONCAT" // 복호화 함수: ScholarshipDataParser().decodeFCat
		val S_CAT = "상품구분" // 복호화 함수: ScholarshipDataParser().decodeSCat1
		val S_CAT2 = "학자금유형구분" // 복호화 함수: ScholarshipDataParser().decodeSCat2
		val SCHOOL_CAT = "대학구분" // 복호화 함수: ScholarshipDataParser().decodeSchoolCat
		val YEAR = "학년구분" // 복호화 함수: ScholarshipDataParser().decodeYear
		val DEPARTMENT = "학과구분" // 복호화 함수: ScholarshipDataParser().decodeDepartment
		val GRADE_CUT = "성적기준"
		val INCOME_CUT = "소득기준"
		val AMOUNT = "지원금액"
		val SPEC = "특정자격"
		val REGION = "지역거주여부"
		val DATE_START = "신청시작"
		val DATE_END = "신청마감"
		val SELECT_METHOD = "선발방법"
		val NUMBER = "선발인원"
		val SPEC_RESTRICT = "자격제한"
		val RECOMMEND = "추천필요여부"
		val PAPERWORK = "제출서류"
		val FAVORITE = "관심등록"
		val ALARMCHECK = "알람체크"
	}
	fun getAllRecord():ArrayList<ScholarshipData>{
		val strSql = "select * from $TABLE_NAME_MAIN"
		val db = readableDatabase
		val cursor = db.rawQuery(strSql, null)
		val ret = ArrayList<ScholarshipData>()
		cursor.moveToFirst()
		while(!cursor.isAfterLast){
			ret.add(
				ScholarshipData(
					cursor.getInt(0),
					cursor.getString(1),
					cursor.getString(2),
					cursor.getInt(3),
					cursor.getInt(4),
					cursor.getInt(5),
					cursor.getInt(6),
					cursor.getInt(7),
					cursor.getInt(8),
					cursor.getString(9),
					cursor.getString(10),
					cursor.getString(11),
					cursor.getString(12),
					cursor.getString(13),
					cursor.getString(14),
					cursor.getString(15),
					cursor.getString(16),
					cursor.getString(17),
					cursor.getString(18),
					cursor.getString(19),
					cursor.getString(20),
					cursor.getInt(21) == 1,
					cursor.getInt(22) == 1
				)
			)
			cursor.moveToNext()
		}
		cursor.close()
		db.close()
		return ret
	}
	fun insertData(scholarshipData: ScholarshipData):Boolean{
		val values = ContentValues()
		var flag = false
		val db = writableDatabase
		values.put(SNO, scholarshipData.번호)
		values.put(FOUNDATION, scholarshipData.운영기관명)
		values.put(S_NAME, scholarshipData.상품명)
		values.put(FOUNDATION_CAT, scholarshipData.운영기관구분)
		values.put(S_CAT, scholarshipData.상품구분)
		values.put(S_CAT2, scholarshipData.학자금유형구분)
		values.put(SCHOOL_CAT, scholarshipData.대학구분)
		values.put(YEAR, scholarshipData.학년구분)
		values.put(DEPARTMENT, scholarshipData.학과구분)
		values.put(GRADE_CUT, scholarshipData.성적기준)
		values.put(INCOME_CUT, scholarshipData.소득기준)
		values.put(AMOUNT, scholarshipData.지원금액)
		values.put(SPEC, scholarshipData.특정자격)
		values.put(REGION, scholarshipData.지역거주여부)
		values.put(DATE_START, scholarshipData.신청시작)
		values.put(DATE_END, scholarshipData.신청마감)
		values.put(SELECT_METHOD, scholarshipData.선발방법)
		values.put(NUMBER, scholarshipData.선발인원)
		values.put(SPEC_RESTRICT, scholarshipData.자격제한)
		values.put(RECOMMEND, scholarshipData.추천필요여부)
		values.put(PAPERWORK, scholarshipData.제출서류)
		if(scholarshipData.favorite)
			values.put(FAVORITE, 1)
		else
			values.put(FAVORITE, 0)
		if(scholarshipData.alarmCheck)
			values.put(ALARMCHECK, 1)
		else
			values.put(ALARMCHECK, 0)
		flag = db.insert(TABLE_NAME_MAIN, "", values)>0
		db.close()
		return flag
	}

	fun insertAll(scholarshipDataList: ArrayList<ScholarshipData>):Boolean{
		val values = ContentValues()
		val db = writableDatabase
		var flag :Boolean = false
		for(scholarshipData in scholarshipDataList) {
			values.put(SNO, scholarshipData.번호)
			values.put(FOUNDATION, scholarshipData.운영기관명)
			values.put(S_NAME, scholarshipData.상품명)
			values.put(FOUNDATION_CAT, scholarshipData.운영기관구분)
			values.put(S_CAT, scholarshipData.상품구분)
			values.put(S_CAT2, scholarshipData.학자금유형구분)
			values.put(SCHOOL_CAT, scholarshipData.대학구분)
			values.put(YEAR, scholarshipData.학년구분)
			values.put(DEPARTMENT, scholarshipData.학과구분)
			values.put(GRADE_CUT, scholarshipData.성적기준)
			values.put(INCOME_CUT, scholarshipData.소득기준)
			values.put(AMOUNT, scholarshipData.지원금액)
			values.put(SPEC, scholarshipData.특정자격)
			values.put(REGION, scholarshipData.지역거주여부)
			values.put(DATE_START, scholarshipData.신청시작)
			values.put(DATE_END, scholarshipData.신청마감)
			values.put(SELECT_METHOD, scholarshipData.선발방법)
			values.put(NUMBER, scholarshipData.선발인원)
			values.put(SPEC_RESTRICT, scholarshipData.자격제한)
			values.put(RECOMMEND, scholarshipData.추천필요여부)
			values.put(PAPERWORK, scholarshipData.제출서류)
			if(scholarshipData.favorite)
				values.put(FAVORITE, 1)
			else
				values.put(FAVORITE, 0)
			if(scholarshipData.alarmCheck)
				values.put(ALARMCHECK, 1)
			else
				values.put(ALARMCHECK, 0)
			flag = db.insert(TABLE_NAME_MAIN, "", values)>0
		}
		db.close()
		return flag
	}
	override fun onCreate(db: SQLiteDatabase?) {
		val create_table_main = "create table if not exists $TABLE_NAME_MAIN("+
				"$SNO integer primary key," +
				"$FOUNDATION text," +
				"$S_NAME text," +
				"$FOUNDATION_CAT INTEGER," +
				"$S_CAT INTEGER," +
				"$S_CAT2 INTEGER," +
				"$SCHOOL_CAT INTEGER," +
				"$YEAR INTEGER,"+
				"$DEPARTMENT INTEGER,"+
				"$GRADE_CUT text," +
				"$INCOME_CUT text," +
				"$AMOUNT text," +
				"$SPEC text," +
				"$REGION text," +
				"$DATE_START text," +
				"$DATE_END text, " +
				"$SELECT_METHOD text," +
				"$NUMBER text," +
				"$SPEC_RESTRICT text, " +
				"$RECOMMEND text, " +
				"$PAPERWORK text," +
				"$FAVORITE integer default 0," +
				"$ALARMCHECK integer default 0);"
		db!!.execSQL(create_table_main)
	}

	override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVirsion: Int) {
		val drop_table = "drop table if exists $TABLE_NAME_MAIN;"
		db!!.execSQL(drop_table)
		onCreate(db)
	}

	fun deleteScholarship(sno: String): Boolean {
		val strsql = "select * from $TABLE_NAME_MAIN where $SNO = '$sno'"
		val db = readableDatabase
		val cursor = db.rawQuery(strsql, null)
		val flag = cursor.count!=0
		if(flag){
			cursor.moveToFirst()
			db.delete(TABLE_NAME_MAIN, "$SNO=?", arrayOf(sno)) // ? 에 pid값 들을 하나씩 넘겨줌
		}
		cursor.close()
		db.close()
		return flag
	}

	fun findScholarship(name: String): ArrayList<ScholarshipData> { // 특정 문자열로 시작하는 제품들 나열
		val strsql = "select * from $TABLE_NAME_MAIN where $S_NAME like '$name%'"
		val db = readableDatabase
		val cursor = db.rawQuery(strsql, null)
		val ret = ArrayList<ScholarshipData>()
		cursor.moveToFirst()
		while(!cursor.isAfterLast){
			ret.add(
				ScholarshipData(
					cursor.getInt(0),
					cursor.getString(1),
					cursor.getString(2),
					cursor.getInt(3),
					cursor.getInt(4),
					cursor.getInt(5),
					cursor.getInt(6),
					cursor.getInt(7),
					cursor.getInt(8),
					cursor.getString(9),
					cursor.getString(10),
					cursor.getString(11),
					cursor.getString(12),
					cursor.getString(13),
					cursor.getString(14),
					cursor.getString(15),
					cursor.getString(16),
					cursor.getString(17),
					cursor.getString(18),
					cursor.getString(19),
					cursor.getString(20),
					cursor.getInt(21) == 1,
					cursor.getInt(22) == 1
				)
			)
			cursor.moveToNext()
		}
		cursor.close()
		db.close()
		return ret
	}
	fun catSelector(fCatBit: Int, sCat1Bit: Int, sCat2Bit: Int, schoolCatBit: Int, yearBit: Int, departmentBit: Int): ArrayList<ScholarshipData> { // 특정 문자열로 시작하는 제품들 나열
		val strsql = "select * from $TABLE_NAME_MAIN where $FOUNDATION_CAT & $fCatBit==$fCatBit and $S_CAT & $sCat1Bit == $sCat1Bit and $S_CAT2 & $sCat2Bit == $sCat2Bit and $SCHOOL_CAT & $schoolCatBit == $schoolCatBit and $YEAR & $yearBit == $yearBit and $DEPARTMENT & $departmentBit == $departmentBit"
		val db = readableDatabase
		val cursor = db.rawQuery(strsql, null)
		val ret = ArrayList<ScholarshipData>()
		cursor.moveToFirst()
		while(!cursor.isAfterLast){
			ret.add(
				ScholarshipData(
					cursor.getInt(0),
					cursor.getString(1),
					cursor.getString(2),
					cursor.getInt(3),
					cursor.getInt(4),
					cursor.getInt(5),
					cursor.getInt(6),
					cursor.getInt(7),
					cursor.getInt(8),
					cursor.getString(9),
					cursor.getString(10),
					cursor.getString(11),
					cursor.getString(12),
					cursor.getString(13),
					cursor.getString(14),
					cursor.getString(15),
					cursor.getString(16),
					cursor.getString(17),
					cursor.getString(18),
					cursor.getString(19),
					cursor.getString(20),
					cursor.getInt(21) == 1,
					cursor.getInt(22) == 1
				)
			)
			cursor.moveToNext()
		}
		cursor.close()
		db.close()
		return ret
	}
	fun setFavorite(num: Int){
		var strSql = "select * from $TABLE_NAME_MAIN"
		var db = readableDatabase
		var cursor = db.rawQuery(strSql, null)
		val snos = ArrayList<Int>()
		var count = -1
		cursor.moveToFirst()
		while(!cursor.isAfterLast){
			snos.add(cursor.getInt(0))
			cursor.moveToNext()
		}
		cursor.close()
		db.close()
		db = writableDatabase
		strSql = "select * from $TABLE_NAME_MAIN"
		cursor = db.rawQuery(strSql, null)
		cursor.moveToFirst()
		val flag = cursor.count!=0
		while(++count<num) {
			val changeSNO = snos.random()
			if (flag) {
				val values = ContentValues()
				values.put(FAVORITE, 1)
				db.update(
					TABLE_NAME_MAIN,
					values,
					"$SNO=?",
					arrayOf(changeSNO.toString())
				)
			}
		}
		cursor.close()
		db.close()
	}

	fun resetFavorite() {
		val db = writableDatabase
		val strSql = "update $TABLE_NAME_MAIN set $FAVORITE = 0;"
		val cursor = db.execSQL(strSql)
		db.close()
	}
	fun getFavoriteRecord():ArrayList<ScholarshipData>{
		val strSql = "select * from $TABLE_NAME_MAIN where $FAVORITE = 1"
		val db = readableDatabase
		val cursor = db.rawQuery(strSql, null)
		val ret = ArrayList<ScholarshipData>()
		cursor.moveToFirst()
		while(!cursor.isAfterLast){
			ret.add(
				ScholarshipData(
					cursor.getInt(0),
					cursor.getString(1),
					cursor.getString(2),
					cursor.getInt(3),
					cursor.getInt(4),
					cursor.getInt(5),
					cursor.getInt(6),
					cursor.getInt(7),
					cursor.getInt(8),
					cursor.getString(9),
					cursor.getString(10),
					cursor.getString(11),
					cursor.getString(12),
					cursor.getString(13),
					cursor.getString(14),
					cursor.getString(15),
					cursor.getString(16),
					cursor.getString(17),
					cursor.getString(18),
					cursor.getString(19),
					cursor.getString(20),
					cursor.getInt(21) == 1,
					cursor.getInt(22) == 1
				)
			)
			cursor.moveToNext()
		}
		cursor.close()
		db.close()
		return ret
	}

	fun updateFavorite(data : ScholarshipData):Boolean{
		val sno = data.번호.toString()
		val selected = data.favorite
		val db = readableDatabase
		val strSql = "select * from $TABLE_NAME_MAIN where ${ScholarshipDBHelper.SNO} = $sno"
		val cursor = db.rawQuery(strSql,null)
		val flag =  cursor.count!=0
		if(flag){
			cursor.moveToFirst()
			val values = ContentValues()
			values.put(FAVORITE,!selected)
			db.update(TABLE_NAME_MAIN,values,"${ScholarshipDBHelper.SNO}=?", arrayOf(sno))
		}
		cursor.close()
		db.close()
		return flag
	}
//	private fun showRecord(cursor: Cursor){
//		cursor.moveToFirst()
//		val attrcount = cursor.columnCount
//		val activity = context as Temp_DataTestingActivity // 실행할 때 mainActivity의 context 정보를 넘겨 받음. 그걸로 tableLayout 접근
//		activity.binding.recyclerView.removeAllViewsInLayout() // 테이블 레이아웃의 모든 뷰를 지우고 타이틀부터 만들기
//		val tablerow = TableRow(activity)
//		val rowParam = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT)
//		tablerow.layoutParams = rowParam
//		val viewParam = TableRow.LayoutParams(0, 100, 1f)
//		for(i in 0 until attrcount){
//			val textView = TextView(activity)
//			textView.layoutParams = viewParam
//			textView.text = cursor.getColumnName(i)
//			textView.setBackgroundColor(Color.LTGRAY)
//			textView.textSize = 15.0f
//			textView.gravity = Gravity.CENTER
//			tablerow.addView(textView)
//		}
//		activity.binding.tableLayout.addView(tablerow)
//		if(cursor.count ==0) return
//
//		// 레코드 추가하기
//		do{
//			val row = TableRow(activity)
//			row.layoutParams = rowParam
//			row.setOnClickListener {
//				for(i in 0 until attrcount){
//					val textView = row.getChildAt(i) as TextView
//					when(textView.tag){
//						0->activity.binding.pIdEdit.setText(textView.text)
//						1->activity.binding.pNamedEdit.setText(textView.text)
//						2->activity.binding.pQuantityEdit.setText(textView.text)
//					}
//				}
//			}
//			for(i in 0 until attrcount){
//				val textView = TextView(activity)
//				textView.tag = i 			// id 없이 식별하기 위해 (바로 위 클릭 리스너 getChildAt 에서 사용함)
//				textView.layoutParams = viewParam
//				print(cursor.columnCount)
//				textView.text = cursor.getString(i) // 첫번째 데이터 가져와
//				textView.textSize = 13.0f
//				textView.gravity = Gravity.CENTER
//				row.addView(textView)
//			}
//			activity.binding.tableLayout.addView(row)
//		}while(cursor.moveToNext())
//	}

//
//	fun updateProduct(product: Product): Boolean {
//		val pid = product.pId
//		val strsql = "select * from $TABLE_NAME where $PID = '$pid'"
//		val db = writableDatabase
//		val cursor = db.rawQuery(strsql, null)
//		val flag = cursor.count!=0
//		if(flag){
//			cursor.moveToFirst()
//			val values = ContentValues()
//			values.put(PNAME, product.pName)
//			values.put(PQUANTITY, product.pQuantity)
//			db.update(TABLE_NAME, values, "$PID=?",arrayOf(pid.toString())) // ? 에 pid값 들을 하나씩 넘겨줌
//		}
//		cursor.close()
//		db.close()
//		return flag
//	}

}