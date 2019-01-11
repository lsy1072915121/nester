package com.liushiyao.nester.entity;

/**
 * Created by 电子小孩 on 2016/8/9.
 */

import java.sql.Date;

/**
 * 账号：1072915121 名字：lsy 年龄：21 性别：男 病史：content 注意事项：content
 *
 * “account”:”1072915121” "password":"123456" “name”:”lsy” “age”:”21" “sexy”:”man”
 * “medical_history”:”content” “attention”:”content” “device”：“null”/”xx2”
 */
public class User {

  private Integer uid;
  private String account;
  private String passwd;
  private Integer age;
  private boolean sex;
  private String deviceId;
  private String nick;
  private Date createTime;
  private Date updateTime;

  public Integer getUid() {
    return uid;
  }

  public void setUid(Integer uid) {
    this.uid = uid;
  }

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public String getPasswd() {
    return passwd;
  }

  public void setPasswd(String passwd) {
    this.passwd = passwd;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public boolean isSex() {
    return sex;
  }

  public void setSex(boolean sex) {
    this.sex = sex;
  }

  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public String getNick() {
    return nick;
  }

  public void setNick(String nick) {
    this.nick = nick;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }
}
