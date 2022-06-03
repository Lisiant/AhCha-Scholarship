package com.example.ahchascholarship

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.RadioButton
import com.example.ahchascholarship.databinding.ActivityOutdoorFilterBinding

class OutdoorFilter : AppCompatActivity() {
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


    lateinit var binding: ActivityOutdoorFilterBinding
    lateinit var db: OutdoorDBHelper
    lateinit var scholarshipData: OutdoorActivityData


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOutdoorFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initFilter()
    }

    private fun getSCatBit(): Int {
        var sCatbit = 0
        binding.apply {
            if (outdoorRadioButton11.isChecked) {
                sCatbit = sCatbit.or(BIT_0)       //동아리
            }
            if (outdoorRadioButton12.isChecked) {
                sCatbit = sCatbit.or(BIT_1)       //봉사
            }
            if (outdoorRadioButton13.isChecked) {
                sCatbit = sCatbit.or(BIT_2)       //공모전
            }
            if (outdoorRadioButton14.isChecked) {
                sCatbit = sCatbit.or(BIT_3)       //인턴
            }
            if (outdoorRadioButton15.isChecked) {
                sCatbit = sCatbit.or(BIT_4)       //채용
            }
        }
        Log.d("scatbit", sCatbit.toString())
        return sCatbit
    }

    private fun getFCatBit(): Int {
        var fCatbit = 0
        binding.apply {
            if (outdoorRadioButton21.isChecked) {
                fCatbit = fCatbit.or(BIT_0)       //민간
            }
            if (outdoorRadioButton22.isChecked) {
                fCatbit = fCatbit.or(BIT_1)       //공공재단
            }
            if (outdoorRadioButton23.isChecked) {
                fCatbit = fCatbit.or(BIT_2)       //정부재단
            }
            if (outdoorRadioButton24.isChecked) {
                fCatbit = fCatbit.or(BIT_3)       //지자체
            }
            if (outdoorRadioButton25.isChecked) {
                fCatbit = fCatbit.or(BIT_4)       //공공기업
            }
            if (outdoorRadioButton26.isChecked) {
                fCatbit = fCatbit.or(BIT_5)       //관계부처
            }
        }
        Log.d("fCatbit", fCatbit.toString())
        return fCatbit
    }

    private fun getDepartmentBit(): Int {
        var departmentBit = 0
        binding.apply {
            if (outdoorCheckBox31.isChecked) {
                departmentBit = departmentBit.or(BIT_0)
            }
            if (outdoorCheckBox32.isChecked) {
                departmentBit = departmentBit.or(BIT_1)
            }
            if (outdoorCheckBox33.isChecked) {
                departmentBit = departmentBit.or(BIT_2)
            }
            if (outdoorCheckBox34.isChecked) {
                departmentBit = departmentBit.or(BIT_3)
            }
            if (outdoorCheckBox35.isChecked) {
                departmentBit = departmentBit.or(BIT_4)
            }
            if (outdoorCheckBox36.isChecked) {
                departmentBit = departmentBit.or(BIT_5)
            }
            if (outdoorCheckBox37.isChecked) {
                departmentBit = departmentBit.or(BIT_6)
            }
            if (outdoorCheckBox38.isChecked) {
                departmentBit = departmentBit.or(BIT_7)
            }
            if (outdoorCheckBox39.isChecked) {
                departmentBit = departmentBit.or(BIT_8)
            }
            Log.d("departmentBit", departmentBit.toString())
            return departmentBit
        }
    }

    private fun initFilter() {
        var sCatBit = 0
        var fCatBit = 0
        var departmentBit = 0

        binding.apply {
            val sCatCheckid = intArrayOf(
                outdoorRadioButton11.id, outdoorRadioButton12.id, outdoorRadioButton13.id,
                outdoorRadioButton14.id, outdoorRadioButton15.id
            )
            for (id in sCatCheckid) {
                val radioButton = findViewById<RadioButton>(id)
                radioButton.setOnClickListener {
                    outdoorRadioButton11.setChecked(false)
                    outdoorRadioButton12.setChecked(false)
                    outdoorRadioButton13.setChecked(false)
                    outdoorRadioButton14.setChecked(false)
                    outdoorRadioButton15.setChecked(false)
                    radioButton.setChecked(true)
                    sCatBit = getSCatBit()
                }
            }

            val fCatCheckid = intArrayOf(
                outdoorRadioButton21.id, outdoorRadioButton22.id, outdoorRadioButton23.id,
                outdoorRadioButton24.id, outdoorRadioButton25.id, outdoorRadioButton26.id
            )
            for (id in fCatCheckid) {
                val radioButton = findViewById<RadioButton>(id)
                radioButton.setOnClickListener {
                    outdoorRadioButton21.setChecked(false)
                    outdoorRadioButton22.setChecked(false)
                    outdoorRadioButton23.setChecked(false)
                    outdoorRadioButton24.setChecked(false)
                    outdoorRadioButton25.setChecked(false)
                    outdoorRadioButton26.setChecked(false)
                    radioButton.setChecked(true)
                    fCatBit = getFCatBit()
                }
            }

            val departmentCheckid = intArrayOf(
                outdoorCheckBox31.id,
                outdoorCheckBox32.id,
                outdoorCheckBox33.id,
                outdoorCheckBox34.id,
                outdoorCheckBox35.id,
                outdoorCheckBox36.id,
                outdoorCheckBox37.id,
                outdoorCheckBox38.id,
                outdoorCheckBox39.id
            )
            for (id in departmentCheckid) {
                val checkBox = findViewById<CheckBox>(id)
                checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                    departmentBit = getDepartmentBit()
                }
            }
        }
        db = OutdoorDBHelper(this)
        binding.outdoorFilterBackBtn.setOnClickListener{
                val bitArray = intArrayOf(fCatBit,sCatBit,departmentBit)
                val intent = Intent(this, MainActivity::class.java)     //메인으로 이동하면 바텀네비게이션이 장학금을 가리키고 있음
                intent.putExtra("filtered_outdoor" , bitArray)
                startActivity(intent)
        }
    }
}
