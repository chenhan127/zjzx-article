package com.zjzx.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.zjzx.dao.Article_collectDao;
import com.zjzx.dao.Wd_collectDao;
// import com.zjzx.redis.Article_collectRedis;
import com.zjzx.service.Article_collectService;
import com.zjzx.util.JSONUtil;

public class Article_collectService {
	@Autowired
	private Article_collectDao article_collectDao;
//	@Autowired
//	private Article_collectRedis article_collectRedis;
	@Autowired
	private Wd_collectDao wd_collectDao;
	

	public List<Integer> getCollectArticleids(Integer userid) {
		// String key = "getCollectArticleids"+userid;
//		List<Integer> articleids = article_collectRedis
//				.getCollectArticleids(userid);
//		if (articleids == null) {
//			articleids = article_collectDao.getCollectArticleids(userid);
//			article_collectRedis.setCollectArticleids(userid, articleids);
//		}
		List<Integer> articleids = article_collectDao.getCollectArticleids(userid); 
		return articleids;
	}

	public JSONObject articleCollect(Integer articleid, Integer userid) {
		Record article_Collect = article_collectDao.getComment(
				articleid, userid);
		String result = "";
		if (article_Collect != null) {
			article_collectDao.deleteArticleCollect(article_Collect);
			result = "0";
		} else {
			article_Collect = new Record();
			article_Collect.set("articleid", articleid).set("userid", userid)
					.set("state", 1);
			article_collectDao.saveArticleCollect(article_Collect);
			result = "1";
		}
//		article_collectRedis.deleteCollectArticleids(userid);
//		article_collectRedis.deleteUserArticleCollectCount(userid);
		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		resMap.put("result", result);

		return JSONUtil.parseJSON(resMap);
	}

	public JSONObject testCollect(Integer articleid, Integer userid) {
		// TODO Auto-generated method stub
		Record article_Collect = article_collectDao.getComment(
				articleid, userid);
		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		resMap.put("result", "0");
		if (article_Collect != null) {
			resMap.put("result", "1");
		}
		return JSONUtil.parseJSON(resMap);
	}

	public JSONObject getUserArticleCollectCount(Integer userid) {
		// TODO Auto-generated method stub
//		Long count = article_collectRedis.getUserArticleCollectCount(userid);
//		if (count == null) {
//			count = article_collectDao.getUserArticleCollectCount(userid);
//			article_collectRedis.setUserArticleCollectCount(count, userid);
//		}
		
		Long count = article_collectDao.getUserArticleCollectCount(userid);
		JSONObject resMap = new JSONObject();
		JSONObject result = new JSONObject();
		resMap.put("result", result);
		result.put("count", count);
		return JSONUtil.parseJSON(resMap);
	}

	public JSONObject deleteCollect(Integer[] ids, Integer userid) {
		// TODO Auto-generated method stub
		for (Integer id : ids) {
			this.articleCollect(id, userid);
		}
		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		return resMap;
	}

	public JSONObject getCollectPage(Integer page, Integer size, Integer userid) {
		// TODO Auto-generated method stub
		Page<Record> pageObj = article_collectDao.getCollectPage(page, size,
				userid);
		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		resMap.put("recordPage", pageObj);
		return JSONUtil.parseJSON(resMap);
	}

	public JSONObject wdCollect(Integer wdid, Integer userid) {
		// TODO Auto-generated method stub
		JSONObject resMap = new JSONObject();

		resMap.put("status", "success");

		// 判断用户是否收藏该问答
		Record article_collect = wd_collectDao
				.getWdCollectByUseridAndWdId(userid, wdid);

		// 如果有收藏，取消收藏，否则 添加收藏
		if (article_collect != null) {
			article_collectDao.deleteArticleCollect(article_collect);
			resMap.put("result", 0);
			return resMap;
		}

		article_collect = new Record();
		article_collect.set("articleid", wdid).set("userid", userid)
				.set("state", 1).set("type", 1);
		article_collectDao.saveArticleCollect(article_collect);

		resMap.put("result", 1);
		return resMap;
	}

	public JSONObject testWdCollect(Integer wdid, Integer userid) {
		// TODO Auto-generated method stub
		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		resMap.put("result", 0);
		Record article_collect = wd_collectDao
				.getWdCollectByUseridAndWdId(userid, wdid);
		if (article_collect != null) {
			resMap.put("result", 1);
		}
		return resMap;
	}

	public JSONObject getUserWdCollectCount(Integer userid) {
		// TODO Auto-generated method stub
		Integer count = wd_collectDao.getUserWdCollectCount(userid);
		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		resMap.put("count", count);
		return resMap;
	}

	public JSONObject deleteWdCollect(Integer[] ids, Integer userid) {
		// TODO Auto-generated method stub
		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		if (ids == null) {
			resMap.put("status", "error");
			resMap.put("tip", "无删除项");
			return resMap;
		}
		for (Integer id : ids){
			wd_collectDao.deleteWdCollect(id,userid);
		}
		return resMap;
	}

	public JSONObject getWdCollectCount(Integer wdid) {
		// TODO Auto-generated method stub
		Integer count = wd_collectDao.getWdCollectCount(wdid);
		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		resMap.put("count", count);
		return resMap;
	}

	

}
