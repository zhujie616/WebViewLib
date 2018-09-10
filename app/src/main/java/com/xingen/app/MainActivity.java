package com.xingen.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private WebView webView;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        findViewById(R.id.main_test_input_click_btn).setOnClickListener(this);
        findViewById(R.id.main_test_scroll_btn).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_test_input_click_btn: {
                Intent intent = new Intent(this, InputAndClickTestActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.main_test_scroll_btn: {

                Intent intent = new Intent(this, ScrollTestActivity.class);
                startActivity(intent);
            }

            break;
        }
    }
}
