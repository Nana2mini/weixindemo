package util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import menu.*;
import net.sf.json.JSONObject;
import pojo.AccessToken;

/**
 * 微信工具类
 * @author z
 *
 */
public class WeixinUtil {
	private static final String APPID = "wx11104aea81222551";
	private static final String APPSECRET = "1a91386872151c2be9f53b33fc9e54d1";
	
	private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
												   
	private static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	
	
	/**
	 * 获取accessToken
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static AccessToken getAccessToken() throws ParseException, IOException{
		AccessToken token = new AccessToken();
		String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
		JSONObject jsonObject = HttpMethodUtil.doGetStr(url);

		if(jsonObject!=null){
			token.setToken(jsonObject.getString("access_token"));
			token.setExpiresIn(jsonObject.getInt("expires_in"));
		}
		return token;
	}
	
	/**
	 *  组装菜单
	 * @return
	 */
	public static Menu initMenu(){
		Menu menu = new Menu();
		ClickButton button1 = new ClickButton();
		button1.setName("菜单A");
		button1.setType("click");
		button1.setKey("b1");		
		
		ViewButton button21 = new ViewButton();
		button21.setName("baidu");
		button21.setType("view");
		button21.setUrl("http://www.baidu.com");
		
		ClickButton button22 = new ClickButton();
		button22.setName("文本信息");
		button22.setType("click");
		button22.setKey("b2");
		
		ClickButton button23 = new ClickButton();
		button23.setName("图文信息");
		button23.setType("click");
		button23.setKey("b3");
		
		Button button2 = new Button();
		button2.setName("菜单B");
		button2.setSub_button(new Button[]{button21, button22, button23});
				
		menu.setButton(new Button[]{button1, button2});	
		return menu;				
	}
	
	/**
	 * 自定义菜单-创建接口
	 * @param accessToken
	 * @param menu
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static int createMenu(String accessToken,String menu) throws ParseException, IOException{
		int result = 0;
		String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = HttpMethodUtil.doPostStr(url, menu);
		if(jsonObject != null){
			result = jsonObject.getInt("errcode");
		}
		return result;
	}
	

}
