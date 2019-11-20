package com.example.mina;


import java.nio.charset.Charset;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.textline.TextLineDecoder;
import org.apache.mina.filter.codec.textline.TextLineEncoder;

public class CharsetCodecFactory implements ProtocolCodecFactory {
    private ProtocolDecoder decoder;
    private ProtocolEncoder encoder;

    public CharsetCodecFactory() {
        TextLineDecoder textLineDecoder = new TextLineDecoder(Charset.forName("GBK"), Constant.SPLIT_END);
        textLineDecoder.setMaxLineLength(102400);
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

