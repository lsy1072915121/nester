package com.liushiyao.nester.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.liushiyao.nester.entity.City;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.servlet.http.HttpServletRequest;
import org.junit.Test;

public class JsonUtil {


  private static Gson gson;

  static{
    gson = new Gson();
  }

  /**
   * 将对象转成Json
   * @param object
   * @return
   */
  public static String toJson(Object object){
    return gson.toJson(object);
  }


  /**
   * 將Request中的Json
   * @param request 转换成字符串
   * @return
   */
  public static JsonObject toJson(HttpServletRequest request) throws IOException {

    BufferedReader bufferedReader = new BufferedReader(
        new InputStreamReader(request.getInputStream(),"utf-8"));
    String str = null;
    StringBuffer json = new StringBuffer();
    while((str = bufferedReader.readLine()) != null){
      json.append(str);
    }
    JsonObject jsonObject = new JsonParser().parse(json.toString()).getAsJsonObject();
    return jsonObject;

  }

  /**
   * @param json
   * @param classOfT
   * @return
   * @MethodName : fromJson
   * @Description : 用来将JSON串转为对象，但此方法不可用来转带泛型的集合
   */
  public static <T> Object fromJson ( String json , Class <T> classOfT ) {
    return gson.fromJson ( json , ( java.lang.reflect.Type ) classOfT );
  }

  /**
   * @param json
   * @param typeOfT
   * @return
   * @MethodName : fromJson
   * @Description : 用来将JSON串转为对象，此方法可用来转带泛型的集合，如：Type为
   * new TypeToken<List<T>>(){}.getType()
   * ，其它类也可以用此方法调用，就是将List<T>替换为你想要转成的类
   */
  public static Object fromJson ( String json , java.lang.reflect.Type typeOfT ) {
    return gson.fromJson ( json , typeOfT );
  }


  @Test
  public void toJsonTest(){
    City city =  new City("惠州","123");
    System.out.println(JsonUtil.toJson(city));
  }

}
