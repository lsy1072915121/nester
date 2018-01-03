package com.liushiyao.nester.entity;

/**
 * Created by 电子小孩 on 2016/8/9.
 */

/**
 * 体温报警：开关
 提醒吃药：开关、时间
 心率提醒：开关 跌到报警：开关
 */

/**
 * “temper”:”no”
 “drug”:“off”、“time”
 “heart”:”on”
 “fall”：“on”
 */
public class SystemInfo {
    private String temper;
    private String drug;
    private String drug_time;
    private String heart;
    private String fall;
    
	public String getTemper()
	{
		return temper;
	}
	public void setTemper(String temper)
	{
		this.temper = temper;
	}
	public String getDrug()
	{
		return drug;
	}
	public void setDrug(String drug)
	{
		this.drug = drug;
	}
	public String getDrug_time()
	{
		return drug_time;
	}
	public void setDrug_time(String drug_time)
	{
		this.drug_time = drug_time;
	}
	public String getHeart()
	{
		return heart;
	}
	public void setHeart(String heart)
	{
		this.heart = heart;
	}
	public String getFall()
	{
		return fall;
	}
	public void setFall(String fall)
	{
		this.fall = fall;
	}

    
	
}
