package com.zjzx.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

@Repository
public class InterlocutionDao {
	
	private static final String tableName = "interlocution";
	
	public void saveQuestion(Record record){
		Db.save(tableName, record);
	}

	public Page<Record> getQuestionPage(Integer page, Integer size,
			Integer userid,Integer classify) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append(" from "+tableName+" where 1=1");
		List<Object> params = new ArrayList<Object>();
		if(userid != null){
			sb.append(" and userid=?");
			params.add(userid);
		}
		
		if(classify != null){
			sb.append(" and classify =?");
			params.add(classify);
		}
		
		sb.append(" order by id desc");
		Page<Record> pageObj = Db.paginate(page, size, "select *",sb.toString(),params.toArray());
		return pageObj;
	}

	public void deleteQuestionById(Integer id) {
		// TODO Auto-generated method stub
		Db.deleteById(tableName, id);
	}

	public Integer getUserQuestionCount(Integer userid) {
		// TODO Auto-generated method stub
		String sql = "select count(id) from "+tableName+" where userid=?";
		Long count =Db.queryLong(sql,userid);
		return count.intValue();
	}
	
	

}
