package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.example.ahchascholarship.R
import com.example.ahchascholarship.ScholarshipData
import com.example.ahchascholarship.ScholarshipDataParser
import com.example.ahchascholarship.databinding.FavoriteBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FavoriteAdapter(val items:ArrayList<ScholarshipData>) :RecyclerView.Adapter<FavoriteAdapter.ViewHolder>(){
    interface OnItemClickListener{
        fun favoriteClick(data: ScholarshipData, position: Int){
        }
        fun alarmClick(data: ScholarshipData, position: Int){

        }
        fun siteClick(data: ScholarshipData, position: Int){

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

    val parser = ScholarshipDataParser()
    var itemClickListener:OnItemClickListener?=null

    inner class ViewHolder(val binding:FavoriteBinding) : RecyclerView.ViewHolder(binding.root){

        init {

            binding.favoriteFavoriteBtn.setOnClickListener {
                itemClickListener?.favoriteClick(items[adapterPosition],adapterPosition)
            }
            binding.favoriteAlarmBtn.setOnClickListener {
                itemClickListener?.alarmClick(items[adapterPosition],adapterPosition)
            }
            binding.favoriteSiteBtn.setOnClickListener {
                itemClickListener?.siteClick(items[adapterPosition],adapterPosition)
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FavoriteBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val applyTime = "${items[position].신청시작} ~ ${items[position].신청마감}"
        val dday = getDday(items[position])
        val yearDivision = parser.decodeYear(items[position].학년구분)
        val departmentDivision = parser.decodeDepartment(items[position].학과구분)
        holder.binding.apply{
            favoriteNameTv.text = "[${items[position].운영기관명} ${items[position].상품명}]"
            favoriteScholarshipDivisionTv.text = parser.decodeSCat1(items[position].상품구분)
            favoriteProductDivisionTv.text = parser.decodeSCat2(items[position].상품구분)

            if (yearDivision.isEmpty()) {
                favoriteYearDivisionTv.text = "-"
            } else {
                favoriteYearDivisionTv.text = yearDivision.toString()
            }

            if (departmentDivision.isEmpty()) {
                favoriteDepartmentDivisionTv.text = "-"
            } else {
                favoriteDepartmentDivisionTv.text = departmentDivision.toString()
            }

            favoriteGradeTv.text = items[position].성적기준
            favoriteIncomeTv.text = items[position].소득기준
            favoriteMoneyTv.text = items[position].지원금액
            favoriteQualificationTv.text = items[position].특정자격
            favoriteLocalScholarshipTv.text = items[position].지역거주여부
            favoriteApplyTimeTv.text = applyTime
            favoriteSelectionTv.text = items[position].선발방법
            favoriteSelectionNumberTv.text = items[position].선발인원
            favoriteLimitTv.text = items[position].자격제한
            favoriteRecommendTv.text = items[position].추천필요여부
            favoriteApplicationTv.text = items[position].제출서류


            if (dday >= 0) {
                favoriteDdayTv.text = "D-$dday"
                favoriteDdayTv.setBackgroundResource(R.drawable.dday_textview_background_radius)

            }else{
                favoriteDdayTv.text = "D+${-dday}"
                favoriteDdayTv.setBackgroundResource(R.drawable.dday_textview_background_off_radius)
            }

            if (items[position].favorite) {
                favoriteBottomFavoriteIv.setImageResource(R.drawable.ic_baseline_star_24)
            } else {
                favoriteBottomFavoriteIv.setImageResource(R.drawable.ic_baseline_star_border_24)
            }

            if (items[position].alarmCheck) {
                favoriteBottomAlarmIv.setImageResource(R.drawable.ic_baseline_alarm_on_24)
            } else {
                favoriteBottomAlarmIv.setImageResource(R.drawable.ic_baseline_alarm_off_24)
            }
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }
}


//번호1
//운영기관명2
//운영기관부분3
//상품구분4
//학자금유형구분5
//대학구분6
//학년구분7
//학과구분8
//성적구분9
//소득기준10
//지원금액11
//특정자격12
//지역거주여부13
//신청시작14
//신청마감15
//선바방법16
//선발인원17
//자격제한18
//추천필요여부19
//제출서류20