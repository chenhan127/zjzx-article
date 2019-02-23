package com.zjzx.dao;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.zjzx.util.DateUtil;

@Repository
public class NotLikeDao {
	private static final String tableName = "notlike";
	public void doNotLike(Integer articleid, Integer userid) {
		// TODO Auto-generated method stub
		Record record = new Record();
		record.set("articleid", articleid);
		record.set("userid", userid);
		record.set("createtime", DateUtil.convertY_M_D_H_M(new Date()));
		Db.save(tableName, record);
	}
	/**
	 * 根据用户id跟文章id获取 记录
	 * @param userid
	 * @param articleid
	 * @return
	 */
	public Record getNotLikeByUserAndArtile(Integer userid, Integer articleid) {
		// TODO Auto-generated method stub
		String sql = "select * from "+tableName+" where userid=? and articleid=?";
		Record record = Db.findFirst(sql,userid, articleid);
		return record;
	}
	
	

}
