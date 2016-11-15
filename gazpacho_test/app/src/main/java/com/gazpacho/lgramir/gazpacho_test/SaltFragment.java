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


public class SaltFragment extends Fragment {

    private Button mSaltButton;
    private Button mNotSaltButton;
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
        View v = inflater.inflate(R.layout.fragment_salt, parent, false);

        mQuestionTextView = (TextView) v.findViewById(R.id.question_text_view);

        mSaltButton = (Button) v.findViewById(R.id.salt_button);
        mSaltButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton.getInstance().setAnswerSalt("con sal");
                Intent intent = new Intent(getActivity(), SpicyActivity.class);
                startActivity(intent);

                Toast.makeText(getActivity() , R.string.salt_toast, Toast.LENGTH_SHORT).show();

            }
        });

        mNotSaltButton = (Button) v.findViewById(R.id.notSalt_button);
        mNotSaltButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton.getInstance().setAnswerSalt("sin sal");
                Intent intent = new Intent(getActivity(), SpicyActivity.class);
                startActivity(intent);

                Toast.makeText(getActivity() , R.string.notSalt_toast, Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}
