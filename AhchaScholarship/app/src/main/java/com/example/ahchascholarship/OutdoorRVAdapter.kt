package com.example.ahchascholarship

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ahchascholarship.databinding.ItemOutdoorBinding



class OutdoorRVAdapter(val items: ArrayList<OutdoorActivityData>) :
    RecyclerView.Adapter<OutdoorRVAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(outdoorData: OutdoorActivityData)
    }

    private lateinit var outdoorItemClickListener: OnItemClickListener
    fun setOutdoorItemClickListener(itemClickListener: OnItemClickListener) {
        outdoorItemClickListener = itemClickListener
    }

    inner class ViewHolder(val binding: ItemOutdoorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: OutdoorActivityData) {
            val parser = OutdoorDataParser()


            binding.apply {
                itemOutdoorNameTv.text = data.활동이름
                itemOutdoorTypeTv.text = parser.decodeSCat(data.활동종류)
                itemOutdoorAgencyTv.text = data.운영기관명
                itemOutdoorDateStartTv.text = data.신청시작
                itemOutdoorDateEndTv.text = data.신청마감
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemOutdoorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            outdoorItemClickListener.onItemClick(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}