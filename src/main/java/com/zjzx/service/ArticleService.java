package com.zjzx.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.zjzx.dao.AnswerDao;
import com.zjzx.dao.ArticleDao;
import com.zjzx.dao.Article_collectDao;
import com.zjzx.dao.Article_commentDao;
import com.zjzx.dao.Article_fileDao;
import com.zjzx.dao.PraiseDao;
import com.zjzx.dao.VpariseDao;
//import com.zjzx.redis.ArticleRedis;
//import com.zjzx.redis.Article_collectRedis;
import com.zjzx.service.ArticleService;
import com.zjzx.util.AsyncUtil;
import com.zjzx.util.DateUtil;
import com.zjzx.util.JSONUtil;
import com.zjzx.util.PageObj;
@Service
public class ArticleService{
	@Autowired
	private ArticleDao articleDao;
	@Autowired
	private Article_collectDao article_collectDao;
	@Autowired
	private Article_commentDao article_commentDao;
	@Autowired
	private PraiseDao praiseDao;
//	@Autowired
//	private ArticleRedis articleRedis;
//	@Autowired
//	private Article_collectRedis article_collectRedis;
	@Autowired
	private AnswerDao answerDao;
	@Autowired 
	private Article_fileDao article_fileDao;
	@Autowired
	VpariseDao vpariseDao;

	public JSONObject publishArticle(final Record article, JSONArray filearray) {
		String date = DateUtil.convertY_M_D_H_M(new Date());
		Integer recordid = article.getInt("id");
		if(recordid == null){
//			article.set("publishtime", date).save();
			article.set("publishtime", date);
			recordid = articleDao.saveArticle(article);
			// 设置虚拟点赞数
			vpariseDao.initVcount(recordid,article.getInt("author"));
			
		}else{
			Record record = new Record();
			record.setColumns(article);
			articleDao.updateArticle(record);
			
		}
		if (filearray != null) {
			Integer articleid = recordid;
			article_fileDao.deleteFileByArticleId(articleid);
			for (int i = 0; i < filearray.size(); i++) {
				JSONObject fileObj = filearray.getJSONObject(i);
				Record article_file = new Record();
				article_file.setColumns(fileObj)
						.set("articleid", articleid);
				article_fileDao.saveFile(article_file);
			}
		}
		Integer userid = article.getInt("author");
		//articleRedis.deleteUserArticleCount(userid);
		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		JSONObject result = new JSONObject();
		//result.put("recordid", article.getInt("id"));
		result.put("recordid", recordid);
		resMap.put("result", result);
		String content = article.getStr("content");
		
		
		
	/*	// 内容非空 存储音频
		if (content != null && !"".equals(content)) {
			final List<String> contentBlock = TtsUtil.conVertContent(content);
			new AsyncUtil() {
				@Override
				public void runMethod() {
					// TODO Auto-generated method stub
					String urls = null;
					try {
						System.out.println("-------------"+contentBlock);
						urls = TtsUtil.getAudioUrls(contentBlock);
						System.out.println("--------"+urls);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					System.out.println(urls);
					article.set("audio", urls);
					article.update();
				}
				
			}.excute();
			
		}*/
		return JSONUtil.parseJSON(resMap);
	}

//	@Override
//	public JSONObject articlePage(Integer page, Integer size, JSONObject params) {
//		params.put("parentid", 0);
//		Page<Article> pageObj = articleDao.articlePage(page, size,  params);
//		JSONObject resMap = new JSONObject();
//		resMap.put("status", "success");
//		resMap.put("recordPage", pageObj);
//		return JSONUtil.parseJSON(resMap);
//	}
	
//	
//	@Override
//	public JSONObject articlePage2(Integer page, Integer size, JSONObject params) {
//		
//		params.put("parentid", 0);
//		Integer classify = params.getInteger("classify");
//		Integer type = params.getInteger("type");
//		if((classify!=null && classify== 7) || (type!=null && type == 2)){
//			Page<Article> pageObj = articleDao.articlePage(page, size, params);
//			JSONObject resMap = new JSONObject();
//			resMap.put("status", "success");
//			resMap.put("recordPage", pageObj);
//			return JSONUtil.parseJSON(resMap);
//		}
//		//获取一条视频
//		Article spRecord = articleDao.getSpRecord(page,params);
//		int newsize = size;
//		//如果没有视频，那么获取size条图文
//		if(spRecord != null){
//			// 获取 size -1 条图文
//			newsize = size -1;
//		}
//		Page<Article> pageObj = articleDao.getTwPage(page,newsize,params);
//		PageObj<Article> pageObj2 = new PageObj<Article>(pageObj);
//		pageObj2.appendPage(size/2, spRecord);
//		JSONObject resMap = new JSONObject();
//		resMap.put("status", "success");
//		resMap.put("recordPage", pageObj2);
//		return JSONUtil.parseJSON(resMap);
//	}
//	
//	
	public JSONObject articlePage(Integer page, Integer size, JSONObject params) {
		Integer videosize = size/3;
		Integer articlesize = size - videosize;
		
		params.put("parentid", 0);
		Integer classify = params.getInteger("classify");
		Integer type = params.getInteger("type");
		if((classify!=null && classify== 7) || (type!=null && type == 2)){
			Page<Record> pageObj = articleDao.articlePage(page, size, params);
			JSONObject resMap = new JSONObject();
			resMap.put("status", "success");
			resMap.put("recordPage", pageObj);
			return JSONUtil.parseJSON(resMap);
		}
		//获取一条视频
		//Article spRecord = articleDao.getSpRecord(page,params);
		List<Record> articleList = articleDao.getSpPage(page, videosize, params);
		//如果没有视频，那么获取size条图文
		if(articleList == null || articleList.size()<=0){
			// 获取 size -1 条图文
			articlesize = size;
		}
		Page<Record> pageObj = articleDao.getTwPage(page,articlesize,params);
		PageObj<Record> pageObj2 = new PageObj<Record>(pageObj);
		//pageObj2.appendPage(size/2, spRecord);
		pageObj2.appendPage(articleList);
		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		resMap.put("recordPage", pageObj2);
		return JSONUtil.parseJSON(resMap);
		
		
	}
	public JSONObject articlePage2(Integer page, Integer size, JSONObject params) {
		// TODO Auto-generated method stub
		Page<Record> pageObj = new Page<>();
		if(page>1){
			pageObj = articleDao.getTop100(params);
		}
		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		resMap.put("recordPage", pageObj);
		return JSONUtil.parseJSON(resMap);
	}
	
	
	public JSONObject getAnswers(Integer page, Integer size, Integer parentid) {
		// TODO Auto-generated method stub
		JSONObject params = new JSONObject();
		params.put("parentid", parentid);
		Page<Record> pageObj = articleDao.articlePage(page, size, params);
		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		resMap.put("recordPage", pageObj);
		return JSONUtil.parseJSON(resMap);
	}
	public JSONObject getArticleById(Integer recordid) {
		// redis 键值
		// String key = "article"+recordid;
		// Article article = RedisUtil.zjzx.get(key);

		// Article article = articleRedis.getArticleById(recordid);
		Record article = articleDao.findById(recordid);
		
//		// 如果从缓存没查出 那么从数据库中查询，并存入缓存
//		if (article == null) {
//			article = articleDao.findById(recordid);
//			articleRedis.setArticle(article);
//		}
		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		resMap.put("record", article);
		return JSONUtil.parseJSON(resMap);
	}

