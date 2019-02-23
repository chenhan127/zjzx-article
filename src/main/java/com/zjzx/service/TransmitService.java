package com.zjzx.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.zjzx.dao.TransmitDao;
import com.zjzx.service.TransmitService;
import com.zjzx.util.DateUtil;
import com.zjzx.util.JSONUtil;

public class  TransmitService {
	@Autowired
	private TransmitDao transmitDao;

	public JSONObject addTransmitRecord(Integer articleid, Integer userid) {
		// TODO Auto-generated method stub
		Record record = new Record();
		record.set("articleid", articleid);
		record.set("userid", userid);
		record.set("createtime", DateUtil.convertY_M_D_H_M(new Date()));
		transmitDao.addTransmitRecord(record);
		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		return resMap;
	}

	public JSONObject getTransmitList(Integer articleid, Integer page,
			Integer size) {
		// TODO Auto-generated method stub
		Page<Record> pageObj = transmitDao.getTransmitList(articleid,page,size);
		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		resMap.put("recordPage", pageObj);
		return JSONUtil.parseJSON(resMap);
	}

}
