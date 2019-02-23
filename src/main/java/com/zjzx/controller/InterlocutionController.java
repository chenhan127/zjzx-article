package com.zjzx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.zjzx.annotation.EmptyCheck;
import com.zjzx.annotation.TokenAop;
import com.zjzx.clientservice.SensitiveClientService;
import com.zjzx.service.ArticleService;
import com.zjzx.service.InterlocutionService;
import com.zjzx.util.AsyncUtil;
import com.zjzx.util.FutureObj;

@Controller
@RequestMapping("/interlocution")
@ResponseBody
public class InterlocutionController {
	@Autowired
	private InterlocutionService interlocutionService;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private SensitiveClientService sensitiveClientService;
	/**
	 * 发表问题
	 * @return
	 */
	@RequestMapping("/publishQuestion")
	@TokenAop
	@EmptyCheck({"title","userid","classify"})
	public JSONObject publishQuestion(String title,String description, Integer userid, String images ,Integer classify){
		
		JSONObject resMap = interlocutionService.interlocutionService(title,description,images,userid,classify);
		final Integer itemid = resMap.getInteger("itemid");
		new AsyncUtil() {
			@Override
			public void runMethod() {
				// TODO Auto-generated method stub
				FutureObj<Boolean> futureObj = sensitiveClientService.checkInterlocutionWord(itemid);
				System.out.println(futureObj.getValue());
			}
		}.excute();
		return resMap;
	}
	/**
	 * 获取问题分页
	 * @param page
	 * @param size
	 * @param userid
	 * @return
	 */
	@RequestMapping("/getQuestionPage")
	@EmptyCheck({"page","size"})
	public JSONObject getQuestionPage(Integer page,Integer size,Integer userid,Integer classify){
		JSONObject resMap = interlocutionService.getQuestionPage(page,size,userid,classify);
		return resMap;
		
	}
	/**
	 * 获取回答
	 * @param page
	 * @param size
	 * @param parentid
	 * @return
	 */
	@RequestMapping("/getAnswers")
	public JSONObject getAnswers(Integer page,Integer size,Integer parentid){
		JSONObject resMap = articleService.getAnswers(page,size,parentid);
		return resMap;
	}
	/**
	 * 获取回答数量
	 * @param wdid
	 * @return
	 */
	@RequestMapping("/getAnswerCount")
	public JSONObject getAnswerCount(Integer wdid){
		JSONObject resMap = articleService.getAnswerCount(wdid);
		return resMap;
	}
	/**
	 * 问题删除
	 * @return
	 */
	@RequestMapping("/deleteQuestion")
	@TokenAop
	public JSONObject deleteQuestion(@RequestParam("ids[]") Integer[] ids){
		JSONObject resMap = interlocutionService.deleteQuestion(ids);
		return resMap;
	}
	@RequestMapping("/getQuestionById")
	public JSONObject getQuestionById(Integer itemid){
		JSONObject resMap = interlocutionService.getQuestionById(itemid);
		return resMap;
	}
	@RequestMapping("/getUserQuestionCount")
	public JSONObject getUserQuestionCount(Integer userid){
		JSONObject resMap = interlocutionService.getUserQuestionCount(userid);
		return resMap;
	}

}
