package com.example.ahchascholarship

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ahchascholarship.databinding.ItemScholarshipBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

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
            val diff = getDiff(data)
            binding.apply {
                itemScholarshipNameTv.text = data.상품명
                itemScholarshipAgencyTv.text = data.운영기관명
                itemScholarshipDateStartTv.text = data.신청시작
                itemScholarshipDateEndTv.text = data.신청마감

                if (diff >= 0) {
                    itemScholarshipDdayTv.text = "D-$diff"
                } else {
                    itemScholarshipDdayTv.text = "D+${-diff}"
                }
                if (data.favorite){
                    itemScholarshipFavoriteIv.setImageResource(R.drawable.ic_baseline_star_24)
                }else{
                    itemScholarshipFavoriteIv.setImageResource(R.drawable.ic_baseline_star_border_24)
                }
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



    private fun getDiff(data: ScholarshipData): Int {
        val parser = ScholarshipDataParser()
        val ret = parser.calculateDateBetween(
            SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(
                System.currentTimeMillis()
            ).toString(), data.신청마감
        )

        return ret.toInt()
    }


}