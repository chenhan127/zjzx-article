package com.zjzx.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.zjzx.dao.InterlocutionDao;
import com.zjzx.service.InterlocutionService;
import com.zjzx.util.DateUtil;
import com.zjzx.util.JSONUtil;

public class InterlocutionService{
	@Autowired
	private InterlocutionDao interlocutionDao;

	public JSONObject interlocutionService(String title, String description,
			String images, Integer userid,Integer classify) {
		// TODO Auto-generated method stub
		Record record = new Record();
		record.set("title", title);
		record.set("description", description);
		record.set("images", images);
		record.set("userid", userid);
		record.set("classify", classify);
		record.set("createtime", DateUtil.convertY_M_D_H_M(new Date()));
		interlocutionDao.saveQuestion(record);
		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		resMap.put("itemid", record.getInt("id"));
		return resMap;
	}

	public JSONObject getQuestionPage(Integer page, Integer size, Integer userid,Integer classify) {
		// TODO Auto-generated method stub
		Page<Record> pageObj = interlocutionDao.getQuestionPage(page,size,userid,classify);
		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		resMap.put("recordPage", pageObj);
		return JSONUtil.parseJSON(resMap);
	}

	public JSONObject deleteQuestion(Integer[] ids) {
		JSONObject resMap = new JSONObject();
		// TODO Auto-generated method stub
		System.out.println(ids);
		System.out.println("------------1");
		if(ids == null){
			resMap.put("status", "error");
			resMap.put("tip", "无删除项");
			
			System.out.println("------------2");
			return resMap;
		}
		System.out.println("------------3");
		for(Integer id:ids){
			interlocutionDao.deleteQuestionById(id);
		}
		resMap.put("status", "success");
		
		return resMap;
	}

	public JSONObject getQuestionById(Integer itemid) {
		// TODO Auto-generated method stub
//		Record record = interlocutionDao.getQuestionById(itemid);
//		JSONObject resMap = new JSONObject();
		return null;
	}

	public JSONObject getUserQuestionCount(Integer userid) {
		// TODO Auto-generated method stub
		Integer count = interlocutionDao.getUserQuestionCount(userid);
		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		resMap.put("count", count);
		return resMap;
	}

}
