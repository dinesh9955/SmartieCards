package com.smartiecards.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.smartiecards.BaseAppCompactActivity;
import com.smartiecards.R;
import com.smartiecards.util.EasyFontsCustom;

public class FullTextActivity extends BaseAppCompactActivity {


    TextView textView;

    @Override
    protected int getLayoutResource() {
        return R.layout.fulltext_activity;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setBackgroundDrawableResource(R.drawable.bg);

        textView = (TextView) findViewById(R.id.textView4645667);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView textViewTitleBar = (TextView) findViewById(R.id.textView_title);
        textViewTitleBar.setText("Detail");


        Bundle bundle = getIntent().getExtras();

        String xxxx = bundle.getString("key");

        textView.setText(xxxx);
        textView.setTypeface(EasyFontsCustom.avenirnext_TLPro_Medium(FullTextActivity.this));

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
