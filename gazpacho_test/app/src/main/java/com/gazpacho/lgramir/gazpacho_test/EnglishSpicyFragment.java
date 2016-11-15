package com.gazpacho.lgramir.gazpacho_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class EnglishSpicyFragment extends Fragment {

    private Button mSpicyButton;
    private Button mNotSpicyButton;
    private Button mNextButton;
    private Button mBackButton;
    private TextView mQuestionTextView;

    // private Question mQuestion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // mQuestion = new Question();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.english_fragment_spicy, parent, false);

        mQuestionTextView = (TextView) v.findViewById(R.id.question_text_view);

        mSpicyButton = (Button) v.findViewById(R.id.spicy_button);
        mSpicyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton.getInstance().setAnswerSpice("with spicy");
                Intent intent = new Intent(getActivity(), EnglishTakeawayActivity.class);
                startActivity(intent);

                Toast.makeText(getActivity() , R.string.english_spicy_toast, Toast.LENGTH_SHORT).show();

            }
        });

        mNotSpicyButton = (Button) v.findViewById(R.id.notSpicy_button);
        mNotSpicyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton.getInstance().setAnswerSpice("non-spicy");
                Intent intent = new Intent(getActivity(), EnglishTakeawayActivity.class);
                startActivity(intent);

                Toast.makeText(getActivity() , R.string.english_notSpicy_toast, Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}
