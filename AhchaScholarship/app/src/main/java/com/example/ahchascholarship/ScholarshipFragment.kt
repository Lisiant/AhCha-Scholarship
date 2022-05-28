package com.example.ahchascholarship

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ahchascholarship.databinding.FragmentScholarshipBinding

class ScholarshipFragment : Fragment() {
    lateinit var binding: FragmentScholarshipBinding
    lateinit var db: ScholarshipDBHelper
    var scholarshipDataList = ArrayList<ScholarshipData>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = ScholarshipDBHelper(context)
        scholarshipDataList = db.getAllRecord()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScholarshipBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val scholarshipRVAdapter = ScholarshipRVAdapter(scholarshipDataList)
        binding.scholarshipMainRv.adapter = scholarshipRVAdapter
        binding.scholarshipMainRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)


        scholarshipRVAdapter.setScholarshipItemClickListener(object :
            ScholarshipRVAdapter.OnItemClickListener {
            override fun onItemClick(scholarshipData: ScholarshipData) {
                Log.d("DATA_CLICKED", scholarshipData.상품명.toString() + scholarshipData.favorite.toString())
            }

            override fun clickFavorite(scholarshipData: ScholarshipData, position: Int) {
                scholarshipData.favorite = !scholarshipData.favorite
                /*
                 *  updateFavorite 사용 시 문제 발생:
                 *  즐겨찾기 추가 후 앱을 종료하고 다시 켰을 때
                 *  or BottomNavigation 이동(대외활동, MY) 후 다시 장학금 탭으로 돌아왔을 때
                 *  해당 내용이 저장이 안됨.
                 */
                db.setFavorite(10)
                scholarshipRVAdapter.notifyDataSetChanged()
                Log.d("Favorite_CLICKED", scholarshipData.favorite.toString())

            }
        })


    }


}