import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by lqp on 2019/11/19
 */
public class FileUtil {
    public static boolean write(byte[] data,String filename)
    {

        File file = new File("C:\\my_java_code\\SocketWavFile\\"+filename);

//        if(file.exists()){
//            file.delete();
//        }
        try{
//            file.createNewFile();
            OutputStream os=new FileOutputStream(file);
            os.write(data);
            os.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


}
