package com.liushiyao.nester.entity;

public class City
{
	private String name;
	private String cites;
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getCites()
	{
		return cites;
	}
	public void setCites(String cites)
	{
		this.cites = cites;
	}
	public City(String name, String cites)
	{
		super();
		this.name = name;
		this.cites = cites;
	}
	
	
	
}
