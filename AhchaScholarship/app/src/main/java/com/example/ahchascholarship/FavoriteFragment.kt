package com.example.ahchascholarship

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.example.ahchascholarship.databinding.FragmentFavoriteBinding
import com.example.myapplication.FavoriteAdapter
import me.relex.circleindicator.CircleIndicator2


class FavoriteFragment : Fragment() {
    lateinit var favoriteAdapter:FavoriteAdapter
    lateinit var db:ScholarshipDBHelper
    lateinit var indicator : CircleIndicator2
    val snapHelper = PagerSnapHelper()
    var data :ArrayList<ScholarshipData> = ArrayList()


    var binding:FragmentFavoriteBinding?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(layoutInflater,container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        var scholarshipdata :ScholarshipData = ScholarshipData(1,"운영기관명","상품명",
//            "운영기관구분","상품구분","학자금유형구분","대학구분","학년구분",
//            "학과구분","성적기준","소득기준","지원금액",
//            "특정자격","지역거주여부","신청시작","신청마감","선발방법","선별인원",
//            "자격제한","추천필요여부","제출서류")
        var scholarshipdata :ScholarshipData = ScholarshipData(1,"운영기관명","상품명",
            1,1,1,1,1,
            1,"성적기준","소득기준","지원금액",
            "특정자격","지역거주여부","신청시작","신청마감","선발방법","선별인원",
            "자격제한","추천필요여부","제출서류", false, false)
        db = ScholarshipDBHelper(context)
        indicator = binding!!.favoriteIndicator
        data = db.getFavoriteRecord()
        binding!!.favoriteRecyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        favoriteAdapter = FavoriteAdapter(data)
        favoriteAdapter.itemClickListener = object : FavoriteAdapter.OnItemClickListener{
            override fun OnItemClick(data: ScholarshipData, position: Int) {
                db.updateFavorite(data)
                favoriteAdapter.notifyItemChanged(position)
            }
        }
        binding!!.favoriteRecyclerView.adapter = favoriteAdapter
        snapHelper.attachToRecyclerView(binding!!.favoriteRecyclerView)
        indicator.attachToRecyclerView(binding!!.favoriteRecyclerView,snapHelper)
        binding!!.apply{
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}