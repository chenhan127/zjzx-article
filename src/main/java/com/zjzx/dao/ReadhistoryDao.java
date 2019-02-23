package com.zjzx.dao;

import org.springframework.stereotype.Component;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
@Component
public class ReadhistoryDao {
	
	//public static final ReadhistoryDao readhistoryDao = new ReadhistoryDao();
	public Record getReadHistoryByUseridAndArticleid(Integer userid,
			Integer articleid) {
		// TODO Auto-generated method stub
		String sql = "select * from readhistory where userid =? and articleid =?";
		Record readhistory = Db.findFirst(sql, userid,articleid);
		return readhistory;
	}
	public void clearHistory(Integer[] ids) {
		
		for(Integer id:ids){
			// TODO Auto-generated method stub
			Record readhistory = new Record();
			readhistory.set("isclear", 1);
			readhistory.set("id", id);
			Db.update("readhistory", readhistory);
		}
	}
	public Page<Record> getReadHistory(Integer userid, Integer page,
			Integer size) {
		// TODO Auto-generated method stub
		Page<Record> pageObj = Db.paginate(page, size, "select * ", " from readhistory where userid=? and isclear =0 order by id desc",userid);
		return pageObj;
	}
	public Long getReadCount(Integer articleid) {
		// TODO Auto-generated method stub
		String sql = "select count(id) from readhistory where isclear =0 and articleid =?";
		Long count = Db.queryLong(sql,articleid);
		
		return count;
	}
	public void saveReadhistory(Record readhistory) {
		// TODO Auto-generated method stub
		Db.save("readhistory", readhistory);
		
	}
	public void updateReadHistory(Record readhistory) {
		// TODO Auto-generated method stub
		Db.save("readhistory", readhistory);
	}
}
