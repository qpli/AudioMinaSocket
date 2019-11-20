package com.example.mina;

import android.util.Log;

import com.example.record.FileUtils;

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
