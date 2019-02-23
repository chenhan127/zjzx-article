package com.zjzx.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Record;
import com.zjzx.dao.Article_classifyDao;
//import com.zjzx.redis.Article_classifyRedis;
import com.zjzx.service.Article_classifyService;
import com.zjzx.util.JSONUtil;
@Service
public class Article_classifyService {
	@Autowired
	private Article_classifyDao article_classifyDao ;
//	@Autowired
//	private Article_classifyRedis article_classifyRedis;
	
	public JSONObject getArticleClassifyMap() {
		//removeRedis();
				// TODO Auto-generated method stub
//		Map<Integer,String> dic = article_classifyRedis.getArticleClassifyMap();
//		if(dic == null){
//			dic = article_classifyDao.getArticleClassifyMap();
//			article_classifyRedis.setArticleClassifyMap(dic);
//		}
		Map<Integer,String> dic = article_classifyDao.getArticleClassifyMap();
		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		resMap.put("article_classfyDic", dic);
		return JSONUtil.parseJSON(resMap);
	}

	public JSONObject getArticleClassifyList() {
//		List<Record> list = article_classifyRedis.getArticleClassifyList();
//		if(list == null){
//			list = article_classifyDao.getArticleClassifyList();
//			article_classifyRedis.setArticleClassifyList(list);
//		}
		List<Record> list = article_classifyDao.getArticleClassifyList();
//		Record record = new Record();
//		record.set("name", "测试");		
//		record.set("sex", "1");
//		Db.save("test", record);
		
		JSONObject resMap = new JSONObject();
		JSONObject result = new JSONObject();
		resMap.put("status", "success");
		result.put("classfyList", list);
		resMap.put("result", result);
		return JSONUtil.parseJSON(resMap);
	}

	public JSONObject saveArticleClassify(Record article_classify) {
		// TODO Auto-generated method stub
		Integer id = article_classify.getInt("id");
		
		if(id == null){
			article_classifyDao.saveArticleClassify(article_classify);
		}
		article_classify.set("classifycode", id);
		article_classifyDao.updateArticleClassify(article_classify);
		//article_classifyRedis.deleteArticleClassifyList();
		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		return JSONUtil.parseJSON(resMap);
	}

}
