package com.gazpacho.lgramir.gazpacho_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChoiceFruitFragment extends Fragment {

    private Button mTradicionalButton;
    private Button  mNextButton;
    private TextView mQuestionTextView;
    private ListView mcheckList;

    private ImageView image;


    ArrayList<String> selectedItems= new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_choice_ingredients_fruit, parent, false);

        mQuestionTextView = (TextView) v.findViewById(R.id.question_text_view1);

        mcheckList = (ListView) v.findViewById(R.id.checkable_list);

        mcheckList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        String[] items = {"Jicama","Pepino","Sandia","Mango","Piña","Papaya","Melon","Naranja","Jugo de naranja","Limon" ,"Sal","Chile en polvo","Chile de botella","Queso"};


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.rowlayout, R.id.txt_ingredients,items);
        mcheckList.setAdapter(adapter);

        mcheckList.setOnItemClickListener(new AdapterView.OnItemClickListener() {



            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = ((TextView) view).getText().toString();
                if (selectedItems.contains(selectedItem)) {
                    selectedItems.remove(selectedItem); //uncheck itme
                } else
                    selectedItems.add(selectedItem);
            }
        });

        mNextButton = (Button) v.findViewById(R.id.selected_ingredients_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton.getInstance().setIngredients(selectedItems);
                Intent intent = new Intent(getActivity(), LanguageActivity.class);
                startActivity(intent);

                if(selectedItems.size()!=0){
                    showSelectedItems(selectedItems);

                }else
                    Toast.makeText(getActivity(),R.string.tradicional_toast, Toast.LENGTH_SHORT).show();

            }

        });
/*
            mTradicionalButton = (Button) v.findViewById(R.id.tradicional_button);
            mTradicionalButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Singleton.getInstance().setAnswerTradicional("Gazpacho Tradicional");
                    Intent intent = new Intent(getActivity(), SpicyActivity.class);
                    startActivity(intent);
                    Toast.makeText(getActivity(),R.string.tradicional_toast, Toast.LENGTH_SHORT).show();
                }

            });
*/



        return v;
    }

    public void showSelectedItems(ArrayList<String> view){
        String items = " ";

        for(String item:selectedItems){
            items+= item +"\n";
        }
        Toast.makeText(getActivity(),"Ingredientes selecionados: \n"+items, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}
