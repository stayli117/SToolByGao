package net.people.stoolui.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import net.people.stoolui.R;

public class BaseActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView sTitleTV;
    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Log.e(TAG, "onCreate: -->");
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        initToolbar();
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.stool_toolbar);
        sTitleTV = (TextView) findViewById(R.id.stool_toolbar_title);
        if (mToolbar != null) {
            getSupportActionBar().hide();
            mToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.stool_bg_white));
            mToolbar.setNavigationIcon(R.drawable.ic_close);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    public void setCustomTitle(String title) {
        if (sTitleTV != null)
            sTitleTV.setText(title);
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: -->");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: --->");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: --->");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: --->");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: --->");
    }
}
