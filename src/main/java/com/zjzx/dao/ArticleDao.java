package com.zjzx.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
@Repository
public class ArticleDao {
	
	//public static final ArticleDao articleDao = new ArticleDao();
	
	public Page<Record> getTwPage(Integer page, int size, JSONObject params) {
		// TODO Auto-generated method stub
		params.put("type", 1);
		return articlePage(page, size, params);
	}
	
	public Record getSpRecord(Integer page, JSONObject params) {
		params.put("type", 2);
		Page<Record> pageObj = articlePage(page, 1, params);
		List<Record> articles = pageObj.getList();
		if(articles != null && articles.size()>0){
			return articles.get(0);
		}
		return null;
	}
	
	public List<Record> getSpPage(Integer page, Integer size,
			JSONObject params) {
		params.put("type", 2);
		Page<Record> pageObj = articlePage(page, size, params);
		return pageObj.getList();
	}
	public Page<Record> articlePage(Integer page, Integer size,
			JSONObject params) {
		// TODO Auto-generated method stub
		String keyword = params.getString("keyword");
		Integer author = params.getInteger("author");
		String authorname = params.getString("authorname");
		Integer type = params.getInteger("type");
		String publisharea = params.getString("publisharea");
		Integer state = params.getInteger("state");
		Integer classify = params.getInteger("classify");
		
		Integer today = params.getInteger("today");
		if(state == null){
			state = 3;
		}
		Integer parentid = params.getInteger("parentid");
		StringBuffer sqlExceptSelect = new StringBuffer(" from article where state = ? and parentid=?");
		List<Object> paramArray = new ArrayList<Object>();
		paramArray.add(state);
		paramArray.add(parentid);
		if(keyword !=null && !"".equals(keyword)){
			sqlExceptSelect.append(" and (title like ? or content like ?)");
			paramArray.add("%"+keyword+"%");
			paramArray.add("%"+keyword+"%");
		}
		
		if(author != null ){
			sqlExceptSelect.append(" and author =?");
			paramArray.add(author);
		}
		
		if(type != null){
			String str = " and type =?";
			if(type != 2){
				str = " and type !=?";
			}
			sqlExceptSelect.append(str);
			paramArray.add(2);
		}
		
		if(classify != null){
			sqlExceptSelect.append(" and classify =?");
			paramArray.add(classify);
		}
		
		if(authorname != null){
			String ids = getUserIdsByname(authorname);
			if(ids != null){
				sqlExceptSelect.append(" and author in "+ids);
			}
		}
		
		if(publisharea != null && !"".equals(publisharea)){
			sqlExceptSelect.append(" and publisharea =?");
			paramArray.add(publisharea);
		}
		
		sqlExceptSelect.append(" and today =?");
		if(today == null){
			today = 0;
		}
		paramArray.add(today);
		sqlExceptSelect.append(" order by publishtime desc,weight desc ");
		Page<Record> pageObj =  Db.paginate(page, size, "select * ", sqlExceptSelect.toString(),paramArray.toArray());
		return pageObj;
	}
	
	
	public Page<Record> getTop100(JSONObject params) {
		// TODO Auto-generated method stub
		String keyword = params.getString("keyword");
		Integer author = params.getInteger("author");
		String authorname = params.getString("authorname");
		Integer type = params.getInteger("type");
		String publisharea = params.getString("publisharea");
		Integer state = params.getInteger("state");
		Integer classify = params.getInteger("classify");
		
		Integer today = params.getInteger("today");
		if(state == null){
			state = 3;
		}
		Integer parentid = params.getInteger("parentid");
		StringBuffer sqlExceptSelect = new StringBuffer(" from article where state = ? and parentid=?");
		List<Object> paramArray = new ArrayList<Object>();
		paramArray.add(state);
		paramArray.add(parentid);
		if(keyword !=null && !"".equals(keyword)){
			sqlExceptSelect.append(" and (title like ? or content like ?)");
			paramArray.add("%"+keyword+"%");
			paramArray.add("%"+keyword+"%");
		}
		
		if(author != null ){
			sqlExceptSelect.append(" and author =?");
			paramArray.add(author);
		}
		
		if(type != null){
			String str = " and type =?";
			if(type != 2){
				str = " and type !=?";
			}
			sqlExceptSelect.append(str);
			paramArray.add(2);
		}
		
		if(classify != null){
			sqlExceptSelect.append(" and classify =?");
			paramArray.add(classify);
		}
		
		if(authorname != null){
			String ids = getUserIdsByname(authorname);
			if(ids != null){
				sqlExceptSelect.append(" and author in "+ids);
			}
		}
		
		if(publisharea != null && !"".equals(publisharea)){
			sqlExceptSelect.append(" and publisharea =?");
			paramArray.add(publisharea);
		}
		
		sqlExceptSelect.append(" and today =?");
		if(today == null){
			today = 0;
		}
		paramArray.add(today);
		sqlExceptSelect.append(" order by publishtime desc,weight desc ");
		
		Page<Record> pageObj =  Db.paginate(1, 100, "select * ", sqlExceptSelect.toString(),paramArray.toArray());
		return pageObj;
	}
	

