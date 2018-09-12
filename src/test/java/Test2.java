import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by linweili on 2018/6/14/0014.
 */
public class Test2 {

        public static void main(String[] args) throws Exception {
            String s = "17";
            Number num = Float.parseFloat(s);
            int ii = num.intValue();

            System.out.println(ii);
        }
    }