	public JSONObject getCollectArticlePage(Integer page, Integer size,
			Integer userid) {
		// TODO Auto-generated method stub
		Page<Record> pageObj = articleDao.getCollectArticlePage(page, size,
				userid);
		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		resMap.put("recordPage", pageObj);
		return JSONUtil.parseJSON(resMap);
	}

	public JSONObject getArticleCommentCount(Integer articleid) {
		Integer count = articleDao.getArticleCommentCount(articleid);
		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		JSONObject result = new JSONObject();
		result.put("count", count);
		resMap.put("result", result);
		return JSONUtil.parseJSON(resMap);
	}

	public JSONObject getArticleByUser(Integer page, Integer size,
			Integer userid, Integer type,Integer parentid) {
		Page<Record> pageObj = articleDao.getArticleByUser(page, size, userid,
				type,parentid);
		JSONObject resMap = new JSONObject();
		JSONObject result = new JSONObject();
		resMap.put("status", "success");
		result.put("recordPage", pageObj);
		resMap.put("result", result);
		return JSONUtil.parseJSON(resMap);
	}

	public JSONObject getUserArticleCount(Integer userid,Integer parentid) {
//		Long count = articleRedis.getUserArticleCount(userid);
//		if (count == null) {
//			count = articleDao.getUserArticleCount(userid);
//			articleRedis.setUserArticleCount(count, userid);
//		}
		Long count = articleDao.getUserArticleCount(userid,parentid);
		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		JSONObject result = new JSONObject();
		result.put("count", count);
		resMap.put("result", result);
		return JSONUtil.parseJSON(resMap);
	}

