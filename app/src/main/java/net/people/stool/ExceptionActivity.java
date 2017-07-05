package net.people.stool;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ExceptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exception);
        throw new NullPointerException();
    }
}
