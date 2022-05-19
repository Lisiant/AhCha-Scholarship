package com.example.ahchascholarship

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ahchascholarship.databinding.ActivityMainBinding
import com.opencsv.CSVReader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.parser.Parser
import java.io.BufferedReader
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    lateinit var scholarshipDBHelper: ScholarshipDBHelper
    val scholarshipDataList = ArrayList<ScholarshipData>()
    val dataIOScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigation()
        initScholarshipData()

    }

    private fun initBottomNavigation() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, ScholarshipFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.scholarshipFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, ScholarshipFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.favoriteFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, FavoriteFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.outdoorFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, OutdoorFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }

    }

    private fun initScholarshipData(){
        scholarshipDBHelper = ScholarshipDBHelper(this)
        dataIOScope.launch {
            scholarshipDataList.clear()
            val FStream = InputStreamReader(getResources().openRawResource(R.raw.data));
            val reader = BufferedReader(FStream);
            val read = CSVReader(reader)
            for (scholarship in read) {
                val date = ScholarshipDataParser().stringToDate(scholarship[14]) ?: continue
                if (date.end.before(ScholarshipDataParser().dateFormat.parse("2022-05-11")))
                    continue
                scholarshipDataList.add(
                    ScholarshipData(
                        scholarship[0].removePrefix(" - ").trim().replace(",","").toInt(),
                        scholarship[1].removePrefix(" - ").trim(),
                        scholarship[2].removePrefix(" - ").trim(),
                        ScholarshipDataParser().encodeFCat(scholarship[3].removePrefix(" - ").trim()),
                        ScholarshipDataParser().encodeSCat1(scholarship[4].removePrefix(" - ").trim()),
                        ScholarshipDataParser().encodeSCat2(scholarship[5].removePrefix(" - ").trim()),
                        ScholarshipDataParser().encodeSchoolCat(scholarship[6].removePrefix(" - ").trim()),
                        ScholarshipDataParser().encodeYear(scholarship[7].removePrefix(" - ").trim()),
                        ScholarshipDataParser().encodeDepartment(scholarship[8].removePrefix(" - ").trim()),
                        scholarship[9].removePrefix(" - ").trim(),
                        scholarship[10].removePrefix(" - ").trim(),
                        scholarship[11].removePrefix(" - ").trim(),
                        scholarship[12].removePrefix(" - ").trim(),
                        scholarship[13].removePrefix(" - ").trim(),
                        SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(date.start),
                        SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(date.end),
                        scholarship[15].removePrefix(" - ").trim(),
                        scholarship[16].removePrefix(" - ").trim(),
                        scholarship[17].removePrefix(" - ").trim(),
                        scholarship[18].removePrefix(" - ").trim(),
                        scholarship[19].removePrefix(" - ").trim(),
                        false
                    )
                )
            }
            read.close()
            reader.close()
            FStream.close()
        }
        for(scholarship in scholarshipDataList) {
            scholarshipDBHelper.insertData(scholarship)
        }
        binding.DataTestBtn.setOnClickListener {
            val intent = Intent(this, Temp_DataTestingActivity::class.java)
            intent.putExtra("scholar", scholarshipDataList)
            startActivity(intent)
        }
    }
}