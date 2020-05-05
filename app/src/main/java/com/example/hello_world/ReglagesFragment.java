package com.example.hello_world;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ReglagesFragment extends Fragment {

    private Button button_7;
    private Button button_6;
    private Button button_5;
    private Button button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_reglages, container, false);
        getActivity().setTitle("Réglages");

        button_7 =(Button) v.findViewById(R.id.button7);
        button_6 =(Button) v.findViewById(R.id.button6);
        button_5 =(Button) v.findViewById(R.id.button5);
        button =(Button) v.findViewById(R.id.button);

        goToEnseignes(button_5);
        goToLangues(button);
        goToSuggestions(button_6);
        goToFAQ(button_7);

        return v;

    }

    public void goToEnseignes(Button button_5) {
        button_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Enseignes.class);
                startActivity(intent);
            }
        });
    }
    public void goToLangues(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Langues.class);
                startActivity(intent);
            }
        });
    }
    public void goToSuggestions(Button button_6) {
        button_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Suggestions.class);
                startActivity(intent);
            }
        });
    }
    public void goToFAQ(Button button_7) {
        button_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FAQ.class);
                startActivity(intent);
            }
        });
    }

}
