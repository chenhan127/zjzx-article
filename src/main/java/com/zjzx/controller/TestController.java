//package com.zjzx.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.alibaba.fastjson.JSONObject;
//import com.zjzx.model.Article_classify;
//import com.zjzx.service.ArticleService;
//import com.zjzx.service.Article_classifyService;
//
//@Controller
//@RequestMapping("test")
//@ResponseBody
//public class TestController {
//	
//	@Autowired
//	private ArticleService articleService;
//	
//	
//	@Autowired
//	private Article_classifyService article_classifyService;
//	@RequestMapping("testCache")
//	public JSONObject testCache(Integer recordid){
//		return articleService.getArticleById(recordid);
//	}
//	@RequestMapping("getClassify")
//	public JSONObject getClassify(){
//		return article_classifyService.getArticleClassifyMap();
//	}
//	
////	public JSONObject publishTest(){
////		//articleService.publishArticle(article, filearray);
////		return null;
////	}
//	
//
//}
