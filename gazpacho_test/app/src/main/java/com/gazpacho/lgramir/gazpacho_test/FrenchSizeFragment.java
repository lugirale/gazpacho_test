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

/**
 * Created by homeOne on 10/5/2016.
 */
public class FrenchSizeFragment extends Fragment {

    private Button mBigButton;
    private Button mSmallButton;
    private TextView mQuestionTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.french_fragment_size, parent, false);

        // final TextView big = (TextView) v.findViewById(R.id.big_button);

        mQuestionTextView = (TextView) v.findViewById(R.id.question_text_view);

        mBigButton = (Button) v.findViewById(R.id.big_button);
        mBigButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton.getInstance().setAnswerSize("Grande \n\nPrix total: $ 20.00 (Vingt pesos mexicains 00/100 M.N) \n\n");
                Intent intent = new Intent(getActivity(), FrenchIngredientsActivity.class);
                startActivity(intent);

                Toast.makeText(getActivity() , R.string.french_big_toast, Toast.LENGTH_SHORT).show();
            }
        });

        mSmallButton = (Button) v.findViewById(R.id.small_button);
        mSmallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton.getInstance().setAnswerSize("Petite \n\nPrix total: $ 10.00 (Dix pesos mexicains 00/100 M.N)) \n\n");
                Intent intent = new Intent(getActivity(), FrenchIngredientsActivity.class);
                startActivity(intent);

                Toast.makeText(getActivity() , R.string.french_small_toast, Toast.LENGTH_SHORT).show();

            }
        });

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

}
