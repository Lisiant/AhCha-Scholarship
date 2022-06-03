package com.example.ahchascholarship

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContract
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ahchascholarship.databinding.FragmentScholarshipBinding

class ScholarshipFragment : Fragment() {
    lateinit var binding: FragmentScholarshipBinding
    lateinit var db: ScholarshipDBHelper
    lateinit var intent: Intent
    var scholarshipDataList = ArrayList<ScholarshipData>()
//    val model:ScholarshipViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = ScholarshipDBHelper(context)
//        model.ScholarshipDataList.observe(viewLifecycleOwner, Observer<ArrayList<ScholarshipData>> {
//            item->
//            scholarshipDataList = item
//        })
//
//        val extra = arguments
//       if(extra!=null){
//            Log.d("getdataaa","a")
//           val bitArray = extra?.getIntArray("filter")
//            scholarshipDataList = db.catSelector(bitArray!![0],bitArray[1],bitArray[2],bitArray[3],bitArray[4],bitArray[5])
//        }
//        else {
//            Log.d("nodata" , "123")
//            scholarshipDataList = db.getAllRecord()
//        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScholarshipBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.scholarshipFilterBtn.setOnClickListener {
            val intent = Intent(context, ScholarshipFilter::class.java)
            startActivity(intent)
        }
        super.onViewCreated(view, savedInstanceState)
        Log.d("argumentData" , arguments?.size().toString())
        if(getArguments()!=null){
            Log.d("getdataaa","a")
            val bitArray = arguments?.getIntArray("filter")
            scholarshipDataList = db.catSelector(bitArray!![0],bitArray[1],bitArray[2],bitArray[3],bitArray[4],bitArray[5])
        }
        else {
            Log.d("nodata" , "123")
            scholarshipDataList = db.getAllRecord()
        }
//        var bitArray : IntArray
//        val i = requireActivity().intent
//        val num = i.getIntExtra("filtered",-1)
//        val fCatBit : Int
//        if(num==-1) {
//            Log.d("nothing come" , num.toString())
//            scholarshipDataList = db.getAllRecord()
//        }
//        else {
//            Log.d("data come" , num.toString())
//            model.yearBit.observe(viewLifecycleOwner, Observer {
//                  val yearBit = it
//                Log.d("yearbit" ,yearBit.toString())
//
////                val sCat1Bit = model.sCat1Bit.value !!.toInt()
////                val sCat2Bit = model.sCat2Bit.value!!.toInt()
////                val schoolCatBit = model.schoolCatBit.value!!.toInt()
////                val yearBit = model.yearBit.value!!.toInt()
////                val departmentBit = model.departmentBit.value!!.toInt()
////                scholarshipDataList = db.catSelector(fCatBit,sCat1Bit, sCat2Bit, schoolCatBit, yearBit, departmentBit)
////                Log.d("fuckyou" , scholarshipDataList.size.toString())
//            })
//            model.sCat1Bit.observe(viewLifecycleOwner, Observer {
//                val sCatBit  = it
//            })
//        }

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
