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
  //System.out.println("��Ԫ��Ϊ:"+element.getTagName());
  NodeList nodeList=doc.getElementsByTagName(nodeName);
  //System.out.println("info�ڵ����ĳ���:"+nodeList.getLength());
  JSONArray array=new JSONArray();
  for (int m = 0; m < nodeList.getLength(); m++) {
	  JSONObject json=new JSONObject();
	  Node fatherNode=nodeList.item(m);
	  //System.out.println("���ڵ�Ϊ:"+fatherNode.getNodeName());
	  //�Ѹ��ڵ�������ó���
	  NamedNodeMap attributes=fatherNode.getAttributes();
	  for(int i=0;i<attributes.getLength();i++){
	   Node attribute=attributes.item(i);
	   //System.out.println("������Ϊ:"+attribute.getNodeName()+" ���Ӧ������ֵΪ:"+attribute.getNodeValue());
	   json.element(attribute.getNodeName(), attribute.getNodeValue());
	  }
	  
	  NodeList childNodes = fatherNode.getChildNodes();
	  for(int j=0;j<childNodes.getLength();j++){
	   Node childNode=childNodes.item(j);
	   //�������ڵ�����Element ,�ٽ���ȡֵ
	   if(childNode instanceof Element){
		   //System.out.println("�ӽڵ���Ϊ:"+childNode.getNodeName()+"���Ӧ��ֵΪ"+childNode.getFirstChild().getNodeValue());
	    	json.element(childNode.getNodeName(), childNode.getFirstChild().getNodeValue());
	   }
	  }
	  array.add(json);
}
  
  return array;
  
 }
 
 public static void main(String[] args)throws Exception{
  XmlUtil xmlUtil=new XmlUtil();

//�ҵ�XML�ļ�
 JSONArray array=  xmlUtil.viewXML(ConfigUtil.getString("vipxml")+"cominfo.xml","info");
 System.out.println(array);
 }
} 

 
