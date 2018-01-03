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
 private String device_id;
 private Date create_time;
 private Date update_time;

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

  public String getDevice_id() {
    return device_id;
  }

  public void setDevice_id(String device_id) {
    this.device_id = device_id;
  }

  public Date getCreate_time() {
    return create_time;
  }

  public void setCreate_time(Date create_time) {
    this.create_time = create_time;
  }

  public Date getUpdate_time() {
    return update_time;
  }

  public void setUpdate_time(Date update_time) {
    this.update_time = update_time;
  }


}
