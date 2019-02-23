package com.zjzx.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.zjzx.dao.PraiseDao;
import com.zjzx.dao.VpariseDao;
import com.zjzx.service.PraiseService;
import com.zjzx.util.DateUtil;

public class  PraiseService{
	@Autowired
	public PraiseDao praiseDao ;
	
	@Autowired
	VpariseDao vpariseDao;
	
	public JSONObject doPraise(Integer itemid, String type, Integer userid) {
		// TODO Auto-generated method stub
				Record praise = praiseDao.getByItemIdAndTypeAndUserid(itemid,type,userid);
				JSONObject resMap = new JSONObject();
				JSONObject result = new JSONObject();
				resMap.put("status", "success");
				resMap.put("result", result);
				if(praise == null ){
					praise = new Record();
					praise.set("itemid", itemid);
					praise.set("type", type);
					praise.set("userid", userid);
					praise.set("praisetime", DateUtil.convertY_M_D_H_M(new Date()));
					praiseDao.savePraise(praise);
					
					result.put("code", "1"); //点赞成功
				}else{
					praiseDao.deletePraise(praise);
					result.put("code", "0"); //取消点赞
				}
				
				long praiseCount = praiseDao.getPraiseCount(itemid,type,userid);
				result.put("count", praiseCount);
				return resMap;
	}

	public JSONObject testPraise(Integer itemid, String type, Integer userid) {
		Record praise = praiseDao.getByItemIdAndTypeAndUserid(itemid,type,userid);
		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		resMap.put("result", "0");
		if(praise != null){
			resMap.put("result", "1");
		}
		return resMap;
	}

	public JSONObject getPraiseCount(Integer itemid, String type) {
		Long count = praiseDao.getPraiseCount(itemid,type);
		Integer vcount = 0;
		if("1".equals(type)){
			vcount = vpariseDao.getVcount(itemid);
		}
		JSONObject resMap = new JSONObject();
		JSONObject result = new JSONObject();
		resMap.put("result", result);
		resMap.put("status", "success");
		result.put("count", count+vcount);
		return resMap;
	}

	public JSONObject getPraiseList(Integer itemid, String type, Integer page,
			Integer size) {
		// TODO Auto-generated method stub\
		Page<Record> pageObj = praiseDao.getPraiseList(itemid,type,page,size);
		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		resMap.put("recordPage", pageObj);
		return resMap;
	}

}
