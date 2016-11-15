package com.gazpacho.lgramir.gazpacho_test;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GazpachoFragment extends Fragment {

    private Button mTradicionalButton;
    private Button  mChoiceButton;
    private TextView mQuestionTextView;

    private ImageView image;

    ArrayList<String> selectedItems= new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gazpacho, parent, false);

        mQuestionTextView = (TextView) v.findViewById(R.id.question_text_view);

        image = (ImageView) v.findViewById(R.id.imageView1);
        image = (ImageView) v.findViewById(R.id.imageView2);

        mChoiceButton = (Button) v.findViewById(R.id.choice_button);
        mChoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), IngredientsGazpachoActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity(),R.string.choice_toast, Toast.LENGTH_SHORT).show();
            }

        });


        mTradicionalButton = (Button) v.findViewById(R.id.tradicional_button);
        mTradicionalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton.getInstance().setIngredients(selectedItems);
                Intent intent = new Intent(getActivity(), SizeActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity(),R.string.tradicional_toast, Toast.LENGTH_SHORT).show();
            }

        });

        return v;
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


}