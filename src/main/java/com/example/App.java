/*
 * @Author: Jiwei
 * @Date: 2021-12-06 08:57:00
 * @LastEditTime: 2021-12-09 16:56:30
 * @LastEditors: Jiwei
 * @Description: 通过feign 调用soap服务
 * @FilePath: \soap-clint-demo\soapdemo\src\main\java\com\example\App.java
 * 
 */
package com.example;

import com.example.soapclint.Soap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

import feign.Feign;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableFeignClients
public class App 
{

  public static ConfigurableApplicationContext ac;

  private static String createSoapXml(String arg0, String arg1) {
    StringBuilder sb = new StringBuilder();
    sb.append(
        "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://webservice.soap.chinahuanghe.com/\">");
    sb.append("<soapenv:Header/>");
    sb.append("<soapenv:Body>");
    sb.append("<web:Get_Info_Frmbarcode>");
    sb.append("<sTaskType>" + arg0 + "</sTaskType>");
    sb.append("<sImport><![CDATA[" + arg1 + "]]></sImport>");
    sb.append("</web:Get_Info_Frmbarcode>");
    sb.append("</soapenv:Body>");
    sb.append("</soapenv:Envelope>");
    return sb.toString();
  }

    public static void main( String[] args )
    {

      SpringApplication.run(App.class, args);
      System.out.println( "App run success!" );

      Soap soap = Feign.builder()
            // .decoder(new GsonDecoder())
            .target(Soap.class, "http://192.168.2.251:8080");
            String content = createSoapXml("GetCurrentTime", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><GetCurrentTime><Import><DateTimeType>Localtime</DateTimeType></Import></GetCurrentTime>");
        String result = soap.contributors(content);
        System.out.println(result);
    }
}
