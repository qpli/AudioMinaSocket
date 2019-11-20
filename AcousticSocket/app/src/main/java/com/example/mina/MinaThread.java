package com.example.mina;

import android.util.Base64;

import com.example.record.FileUtils;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.List;

import static org.apache.mina.filter.codec.textline.LineDelimiter.WINDOWS;

/**
 * Created by pactera on //.
 */

public class MinaThread extends Thread {
//    private IoSession session = null;
//    public String filename ;
    public MinaThread( ){

    }

    public boolean isFileExit(){
        List<File> list = FileUtils.getWavFiles();
        if(list.size()<1||list==null){
            return false;
        }
        return true;
    }

    public String getFilename(){
        if(isFileExit()){
            List<File> list = FileUtils.getWavFiles();
            String filename = list.get(FileUtils.getWavFiles().size()-1).getName();
            return filename;
        }
        return "";
    }
    @Override
    public void run() {
        // Create TCP/IP connector.
        IoConnector connector=new NioSocketConnector();
        //编写过滤器，设置编码
//        connector.getFilterChain().addLast("codec",
//                new ProtocolCodecFilter(
//                        new TextLineCodecFactory(
//                                Charset.forName("GBK"),
//                                WINDOWS.getValue(),
//                                WINDOWS.getValue()
//                        )
//                )
//        );
        TextLineCodecFactory lineCodec=new TextLineCodecFactory(Charset.forName("ISO_8859_1"));
        //最大传输为1M

        lineCodec.setDecoderMaxLineLength(1024*1024);
        lineCodec.setEncoderMaxLineLength(1024*1024);
        connector.getFilterChain().addLast( "logger", new LoggingFilter() );
        connector.getFilterChain().addLast( "codec", new ProtocolCodecFilter( lineCodec));
//        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new CharsetCodecFactory()));
        //设置超时
        connector.setConnectTimeoutMillis(30000);
        //为连接器设置管理服务
        connector.setHandler(new MinaClientHandler());

        //连接服务器
        ConnectFuture connectFuture = connector.connect(new InetSocketAddress(ConstantUtil.IP_ADDRESS, ConstantUtil.PORT));

        connectFuture.awaitUninterruptibly(); // 连接完了，等着吧
        System.err.println("Mina客户端启动完成。。。");
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        // 读取输入内容，发送
        while (true){
            try {
            //这里获取需要上传的数据
                if(getFilename()!=""){
                    String absoulateFilePath = FileUtils.getWavFileAbsolutePath(getFilename());
                    byte[] data = FileUtils.getWAVFileByte(absoulateFilePath);
                    JSONObject jsonObject = new JSONObject();
//                    String str = Base64.encodeToString(data,Base64.DEFAULT);
                    String str = new String(data, Charset.forName("ISO_8859_1"));

                    jsonObject.put("wav",str);
                    jsonObject.put("user","lqp");
                    jsonObject.put("fileName",getFilename());

                    connectFuture.getSession().write(jsonObject);
                    System.out.println("发送消息成功"+jsonObject.toString());
//                    System.out.println("发送消息成功");
                    Thread.sleep(3000);
                }
            }catch (InterruptedException | IOException | JSONException e){
                e.printStackTrace();
            }

        }
        // 关闭连接
//        connector.dispose();
//        connectFuture.getSession().getCloseFuture().awaitUninterruptibly();
    }

}
