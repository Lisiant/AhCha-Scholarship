package com.example.ahchascholarship

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ahchascholarship.databinding.FragmentOutdoorBinding

class OutdoorFragment : Fragment() {
    lateinit var binding : FragmentOutdoorBinding
    lateinit var db : OutdoorDBHelper
    var outdoorDataList = ArrayList<OutdoorActivityData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = OutdoorDBHelper(context)
        outdoorDataList = db.getAllRecord()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOutdoorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bitArray = arguments?.getIntArray("filter")
        if (bitArray != null){
            db.catSelector(bitArray[0], bitArray[1],bitArray[2])
        }else{
            db.getAllRecord()
        }

        val outdoorRVAdapter = OutdoorRVAdapter(outdoorDataList)
        binding.outdoorMainRv.adapter = outdoorRVAdapter
        binding.outdoorMainRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        outdoorRVAdapter.setOutdoorItemClickListener(object : OutdoorRVAdapter.OnItemClickListener {
            override fun onItemClick(outdoorData: OutdoorActivityData) {
//                val intent = Intent(context, DetailOutdoorActivity::class.java)
            }

        })

        binding.outdoorFilterIv.setOnClickListener {
            val intent = Intent(context, OutdoorFilter::class.java)
            startActivity(intent)
        }
    }
}