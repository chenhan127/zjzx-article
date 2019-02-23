package com.zjzx.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
@Component
public class Article_collectDao {
	
	//public static final Article_collectDao article_collectDao = new Article_collectDao();
	/**
	 * 获取收藏的文章
	 * @param userid
	 * @return
	 */
	public List<Integer> getCollectArticleids(Integer userid) {
		// TODO Auto-generated method stub
		String sql = "select articleid from article_collect where state = 1 and userid =? and type=0";
		
		List<Integer> ids = Db.query(sql, userid);
		
		return ids;
	}
	/**
	 * 根据文章id和用户id 判断用户是否收藏该文章
	 * @param articleid
	 * @param userid
	 * @return
	 */
	public  Record  getComment(Integer articleid,Integer userid){
		String sql = "select * from article_collect where articleid=? and userid =? and type=0";
		Record article_collect = Db.findFirst(sql,articleid,userid);
		return  article_collect;
		
	}
	public void  articleCollect(Integer articleid,
			Integer userid, Integer state) {
		
		
	}

	public Long getUserArticleCollectCount(Integer userid) {
		// TODO Auto-generated method stub
		String sql = "select count(id) from article_collect where userid=? and type =0";
		Long count = Db.queryLong(sql,userid);
		return count;
	}
	/**
	 * 获取该收藏该文章的用户
	 * @param articleid
	 * @return
	 */
	public List<Integer> getCollectUserIds(Integer articleid) {
		// TODO Auto-generated method stub
		String sql = "select userid from article_collect where articleid =? and type =0";
		List<Integer> userids = Db.query(sql, articleid);
		return userids;
	}
	/**
	 * 根据文章id删除评论
	 */
	public void deleteArticleCollectByArticleId(Integer articleid){
		String sql = "delete from article_collect where articleid=? and type = 0";
		Db.update(sql, articleid);
	}
	/**
	 * 获取收藏文章分页
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Record> getCollectPage(Integer page, Integer size,Integer userid) {
		Page<Record> pageObj = Db.paginate(page, size, "select * ", "from article_collect where userid =? and type = 0 order by id desc",userid);
		return pageObj;
	}
	public void deleteArticleCollect(Record article_Collect) {
		// TODO Auto-generated method stub
		Db.delete("article_collect", article_Collect);
		
	}
	public void saveArticleCollect(Record article_Collect) {
		// TODO Auto-generated method stub
		Db.save("article_collect", article_Collect);
	}


}
