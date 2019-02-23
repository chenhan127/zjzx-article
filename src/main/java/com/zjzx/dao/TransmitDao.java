package com.zjzx.dao;

import org.springframework.stereotype.Repository;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
@Repository
public class TransmitDao {
	
	private static final String tableName = "transmit";
	/**
	 * 添加转发记录
	 * @param record
	 */
	public void addTransmitRecord(Record record){
		Db.save(tableName, record);
	}
	/**
	 *获取装发列表
	 * @param articleid
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Record> getTransmitList(Integer articleid, Integer page,
			Integer size) {
		Page<Record> pageObj = Db.paginate(page, size, "select * ", " from "+tableName+" where articleid=?",articleid);
		return pageObj;
	}

}
