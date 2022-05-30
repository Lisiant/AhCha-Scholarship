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
        fun clickFavorite(scholarshipData: ScholarshipData, position: Int)

    }

    private lateinit var scholarshipItemClickListener: OnItemClickListener
    fun setScholarshipItemClickListener(itemClickListener: OnItemClickListener) {
        scholarshipItemClickListener = itemClickListener
    }

    inner class ViewHolder(val binding: ItemScholarshipBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ScholarshipData) {
            val date = LocalDate.now()
            binding.itemScholarshipNameTv.text = data.상품명
            binding.itemScholarshipAgencyTv.text = data.운영기관명
            binding.itemScholarshipDateStartTv.text = data.신청시작
            binding.itemScholarshipDateEndTv.text = data.신청마감
            if (data.favorite){
                binding.itemScholarshipFavoriteIv.setImageResource(R.drawable.ic_baseline_star_24)
            }else{
                binding.itemScholarshipFavoriteIv.setImageResource(R.drawable.ic_baseline_star_border_24)
            }
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
        holder.binding.itemScholarshipFavoriteIv.setOnClickListener {
            scholarshipItemClickListener.clickFavorite(items[position], position)
            if (!items[position].favorite)
                holder.binding.itemScholarshipFavoriteIv.setImageResource(R.drawable.ic_baseline_star_border_24)
            else
                holder.binding.itemScholarshipFavoriteIv.setImageResource(R.drawable.ic_baseline_star_24)

        }

    }

    override fun getItemCount(): Int {
        return items.size
    }
}