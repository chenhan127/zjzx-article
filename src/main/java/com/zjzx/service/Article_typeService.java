package com.zjzx.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.zjzx.dao.Article_typeDao;
//import com.zjzx.redis.Article_typeRedis;
import com.zjzx.service.Article_typeService;
import com.zjzx.util.JSONUtil;

public class Article_typeService{
	@Autowired
	private  Article_typeDao article_typeDao ;
//	@Autowired
//	private Article_typeRedis article_typeRedis;
	public JSONObject getArticleTypeMap() {
		//removeRedis();
		// TODO Auto-generated method stub
		// String key = "article_type_dic";
//		Map<Integer,String> dic = article_typeRedis.getArticleTypeMap();
//		if(dic == null){
//			dic = article_typeDao.getArticleTypeMap();
//			article_typeRedis.setArticleTypeMap(dic);
//		}
		Map<Integer,String> dic =  article_typeDao.getArticleTypeMap();
		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		resMap.put("article_typeDic", dic);
		return JSONUtil.parseJSON(resMap);
	}

}
