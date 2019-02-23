package com.zjzx.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Record;
import com.zjzx.dao.NotLikeDao;
import com.zjzx.service.NotLikeService;

public class NotLikeService{
	@Autowired
	private NotLikeDao notLikeDao;
	
	public JSONObject doNotLike(Integer articleid, Integer userid) {
		// TODO Auto-generated method stub
		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		Record notLike = notLikeDao.getNotLikeByUserAndArtile(userid,articleid);
		if(notLike != null){
			return resMap;
		}
		notLikeDao.doNotLike(articleid,userid);
		return resMap;
	}

}
