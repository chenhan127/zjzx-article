package com.zjzx.dao;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
@Component
public class Article_commentDao {
	
	//public static final Article_commentDao article_commentDao = new Article_commentDao();
	
	

	public Page<Record> getArticleCommentPage(Integer page, Integer size,
			Integer articleid) {
		StringBuffer sqlExceptSelect = new StringBuffer(" from article_comment where type = 1 ");
		sqlExceptSelect.append(" and articleid = ? order by commenttime desc");
		Page<Record> pageObj =  Db.paginate(page, size, "select * ", sqlExceptSelect.toString(),articleid);
		return pageObj;
	}

	public Integer getReplyCount(Integer commentid) {
		String sql = "select count(id) from article_comment where  commentid =? and type = 2";
		Long amount = Db.queryLong(sql,commentid);
		Integer some = amount.intValue();
		return some;
	}

	public Page<Record> getReplyList(Integer page, Integer size,
			Integer commentid) {
		StringBuffer sqlExceptSelect = new StringBuffer(" from article_comment where type = 2 ");
		sqlExceptSelect.append(" and commentid = ? order by commenttime desc");
		Page<Record> pageObj =  Db.paginate(page, size, "select * ", sqlExceptSelect.toString(),commentid);
		return pageObj;
	}
	@Cacheable(key="#articleid+'count'",value="article_comment")
	public Integer getArticleCommentCount(Integer articleid) {
		String sql = "select count(id) from article_comment where  articleid =? and type=1";
		Long amount = Db.queryLong(sql,articleid);
		Integer some = amount.intValue();
		return some;
	}
	/**
	 * 删除该文章的评论数据
	 * @param articleid
	 */
	@CacheEvict(value="article_comment",allEntries=true)
	public void deleteCommentByArticleId(Integer articleid) {
		// TODO Auto-generated method stub
		String sql ="delete from article_comment where articleid =?";
		Db.update(sql, articleid);
	}
	/**
	 * 根据评论删除回复id
	 * @param commonid
	 */
	public void deleteHfByCommentId(Integer commonid) {
		// TODO Auto-generated method stub
		String sql = "delete from article_comment where commentid=?";
		Db.update(sql, commonid);
	}
	@Cacheable(key="#commonid",value="article_comment")
	public Record findById(Integer commonid) {
		// TODO Auto-generated method stub
		return Db.findById("article_comment",commonid);
	}
	@CacheEvict(value="article_comment",allEntries=true)
	public void deleteById(Integer commonid) {
		// TODO Auto-generated method stub
		Db.deleteById("article_comment",commonid);
	}
	@CacheEvict(value="article_comment",allEntries=true)
	public void saveArticleComment(Record articlecom) {
		// TODO Auto-generated method stub
		Db.save("article_comment", articlecom);
	}
}
