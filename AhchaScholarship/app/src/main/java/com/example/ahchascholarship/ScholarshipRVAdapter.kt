package com.example.ahchascholarship

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ahchascholarship.databinding.ItemScholarshipBinding
import java.time.LocalDate

class ScholarshipRVAdapter(val items: ArrayList<ScholarshipData>) :
    RecyclerView.Adapter<ScholarshipRVAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(scholarshipData: ScholarshipData)
        fun applyFavorite(position: Int)

    }

    private lateinit var scholarshipItemClickListener: OnItemClickListener
    fun setScholarshipItemClickListener(itemClickListener: OnItemClickListener){
        scholarshipItemClickListener = itemClickListener
    }

    inner class ViewHolder(val binding: ItemScholarshipBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ScholarshipData) {
            val date =  LocalDate.now()
            binding.itemScholarshipNameTv.text = data.상품명
            binding.itemScholarshipAgencyTv.text = data.운영기관명
            binding.itemScholarshipDateStartTv.text = data.신청시작
            binding.itemScholarshipDateEndTv.text = data.신청마감
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemScholarshipBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            scholarshipItemClickListener.onItemClick(items[position])
        }
        holder.binding.itemScholarshipFavoriteIv.setOnClickListener{
            // 즐겨찾기 추가 기능 함수 구현 필요
            scholarshipItemClickListener.applyFavorite(position)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}