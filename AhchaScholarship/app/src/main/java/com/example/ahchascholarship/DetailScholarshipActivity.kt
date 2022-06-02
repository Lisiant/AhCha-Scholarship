package com.example.ahchascholarship

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.ahchascholarship.databinding.ActivityDetailScholarshipBinding
import java.net.URLEncoder
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.*

class DetailScholarshipActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailScholarshipBinding
    lateinit var db: ScholarshipDBHelper
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
            val name = "[${scholarshipData.운영기관명}] ${scholarshipData.상품명}"
            val applyTime = "${scholarshipData.신청시작} ~ ${scholarshipData.신청마감}"
            val dday = getDday(scholarshipData)
            val yearDivision = parser.decodeYear(scholarshipData.학년구분)
            val departmentDivision = parser.decodeDepartment(scholarshipData.학과구분)

            detailNameTv.text = name
            detailProductDivisionTv.text = parser.decodeSCat1(scholarshipData.상품구분)
            detailScholarshipDivisionTv.text = parser.decodeSCat2(scholarshipData.상품구분)
//            detailUnivDivisionTv.text = parser.decodeSchoolCat(scholarshipData.대학구분).toString()

            if (yearDivision.isEmpty()) {
                detailYearDivisionTv.text = "-"
            } else {
                detailYearDivisionTv.text = yearDivision.toString()
            }

            if (departmentDivision.isEmpty()) {
                detailDepartmentDivisionTv.text = "-"
            } else {
                detailDepartmentDivisionTv.text = departmentDivision.toString()
            }

            detailGradeTv.text = scholarshipData.성적기준
            detailIncomeTv.text = scholarshipData.소득기준
            detailMoneyTv.text = scholarshipData.지원금액
            detailQualificationTv.text = scholarshipData.특정자격
            detailLocalScholarshipTv.text = scholarshipData.지역거주여부
            detailApplyTimeTv.text = applyTime
            detailSelectionTv.text = scholarshipData.선발방법
            detailSelectionNumberTv.text = scholarshipData.선발인원
            detailLimitTv.text = scholarshipData.자격제한
            detailRecommendTv.text = scholarshipData.추천필요여부
            detailApplicationTv.text = scholarshipData.제출서류


            if (dday >= 0) {
                detailDdayTv.text = "D-$dday"
                detailDdayTv.setBackgroundResource(R.drawable.dday_textview_background_radius)
            } else {
                detailDdayTv.text = "D+${-dday}"
                detailDdayTv.setBackgroundResource(R.drawable.dday_textview_background_off_radius)
            }

            if (scholarshipData.favorite) {
                detailBottomFavoriteIv.setImageResource(R.drawable.ic_baseline_star_24)
                detailBottomFavoriteTv.text = "관심 장학금 해제"
            } else {
                detailBottomFavoriteIv.setImageResource(R.drawable.ic_baseline_star_border_24)
                detailBottomFavoriteTv.text = "관심 장학금 설정"
            }
        }





        binding.detailBottomSiteCl.setOnClickListener{
            val strData = URLEncoder.encode(scholarshipData.운영기관명, Charsets.UTF_8.toString())
            val uri = "https://www.google.com/search?q=$strData"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            startActivity(intent)
        }



        binding.detailBottomFavoriteCl.setOnClickListener {
            scholarshipData.favorite = !scholarshipData.favorite

            if (scholarshipData.favorite) {
                binding.detailBottomFavoriteIv.setImageResource(R.drawable.ic_baseline_star_24)
                binding.detailBottomFavoriteTv.text = "관심 장학금 해제"
            }else{
                binding.detailBottomFavoriteIv.setImageResource(R.drawable.ic_baseline_star_border_24)
                binding.detailBottomFavoriteTv.text = "관심 장학금 설정"
            }

            db.setFavorite(sno, scholarshipData.favorite)

        }


    }


    private fun getDday(data: ScholarshipData): Int {
        val ret = parser.calculateDateBetween(
            SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(
                System.currentTimeMillis()
            ).toString(), data.신청마감
        )

        return ret.toInt()
    }


}