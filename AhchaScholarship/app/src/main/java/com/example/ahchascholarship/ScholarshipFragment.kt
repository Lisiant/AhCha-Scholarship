package com.example.ahchascholarship

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ahchascholarship.databinding.FragmentScholarshipBinding

class ScholarshipFragment : Fragment() {
    lateinit var binding: FragmentScholarshipBinding
    private var scholarshipDataList = ArrayList<ScholarshipData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScholarshipBinding.inflate(inflater, container, false)
        inputDummyData()

        val scholarshipRVAdapter = ScholarshipRVAdapter(scholarshipDataList)
        binding.scholarshipMainRv.adapter = scholarshipRVAdapter
        binding.scholarshipMainRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        scholarshipRVAdapter.setScholarshipItemClickListener(object :
            ScholarshipRVAdapter.OnItemClickListener {
            override fun onItemClick(scholarshipData: ScholarshipData) {
                Log.d("DATA_CLICKED", "true")
            }

            override fun applyFavorite(position: Int) {
                Log.d("FAVORITE_CLICKED", "true")

            }
        })

        return binding.root
    }



    private fun inputDummyData() {
        val scholarshipData: ScholarshipData = ScholarshipData(
            1, "운영기관명", "상품명",
            1, 1, 1, 1, 1,
            1, "성적기준", "소득기준", "지원금액",
            "특정자격", "지역거주여부", "신청시작", "신청마감", "선발방법", "선별인원",
            "자격제한", "추천필요여부", "제출서류", favorite = false, alarmCheck = false
        )

        scholarshipDataList.add(scholarshipData)
        scholarshipDataList.add(scholarshipData)
        scholarshipDataList.add(scholarshipData)
    }


}