package ca.induja.hourofregex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by induj on 2016-10-11.
 */

public class HomeActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void startTeachActivity(View v) {
        Intent intent = new Intent(this, TeachActivity.class);
        startActivity(intent);
    }

//    public void startExpressionTestActivity(View v) {
//        Intent intent = new Intent(this, ExpressionTestActivity.class);
//        startActivity(intent);
//    }
}
