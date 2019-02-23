package com.zjzx.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.zjzx.dao.Article_commentDao;
import com.zjzx.dao.PraiseDao;
import com.zjzx.service.Article_commentService;
import com.zjzx.util.DateUtil;
import com.zjzx.util.JSONUtil;

public class Article_commentService{
	@Autowired
	private  Article_commentDao article_commentDao ;
	@Autowired
	private  PraiseDao praiseDao ;
	
	
	public JSONObject articleComment(Record articlecom) {
		String date = DateUtil.convertY_M_D_H_M(new Date());
		
//		articlecom.set("commenttime", date).save();
		articlecom.set("commenttime", date);
		article_commentDao.saveArticleComment(articlecom);
		
		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		
		JSONObject result = new JSONObject();
		result.put("recordid", articlecom.getInt("id"));
		
		resMap.put("result", result);
		
		return JSONUtil.parseJSON(resMap);
	}

	public JSONObject getArticleCommentPage(Integer page, Integer size,
			Integer articleid) {
		Page<Record> pageObj = article_commentDao.getArticleCommentPage(page,size,articleid);
		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		resMap.put("list", pageObj);
		return JSONUtil.parseJSON(resMap);
	}

	public JSONObject getReplyCount(Integer commentid) {
		Integer count = article_commentDao.getReplyCount(commentid);
		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		JSONObject result = new JSONObject();
		result.put("count", count);
		resMap.put("result", result);
		return JSONUtil.parseJSON(resMap);
	}

	public JSONObject getReplyList(Integer page, Integer size, Integer commentid) {
		Page<Record> pageObj = article_commentDao.getReplyList(page,size,commentid);
		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		resMap.put("recordPage", pageObj);
		return JSONUtil.parseJSON(resMap);
	}

	public JSONObject getArticleCommentCount(Integer articleid) {
		Integer count = article_commentDao.getArticleCommentCount(articleid);
		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		JSONObject result = new JSONObject();
		result.put("count", count);
		resMap.put("result", result);
		return JSONUtil.parseJSON(resMap);
	}

	public JSONObject deleteArticleConmon(Integer commonid) {
		// TODO Auto-generated method stub
		Record article_comment = article_commentDao.findById(commonid);
		Integer type = article_comment.getInt("type");
		if(type ==1){
			//根据评论删除回复id
			article_commentDao.deleteHfByCommentId(commonid);
		}
		article_commentDao.deleteById(commonid);
		//删除评论的点赞记录
		praiseDao.deletePraiseByCommentId(commonid);
		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		return JSONUtil.parseJSON(resMap);
	}

	public JSONObject getCommentById(Integer itemid) {
		// TODO Auto-generated method stub
		Record article_comment = article_commentDao.findById(itemid);
		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		resMap.put("record", article_comment);
		return JSONUtil.parseJSON(resMap);
	}
	
	
}
