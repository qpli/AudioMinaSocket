import java.nio.charset.Charset;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.codec.textline.TextLineDecoder;
import org.apache.mina.filter.codec.textline.TextLineEncoder;

public class CharsetCodecFactory implements ProtocolCodecFactory {
    private ProtocolDecoder decoder;
    private ProtocolEncoder encoder;

    public CharsetCodecFactory() {
//        TextLineCodecFactory lineCodec=new TextLineCodecFactory(Charset.forName("UTF-8"));
//        //最大传输为1M
//
//        lineCodec.setDecoderMaxLineLength(1024*1024);
//        lineCodec.setEncoderMaxLineLength(1024*1024);
        TextLineDecoder textLineDecoder = new TextLineDecoder(Charset.forName("GBK"), Constant.SPLIT_END);
        textLineDecoder.setMaxLineLength(10240000);
        this.decoder = textLineDecoder;
        TextLineEncoder textLineEncoder = new TextLineEncoder(Charset.forName("GBK"), Constant.SPLIT_END);
        textLineEncoder.setMaxLineLength(102400);
        this.encoder = textLineEncoder;
    }

    public ProtocolDecoder getDecoder(IoSession session) throws Exception {
        return this.decoder;
    }

    public ProtocolEncoder getEncoder(IoSession session) throws Exception {
        return this.encoder;
    }
}

