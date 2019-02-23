package com.zjzx.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

@Repository
public class VpariseDao {
	private static String tableName = "vparise";
	
	private static final List<Integer> userids = new ArrayList<Integer>();
	static{
		//滴水穿石
		userids.add(77);
		//小鱼仙馆
		userids.add(481);
		//上学
		userids.add(9);
		
		//咚咚锵
		userids.add(10);
		
	}
	
	/**
	 * 初始化 文章虚拟点赞数量
	 * @param record
	 */
	@Async
	public void initVcount(Integer articleid,Integer userid){
		
		if(!userids.contains(userid)){
			return;
		}
		Record record = new Record();
		record.set("articleid", articleid);
		record.set("vcount", getRandom(10000,100000));
		Db.save(tableName, record);
	}
	
	/**
	 * 获取虚拟点赞数
	 * @param articleid
	 * @return
	 */
	public Integer getVcount(Integer articleid){
		
		String sql = "select vcount from "+tableName+" where articleid =?";
		
		Integer count = Db.queryInt(sql,articleid);
		if(count == null){
			count = 0;
		}
		return count;
	}
	
	private  String getRandom(int min, int max){
	    Random random = new Random();
	    int s = random.nextInt(max) % (max - min + 1) + min;
	    return String.valueOf(s);

	}


}
