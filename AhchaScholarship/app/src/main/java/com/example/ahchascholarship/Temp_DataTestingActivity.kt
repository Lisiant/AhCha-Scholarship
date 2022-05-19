package com.example.ahchascholarship

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ahchascholarship.databinding.TempActivityDataTestingBinding

class Temp_DataTestingActivity : AppCompatActivity() {
	lateinit var binding: TempActivityDataTestingBinding
	lateinit var adapter: Temp_DataTestActivityAdapter
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = TempActivityDataTestingBinding.inflate(layoutInflater)
		setContentView(binding.root)
		initLayout()
	}
	private fun initLayout()
	{
		binding.recyclerView.layoutManager =
			LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
		binding.recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
		// ^ recyclerView 의 각 아이템 사이에 구분 선 넣는 것
		val data = ScholarshipDBHelper(this).getAllRecord()
		adapter = Temp_DataTestActivityAdapter(data)
		binding.recyclerView.adapter = adapter
	}
}