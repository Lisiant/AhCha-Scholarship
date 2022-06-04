package com.example.ahchascholarship

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ahchascholarship.alarmhelper.AlarmReceiver
import com.example.ahchascholarship.databinding.ActivityMainBinding
import com.opencsv.CSVReader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.io.BufferedReader
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    lateinit var scholarshipDBHelper: ScholarshipDBHelper
    val scholarshipDataList = ArrayList<ScholarshipData>()
    lateinit var outdoorDBHelper: OutdoorDBHelper
    val outdoorActivityDataList = ArrayList<OutdoorActivityData>()
    val dataIOScope = CoroutineScope(Dispatchers.IO)
    val DBParser = ScholarshipDataParser()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigation()
        initOutdoorData()
        initScholarshipData()
        initAlarm()

    }

    private fun initAlarm() {

//        val tempdata = scholarshipDataList[0]
//        tempdata.신청시작 = "2022-05-23"
//        tempdata.신청마감 = "2022-05-24"
//        tempdata.번호 = 994
//        scholarshipDBHelper.insertData(tempdata)
        val temps = "-09-00-0"
        //scholarshipDBHelper.setAllFavoriteAlarm()
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val alarmable = scholarshipDBHelper.getAlarmableContents()
        var flag = false
        var id = -1;
        for (alarmableContents in alarmable) {
            var contentString = ""
            var DDay = DBParser.calculateDateBetween(
                SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(
                    System.currentTimeMillis()
                ).toString(), alarmableContents.신청시작
            )
            if (DDay >= 0) {
                //Toast.makeText(this, DDay.toString(), Toast.LENGTH_SHORT).show()
                contentString = contentString.plus("신청시작 D-Day")
                // contentString = contentString.plus(DDay.toString())
            } else {
                DDay = DBParser.calculateDateBetween(
                    SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(
                        System.currentTimeMillis()
                    ).toString(), alarmableContents.신청마감
                )
                if (DDay >= 0) {
                    //Toast.makeText(this, DDay.toString(), Toast.LENGTH_SHORT).show()
                    contentString = contentString.plus("신청마감 D-1")
                    //contentString = contentString.plus(DDay.toString())
                    flag = true
                } else {
                    continue
                }
            }
            val intent = Intent(this, AlarmReceiver::class.java)
            intent.putExtra("title", alarmableContents.상품명)
            intent.putExtra("contents", contentString.plus(alarmableContents.운영기관명))
            id += 1
            val pendingIntent = PendingIntent.getBroadcast(
                this, AlarmReceiver.NOTIFICATION_ID.plus(id), intent,
                PendingIntent.FLAG_ONE_SHOT
            )
            var triggerTime: Long = 0
            if (!flag) {
                triggerTime = (SimpleDateFormat(
                    "yyyy-MM-dd-HH-mm-ss",
                    Locale.KOREA
                ).parse(alarmableContents.신청시작.plus(temps).plus(id)).time)
            } else {
                triggerTime = (
                        SimpleDateFormat(
                            "yyyy-MM-dd-HH-mm-ss",
                            Locale.KOREA
                        ).parse(alarmableContents.신청마감.plus(temps).plus(id)).time
                                - (3600 * 24 * 1000).toLong())
            }
            Toast.makeText(
                this, SimpleDateFormat(
                    "yyyy-MM-dd-HH-mm-ss",
                    Locale.KOREA
                ).format(triggerTime).toString(), Toast.LENGTH_SHORT
            ).show()
            alarmManager.cancel(pendingIntent)
            if (triggerTime > 0) {
                alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    triggerTime,
                    pendingIntent
                )
            }
        }
        // alarmManager.cancel(pendingIntent)
    }

    private fun initBottomNavigation() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, ScholarshipFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener { item ->
            Log.d("selected",binding.mainBnv.selectedItemId.toString())
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

    private fun initScholarshipData() {
        scholarshipDBHelper = ScholarshipDBHelper(this)
        scholarshipDataList.clear()
        val FStream = InputStreamReader(getResources().openRawResource(R.raw.data));
        val reader = BufferedReader(FStream);
        val read = CSVReader(reader)
        for (scholarship in read) {
            val date = ScholarshipDataParser().stringToDate(scholarship[14]) ?: continue
            if (date.end.before(ScholarshipDataParser.dateFormat.parse("2022-05-11")))
                continue
            scholarshipDataList.add(
                ScholarshipData(
                    scholarship[0].removePrefix(" - ").trim().replace(",", "").toInt(),
                    scholarship[1].removePrefix(" - ").trim(),
                    scholarship[2].removePrefix(" - ").trim(),
                    ScholarshipDataParser().encodeFCat(scholarship[3].removePrefix(" - ").trim()),
                    ScholarshipDataParser().encodeSCat1(scholarship[4].removePrefix(" - ").trim()),
                    ScholarshipDataParser().encodeSCat2(scholarship[5].removePrefix(" - ").trim()),
                    ScholarshipDataParser().encodeSchoolCat(scholarship[6].removePrefix(" - ").replace(" ", "")),
                    ScholarshipDataParser().encodeYear(scholarship[7].removePrefix(" - ").replace(" ", "")),
                    ScholarshipDataParser().encodeDepartment(scholarship[8].removePrefix(" - ").replace(" ", "")),
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
                    false,
                    false
                )
            )
        }
        read.close()
        reader.close()
        FStream.close()
        for (scholarship in scholarshipDataList) {
            scholarshipDBHelper.insertData(scholarship)
        }
        binding.DataTestBtn.setOnClickListener {
            val intent = Intent(this, Temp_DataTestingActivity::class.java)
            intent.putExtra("scholar", scholarshipDataList)
            startActivity(intent)
        }
    }

    private fun initOutdoorData() {
        outdoorDBHelper = OutdoorDBHelper(this)
        outdoorActivityDataList.clear()
        val FStream = InputStreamReader(getResources().openRawResource(R.raw.outdoordata));
        val reader = BufferedReader(FStream);
        val read = CSVReader(reader)
        read.readNext()
        for (outdoor in read) {
            val startDate = OutdoorDataParser().stringToDate(outdoor[6])
            val endDate = OutdoorDataParser().stringToDate(outdoor[7]) ?: continue
            if (endDate.before(OutdoorDataParser.dateFormat.parse("2022-05-27"))) {
                outdoorActivityDataList.add(
                    OutdoorActivityData(
                        outdoor[0].removePrefix(" - ").trim().replace(",", "").toInt(),
                        outdoor[1].removePrefix(" - ").trim(),
                        outdoor[2].removePrefix(" - ").trim(),
                        OutdoorDataParser().encodeFCat(outdoor[3].removePrefix(" - ").trim()),
                        OutdoorDataParser().encodeSCat(outdoor[4].removePrefix(" - ").trim()),
                        OutdoorDataParser().encodeDepartment(outdoor[5].removePrefix(" - ").trim()),
                        SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(startDate),
                        SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(endDate),
                        outdoor[8].removePrefix(" - ").trim(),
                        outdoor[9].removePrefix(" - ").trim(),
                    )
                )
            }
        }
        read.close()
        reader.close()
        FStream.close()
        for (outdoorActivity in outdoorActivityDataList) {
            outdoorDBHelper.insertData(outdoorActivity)
        }
    }

    override fun onResume() {
        super.onResume()

        val bitArray = intent.getIntArrayExtra("filtered_scholarship")

        if (bitArray != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, ScholarshipFragment().apply {
                    arguments = Bundle().apply {
                        putIntArray("filter", bitArray)
                    }
                })
                .commitAllowingStateLoss()

            binding.mainBnv.selectedItemId = R.id.scholarshipFragment
        }

        val bitArray2 = intent.getIntArrayExtra("filtered_outdoor")
        if (bitArray2 != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, OutdoorFragment().apply {
                    arguments = Bundle().apply {
                        putIntArray("filter", bitArray)
                    }
                })
                .commitAllowingStateLoss()

            binding.mainBnv.selectedItemId = R.id.outdoorFragment
        }
    }
}