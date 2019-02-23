package com.zjzx.serviceconfig;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;


@Configuration
public class ActiveRecordConfig {
	@Resource
	private DataSource dataSource;
	@Bean(initMethod = "start", destroyMethod = "stop")
	public ActiveRecordPlugin init() {
		ActiveRecordPlugin arp = new ActiveRecordPlugin(dataSource);
		System.out.println("数据库启动成功");
		return arp;
	}
}
