package com.example.ahchascholarship

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ahchascholarship.databinding.TempActivityDataTestingBinding

class Temp_DataTestingActivity : AppCompatActivity() {
	lateinit var binding: TempActivityDataTestingBinding
	lateinit var adapter: Temp_DataTestActivityAdapter
	lateinit var scholarshipDataList: ArrayList<ScholarshipData>
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = TempActivityDataTestingBinding.inflate(layoutInflater)
		setContentView(binding.root)
		initLayout()
	}
	private fun initLayout(){
		val dataList = intent.getSerializableExtra("scholarshipDataList")
		adapter = Temp_DataTestActivityAdapter((ArrayList<ScholarshipData>)dataList)

	}
}