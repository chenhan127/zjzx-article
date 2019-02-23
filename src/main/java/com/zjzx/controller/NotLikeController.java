package com.zjzx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.zjzx.annotation.TokenAop;
import com.zjzx.service.NotLikeService;


@Controller
@RequestMapping("/notlike")
@ResponseBody
public class NotLikeController {
	@Autowired
	private NotLikeService notLikeService;
	
	
	@RequestMapping("/doNotLike")
	@TokenAop
	public JSONObject doNotLike(Integer userid,Integer articleid){
		JSONObject resMap = notLikeService.doNotLike(articleid, userid);
		return resMap;
	}

}
