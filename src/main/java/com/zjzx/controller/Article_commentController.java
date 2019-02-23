package com.zjzx.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Record;
import com.zjzx.annotation.EmptyCheck;
import com.zjzx.annotation.TokenAop;
import com.zjzx.clientservice.IntegrationClientService;
import com.zjzx.consts.ComConst;
import com.zjzx.service.Article_commentService;
import com.zjzx.util.AsyncUtil;

@org.springframework.stereotype.Controller
@RequestMapping("/article_comment")
@ResponseBody
public class Article_commentController {
	@Autowired
	private Article_commentService article_commentService;
	@Autowired
	private IntegrationClientService integrationClientService;
	@RequestMapping("/articleComment")
	@TokenAop
	public JSONObject articleComment(String record) {
		JSONObject json = JSONObject.parseObject(record);
		final Record articlecom = new Record();
		articlecom.setColumns(json);
		JSONObject resMap = article_commentService.articleComment(articlecom);
		
		new AsyncUtil() {
			
			@Override
			public void runMethod() {
				// TODO Auto-generated method stub
				integrationClientService.addIntegration(articlecom.getInt("douserid"), ComConst.ING_PL, "评论了一篇文章");
				
			}
		}.excute();
		
		return resMap;

	}

	@RequestMapping("/getArticleCommentPage")
	@EmptyCheck({"page","size","articleid"})
	public JSONObject getArticleCommentPage(Integer page, Integer size,
			Integer articleid) {

		JSONObject resMap = article_commentService.getArticleCommentPage(page,
				size, articleid);
		return resMap;
	}

	@RequestMapping("/getReplyCount")
	@EmptyCheck({"commentid"})
	public JSONObject getReplyCount(Integer commentid) {
		JSONObject resMap = article_commentService.getReplyCount(commentid);
		return resMap;
	}

	@RequestMapping("/getReplyList")
	@EmptyCheck({"page","size","commentid"})
	public JSONObject getReplyList(Integer page, Integer size, Integer commentid) {
		JSONObject resMap = article_commentService.getReplyList(page, size,
				commentid);
		return resMap;
	}

	/**
	 * 获取文章评论数量
	 */
	@RequestMapping("/getArticleCommentCount")
	@EmptyCheck({"articleid"})
	public JSONObject getArticleCommentCount(Integer articleid) {
		JSONObject resMap = article_commentService
				.getArticleCommentCount(articleid);
		return resMap;
	}

	/*
	 * 删除评论/回复
	 */
	@RequestMapping("/deleteArticleConmon")
	@TokenAop
	@EmptyCheck({"itemid"})
	public JSONObject deleteArticleConmon(Integer itemid) {
		JSONObject resMap = article_commentService.deleteArticleConmon(itemid);
		return resMap;
	}
	/**
	 * 获取评论/回复详情
	 * @return
	 */
	@RequestMapping("/getCommentById")
	@EmptyCheck({"itemid"})
	public JSONObject getCommentById(Integer itemid){
		JSONObject resMap = article_commentService.getCommentById(itemid);
		return resMap;
	}


}
