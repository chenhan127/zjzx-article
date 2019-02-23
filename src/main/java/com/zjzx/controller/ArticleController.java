package com.zjzx.controller;



import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Record;
import com.zjzx.annotation.EmptyCheck;
import com.zjzx.annotation.TokenAop;
import com.zjzx.clientservice.IntegrationClientService;
import com.zjzx.clientservice.SmsClientService;
import com.zjzx.consts.ComConst;
import com.zjzx.service.ArticleService;
//import com.zjzx.service.IntegrationService;
//import com.zjzx.service.SensitiveService;
//import com.zjzx.service.SmsService;
import com.zjzx.util.DateUtil;
@org.springframework.stereotype.Controller
@RequestMapping("/article")
@ResponseBody
public class ArticleController {
	/**
	 * 
	 */
	@Autowired
	private ArticleService articleService; 
	
	@Autowired
	private IntegrationClientService integrationClientService;
	
//	@Autowired
//	private SmsClientService smsClientService;
	
//	@Autowired
//	private IntegrationService integrationService;
//	@Autowired
//	private SensitiveService sensitiveService;
//	
//	@Autowired
//	private SmsService smsService;
	
	/**
	 * 发布文章
	 */
	@RequestMapping("/publishArticle")
	public JSONObject publishArticle(String record,String record_file){
		JSONObject json = JSONObject.parseObject(record);
		JSONArray array = null;
		if(record_file!=null){
			array = JSONObject.parseArray(record_file);
		}
		final Record article = new Record();
		article.setColumns(json);
		final JSONObject resMap = articleService.publishArticle(article,array);
//		Integer type = article.getInt("type");
//		if(type == null || type !=3){
//		  JSONObject res =	smsClientService.sendCode("18501957486", "8888");
//		  System.out.println(res);
//		}
		/**
		 * 添加发布文章积分
		 */
		integrationClientService.addIntegration(article.getInt("author"), ComConst.ING_FB, "发布了一篇文章");
		
		return resMap;
	}
//	
	
	/**
	 * 推荐
	 * @return
	 */
	@RequestMapping("/recommendArticle")
	public JSONObject recommendArticle(){
		JSONObject resMap = articleService.recommendArticle();
		return resMap;
	}
	/*
	 * 获取文章分页
	 */
	@RequestMapping("/articlePage")
	@EmptyCheck({"page","size"})
	public JSONObject articlePage(Integer page,Integer size,final String keyword,Integer author,String authorname,Integer type,Integer state ,String publisharea,Integer classify ,final Integer userid,Integer today){
		JSONObject params = new JSONObject();
		params.put("keyword", keyword);
		params.put("author", author);
		params.put("authorname", authorname);
		params.put("type", type);
		params.put("publisharea", publisharea);
		params.put("classify", classify);
		params.put("state", state);
		params.put("state", today);
		JSONObject resMap =  articleService.articlePage(page,size,params);
		
		return resMap;
	}
	/**
	 * 获取文章详情
	 */
	@RequestMapping("/getArticleById")
	@EmptyCheck({"recordid"})
	public JSONObject getArticleById(Integer recordid){
		JSONObject resMap =  articleService.getArticleById(recordid);
		return resMap;
			
	}
	/**
	 * 获取收藏的文章列表
	 */
	@RequestMapping("/getCollectArticlePage")
	@EmptyCheck({"page","size","userid"})
	public JSONObject getCollectArticlePage(Integer page,Integer size,Integer userid){
	//	List<Integer> articleids = Article_collectService.me.getCollectArticleids(userid);
		JSONObject resMap = articleService.getCollectArticlePage(page,size,userid);
		return resMap;
		
	}
//	/**
//	 * 获取文章评论数量
//	 */
//	@Before(IocInterceptor.class)
//	public void getArticleCommentCount(){
//		Integer articleid = getParaToInt("articleid");
//		JSONObject resMap =  articleService.getArticleCommentCount(articleid);
//		renderJson(resMap);
//	}
	/**
	 * 获取用户的文章/视频
	 */
	@RequestMapping("/getArticleByUser")
	@EmptyCheck({"page","size","userid"})
	public JSONObject getArticleByUser(Integer page,Integer size,Integer userid,Integer type){
		JSONObject resMap = articleService.getArticleByUser(page,size,userid,type,0);
		return resMap;
		
	}
	/**
	 * 获取用户发布文章的数量
	 */
	@RequestMapping("/getUserArticleCount")
	@EmptyCheck({"userid"})
	public JSONObject getUserArticleCount(Integer userid){
		JSONObject resMap = articleService.getUserArticleCount(userid,0);
		return resMap;
	}
	/**
	 * 根据id删除文章
	 */
	@RequestMapping("/deleteArticleById")
	@TokenAop
	@EmptyCheck({"articleid"})
	public JSONObject deleteArticleById(Integer articleid){
		JSONObject resMap = articleService.deleteArticleById(articleid);
		return resMap;
	}
	@RequestMapping("deleteArticleByIds")
	@TokenAop
	public JSONObject deleteArticleByIds(@RequestParam("ids[]") Integer ids[]){
		JSONObject resMap = articleService.deleteArticleByIds(ids);
		return resMap;
	}
	
	/**
	 * 文章审核
	 */
	@RequestMapping("/articleCheck")
	@TokenAop
	@EmptyCheck({"userid","articleid","state"})
	public JSONObject articleCheck(Integer userid,Integer articleid,Integer state,String checknoreason ){
		Record article = new Record();
		article.set("checkuser", userid);
		article.set("id", articleid);
		article.set("state", state);
		article.set("checknoreason", checknoreason);
		article.set("checktime", DateUtil.convertY_M_D_H_M(new Date()));
		JSONObject resMap = articleService.articleCheck(article);
		return resMap;
		
	}
	@RequestMapping("/getTodayArticle")
	public JSONObject getTodayArticle(){
		JSONObject resMap = articleService.getTodayArticle();
		return resMap;
	}
	/**
	 * 获取推荐的视频
	 * @return
	 */
	@RequestMapping("/getTjsp")
	public JSONObject getTjsp(Integer classify){
		JSONObject resMap = articleService.getTjsp(classify);
		return resMap;
	}
	@RequestMapping("/getTjwz")
	public JSONObject getTjwz(Integer classify,Integer count){
		JSONObject resMap = articleService.getTjwz(classify,count);
		return resMap;
		
	}
	

}
