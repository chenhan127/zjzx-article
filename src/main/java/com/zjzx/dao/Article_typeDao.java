package com.zjzx.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
@Component
public class Article_typeDao {
	
	//public static final Article_typeDao article_typeDao = new Article_typeDao();
	
	public Map<Integer, String> getArticleTypeMap() {
		// TODO Auto-generated method stub
		List<Record> article_types = Db.find("select * from article_type");
		Map<Integer, String> dic = new HashMap<Integer, String>();
		if(article_types == null || article_types.size()<=0){
			return dic;
		}
		
		for(Record article_type:article_types){
			dic.put(article_type.getInt("typecode"), article_type.getStr("typename"));
		}
		return dic;
	}
	
	

}
