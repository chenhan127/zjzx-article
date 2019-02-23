package com.zjzx.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Record;
import com.zjzx.dao.Article_fileDao;
import com.zjzx.service.Article_fileService;
import com.zjzx.util.JSONUtil;

public class Article_fileService{
	@Autowired
	public  Article_fileDao article_fileDao ;
	public JSONObject getFileByArticle(Integer articleid) {
		// TODO Auto-generated method stub
		List<Record> article_files = article_fileDao.getFileByArticle(articleid);
		JSONObject resMap = new JSONObject();
		JSONObject result = new JSONObject();
		resMap.put("status", "success");
		result.put("filelist", article_files);
		resMap.put("result", result);
		return JSONUtil.parseJSON(resMap);
	}

}
