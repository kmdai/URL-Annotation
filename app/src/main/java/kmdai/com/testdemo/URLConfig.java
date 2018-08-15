package kmdai.com.testdemo;

import com.codyy.urlannotation.URL;
import com.codyy.urlannotation.URLBase;

/**
 * Created by kmdai on 17-12-18.
 */

public class URLConfig {

    @URLBase
    public static String sURLBase = "www.baidu.com";
    @URL("/hahah/gagaga")
    public static String mTest;
    @URL("/test/haha")
    public static String mUrl;
}
