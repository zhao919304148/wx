package com.wx.util;

import com.wx.model.pojo.AccessToken;
import com.wx.model.pojo.Button;
import com.wx.model.pojo.CommonButton;
import com.wx.model.pojo.ComplexButton;
import com.wx.model.pojo.Menu;
import com.wx.model.pojo.ViewButton;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * �˵���������
 * 
 * @author huzhonggan
 * @date 2013-12-09
 */
public class MenuManager {
	private static Logger log = LoggerFactory.getLogger(MenuManager.class);

	public static void main(String[] args) {
		UrlUtil url=new UrlUtil();
		// �������û�Ψһƾ֤
		//String appId = url.appId;
		String appId = ConfigUtil.getString("appId"); 
		// �������û�Ψһƾ֤��Կ
		//String appSecret = url.appSecret;
		String appSecret = ConfigUtil.getString("appSecret");
		// ���ýӿڻ�ȡaccess_token
		AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);

		if (null != at) {
			
			// ���ýӿڴ����˵�
			int result = WeixinUtil.createMenu(getMenu(), at.getToken());

			// �жϲ˵��������
			if (0 == result)
				log.info("�˵������ɹ���");
			else
				log.info("�˵�����ʧ�ܣ������룺" + result);
		}
	}

	/**
	 * ��װ�˵�����
	 * 
	 * @return
	 */
	private static Menu getMenu() {
        /***
         * �����ڷ���start
         */
        ViewButton clubWeb = new ViewButton();  
        clubWeb.setName("���ֲ���");  
        clubWeb.setType("view");  
        clubWeb.setUrl("https://www.95518club.com");  
       
        ViewButton clubWx = new ViewButton();  
        clubWx.setName("�����ڷ���");  
        clubWx.setType("view");  
        clubWx.setUrl("http://220.178.5.243:8091/epicc");  
        
        ViewButton viewIBaoYang = new ViewButton();  
        viewIBaoYang.setName("i����");  
        viewIBaoYang.setType("view");  
        viewIBaoYang.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe73bf0ed24cfca2c&redirect_uri=http://www.chebilin.com/cblwx/index.jsp&response_type=code&scope=snsapi_base&state=openid#wechat_redirect");  
        
        Menu menu = new Menu();  
        menu.setButton(new Button[] {clubWeb,clubWx ,viewIBaoYang});  
  
        return menu;  
	}
}
