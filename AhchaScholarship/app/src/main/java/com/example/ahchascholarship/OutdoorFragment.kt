package com.example.ahchascholarship

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ahchascholarship.databinding.FragmentOutdoorBinding

class OutdoorFragment : Fragment() {
    lateinit var binding : FragmentOutdoorBinding
    lateinit var db : OutdoorDBHelper
    var outdoorDatalist = ArrayList<OutdoorActivityData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = OutdoorDBHelper(context)
        outdoorDatalist = db.getAllRecord()
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
        ///////recycler view와 어뎁터 연결///////

        /////////////////////////////////////
        binding.outdoorFilterBtn.setOnClickListener {
            val intent = Intent(context, OutdoorFilter::class.java)
            startActivity(intent)
        }
    }
}