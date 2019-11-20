package com.example.mina;

import android.util.Log;

import com.example.record.FileUtils;

import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.json.JSONObject;

import java.io.FileInputStream;

import static android.R.id.message;

/**
 * Created by pactera on //.
 */

public class MinaClientHandler extends IoHandlerAdapter {

//    public String filename;
//    public MinaClientHandler(String filename){
//        this.filename = filename;
//    }
    //接收到来自服务端的消息
    @Override
    public void messageReceived(IoSession session, Object message)
            throws Exception {
        super.messageReceived(session, message);
        String msg = message.toString();
        System.out.println("TEST"+"客户端接收到的信息为:" + msg);
        //获取文件并上传
//        String absoulateFilePath = FileUtils.getWavFileAbsolutePath(filename);
//        byte[] data = FileUtils.getWAVFileByte(absoulateFilePath);
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("wav",data);
//        jsonObject.put("user","lqp");
//        session.write(jsonObject);
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        //向服务器端发送消息
      //  session.write("this is client");

        super.sessionOpened(session);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        super.sessionClosed(session);
        for(;;){
            try{
                System.out.println("尝试重连");
                Thread.sleep(3000);
                ConnectFuture future = MinaThread.connector.connect();
                future.awaitUninterruptibly();// 等待连接创建成功
                session = future.getSession();// 获取会话
                if(session.isConnected()){
                    // logger.info("断线重连["+ connector.getDefaultRemoteAddress().getHostName() +":"+ connector.getDefaultRemoteAddress().getPort()+"]成功");
                    System.out.println("断线重连["+ MinaThread.connector.getDefaultRemoteAddress().toString() +":"+ MinaThread.connector.getDefaultRemoteAddress().toString()+"]成功");
                    break;
                }
            }catch(Exception ex){
                //logger.info("重连服务器登录失败,3秒再连接一次:" + ex.getMessage());
                System.out.println("重连服务器登录失败,3秒再连接一次:" + ex.getMessage());
            }
        }
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        super.messageSent(session, message);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause)
            throws Exception {
        Log.d("TEST", "客户端发生异常"+cause.toString());
        super.exceptionCaught(session, cause);
    }
}
