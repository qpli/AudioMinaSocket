import org.apache.mina.filter.codec.textline.LineDelimiter;

public class Constant {
    public static int seriaNum = 0;
    public static String USER_ID = "EVT_ID";
    public static String ALIVE = "EVT_ALIVE";
    public static String RE_OK = "EVT_GET_RECORD_OK";
    public static String RECORD = "FUN_GET_RECORD";
    public static String RE_RET = "EVT_GET_RECORD_RET";
    public static final LineDelimiter SPLIT_END = new LineDelimiter("\u0005");
    public static String one = "\u0001";
    public static String three = "\u0003";
    public static String four = "\u0004";

    public Constant() {
    }
}
