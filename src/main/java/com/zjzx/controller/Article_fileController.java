package com.zjzx.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.zjzx.annotation.EmptyCheck;
import com.zjzx.service.Article_fileService;
@org.springframework.stereotype.Controller
@RequestMapping("/article_file")
@ResponseBody
public class Article_fileController{
	@Autowired
	private Article_fileService article_fileService; 
	/**
	 * 获取文章附件
	 */
	@RequestMapping("/getFileByArticle")
	@EmptyCheck({"articleid"})
	public JSONObject getFileByArticle(Integer articleid){
		JSONObject resMap = article_fileService.getFileByArticle(articleid);
		return resMap;
	}

}
