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
		val temps = "-09-00-"
		var flag = false
//		var id = -1;
		var contentStringS = ""
		var contentStringE = ""
		val DDayS = DBParser.calculateDateBetween(
			SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(
				System.currentTimeMillis()).toString(), alarmableContents.신청시작)
		val DDayE = DBParser.calculateDateBetween(
				SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(
					System.currentTimeMillis()).toString(), alarmableContents.신청마감)
		contentStringS = contentStringS.plus("신청시작 D-Day | ")
		contentStringE = contentStringE.plus("신청마감 D-1 | ")
		val intentS = Intent(mainContext, AlarmReceiver::class.java)
		val intentE = Intent(mainContext, AlarmReceiver::class.java)
		intentS.putExtra("title", alarmableContents.상품명)
		intentS.putExtra("contents", contentStringS.plus(alarmableContents.운영기관명))
		intentE.putExtra("title", alarmableContents.상품명)
		intentE.putExtra("contents", contentStringE.plus(alarmableContents.운영기관명))
		val pendingIntentS = PendingIntent.getBroadcast(
			mainContext, AlarmReceiver.NOTIFICATION_ID+(alarmableContents.번호), intentS,
			PendingIntent.FLAG_ONE_SHOT
		)
		val pendingIntentE = PendingIntent.getBroadcast(
			mainContext, AlarmReceiver.NOTIFICATION_ID+(alarmableContents.번호*1000), intentE,
			PendingIntent.FLAG_ONE_SHOT
		)
		val triggerTimeS :Long= SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.KOREA)
			.parse(alarmableContents.신청시작.plus(temps).plus("00")).time
		val triggerTimeE :Long= SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.KOREA)
			.parse(alarmableContents.신청마감.plus(temps).plus("10")).time - (3600*24*1000).toLong()
//		Toast.makeText(
//			mainContext,DDayS.toString()+","+DDayE.toString()+alarmableContents.alarmCheck+alarmableContents.favorite, Toast.LENGTH_SHORT).show()
		if(alarmableContents.alarmCheck && alarmableContents.favorite){

			if(DDayS >= 0) {
				alarmManager.setExact(
					AlarmManager.RTC_WAKEUP,
					triggerTimeS,
					pendingIntentS
				)
			}
			if(DDayE >= 1) {
				alarmManager.setExact(
					AlarmManager.RTC_WAKEUP,
					triggerTimeE,
					pendingIntentE
				)
//				Toast.makeText(
//					mainContext, alarmableContents.상품명+"\nAlarm: On", Toast.LENGTH_SHORT).show()
			}
		}
		else{
			alarmManager.cancel(pendingIntentS)
			alarmManager.cancel(pendingIntentE)
//			Toast.makeText(
//				mainContext, alarmableContents.상품명+"\nAlarm: Off", Toast.LENGTH_SHORT).show()
		}
	}
}