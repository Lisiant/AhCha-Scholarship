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

class ScholarshipDBHelper (val context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
	companion object{ // 변수를 스테틱으로 만들어버려
		val DB_NAME = "scholarship.db"
		val DB_VERSION = 1
		val TABLE_NAME = "products"
		val SNO = "번호"
		val FOUNDATION = "운영기관명"
		val S_NAME = "상품명"
		val FOUNDATION_CAT = "운영기관구분"
		val S_CAT = "상품구분"
		val S_CAT2 = "학자금유형구분"
		val SCHOOL_CAT = "대학구분"
		val GRADE = "학년구분"
		val DEPARTMENT = "학과구분"
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
	}
	fun getAllRecord(){
		val strSql = "select * from $TABLE_NAME"
		val db = readableDatabase
		val cursor = db.rawQuery(strSql, null)
		val tupleCount = cursor.columnCount
		for(i in 0 until tupleCount){

		}
		cursor.close()
		db.close()
	}
	private fun showRecord(cursor: Cursor){
		cursor.moveToFirst()
		val attrcount = cursor.columnCount
		val activity = context as MainActivity // 실행할 때 mainActivity의 context 정보를 넘겨 받음. 그걸로 tableLayout 접근
		activity.binding.tableLayout.removeAllViewsInLayout() // 테이블 레이아웃의 모든 뷰를 지우고 타이틀부터 만들기
		val tablerow = TableRow(activity)
		val rowParam = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT)
		tablerow.layoutParams = rowParam
		val viewParam = TableRow.LayoutParams(0, 100, 1f)
		for(i in 0 until attrcount){
			val textView = TextView(activity)
			textView.layoutParams = viewParam
			textView.text = cursor.getColumnName(i)
			textView.setBackgroundColor(Color.LTGRAY)
			textView.textSize = 15.0f
			textView.gravity = Gravity.CENTER
			tablerow.addView(textView)
		}
		activity.binding.tableLayout.addView(tablerow)
		if(cursor.count ==0) return

		// 레코드 추가하기
		do{
			val row = TableRow(activity)
			row.layoutParams = rowParam
			row.setOnClickListener {
				for(i in 0 until attrcount){
					val textView = row.getChildAt(i) as TextView
					when(textView.tag){
						0->activity.binding.pIdEdit.setText(textView.text)
						1->activity.binding.pNamedEdit.setText(textView.text)
						2->activity.binding.pQuantityEdit.setText(textView.text)
					}
				}
			}
			for(i in 0 until attrcount){
				val textView = TextView(activity)
				textView.tag = i 			// id 없이 식별하기 위해 (바로 위 클릭 리스너 getChildAt 에서 사용함)
				textView.layoutParams = viewParam
				print(cursor.columnCount)
				textView.text = cursor.getString(i) // 첫번째 데이터 가져와
				textView.textSize = 13.0f
				textView.gravity = Gravity.CENTER
				row.addView(textView)
			}
			activity.binding.tableLayout.addView(row)
		}while(cursor.moveToNext())
	}
	fun insertData(scholarshipData: ScholarshipData):Boolean{
		val values = ContentValues()
		values.put(SNO, scholarshipData.번호)
		values.put(FOUNDATION, scholarshipData.운영기관명)
		values.put(S_NAME, scholarshipData.상품명)
		values.put(FOUNDATION_CAT, scholarshipData.운영기관구분)
		values.put(S_CAT, scholarshipData.상품구분)
		values.put(S_CAT2, scholarshipData.학자금유형구분)
		values.put(SCHOOL_CAT, scholarshipData.대학구분)
		values.put(GRADE, scholarshipData.학년구분)
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
		val db = writableDatabase
		val flag = db.insert(TABLE_NAME, null, values)>0
		db.close()
		return flag
	}
	override fun onCreate(db: SQLiteDatabase?) {
		val create_table = "create table if not exists $TABLE_NAME("+
				"$SNO integer primary key," +
				"$FOUNDATION text," +
				"$S_NAME text," +
				"$FOUNDATION_CAT text," +
				"$S_CAT text," +
				"$S_CAT2 text," +
				"$SCHOOL_CAT text," +
				"$GRADE text," +
				"$DEPARTMENT text," +
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
				"$PAPERWORK text);"
		db!!.execSQL(create_table)
	}

	override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVirsion: Int) {
		val drop_table = "drop table if exists $TABLE_NAME;"
		db!!.execSQL(drop_table)
		onCreate(db)
	}

	fun findProduct(name: String): Boolean {
		val strsql = "select * from $TABLE_NAME where $PNAME='$name'"
		val db = readableDatabase
		val cursor = db.rawQuery(strsql, null)
		val flag = cursor.count!=0
		showRecord(cursor)
		cursor.close()
		db.close()
		return flag
	}

	fun deleteProduct(pid: String): Boolean {
		val strsql = "select * from $TABLE_NAME where $PID = '$pid'"
		val db = readableDatabase
		val cursor = db.rawQuery(strsql, null)
		val flag = cursor.count!=0
		if(flag){
			cursor.moveToFirst()
			db.delete(TABLE_NAME, "$PID=?", arrayOf(pid)) // ? 에 pid값 들을 하나씩 넘겨줌
		}
		cursor.close()
		db.close()
		return flag
	}

	fun updateProduct(product: Product): Boolean {
		val pid = product.pId
		val strsql = "select * from $TABLE_NAME where $PID = '$pid'"
		val db = writableDatabase
		val cursor = db.rawQuery(strsql, null)
		val flag = cursor.count!=0
		if(flag){
			cursor.moveToFirst()
			val values = ContentValues()
			values.put(PNAME, product.pName)
			values.put(PQUANTITY, product.pQuantity)
			db.update(TABLE_NAME, values, "$PID=?",arrayOf(pid.toString())) // ? 에 pid값 들을 하나씩 넘겨줌
		}
		cursor.close()
		db.close()
		return flag
	}


	fun findProduct2(name: String): Boolean { // 특정 문자열로 시작하는 제품들 나열
		val strsql = "select * from $TABLE_NAME where $PNAME like '$name%'"
		val db = readableDatabase
		val cursor = db.rawQuery(strsql, null)
		val flag = cursor.count!=0
		showRecord(cursor)
		cursor.close()
		db.close()
		return flag
	}
}