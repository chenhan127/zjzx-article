package com.zjzx.serviceconfig;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.jfinal.aop.Duang;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.zjzx.service.ArticleService;
import com.zjzx.service.Article_classifyService;
import com.zjzx.service.Article_collectService;
import com.zjzx.service.Article_commentService;
import com.zjzx.service.Article_fileService;
import com.zjzx.service.Article_typeService;
import com.zjzx.service.InterlocutionService;
import com.zjzx.service.NotLikeService;
import com.zjzx.service.PraiseService;
import com.zjzx.service.ReadhistoryService;
import com.zjzx.service.TransmitService;

@Configuration
public class BeanConfiguration {
	
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		
		return new RestTemplate();
	}
	@Bean
	public Article_classifyService article_classifyService() {
		return Duang.duang(Article_classifyService.class, Tx.class);
	}
	@Bean
	public Article_collectService article_collectService() {
		return Duang.duang(Article_collectService.class, Tx.class);
	}
	@Bean
	public Article_commentService article_commentService() {
		return Duang.duang(Article_commentService.class, Tx.class);
	}
	
	@Bean
	public Article_fileService article_fileService() {
		return Duang.duang(Article_fileService.class, Tx.class);
	}
	@Bean
	public Article_typeService article_typeService() {
		return Duang.duang(Article_typeService.class, Tx.class);
	}
	@Bean
	public ArticleService articleService() {
		return Duang.duang(ArticleService.class, Tx.class);
	}
	@Bean
	public InterlocutionService interlocutionService() {
		return Duang.duang(InterlocutionService.class, Tx.class);
	}
	@Bean
	public NotLikeService notLikeService() {
		return Duang.duang(NotLikeService.class, Tx.class);
	}
	@Bean
	public PraiseService praiseService() {
		return Duang.duang(PraiseService.class, Tx.class);
	}
	@Bean
	public ReadhistoryService readhistoryService() {
		return Duang.duang(ReadhistoryService.class, Tx.class);
	}
	@Bean
	public TransmitService transmitService() {
		return Duang.duang(TransmitService.class, Tx.class);
	}
	
	

}
