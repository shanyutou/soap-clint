/*
 * @Author: Jiwei
 * @Date: 2021-12-06 08:58:31
 * @LastEditTime: 2021-12-09 17:14:48
 * @LastEditors: Jiwei
 * @Description: 
 * @FilePath: \soap-clint-demo\soapdemo\src\main\java\com\example\soapclint\SoapClint.java
 * 
 */
package com.example.soapclint;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

public class SoapClint {

  public static void main(String[] args) throws Exception {

    /**
     * 设置超时时间
     */
    System.setProperty("saaj.connect.timeout", "3000");
    System.setProperty("saaj.receive.timeout", "1000");

    SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
    SOAPConnection soapConnection = soapConnectionFactory.createConnection();

    /**
     * 该方式设置超时时间无效，阅读源码可发现
     */
    // URL url = new URL(new URL("http://192.168.2.251:8080/ws/MesService?wsdl"),
		// 		          "",
		// 		          new URLStreamHandler() {
		// 		            @Override
		// 		            protected URLConnection openConnection(URL url) throws IOException {
		// 		              URL target = new URL(url.toString());
		// 		              URLConnection connection = target.openConnection();
		// 		              // Connection settings
		// 		              connection.setConnectTimeout(4000); // 10 sec
		// 		              connection.setReadTimeout(2000); // 1 min
		// 		              return(connection);
		// 		            }
		// 		          });

    URL url = new URL("http://172.19.2.8/MESSvc/WebService.asmx?wsdl");
    
    SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(), url);
    
    // Process the SOAP Response
    printSOAPResponse(soapResponse);
    
    soapConnection.close();
  }

  private static SOAPMessage createSOAPRequest() throws Exception {
    MessageFactory messageFactory = MessageFactory.newInstance();
    SOAPMessage soapMessage = messageFactory.createMessage();
    
    // SOAP Envelope
    SOAPPart soapPart = soapMessage.getSOAPPart();
    
    SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
    soapEnvelope.addNamespaceDeclaration("soap", "http://www.w3.org/2003/05/soap-envelope");
    soapEnvelope.addNamespaceDeclaration("auto", "http://Auto.huawei.com.cn/");

    
    // SOAP Body
    SOAPBody soapBody = soapEnvelope.getBody();
    SOAPElement soapBodyElem = soapBody.addChildElement("Get_Info_Frmbarcode", "auto");

    SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("sTaskType", "auto");
    soapBodyElem1.addTextNode("GetCurrentTime");
    SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("sImport", "auto");
    soapBodyElem2.addTextNode("<?xml version=\"1.0\" encoding=\"UTF-8\"?><GetCurrentTime><Import><DateTimeType>Localtime</DateTimeType></Import></GetCurrentTime>");
    
    // MimeHeaders headers = soapMessage.getMimeHeaders();
    // headers.addHeader("SOAPAction", "http://example.com/sayHello");

    soapMessage.saveChanges();
    return soapMessage;
  }

  private static void printSOAPResponse(SOAPMessage soapResponse) throws Exception {
    System.out.print("\nResponse SOAP Message = ");
    System.out.println(soapResponse.getSOAPBody().getTextContent());
    
  }
  
}
