package com.zjzx.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.zjzx.annotation.EmptyCheck;
import com.zjzx.annotation.TokenAop;
import com.zjzx.service.Article_collectService;
@org.springframework.stereotype.Controller
@RequestMapping("/article_collect")
@ResponseBody
public class Article_collectController {
	@Autowired
	private Article_collectService article_collectService; 
	
	@RequestMapping("/articleCollect")
	@TokenAop
	@EmptyCheck({"articleid","userid"})
	public JSONObject articleCollect(Integer articleid,Integer userid){
		JSONObject resMap =  article_collectService.articleCollect(articleid,userid);
		return resMap;
	}
	@RequestMapping("/deleteCollect")
	@TokenAop
	@EmptyCheck({"userid"})
	public JSONObject deleteCollect(@RequestParam("ids[]") Integer[] ids,Integer userid){
		JSONObject resMap =  article_collectService.deleteCollect(ids,userid);
		return resMap;
	}
	/**
	 * 判断文章是否收藏
	 */
	@RequestMapping("/testCollect")
	@EmptyCheck({"articleid","userid"})
	public JSONObject testCollect(Integer articleid,Integer userid){
		JSONObject resMap = article_collectService.testCollect(articleid,userid);
		return resMap;
	}
	/**
	 * 获取用户收藏的文章数量
	 */
	@RequestMapping("/getUserArticleCollectCount")
	@EmptyCheck({"userid"})
	public JSONObject getUserArticleCollectCount(Integer userid ){
		JSONObject resMap = article_collectService.getUserArticleCollectCount(userid);
		return  resMap;
	}
	@RequestMapping("/getCollectPage")
	@EmptyCheck({"page","size","userid"})
	public JSONObject getCollectPage(Integer page,Integer size,Integer userid){
		JSONObject resMap = article_collectService.getCollectPage(page,size,userid);
		return resMap;
		
	}

	
}
