package com.zjzx.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.zjzx.annotation.EmptyCheck;
import com.zjzx.service.PraiseService;


@org.springframework.stereotype.Controller
@RequestMapping("/praise")
@ResponseBody
public class PraiseController{
	@Autowired
	private PraiseService praiseService; 
	/**
	 * 点赞/取消点赞
	 */
	@RequestMapping("/doPraise")
	@EmptyCheck({"itemid"})
	public JSONObject doPraise(Integer itemid,String type,Integer userid){
		JSONObject resMap = praiseService.doPraise(itemid,type,userid);
		return resMap;
		
	}
	/**
	 * 判断用户是否点赞
	 */
	@RequestMapping("/testPraise")
	public JSONObject testPraise(Integer itemid,String type,Integer userid){
		JSONObject resMap = praiseService.testPraise(itemid,type,userid);
		return resMap;
	}
	/**
	 * 获取点赞数量  文章/评论
	 */
	@RequestMapping("/getPraiseCount")
	public JSONObject getPraiseCount(Integer itemid,String type){
		JSONObject resMap = praiseService.getPraiseCount(itemid,type);
		return resMap;
	}
	/**
	 * 获取 点赞列表
	 * @param itemid
	 * @param type
	 * @return
	 */
	@RequestMapping("/getPraiseList")
	public JSONObject getPraiseList(Integer itemid,String type,Integer page,Integer size){
		JSONObject resMap = praiseService.getPraiseList(itemid,type,page,size);
		return resMap;
	}

}
