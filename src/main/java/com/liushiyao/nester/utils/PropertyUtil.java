package com.liushiyao.nester.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * 配置文件加载
 */
public class PropertyUtil {
  private static final Logger logger = Logger.getLogger(PropertyUtil.class);
  private static Properties props;

  static {
    loadProps();
  }


  synchronized static private void loadProps() {
    logger.info("开始加载properties文件内容.......");
    props = new Properties();
    InputStream in = null;
    try {
      String path = Thread.currentThread().getContextClassLoader().getResource("config.properties").getPath();
      in=new FileInputStream(path);
      props.load(in);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      logger.error("config.properties文件未找到");
    } catch (IOException e) {
      logger.error("出现IOException");
    } finally {
      try {
        if (null != in) {
          in.close();
        }
      } catch (IOException e) {
        logger.error("config.properties文件流关闭出现异常");
      }
    }
    logger.info("加载properties文件内容完成...........");
    logger.info("properties文件内容：" + props);
  }

  public static String getProperty(String key) {
    if (props.isEmpty()) {
      loadProps();
    }
    return props.getProperty(key);
  }

  public static String getProperty(String key, String defaultValue) {
    if (null == props) {
      loadProps();
    }
    return props.getProperty(key, defaultValue);
  }
}
