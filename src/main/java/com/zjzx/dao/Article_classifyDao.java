package com.zjzx.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
@Component
public class Article_classifyDao {
	
	//public static final Article_classifyDao article_classifyDao = new Article_classifyDao();
	
	@Cacheable(key="11",value="article_classify")
	public Map<Integer, String> getArticleClassifyMap() {
		// TODO Auto-generated method stub
		List<Record> article_classifys = Db.find("select * from Article_classify");
		Map<Integer, String> dic = new HashMap<Integer, String>();
		if(article_classifys == null || article_classifys.size()<=0){
			return dic;
		}
		for(Record article_type:article_classifys){
			dic.put(article_type.getInt("classifycode"), article_type.getStr("classifyname"));
		}
		return dic;
	}
	@Cacheable(key="12",value="article_classify")
	public List<Record> getArticleClassifyList() {
		// TODO Auto-generated method stub
		List<Record> article_classifys = Db.find("select classifycode,classifyname from Article_classify");
		return article_classifys;
	}
	@CacheEvict(value="article_classify",allEntries=true)
	public void saveArticleClassify(Record article_classify) {
		// TODO Auto-generated method stub
		Db.save("article_classify", article_classify);
		
	}
	@CacheEvict(value="article_classify",allEntries=true)
	public void updateArticleClassify(Record article_classify) {
		// TODO Auto-generated method stub
		Db.update("article_classify", article_classify);
	}

}
