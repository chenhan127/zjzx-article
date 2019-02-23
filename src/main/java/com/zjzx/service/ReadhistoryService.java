package com.zjzx.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.zjzx.dao.ReadhistoryDao;
import com.zjzx.service.ReadhistoryService;
import com.zjzx.util.DateUtil;
import com.zjzx.util.JSONUtil;

public class ReadhistoryService {
	@Autowired
	public ReadhistoryDao readhistoryDao ;
	public JSONObject addReadHistory(Integer userid, Integer articleid) {
		Record readhistory = readhistoryDao.getReadHistoryByUseridAndArticleid(userid,articleid);
		if(readhistory == null){
			readhistory = new Record();
			readhistory.set("userid", userid);
			readhistory.set("articleid", articleid);
			readhistory.set("readtime", DateUtil.convertY_M_D_H_M(new Date()));
			readhistoryDao.saveReadhistory(readhistory);
		}else{
			readhistory.set("readtime", DateUtil.convertY_M_D_H_M(new Date()));
			readhistoryDao.updateReadHistory(readhistory);
		}
		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		return resMap;
	}
	public JSONObject clearHistory(Integer[] ids) {
		// TODO Auto-generated method stub
		readhistoryDao.clearHistory(ids);
		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		return resMap;
	}
	public JSONObject getReadHistory(Integer userid, Integer page, Integer size) {
		// TODO Auto-generated method stub
		Page<Record> pageObj = readhistoryDao.getReadHistory(userid,page,size);
		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		resMap.put("recordPage", pageObj);
		return JSONUtil.parseJSON(resMap);
	}
	public JSONObject getReadCount(Integer articleid) {
		// TODO Auto-generated method stub
		Long count = readhistoryDao.getReadCount(articleid);
		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		resMap.put("count", count);
		return resMap;
	}

}
