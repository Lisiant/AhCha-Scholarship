package com.example.ahchascholarship

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.*
import com.example.ahchascholarship.alarmhelper.AlarmRegisterHelper
import com.example.ahchascholarship.databinding.FragmentFavoriteBinding
import com.example.myapplication.FavoriteAdapter
import me.relex.circleindicator.CircleIndicator2
import java.net.URLEncoder


class FavoriteFragment : Fragment() {
    val favoriteAlarmHelper = AlarmRegisterHelper()
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
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
            override fun favoriteClick(data: ScholarshipData, position: Int) {

                data.favorite = !data.favorite
                if(data.favorite){
                    db.setFavorite(data.번호,true)
                    data.alarmCheck = data.favorite
                    favoriteAlarmHelper.setAlarm(true, data)
                    Toast.makeText(MainActivity.mainContext, "즐겨찾기에 추가 되었습니다.", Toast.LENGTH_SHORT).show()
                }else{
                    db.setFavorite(data.번호,false)
                    data.alarmCheck = data.favorite
                    favoriteAlarmHelper.setAlarm(false, data)
                    Toast.makeText(MainActivity.mainContext, "즐겨찾기에 해제 되었습니다.", Toast.LENGTH_SHORT).show()
                }
                favoriteAdapter.notifyItemChanged(position)
            }

            override fun alarmClick(data: ScholarshipData, position: Int) {
                if(data.favorite) {
                    db.updateAlarm(data)
                    data.alarmCheck = !data.alarmCheck
                    if (data.alarmCheck) {
                        favoriteAlarmHelper.setAlarm(true, data)
                        Toast.makeText(MainActivity.mainContext, "알림이 설정 되었습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        favoriteAlarmHelper.setAlarm(false, data)
                        Toast.makeText(MainActivity.mainContext, "알림이 해제 되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
                favoriteAdapter.notifyItemChanged(position)
            }

            override fun siteClick(data: ScholarshipData, position: Int) {
                val strData = URLEncoder.encode(data.운영기관명, Charsets.UTF_8.toString())
                val uri = "https://www.google.com/search?q=$strData"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                startActivity(intent)
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