package com.example.ahchascholarship

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ahchascholarship.databinding.ActivityDetailScholarshipBinding

class DetailScholarshipActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailScholarshipBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailScholarshipBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}