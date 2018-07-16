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
 * 菜单管理器类
 * 
 * @author huzhonggan
 * @date 2013-12-09
 */
public class MenuManager {
	private static Logger log = LoggerFactory.getLogger(MenuManager.class);

	public static void main(String[] args) {
		UrlUtil url=new UrlUtil();
		// 第三方用户唯一凭证
		//String appId = url.appId;
		String appId = ConfigUtil.getString("appId"); 
		// 第三方用户唯一凭证密钥
		//String appSecret = url.appSecret;
		String appSecret = ConfigUtil.getString("appSecret");
		// 调用接口获取access_token
		AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);

		if (null != at) {
			
			// 调用接口创建菜单
			int result = WeixinUtil.createMenu(getMenu(), at.getToken());

			// 判断菜单创建结果
			if (0 == result)
				log.info("菜单创建成功！");
			else
				log.info("菜单创建失败，错误码：" + result);
		}
	}

	/**
	 * 组装菜单数据
	 * 
	 * @return
	 */
	private static Menu getMenu() {
        /***
         * 车比邻服务start
         */
        ViewButton clubWeb = new ViewButton();  
        clubWeb.setName("俱乐部网");  
        clubWeb.setType("view");  
        clubWeb.setUrl("https://www.95518club.com");  
       
        ViewButton clubWx = new ViewButton();  
        clubWx.setName("车比邻服务");  
        clubWx.setType("view");  
        clubWx.setUrl("http://220.178.5.243:8091/epicc");  
        
        ViewButton viewIBaoYang = new ViewButton();  
        viewIBaoYang.setName("i保养");  
        viewIBaoYang.setType("view");  
        viewIBaoYang.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe73bf0ed24cfca2c&redirect_uri=http://www.chebilin.com/cblwx/index.jsp&response_type=code&scope=snsapi_base&state=openid#wechat_redirect");  
        
        Menu menu = new Menu();  
        menu.setButton(new Button[] {clubWeb,clubWx ,viewIBaoYang});  
  
        return menu;  
	}
}
