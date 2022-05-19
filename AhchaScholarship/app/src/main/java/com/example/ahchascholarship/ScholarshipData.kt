package com.example.ahchascholarship

import java.io.Serializable
import java.util.*

data class ScholarshipData (var 번호 :Int,
						   var 운영기관명 :String,
						   var 상품명 :String,
						   var 운영기관구분 :Int,
						   var 상품구분 :Int,
						   var 학자금유형구분 :Int,
						   var 대학구분 :Int,
						   var 학년구분 :Int,
						   var 학과구분 :Int,
						   var 성적기준 :String,
						   var 소득기준 :String,
						   var 지원금액 :String,
						   var 특정자격 :String,
							var 지역거주여부 :String,
							var 신청시작 :String,
							var 신청마감 :String,
						   var 선발방법 :String,
						   var 선발인원 :String,
						   var 자격제한 :String,
						   var 추천필요여부 :String,
						   var 제출서류 :String,
						   var selected :Boolean): Serializable

data class StartEndDate(var start: Date, var end: Date)
