import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.sql.Timestamp;


//import java.util.Base64;
//import java.util.Base64.Decoder;
//import java.util.Base64.Encoder;
import java.util.Base64;
//import java.util.Base64;
//import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import jdk.nashorn.internal.parser.JSONParser;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
//import sun.misc.BASE64Decoder;

import java.util.Timer;
import java.util.logging.Logger;

public class Demo1ServerHandler extends IoHandlerAdapter {
	public static Logger logger = Logger.getLogger(Demo1ServerHandler.class + "");

	// 从端口接受消息，会响应此方法来对消息进行处理
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		super.messageReceived(session, message);
		System.out.println("接收到消息");
		String msg = message.toString();

		System.out.println(msg);
		JSONObject jsonObj = JSONObject.parseObject(msg);
		System.out.println(jsonObj.get("wav").getClass());
//		Decoder decoder = Base64.getDecoder();
//		byte[] bytes = Base64.getDecoder().decode(jsonObj.getString("wav"));
		byte[] bytes = (jsonObj.getString("wav")).getBytes(Charset.forName("ISO_8859_1"));
		String fileName = jsonObj.getString("fileName");
		System.out.println("fileName:----"+fileName);
//		Date date = new Date();
//		Timestamp timestamp = new Timestamp(date.getTime());
//		String sql = "insert into ";

		//将socket传输过来的数据流存储为文件
		boolean writeRes = FileUtil.write(bytes,fileName);
		if(writeRes){
			//如果存储成功，则调用python代码进行识别
			System.out.println("存储成功");
		}

		/*float longitude = (float)jsonObj.get("longitude");
		System.out.println(longitude);*/
//		System.out.println(jsonObj.toJSONString());
//		if ("exit".equals(msg)) {
//			// 如果客户端发来exit，则关闭该连接
//			session.close(true);
//		}
		
		// 向客户端发送消息
//		Date date = new Date();
//		session.write(date);
//		System.out.println("服务器接受消息成功..." + jsonObj.toJSONString());

	}

	// 向客服端发送消息后会调用此方法
	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		System.out.println("服务器发送消息成功...");
		super.messageSent(session, message);
	}

	// 关闭与客户端的连接时会调用此方法
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("服务器与客户端断开连接...");
		super.sessionClosed(session);
	}

	// 服务器与客户端创建连接
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		System.out.println("服务器与客户端创建连接...");
		super.sessionCreated(session);
		session.write('1');
	}

	// 服务器与客户端连接打开
	@Override
	public void sessionOpened(IoSession session) throws Exception {
		System.out.println("服务器与客户端连接打开...");
		
		super.sessionOpened(session);
		session.write("This is server");
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		System.out.println("服务器进入空闲状态...");
		super.sessionIdle(session, status);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		System.out.println("服务器发送异常..."+cause.getMessage());
		super.exceptionCaught(session, cause);
	}

}
