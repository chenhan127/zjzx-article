package com.zjzx.dao;

import org.springframework.stereotype.Repository;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
@Repository
public class Wd_collectDao {
	private static final String tableName = "article_collect";
	private static final int type = 1;
	/**
	 * 根据用户id 文章id 获取问答是否有关注
	 * @param userid
	 * @param wdid
	 * @return
	 */
	public Record getWdCollectByUseridAndWdId(Integer userid,
			Integer wdid) {
		// TODO Auto-generated method stub
		String sql = "select * from "+tableName+" where articleid=? and userid=? and type=?";
		Record article_collect = Db.findFirst(sql, wdid,userid,type);
		return article_collect;
	}
	/**
	 * 根据用户id 获取该用户 问答的搜集数量
	 * @param userid
	 * @return
	 */
	public Integer getUserWdCollectCount(Integer userid) {
		// TODO Auto-generated method stub
		String sql = "select count(id) from "+tableName+" where userid=? and type =?";
		Long count = Db.queryLong(sql,userid,type);
		return count.intValue();
	}
	/**
	 * 删除用户的问答收藏
	 * @param id
	 * @param userid
	 */
	public void deleteWdCollect(Integer id, Integer userid) {
		// TODO Auto-generated method stub
		String sql = "delete from "+tableName+" where articleid =? and userid =? and type=?";
		Db.update(sql, id,userid,type);
		
	}
	/**
	 * 获取问答的收藏用户数量
	 * @param wdid
	 * @return
	 */
	public Integer getWdCollectCount(Integer wdid) {
		// TODO Auto-generated method stub
		String sql = "select count(id) from "+tableName+" where articleid=? and type=?";
		Long count = Db.queryLong(sql,wdid,type);
		return count.intValue();
	}
	
}
