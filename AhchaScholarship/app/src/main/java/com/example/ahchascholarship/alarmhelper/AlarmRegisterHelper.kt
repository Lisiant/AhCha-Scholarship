package com.example.ahchascholarship.alarmhelper

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import com.example.ahchascholarship.MainActivity
import com.example.ahchascholarship.MainActivity.Companion.alarmManager
import com.example.ahchascholarship.MainActivity.Companion.mainContext
import com.example.ahchascholarship.ScholarshipDBHelper
import com.example.ahchascholarship.ScholarshipData
import com.example.ahchascholarship.ScholarshipDataParser
import com.example.ahchascholarship.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.contracts.contract

class AlarmRegisterHelper:AppCompatActivity() {
	val DBParser = ScholarshipDataParser()
	lateinit var scholarshipDBHelper:ScholarshipDBHelper
	override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
		super.onCreate(savedInstanceState, persistentState)
	}
	fun setAlarm(offOn:Boolean, alarmableContents:ScholarshipData) {
		val temps = "-09-00-0"
		var flag = false
		var id = -1;
		var contentString = ""
		var DDay = DBParser.calculateDateBetween(
			SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(
				System.currentTimeMillis()).toString(), alarmableContents.신청시작)
			if(DDay >= 0)
			{
				//Toast.makeText(this, DDay.toString(), Toast.LENGTH_SHORT).show()
				contentString = contentString.plus("신청시작 D-Day")
				// contentString = contentString.plus(DDay.toString())
			}
			else {
				DDay = DBParser.calculateDateBetween(
					SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(
					System.currentTimeMillis()).toString(), alarmableContents.신청마감)
				if (DDay >= 0) {
					//Toast.makeText(this, DDay.toString(), Toast.LENGTH_SHORT).show()
					contentString = contentString.plus("신청마감 D-1")
					//contentString = contentString.plus(DDay.toString())
					flag = true
				}
				else {
					return
				}
			}
		val intent = Intent(mainContext, AlarmReceiver::class.java)
			intent.putExtra("title", alarmableContents.상품명)
			intent.putExtra("contents", contentString.plus(alarmableContents.운영기관명))
			id += 1
			val pendingIntent = PendingIntent.getBroadcast(
				mainContext, AlarmReceiver.NOTIFICATION_ID.plus(id), intent,
				PendingIntent.FLAG_ONE_SHOT
			)
			var triggerTime :Long= 0
			if(!flag) {
				triggerTime = (SimpleDateFormat(
					"yyyy-MM-dd-HH-mm-ss",
					Locale.KOREA
				).parse(alarmableContents.신청시작.plus(temps).plus(id)).time)
			}
			else {
				triggerTime = (
						SimpleDateFormat(
							"yyyy-MM-dd-HH-mm-ss",
							Locale.KOREA
						).parse(alarmableContents.신청마감.plus(temps).plus(id)).time
								-(3600*24*1000).toLong())
			}
			Toast.makeText(
				mainContext, SimpleDateFormat(
				"yyyy-MM-dd-HH-mm-ss",
				Locale.KOREA
			).format(triggerTime).toString(), Toast.LENGTH_SHORT).show()
			alarmManager.cancel(pendingIntent)
		if(alarmableContents.alarmCheck && alarmableContents.favorite){
			if(triggerTime > 0) {
				alarmManager.set(
					AlarmManager.RTC_WAKEUP,
					triggerTime,
					pendingIntent
				)
			}
		}
		else{
			Toast.makeText(
				mainContext, "off", Toast.LENGTH_SHORT).show()
		}
		// alarmManager.cancel(pendingIntent)
	}
}