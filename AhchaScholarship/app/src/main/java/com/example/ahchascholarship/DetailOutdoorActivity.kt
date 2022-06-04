package com.example.ahchascholarship

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.ahchascholarship.databinding.ActivityDetailOutdoorBinding
import java.net.URLEncoder
import java.nio.charset.Charset

class DetailOutdoorActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailOutdoorBinding
    lateinit var db: OutdoorDBHelper
    lateinit var outdoorData: OutdoorActivityData
    val parser = OutdoorDataParser()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailOutdoorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initDetailOutdoorActivity()

    }

    private fun initDetailOutdoorActivity() {
        val outdoorNum = intent.getIntExtra("outdoorNum", -1)
        db = OutdoorDBHelper(this)
        binding.apply {
            outdoorData = db.findOutdoorByNumber(outdoorNum)
            val name = "[${outdoorData.운영기관명}] ${outdoorData.활동이름}"
            val applyTime = "${outdoorData.신청시작} ~ ${outdoorData.신청마감}"
            val outdoorType = parser.decodeSCat(outdoorData.활동종류)
            val department = parser.decodeDepartment(outdoorData.활동내용계열)
            val agencyType = parser.decodeFCat(outdoorData.운영기관종류)
            val detailInfo = outdoorData.상세

            detailOutdoorNameTv.text = name
            detailOutdoorApplyTimeTv.text = applyTime
            detailOutdoorTypeTv.text = outdoorType
            detailOutdoorAgencyTypeTv.text = agencyType
            detailOutdoorDepartmentTv.text = department.toString()
            detailOutdoorInfoTv.text = detailInfo


            detailOutdoorBottomSiteCl.setOnClickListener{
                val uri =outdoorData.사이트링크
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                startActivity(intent)
            }
        }
    }
}