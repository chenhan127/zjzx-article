package com.zjzx.dao;

import org.springframework.stereotype.Component;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
@Component
public class PraiseDao {
	
	//public static final PraiseDao praiseDao = new PraiseDao();
	
	public Record getByItemIdAndTypeAndUserid(Integer itemid, String type,
			Integer userid) {
		// TODO Auto-generated method stub
		String sql = "select * from praise where userid=? and type=? and itemid =?";
		Record praise = Db.findFirst(sql, userid,type,itemid);
		return praise;
	}

	public long getPraiseCount(Integer itemid, String type, Integer userid) {
		// TODO Auto-generated method stub
		String sql = "select count(id) from praise where userid=? and type=? and itemid =?";
		long count = Db.queryLong(sql, userid,type,itemid);
		return count;
	}
	/**
	 * 删除文章的点赞信息
	 * @param articleid
	 */
	public void deletePraiseByArticleId(Integer articleid) {
		// TODO Auto-generated method stub
		String sql ="delete from praise where itemid=? and type=1";
		Db.update(sql, articleid);
		
	}
	/**
	 * 删除评论的点赞信息
	 */
	public void deletePraiseByCommentId(Integer commentid){
		// TODO Auto-generated method stub
		String sql ="delete from praise where itemid=? and type=1";
		Db.update(sql, commentid);
	}
	/**
	 * 获取点赞数量
	 * @param itemid  对象id
	 * @param type	     对象类型
	 * @return
	 */
	public Long getPraiseCount(Integer itemid, String type) {
		// TODO Auto-generated method stub
		String sql ="select count(id) from praise where  itemid=? and type=?";
		Long count = Db.queryLong(sql,itemid,type);
		
		return count;
	}

	public Page<Record> getPraiseList(Integer itemid, String type,
			Integer page, Integer size) {
		// TODO Auto-generated method stub
		Page<Record> pageObj = Db.paginate(page, size, "select * ", "from praise where itemid =? and type=?",itemid,type);
		return pageObj;
	}

	public void savePraise(Record praise) {
		// TODO Auto-generated method stub
		Db.save("praise", praise);
		
	}

	public void deletePraise(Record praise) {
		// TODO Auto-generated method stub
		Db.delete("praise", praise);
		
	}
	

}
