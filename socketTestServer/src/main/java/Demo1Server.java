import java.net.InetSocketAddress;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.util.logging.Logger;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketConnector;


public class Demo1Server {

	//日志类的实现
    private static Logger logger = Logger.getLogger(Demo1Server.class+"");
    //端口号，要求客户端与服务器端一致
    private static int PORT = 9088;
    public static void main(String[] args) throws Exception{

        TextLineCodecFactory lineCodec=new TextLineCodecFactory(Charset.forName("ISO_8859_1"));
        //最大传输为1M

        lineCodec.setDecoderMaxLineLength(1024*1024);
        lineCodec.setEncoderMaxLineLength(1024*1024);
        IoAcceptor acceptor = new NioSocketAcceptor();
        acceptor.getFilterChain().addLast( "logger", new LoggingFilter() );
        acceptor.getFilterChain().addLast( "codec", new ProtocolCodecFilter( lineCodec));
//        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new CharsetCodecFactory()));
        acceptor.setHandler(  new Demo1ServerHandler() );
        acceptor.getSessionConfig().setReadBufferSize( 2048 );
        acceptor.getSessionConfig().setIdleTime( IdleStatus.BOTH_IDLE, 10 );
        acceptor.bind(new InetSocketAddress(PORT));

//        IoSession ioSession =
   }
}