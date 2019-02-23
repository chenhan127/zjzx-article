package com.zjzx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.zjzx.service.Article_typeService;


@org.springframework.stereotype.Controller
@RequestMapping("/article_type")
@ResponseBody
public class Article_typeController {
	@Autowired
	private Article_typeService article_typeService; 
	
	public JSONObject getArticleTypeMap(){
		JSONObject resMap = article_typeService.getArticleTypeMap();
		return resMap;
	}
	

}
