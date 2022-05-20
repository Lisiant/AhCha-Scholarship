package com.example.ahchascholarship

import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ahchascholarship.databinding.TempActivityDataTestingBinding
import java.util.*

class Temp_DataTestingActivity : AppCompatActivity() {
	companion object {
		val SELECTALL = 0x00
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
		val DEPT_제한없음 = 0x100
		val DEPT_공학계열 = 0x80
		val DEPT_교육계열 = 0x40
		val DEPT_사회계열= 0x20
		val DEPT_예체능계열=0x10
		val DEPT_의약계열=0x08
		val DEPT_인문계열=0x04
		val DEPT_자연계열=0x02
		val DEPT_특정학과=0x01
		val YEAR_제한없음=0x1000
		val YEAR_대학신입생=0x800
		val YEAR_대학2학기=0x400
		val YEAR_대학3학기=0x200
		val YEAR_대학4학기=0x100
		val YEAR_대학5학기=0x80
		val YEAR_대학6학기=0x40
		val YEAR_대학7힉기=0x20
		val YEAR_대학8학기이상=0x10
		val YEAR_석사신입생=0x08
		val YEAR_석사2학기이상=0x04
		val YEAR_박사과정=0x02
		val YEAR_연령제한=0x01
		val SCHOOLCAT_제한없음=0x200
		val SCHOOLCAT_4년제=0x100
		val SCHOOLCAT_전문대=0x80
		val SCHOOLCAT_특정대학=0x40
		val SCHOOLCAT_해외대학=0x20
		val SCHOOLCAT_원격대학=0x10
		val SCHOOLCAT_학점은행제대학=0x08
		val SCHOOLCAT_기술대학=0x04
		val SCHOOLCAT_전문대학원=0x02
		val SCHOOLCAT_일반대학원=0x01
		val S_CAT1_장학금=0x02
		val S_CAT1_학자금=0x01
		val S_CAT2_기타=0x20
		val S_CAT2_성적우수=0x10
		val S_CAT2_소득구분=0x08
		val S_CAT2_장애인=0x04
		val S_CAT2_지역연고=0x02
		val S_CAT2_특기자=0x01
		val F_CAT_관계부처=0x20
		val F_CAT_대학교=0x10
		val F_CAT_민간기업=0x08
		val F_CAT_민간기타=0x04
		val F_CAT_지자체=0x02
		val F_CAT_한국장학재단=0x01
	}
	lateinit var binding: TempActivityDataTestingBinding
	lateinit var adapter: Temp_DataTestActivityAdapter
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = TempActivityDataTestingBinding.inflate(layoutInflater)
		setContentView(binding.root)
		initLayout()
	}
	private fun initLayout()
	{
		binding.recyclerView.layoutManager =
			LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
		binding.recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
		// ^ recyclerView 의 각 아이템 사이에 구분 선 넣는 것
		//val data = ScholarshipDBHelper(this).getAllRecord()
		var fCatBit:Int = 0
		var sCat1Bit:Int = 0
		var sCat2Bit:Int = 0
		var schoolCatBit:Int = 0
		var yearBit:Int = 0
		var departmentBit:Int = 0
		fCatBit = F_CAT_대학교
		sCat1Bit = SELECTALL
		sCat2Bit = SELECTALL
		schoolCatBit = SELECTALL
		yearBit = YEAR_대학신입생.or(YEAR_대학2학기) // or 연산으로 원하는 값들을 넣을 수 있음.
		departmentBit = DEPT_공학계열.or(DEPT_사회계열)
		val data = ScholarshipDBHelper(this).catSelector(fCatBit,sCat1Bit,sCat2Bit,schoolCatBit,yearBit,departmentBit)
		adapter = Temp_DataTestActivityAdapter(data)
		binding.recyclerView.adapter = adapter
	}
}