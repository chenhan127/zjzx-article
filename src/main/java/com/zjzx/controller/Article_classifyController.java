package com.zjzx.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Record;
import com.zjzx.annotation.TokenAop;
import com.zjzx.service.Article_classifyService;
@org.springframework.stereotype.Controller
@RequestMapping("/article_classify")
@ResponseBody
public class Article_classifyController {
	@Autowired
	private Article_classifyService article_classifyService; 
	
	@RequestMapping("/getArticleClassifyMap")
	public JSONObject getArticleClassifyMap(){
		JSONObject resMap = article_classifyService.getArticleClassifyMap();
		return resMap;
	}
	/**
	 * 获取文章分类列表
	 */
	@RequestMapping("/getArticleClassifyList")
	public JSONObject getArticleClassifyList(){
		
		JSONObject resMap = article_classifyService.getArticleClassifyList();
		return resMap;
		
	}
	/**
	 * 添加文章分类
	 */
	@RequestMapping("/saveArticleClassify")
	@TokenAop
	public JSONObject saveArticleClassify(String record){
		JSONObject recordJSON = JSONObject.parseObject(record);
		//Article_classify article_classify = new Article_classify();
		Record article_classify = new Record();
		article_classify.setColumns(recordJSON);
		JSONObject resMap =  article_classifyService.saveArticleClassify(article_classify);
		return resMap;
		
	}

}
