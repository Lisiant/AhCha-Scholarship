package com.example.ahchascholarship

import java.util.*

data class OutdoorActivityData(
	var 번호 :Int,
	var 활동이름 :String,
	var 운영기관명 :String,
	var 운영기관종류:Int, // 민간기업, 공공기업, 지자체, 정부기관, 공공재단, ...
	var 활동종류 :Int, // 인턴, 공모전, 봉사, ...
	var 활동내용계열 :Int, // 공학계열,자연계열,인문계열...
	var 신청시작 :String, // yyyy-MM-dd
	var 신청마감 :String, // yyyy-MM-dd
	var 사이트링크 :String,
	var 상세 :String
)
