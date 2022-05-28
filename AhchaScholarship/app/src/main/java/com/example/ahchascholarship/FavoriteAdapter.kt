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

class FavoriteAdapter(val items:ArrayList<ScholarshipData>) :RecyclerView.Adapter<FavoriteAdapter.ViewHolder>(){
    interface OnItemClickListener{
        fun OnItemClick(data: ScholarshipData, position: Int){
        }
    }


    var itemClickListener:OnItemClickListener?=null

    inner class ViewHolder(val binding:FavoriteBinding) : RecyclerView.ViewHolder(binding.root){
        val str1 = itemView.findViewById<TextView>(R.id.favoriteStr1)
        val str2 = itemView.findViewById<TextView>(R.id.favoriteStr2)
        val str3 = itemView.findViewById<TextView>(R.id.favoriteStr3)
        val str4 = itemView.findViewById<TextView>(R.id.favoriteStr4)
        val str5 = itemView.findViewById<TextView>(R.id.favoriteStr5)
        val str6 = itemView.findViewById<TextView>(R.id.favoriteStr6)
        val str7 = itemView.findViewById<TextView>(R.id.favoriteStr7)
        val str8 = itemView.findViewById<TextView>(R.id.favoriteStr8)
        val str9 = itemView.findViewById<TextView>(R.id.favoriteStr9)
        val str10 = itemView.findViewById<TextView>(R.id.favoriteStr10)
        val str11 = itemView.findViewById<TextView>(R.id.favoriteStr11)
        val str12 = itemView.findViewById<TextView>(R.id.favoriteStr12)
        val str13 = itemView.findViewById<TextView>(R.id.favoriteStr13)
        val str14 = itemView.findViewById<TextView>(R.id.favoriteStr14)
        val str15 = itemView.findViewById<TextView>(R.id.favoriteStr15)
        val str16 = itemView.findViewById<TextView>(R.id.favoriteStr16)
        val str17 = itemView.findViewById<TextView>(R.id.favoriteStr17)
        val str18 = itemView.findViewById<TextView>(R.id.favoriteStr18)
        val str19 = itemView.findViewById<TextView>(R.id.favoriteStr19)
        val str20 = itemView.findViewById<TextView>(R.id.favoriteStr20)
        val str21 = itemView.findViewById<TextView>(R.id.favoriteStr21)

        init {
            binding.favoriteBtn.setOnClickListener {
                itemClickListener?.OnItemClick(items[adapterPosition],adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FavoriteBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.favoriteStr1.text = items[position].번호.toString()
        holder.binding.favoriteStr2.text = items[position].운영기관명
        holder.binding.favoriteStr3.text = items[position].상품명
//        holder.binding.favoriteStr4.text = items[position].운영기관구분
//        holder.binding.favoriteStr5.text = items[position].상품구분
//        holder.binding.favoriteStr6.text = items[position].학자금유형구분
//        holder.binding.favoriteStr7.text = items[position].대학구분
//        holder.binding.favoriteStr8.text = items[position].학년구분
//        holder.binding.favoriteStr9.text = items[position].학과구분
        holder.binding.favoriteStr4.text = ScholarshipDataParser().decodeFCat(items[position].운영기관구분)
        holder.binding.favoriteStr5.text = ScholarshipDataParser().decodeSCat1(items[position].상품구분)
        holder.binding.favoriteStr6.text = ScholarshipDataParser().decodeSCat2(items[position].학자금유형구분)
        holder.binding.favoriteStr7.text = ScholarshipDataParser().decodeSchoolCat(items[position].대학구분).toString()
        holder.binding.favoriteStr8.text = ScholarshipDataParser().decodeYear(items[position].학년구분).toString()
        holder.binding.favoriteStr9.text = ScholarshipDataParser().decodeDepartment(items[position].학과구분).toString()
        holder.binding.favoriteStr10.text = items[position].성적기준
        holder.binding.favoriteStr11.text = items[position].소득기준
        holder.binding.favoriteStr12.text = items[position].지원금액
        holder.binding.favoriteStr13.text = items[position].특정자격
        holder.binding.favoriteStr14.text = items[position].지역거주여부
        holder.binding.favoriteStr15.text = items[position].신청시작
        holder.binding.favoriteStr16.text = items[position].신청마감
        holder.binding.favoriteStr17.text = items[position].선발방법
        holder.binding.favoriteStr18.text = items[position].선발인원
        holder.binding.favoriteStr19.text = items[position].자격제한
        holder.binding.favoriteStr20.text = items[position].추천필요여부
        holder.binding.favoriteStr21.text = items[position].제출서류



        //holder.binding.meaningView.text = items[position].meaning
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