package servlet;

import util.CheckUtil;
import util.MessageUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import pojo.TextMessage;

public class WeixinServlet extends HttpServlet {
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws IOException,ServletException{
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");
		
		PrintWriter out = resp.getWriter();
		if(CheckUtil.checkSignature(signature, timestamp, nonce)){
			out.print(echostr);
		}
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException,ServletException{
		
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		PrintWriter out=resp.getWriter();
		try {
			Map<String,String> map = MessageUtil.xmlToMap(req);
			String fromUserName =(String) map.get("FromUserName");
			String toUserName =(String) map.get("ToUserName");
			String msgType =(String) map.get("MsgType");
			String content =(String) map.get("Content");
			String eventKey =(String) map.get("EventKey");
			
			String message=null;
			if("text".equals(msgType)){
				if("你好".equals(content)){
					message=MessageUtil.initText(toUserName, fromUserName, MessageUtil.textMenu());
				}
			}else if(MessageUtil.MESSAGE_EVENT.equals(msgType)){
				String eventType=map.get("Event");
				if(MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)){
					message=MessageUtil.initNewsMessage(toUserName, fromUserName);
				}else if(MessageUtil.MESSAGE_CLICK.equals(eventType)){
					if(eventKey.equals("b2")){
						message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.mainMenu());
					}
					if(eventKey.equals("b3")){
						message=MessageUtil.initNewsMessage(toUserName, fromUserName);
					}
				}
			}
			
			System.out.println(message);
			out.print(message);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		finally {
			out.close();
		}
	}
	

}
