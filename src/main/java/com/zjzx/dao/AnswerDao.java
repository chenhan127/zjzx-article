package com.zjzx.dao;

import org.springframework.stereotype.Repository;

import com.jfinal.plugin.activerecord.Db;

@Repository
public class AnswerDao {
	private final String tableName ="article";
	/**
	 * 获取回答数量
	 * @param wdid
	 * @return
	 */
	public Integer getAnswerCount(Integer wdid) {
		// TODO Auto-generated method stub
		String sql = "select count(id) from "+tableName+" where parentid=? and state=?";
		Long count = Db.queryLong(sql,wdid,3);
		return count.intValue();
	}

}
