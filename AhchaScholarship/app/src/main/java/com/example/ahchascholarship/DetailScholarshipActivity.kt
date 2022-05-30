package com.example.ahchascholarship

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.ahchascholarship.databinding.ActivityDetailScholarshipBinding

class DetailScholarshipActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailScholarshipBinding
    lateinit var db:ScholarshipDBHelper
    lateinit var scholarshipData: ScholarshipData
    val parser = ScholarshipDataParser()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailScholarshipBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initDetailScholarship()

    }


    private fun initDetailScholarship() {
        val sno = intent.getIntExtra("sno", -1)
        db = ScholarshipDBHelper(this)

        binding.apply {
            scholarshipData = db.findScholarshipBySno(sno)
            val name = "${scholarshipData.운영기관명} ${scholarshipData.상품명}"

            detailNameTv.text = name
            detailProductDivisionTv.text = parser.decodeSCat1(scholarshipData.상품구분)
            detailScholarshipDivisionTv.text = parser.decodeSCat2(scholarshipData.상품구분)
            detailUnivDivisionTv.text = parser.decodeSchoolCat(scholarshipData.대학구분).toString()
            detailYearDivisionTv.text = parser.decodeYear(scholarshipData.학년구분).toString()
            detailDepartmentDivisionTv.text = parser.decodeDepartment(scholarshipData.학과구분).toString()
            detailGradeTv.text = scholarshipData.성적기준
            detailIncomeTv.text = scholarshipData.소득기준
            detailMoneyTv.text = scholarshipData.지원금액
            detailQualificationTv.text = scholarshipData.특정자격
            detailLocalScholarshipTv.text = scholarshipData.지역거주여부
            detailStartTimeTv.text = scholarshipData.신청시작
            detailEndTimeTv.text = scholarshipData.신청마감
            detailSelectionNumberTv.text = scholarshipData.선발방법
            detailSelectionNumberTv.text = scholarshipData.선발인원
            detailLimitTv.text = scholarshipData.자격제한
            detailRecommendTv.text = scholarshipData.추천필요여부
            detailApplicationTv.text = scholarshipData.제출서류

        }
    }
}