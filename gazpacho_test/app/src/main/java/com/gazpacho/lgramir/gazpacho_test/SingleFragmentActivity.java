package com.gazpacho.lgramir.gazpacho_test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


public abstract class SingleFragmentActivity extends FragmentActivity {



    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
/*
Hiding the Status Bar
it crashes, even if I place it before or after the setContentView()
Use it before super.onCreate(savedInstanceState);
 */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);


        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);



        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

/*
        boolean mHandler =  new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent mainIntent = new Intent(SingleFragmentActivity.this, UsbConnect.class);
                SingleFragmentActivity.this.startActivity(mainIntent);
                SingleFragmentActivity.this.finish();
            }
        }, 5000);
*/
    }


}
