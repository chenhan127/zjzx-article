package com.zjzx.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
@Component
public class Article_fileDao {
	
	//public static final Article_fileDao article_fileDao = new Article_fileDao();
	
	public List<Record> getFileByArticle(Integer articleid) {
		// TODO Auto-generated method stub
		String sql = "select * from article_file where articleid =?";
		List<Record> article_files = Db.find(sql, articleid);
		
		return article_files;
	}
	
	public void deleteFileByArticleId(Integer articleid){
		String sql = "delete from article_file where articleid =?";
		Db.delete(sql, articleid);
	}

	public void saveFile(Record article_file) {
		// TODO Auto-generated method stub
		Db.save("article_file", article_file);
	}
}
