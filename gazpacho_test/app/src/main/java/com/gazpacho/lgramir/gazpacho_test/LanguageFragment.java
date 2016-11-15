package com.gazpacho.lgramir.gazpacho_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class LanguageFragment extends Fragment {

    private Button mSpanishButton;
    private Button mEnglishButton;
    private Button mFrenchButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_language, parent, false);

        mSpanishButton = (Button) v.findViewById(R.id.Spanish_button);
        mSpanishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ChoiceActivity.class);
                startActivity(i);
                Toast.makeText(getActivity(), R.string.Spanish_toast, Toast.LENGTH_SHORT).show();
            }
        });

        mEnglishButton = (Button) v.findViewById(R.id.English_button);
        mEnglishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), EnglishWTFGazpachoActivity.class);
                startActivity(i);
                Toast.makeText(getActivity(), R.string.English_toast, Toast.LENGTH_SHORT).show();
            }
        });

        mFrenchButton = (Button) v.findViewById(R.id.French_button);
        mFrenchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), FrenchWTFGazpachoActivity.class);
                startActivity(i);
                Toast.makeText(getActivity(), R.string.French_toast, Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}


