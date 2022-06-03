package com.example.ahchascholarship

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import com.example.ahchascholarship.databinding.ActivityScholarshipFilterBinding

class ScholarshipFilter : AppCompatActivity() {
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


    lateinit var binding: ActivityScholarshipFilterBinding
    lateinit var db:ScholarshipDBHelper
    lateinit var scholarshipData: ScholarshipData


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScholarshipFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initFilter()
    }

    private fun getSCat1Bit():Int{
        var sCat1bit = 0
        binding.apply {
            if(checkBox11.isChecked){
                sCat1bit = sCat1bit.or(BIT_0)       //학자금
            }
            if(checkBox12.isChecked){
                sCat1bit = sCat1bit.or(BIT_1)       //장학금
            }
        }
        Log.d("scat1bit" , sCat1bit.toString())
        return sCat1bit
    }

    private fun getFCatBit():Int{
        var fCatbit = 0
        binding.apply {
            if(checkBox21.isChecked){
                fCatbit = fCatbit.or(BIT_0)       //한국장학재단
            }
            if(checkBox22.isChecked){
                fCatbit = fCatbit.or(BIT_1)       //지자체
            }
            if(checkBox23.isChecked){
                fCatbit = fCatbit.or(BIT_2)       //민간(기타)
            }
            if(checkBox24.isChecked){
                fCatbit = fCatbit.or(BIT_3)       //민간(기업)
            }
            if(checkBox25.isChecked){
                fCatbit = fCatbit.or(BIT_4)       //대학교
            }
            if(checkBox26.isChecked){
                fCatbit = fCatbit.or(BIT_5)       //관계부처
            }
        }
        Log.d("fCat1bit" , fCatbit.toString())
        return fCatbit
    }

    private fun getSCat2Bit():Int{
        var sCat2bit = 0
        binding.apply {
            if(checkBox31.isChecked){
                sCat2bit = sCat2bit.or(BIT_0)       //특기자
            }
            if(checkBox32.isChecked){
                sCat2bit = sCat2bit.or(BIT_1)       //지역연고
            }
            if(checkBox33.isChecked){
                sCat2bit = sCat2bit.or(BIT_2)       //장애인
            }
            if(checkBox34.isChecked){
                sCat2bit = sCat2bit.or(BIT_3)       //소득구분
            }
            if(checkBox35.isChecked){
                sCat2bit = sCat2bit.or(BIT_4)       //성적우수
            }
            if(checkBox36.isChecked){
                sCat2bit = sCat2bit.or(BIT_5)       //기타
            }
        }
        return sCat2bit
    }

    private fun getSchoolCatBit():Int {
        var schoolCatBit = 0
        binding.apply {
            if (checkBox41.isChecked) {
                schoolCatBit = schoolCatBit.or(BIT_0)
            }
            if (checkBox42.isChecked) {
                schoolCatBit = schoolCatBit.or(BIT_1)
            }
            if (checkBox43.isChecked) {
                schoolCatBit = schoolCatBit.or(BIT_2)
            }
            if (checkBox44.isChecked) {
                schoolCatBit = schoolCatBit.or(BIT_3)
            }
            if (checkBox45.isChecked) {
                schoolCatBit = schoolCatBit.or(BIT_4)
            }
            if (checkBox46.isChecked) {
                schoolCatBit = schoolCatBit.or(BIT_5)
            }
            if (checkBox47.isChecked) {
                schoolCatBit = schoolCatBit.or(BIT_6)
            }
            if (checkBox48.isChecked) {
                schoolCatBit = schoolCatBit.or(BIT_7)
            }
            if (checkBox49.isChecked) {
                schoolCatBit = schoolCatBit.or(BIT_8)
            }
            if (checkBox410.isChecked) {
                schoolCatBit = schoolCatBit.or(BIT_9)
            }
            Log.d("schoolCatbit" , schoolCatBit.toString())
            return schoolCatBit
        }
    }

    private fun getYearBit():Int {
        var yearBit = 0
        binding.apply {
            if (checkBox51.isChecked) {
                yearBit = yearBit.or(BIT_0)
            }
            if (checkBox52.isChecked) {
                yearBit = yearBit.or(BIT_1)
            }
            if (checkBox53.isChecked) {
                yearBit = yearBit.or(BIT_2)
            }
            if (checkBox54.isChecked) {
                yearBit = yearBit.or(BIT_3)
            }
            if (checkBox55.isChecked) {
                yearBit = yearBit.or(BIT_4)
            }
            if (checkBox56.isChecked) {
                yearBit = yearBit.or(BIT_5)
            }
            if (checkBox57.isChecked) {
                yearBit = yearBit.or(BIT_6)
            }
            if (checkBox58.isChecked) {
                yearBit = yearBit.or(BIT_7)
            }
            if (checkBox59.isChecked) {
                yearBit = yearBit.or(BIT_8)
            }
            if (checkBox510.isChecked) {
                yearBit = yearBit.or(BIT_9)
            }
            if (checkBox511.isChecked) {
                yearBit = yearBit.or(BIT_10)
            }
            if (checkBox512.isChecked) {
                yearBit = yearBit.or(BIT_11)
            }
            if (checkBox513.isChecked) {
                yearBit = yearBit.or(BIT_12)
            }
            Log.d("yearBit" , yearBit.toString())
            return yearBit
        }
    }

    private fun getDepartmentBit():Int {
        var departmentBit = 0
        binding.apply {
            if (checkBox61.isChecked) {
                departmentBit = departmentBit.or(BIT_0)
            }
            if (checkBox62.isChecked) {
                departmentBit = departmentBit.or(BIT_1)
            }
            if (checkBox63.isChecked) {
                departmentBit = departmentBit.or(BIT_2)
            }
            if (checkBox64.isChecked) {
                departmentBit = departmentBit.or(BIT_3)
            }
            if (checkBox65.isChecked) {
                departmentBit = departmentBit.or(BIT_4)
            }
            if (checkBox66.isChecked) {
                departmentBit = departmentBit.or(BIT_5)
            }
            if (checkBox67.isChecked) {
                departmentBit = departmentBit.or(BIT_6)
            }
            if (checkBox68.isChecked) {
                departmentBit = departmentBit.or(BIT_7)
            }
            if (checkBox69.isChecked) {
                departmentBit = departmentBit.or(BIT_8)
            }
            Log.d("departmentBit" , departmentBit.toString())
            return departmentBit
        }
    }


    private fun initFilter(){
        var sCat1Bit = 0
        var fCatBit = 0
        var sCat2Bit = 0
        var schoolCatBit = 0
        var yearBit = 0
        var departmentBit = 0

        binding.apply {
            val sCat1Checkid = intArrayOf(checkBox11.id,checkBox12.id)
            for(id in sCat1Checkid){
                val checkBox = findViewById<CheckBox>(id)
                checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                    sCat1Bit = getSCat1Bit()
                }
            }

            val fCatCheckid = intArrayOf(checkBox21.id,checkBox22.id,checkBox23.id,checkBox24.id,checkBox25.id,checkBox26.id)
            for(id in fCatCheckid){
                val checkBox = findViewById<CheckBox>(id)
                checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                    fCatBit = getFCatBit()
                }
            }

            val sCat2Checkid = intArrayOf(checkBox31.id,checkBox32.id,checkBox33.id,checkBox34.id,checkBox35.id,checkBox36.id)
            for(id in sCat2Checkid){
                val checkBox = findViewById<CheckBox>(id)
                checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                    sCat2Bit = getSCat2Bit()
                }
            }

            val schoolCatCheckid = intArrayOf(checkBox41.id,checkBox42.id,checkBox43.id,checkBox44.id,checkBox45.id,checkBox46.id,checkBox47.id,checkBox48.id, checkBox49.id,checkBox410.id)
            for(id in schoolCatCheckid){
                val checkBox = findViewById<CheckBox>(id)
                checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                    schoolCatBit = getSchoolCatBit()
                }
            }

            val yearCheckid = intArrayOf(checkBox51.id,checkBox52.id,checkBox53.id,checkBox54.id,checkBox55.id,checkBox56.id,checkBox57.id,checkBox58.id,
                checkBox59.id,checkBox510.id,checkBox511.id,checkBox512.id,checkBox513.id)
            for(id in yearCheckid){
                val checkBox = findViewById<CheckBox>(id)
                checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                    yearBit = getYearBit()
                }
            }

            val departmentCheckid = intArrayOf(checkBox61.id,checkBox62.id,checkBox63.id,checkBox64.id,checkBox65.id,checkBox66.id,checkBox67.id,checkBox68.id,checkBox69.id)
            for(id in departmentCheckid){
                val checkBox = findViewById<CheckBox>(id)
                checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                    departmentBit = getDepartmentBit()
                }
            }
        }
        db = ScholarshipDBHelper(this)


        // 뒤로가기 버튼(화면에 구현한 보라색 버튼) 누르면 MainActivity로 intent를 보냄.
        // 이 intent에는 bitArray가 들어있음.
        // 핸드폰 기본 뒤로가기 버튼 누르면 반영이 안됨 -> 화면에 구현된 뒤로가기 버튼의 이름을 '저장' 등으로 변경해야할듯
        // 필터에서 다중선택 안되게 하면 좋을텐데 그건 시간상 못할듯

        binding.button.setOnClickListener{
            val bitArray = intArrayOf(fCatBit,sCat1Bit, sCat2Bit, schoolCatBit, yearBit, departmentBit)
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("filtered" , bitArray)
            startActivity(intent)
        }
    }

}

