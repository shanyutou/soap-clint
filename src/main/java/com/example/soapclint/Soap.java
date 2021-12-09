/*
 * @Author: Jiwei
 * @Date: 2021-12-07 16:53:55
 * @LastEditTime: 2021-12-09 16:55:02
 * @LastEditors: Jiwei
 * @Description: 
 * @FilePath: \soap-clint-demo\soapdemo\src\main\java\com\example\soapclint\Soap.java
 * 
 */
package com.example.soapclint;

import feign.Body;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface Soap {
    
  @RequestLine("POST /ws/MesService?wsdl")
  @Body("{content}")
  @Headers(value = {"Content-Type: text/xml; charset=utf-8"})
  String contributors(@Param("content") String content);
  

}
/*
 * @Author: Jiwei
 * @Date: 2021-12-07 16:53:55
 * @LastEditTime: 2021-12-07 16:53:56
 * @LastEditors: Jiwei
 * @Description: 
 * @FilePath: \soap-clint-demo\soapdemo\src\main\java\com\example\soapclint\FeignClint.java
 * 
 */
