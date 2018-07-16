package com.wx.util; 

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.w3c.dom.*;

import javax.xml.parsers.*;

import java.io.*;

public class XmlUtil{
  private Document doc=null;
  
 public void init(String xmlFile) throws Exception{
  DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
  DocumentBuilder db=dbf.newDocumentBuilder();  
  doc=db.parse(new File(xmlFile));
 }
 public JSONArray viewXML(String xmlFile,String nodeName) throws Exception{
  this.init(xmlFile);
  Element element=doc.getDocumentElement();
  //System.out.println("根元素为:"+element.getTagName());
  NodeList nodeList=doc.getElementsByTagName(nodeName);
  //System.out.println("info节点链的长度:"+nodeList.getLength());
  JSONArray array=new JSONArray();
  for (int m = 0; m < nodeList.getLength(); m++) {
	  JSONObject json=new JSONObject();
	  Node fatherNode=nodeList.item(m);
	  //System.out.println("父节点为:"+fatherNode.getNodeName());
	  //把父节点的属性拿出来
	  NamedNodeMap attributes=fatherNode.getAttributes();
	  for(int i=0;i<attributes.getLength();i++){
	   Node attribute=attributes.item(i);
	   //System.out.println("属性名为:"+attribute.getNodeName()+" 相对应的属性值为:"+attribute.getNodeValue());
	   json.element(attribute.getNodeName(), attribute.getNodeValue());
	  }
	  
	  NodeList childNodes = fatherNode.getChildNodes();
	  for(int j=0;j<childNodes.getLength();j++){
	   Node childNode=childNodes.item(j);
	   //如果这个节点属于Element ,再进行取值
	   if(childNode instanceof Element){
		   //System.out.println("子节点名为:"+childNode.getNodeName()+"相对应的值为"+childNode.getFirstChild().getNodeValue());
	    	json.element(childNode.getNodeName(), childNode.getFirstChild().getNodeValue());
	   }
	  }
	  array.add(json);
}
  
  return array;
  
 }
 
 public static void main(String[] args)throws Exception{
  XmlUtil xmlUtil=new XmlUtil();

//我的XML文件
 JSONArray array=  xmlUtil.viewXML(ConfigUtil.getString("vipxml")+"cominfo.xml","info");
 System.out.println(array);
 }
} 

 
