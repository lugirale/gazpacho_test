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

/**************UI Fragment*************/

public class SizeFragment extends Fragment{

    private Button mBigButton;
    private Button mSmallButton;
    private Button mMediumButton;
    private TextView mQuestionTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

       @Override
       public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
           View v = inflater.inflate(R.layout.fragment_size, parent, false);

          // final TextView big = (TextView) v.findViewById(R.id.big_button);

           mQuestionTextView = (TextView) v.findViewById(R.id.question_text_view);

           mBigButton = (Button) v.findViewById(R.id.big_button);
           mBigButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Singleton.getInstance().setAnswerSize("grande \n\nTotal a pagar: $30.00(Treinta pesos 00/100 M.N) \n\n");
                   Intent intent = new Intent(getActivity(), TakeawayActivity.class);
                   startActivity(intent);

                   Toast.makeText(getActivity() , R.string.big_toast, Toast.LENGTH_SHORT).show();
               }
           });

           mMediumButton = (Button) v.findViewById(R.id.medium_button);
           mMediumButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Singleton.getInstance().setAnswerSize("mediano \n\nTotal a pagar: $20.00(Veinte pesos 00/100 M.N) \n\n");
                   Intent intent = new Intent(getActivity(), TakeawayActivity.class);
                   startActivity(intent);

                   Toast.makeText(getActivity() , R.string.medium_toast, Toast.LENGTH_SHORT).show();
               }
           });

           mSmallButton = (Button) v.findViewById(R.id.small_button);
           mSmallButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Singleton.getInstance().setAnswerSize("chico \n\nTotal a pagar: $10.00(Diez pesos 00/100 M.N) \n\n");
                   Intent intent = new Intent(getActivity(), TakeawayActivity.class);
                   startActivity(intent);

                   Toast.makeText(getActivity() , R.string.small_toast, Toast.LENGTH_SHORT).show();

               }
           });

           return v;
       }

    @Override
       public void onSaveInstanceState(Bundle outState) {
           super.onSaveInstanceState(outState);

       }

}
