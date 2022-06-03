package com.example.ahchascholarship

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
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
            if (outdoorCheckbox11.isChecked) {
                sCatbit = sCatbit.or(BIT_0)       //동아리
            }
            if (outdoorCheckbox12.isChecked) {
                sCatbit = sCatbit.or(BIT_1)       //봉사
            }
            if (outdoorCheckbox13.isChecked) {
                sCatbit = sCatbit.or(BIT_2)       //공모전
            }
            if (outdoorCheckbox14.isChecked) {
                sCatbit = sCatbit.or(BIT_3)       //인턴
            }
            if (outdoorCheckbox15.isChecked) {
                sCatbit = sCatbit.or(BIT_4)       //채용
            }
        }
        Log.d("scatbit", sCatbit.toString())
        return sCatbit
    }

    private fun getFCatBit(): Int {
        var fCatbit = 0
        binding.apply {
            if (outdoorCheckBox21.isChecked) {
                fCatbit = fCatbit.or(BIT_0)       //민간
            }
            if (outdoorCheckBox22.isChecked) {
                fCatbit = fCatbit.or(BIT_1)       //공공재단
            }
            if (outdoorCheckBox23.isChecked) {
                fCatbit = fCatbit.or(BIT_2)       //정부재단
            }
            if (outdoorCheckBox24.isChecked) {
                fCatbit = fCatbit.or(BIT_3)       //지자체
            }
            if (outdoorCheckBox25.isChecked) {
                fCatbit = fCatbit.or(BIT_4)       //공공기업
            }
            if (outdoorCheckBox26.isChecked) {
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
                outdoorCheckbox11.id, outdoorCheckbox12.id, outdoorCheckbox13.id,
                outdoorCheckbox14.id, outdoorCheckbox15.id
            )
            for (id in sCatCheckid) {
                val checkBox = findViewById<CheckBox>(id)
                checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                    sCatBit = getSCatBit()
                }
            }

            val fCatCheckid = intArrayOf(
                outdoorCheckBox21.id, outdoorCheckBox22.id, outdoorCheckBox23.id,
                outdoorCheckBox24.id, outdoorCheckBox25.id, outdoorCheckBox26.id
            )
            for (id in fCatCheckid) {
                val checkBox = findViewById<CheckBox>(id)
                checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
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
//            val model:ScholarshipViewModel by viewModels()
////            Log.d("nonselectdata" , model.ScholarshipDataList.value!!.size.toString())
////            model.select(db.catSelector(fCatBit,sCat1Bit, sCat2Bit, schoolCatBit, yearBit, departmentBit))
//            val bitArray = intArrayOf(fCatBit,sCat1Bit, sCat2Bit, schoolCatBit, yearBit, departmentBit)
//            model.setLiveData(yearBit)//,sCat1Bit, sCat2Bit, schoolCatBit, yearBit, departmentBit)
            val bitArray = intArrayOf(sCatBit,fCatBit,departmentBit)
            val frag = ScholarshipFragment()
            val bundle = Bundle()
            bundle.putIntArray("filter" , bitArray)
            frag.setArguments(bundle)
//            val intent = Intent(this, MainActivity::class.java)
//            intent.putExtra("filtered" , 1)
//            startActivity(intent)
        }
    }
}
