package kmdai.com.testdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.codyy.urlannotation.URL;
import com.codyy.urlannotation.URLBase;
import com.codyy.urlannotation.URLManager;

public class MainActivity extends AppCompatActivity {
    @URLBase
    public static String sBase = "hahahahahah";
    @URL("/login")
    public static String sLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void reSet(View view) {
        System.out.println("-------" + sLogin);
        URLManager.reSetUrls(this, "gagagagagagagag", MainActivity.class);
        System.out.println("-------" + sLogin);
    }
}
