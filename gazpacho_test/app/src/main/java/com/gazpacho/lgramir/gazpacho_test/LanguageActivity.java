package com.gazpacho.lgramir.gazpacho_test;

import android.support.v4.app.Fragment;

public class LanguageActivity extends SingleFragmentActivity {



    @Override
    protected Fragment createFragment() {
        return new LanguageFragment();
    }
}