	public Page<Record> getCollectArticlePage(Integer page, Integer size,
			Integer userid) {
		// TODO Auto-generated method stub
		StringBuffer sqlExceptSelect = new StringBuffer(" from article inner join article_collect on(article_collect.articleid = article.id ) where article.state=3 ");
		sqlExceptSelect.append(" and article_collect.userid =? and article_collect.type = 0");
		
		sqlExceptSelect.append(" order by article_collect.id desc ");
		Page<Record> pageObj =  Db.paginate(page, size, "select article.* ", sqlExceptSelect.toString(),userid);
		return pageObj;
	}

	public Integer getArticleCommentCount(Integer articleid) {
		String sql = "select count(id) from article_comment where  articleid =?";
		Long amount = Db.queryLong(sql,articleid);
		Integer some = amount.intValue();
		return some;
	}

	public Page<Record> getArticleByUser(Integer page,Integer size,Integer userid, Integer type,Integer parentid) {
		// TODO Auto-generated method stub
		List<Object> params = new ArrayList<Object>();
		StringBuffer sqlExceptSelect = new StringBuffer(" from article where author = ? and  parentid=?");
		params.add(userid);
		params.add(parentid);
		if(type != null){
			sqlExceptSelect.append(" and type=?");
			params.add(type);
		}
		sqlExceptSelect.append(" order by publishtime desc ");
		Page<Record> pageObj = Db.paginate(page, size, "select * ", sqlExceptSelect.toString(),params.toArray());
		return pageObj;
	}

	public Long getUserArticleCount(Integer userid,Integer parentid) {
		// TODO Auto-generated method stub
		String sql = "select count(id) from article where author =? and parentid=? and state=?";
		Long count = Db.queryLong(sql,userid,parentid,3);
		return count;
	}
	
	
	private String getUserIdsByname(String authorname) {
		// TODO Auto-generated method stub
		String sql = "select id from user where truename like ?";
		List<Integer> ids = Db.query(sql, "%"+authorname+"%");
		if(ids == null || ids.size()<=0){
			return null;
		}
		
		return ids.toString().replaceAll("\\[", "(").replaceAll("\\]", ")");
	}
	@Cacheable(key="#recordid",value="article")
	public Record findById(Integer recordid) {
		// TODO Auto-generated method stub
		System.out.println("从数据库获取");
		return Db.findById("article",recordid);
	}
	@CacheEvict(key="#articleid",value="article")
	public void deleteById(Integer articleid) {
		// TODO Auto-generated method stub
		Db.deleteById("article",articleid);
	}

	public List<Record> getListLit(int line) {
		// TODO Auto-generated method stub
		String sql = "select * from article where state=? limit ?";
		List<Record> list = Db.find(sql, 3,line);
		return list;
	}
	@CacheEvict(key="#record.getInt('id')",value="article")
	public void updateArticle(Record record) {
		Db.update("article",record);
		// TODO Auto-generated method stub
	}

	public List<Record> getTodayArticle() {
		// TODO Auto-generated method stub
		String sql = "select * from article where parentid=0 order by weight desc,publishtime desc limit 2";
		List<Record> list = Db.find(sql);
		return list;
	}

	public List<Record> getTjsp(Integer classify) {
		// TODO Auto-generated method stub
		String sql = "select * from article where type =2 and classify=? limit 100";
		List<Record> list = Db.find(sql,classify);
		return list;
	}

	public List<Record> getTjwz(Integer classify) {
		// TODO Auto-generated method stub
		String sql = "select * from article where type !=2 and classify=? limit 100";
		List<Record> list = Db.find(sql,classify);
		return list;
	}

	public int saveArticle(Record article) {
		// TODO Auto-generated method stub
		Db.save("article", article);
		//article.save();
		return article.getInt("id");
		
	}

	

	

	

	

}
