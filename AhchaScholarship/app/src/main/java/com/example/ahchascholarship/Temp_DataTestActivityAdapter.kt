package com.example.ahchascholarship

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ahchascholarship.databinding.TempDataTestingRowBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Temp_DataTestActivityAdapter(val items:ArrayList<ScholarshipData>)
: RecyclerView.Adapter<Temp_DataTestActivityAdapter.MyViewHolder>()
{
	interface OnItemClickListener{
		fun OnItemClick(position: Int)
	}

	var itemClickListener:OnItemClickListener?=null

	inner class MyViewHolder(val binding: TempDataTestingRowBinding): RecyclerView.ViewHolder(binding.root){
		init{
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
		val view= TempDataTestingRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return MyViewHolder(view)
	}

	override fun getItemCount(): Int {
		return items.size
	}

	override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
		holder.binding.e0 .text = "번호: ".plus(items[position].번호)
		holder.binding.e1 .text = "운영기관명: ".plus(items[position].운영기관명)
		holder.binding.e2 .text = "상품명: ".plus(items[position].상품명)
		holder.binding.e3 .text = "운영기관구분: ".plus(ScholarshipDataParser().decodeFCat(items[position].운영기관구분))
		holder.binding.e4 .text = "상품구분: ".plus(ScholarshipDataParser().decodeSCat1(items[position].상품구분))
		holder.binding.e5 .text = "학자금유형구분: ".plus(ScholarshipDataParser().decodeSCat2(items[position].학자금유형구분))
		holder.binding.e6 .text = "대학구분: ".plus(ScholarshipDataParser().decodeSchoolCat(items[position].대학구분))
		holder.binding.e7 .text = "학년구분: ".plus(ScholarshipDataParser().decodeYear(items[position].학년구분))
		holder.binding.e8 .text = "학과구분: ".plus(ScholarshipDataParser().decodeDepartment(items[position].학과구분))
		holder.binding.e9 .text = "성적기준: ".plus(items[position].성적기준)
		holder.binding.e10.text = "소득기준: ".plus(items[position].소득기준)
		holder.binding.e11.text = "지원금액: ".plus(items[position].지원금액)
		holder.binding.e12.text = "특정자격: ".plus(items[position].특정자격)
		holder.binding.e13.text = "지역거주여부: ".plus(items[position].지역거주여부)
		holder.binding.e14.text = "신청시작: ".plus(items[position].신청시작)
		holder.binding.e15.text = "신청마감: ".plus(items[position].신청마감)
		holder.binding.e16.text = "선발방법: ".plus(items[position].선발방법)
		holder.binding.e17.text = "선발인원: ".plus(items[position].선발인원)
		holder.binding.e18.text = "자격제한: ".plus(items[position].자격제한)
		holder.binding.e19.text = "추천필요여부: ".plus(items[position].추천필요여부)
		holder.binding.e20.text = "제출서류: ".plus(items[position].제출서류)
	}
}

// 0 번호
// 1 운영기관명
// 2 상품명
// 3 운영기관구분
// 4 상품구분
// 5 학자금유형구분
// 6 대학구분
// 7 학년구분
// 8 학과구분
// 9 성적기준
// 10 소득기준
// 11 지원금액
// 12 특정자격
// 13 지역거주여부
// 14 신청시작
// 15 신청마감
// 16 선발방법
// 17 선발인원
// 18 자격제한
// 19 추천필요여부
// 20 제출서류