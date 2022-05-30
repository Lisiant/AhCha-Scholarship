package com.example.ahchascholarship

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContract
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


        scholarshipRVAdapter.setScholarshipItemClickListener(object : ScholarshipRVAdapter.OnItemClickListener {
            override fun onItemClick(scholarshipData: ScholarshipData) {
                Log.d("DATA_CLICKED", scholarshipData.toString())

                val intent = Intent(context, DetailScholarshipActivity::class.java)
                intent.putExtra("sno", scholarshipData.번호)
                startActivity(intent)
            }

            override fun clickFavorite(scholarshipData: ScholarshipData, position: Int) {
                scholarshipData.favorite = !scholarshipData.favorite
                db.setFavorite(scholarshipData.번호, scholarshipData.favorite)
                scholarshipRVAdapter.notifyDataSetChanged()
                Log.d("Favorite_CLICKED", scholarshipData.favorite.toString())

            }
        })


    }


}