	public JSONObject deleteArticleById(Integer articleid) {
		// TODO Auto-generated method stub
		Record article = articleDao.findById(articleid);
	//	Integer author = article.getInt("author");
//		articleRedis.deleteArticleById(articleid);
//		articleRedis.deleteUserArticleCount(author);
		article.set("isdelete", 1);
		articleDao.deleteById(articleid);
		// 获取 搜集该文章的用户
	//	List<Integer> userids = article_collectDao.getCollectUserIds(articleid);
		// 清除该文章搜集用户的缓存数据
	//	article_collectRedis.deleteArticleCollectByUserids(userids);
		// 删除该文章的搜集数据
		article_collectDao.deleteArticleCollectByArticleId(articleid);
		// 删除该文章的评论
		article_commentDao.deleteCommentByArticleId(articleid);
		// 删除该文章的点赞信息
		praiseDao.deletePraiseByArticleId(articleid);

		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		return JSONUtil.parseJSON(resMap);
	}

	public JSONObject articleCheck(Record article) {
		// TODO Auto-generated method stub
		
		Record record = new Record();
		record.setColumns(article);
		articleDao.updateArticle(record);
	//	article.update();
		//articleRedis.deleteArticleById(article.getInt("id"));
		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		return JSONUtil.parseJSON(resMap);
	}

	public static int[] generateDifNums(int n, int small, int big) {
		int length = big - small + 1;
		int[] seed = new int[length];
		for (int i = 0; i < length; i++) {
			seed[i] = small + i;
		}
		int[] ranArr = new int[n];
		Random ran = new Random();
		for (int i = 0; i < n; i++) {
			int j = ran.nextInt(length - i);
			ranArr[i] = seed[j];
			seed[j] = seed[length - 1 - i];
		}
		return ranArr;
	}

	public JSONObject recommendArticle() {
		// TODO Auto-generated method stub
		List<Record> records = articleDao.getListLit(100);
		int radArr[] = generateDifNums(15, 0, 99);
		List<Record> resRecords = new ArrayList<Record>();
		for (int i = 0; i < radArr.length; i++) {
			resRecords.add(records.get(radArr[i]));
		}
		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		resMap.put("recordList", resRecords);
		return JSONUtil.parseJSON(resMap);
	}

	public JSONObject deleteArticleByIds(Integer[] ids) {
		// TODO Auto-generated method stub
		for (Integer id : ids) {
			this.deleteArticleById(id);
		}
		JSONObject resMap = new JSONObject();
		resMap.put("sttaus", "success");
		return resMap;
	}
	
	
	public JSONObject getAnswerCount(Integer wdid) {
		// TODO Auto-generated method stub
		Integer count = answerDao.getAnswerCount(wdid);
		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		resMap.put("count", count);
		return resMap;
	}

	public JSONObject getTodayArticle() {
		// TODO Auto-generated method stub
		List<Record> list = articleDao.getTodayArticle();
		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		resMap.put("list", list);
		return JSONUtil.parseJSON(resMap);
	}

	public JSONObject getTjsp(Integer classify) {
		// TODO Auto-generated method stub
		List<Record> list = articleDao.getTjsp(classify);
		List<Record> newList = new ArrayList<>();
		if(list.size()>2){
			int radArr[] = generateDifNums(2, 0, list.size());
			for(Integer i:radArr){
				newList.add(list.get(i));
			}
		}else{
			newList = list;
		}
		
		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		resMap.put("recordList", newList);
		return JSONUtil.parseJSON(resMap);
	}
	
	public static void main(String[] args) {
		int[] i = generateDifNums(2, 0, 50);
		System.out.println(i[0]);
		System.out.println(i[1]);
	}

	public JSONObject getTjwz(Integer classify, Integer count) {
		// TODO Auto-generated method stub
		List<Record> list = articleDao.getTjwz(classify);
		if(count == null){
			count =2;
		}
		List<Record> newList = new ArrayList<>();
		if(list.size()>count){
			int radArr[] = generateDifNums(count, 0, list.size());
			for(Integer i:radArr){
				newList.add(list.get(i));
			}
		}else{
			newList = list;
		}
		JSONObject resMap = new JSONObject();
		resMap.put("status", "success");
		resMap.put("recordList", newList);
		return JSONUtil.parseJSON(resMap);
	}

	

	

}
