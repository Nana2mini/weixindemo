package main;

import org.junit.Test;

import menu.Menu;
import net.sf.json.JSONObject;
import pojo.AccessToken;
import util.WeixinUtil;

import java.io.IOException;

import org.apache.http.ParseException;

public class WeixinTest {

	@Test
	public void testGetAccessToken() {
		try {
			AccessToken token = WeixinUtil.getAccessToken();
			System.out.println("票据"+token.getToken());
			System.out.println("有效时间"+token.getExpiresIn());
			System.out.println(System.currentTimeMillis());
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCreateMenu(){
		try {
			AccessToken token = WeixinUtil.getAccessToken();
			Menu menu=WeixinUtil.initMenu();
			String menuStr=JSONObject.fromObject(menu).toString();
			int result=WeixinUtil.createMenu(token.getToken(), menuStr);
			if(result==0){
				System.out.println("创建菜单成功");
			}else{
				System.out.println("错误码"+result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
