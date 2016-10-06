package com.hsh.duensina.museumsfuehrer.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hsh.duensina.museumsfuehrer.R;

public class MuseumGuideShowTextFragment extends Fragment implements IMuseumGuideView{

    private TextView infoTextView;
    private Button closeButton;
    private static boolean hasStarted = false;
    private static String text;

    public MuseumGuideShowTextFragment() {
        // Required empty public constructor
    }


    public static MuseumGuideShowTextFragment newInstance() {
        MuseumGuideShowTextFragment fragment = new MuseumGuideShowTextFragment();

        return fragment;
    }

    @Override
    public void onStart(){
        super.onStart();

        infoTextView = (TextView) getView().findViewById(R.id.infoTextView);
        infoTextView.setMovementMethod(new ScrollingMovementMethod());
        closeButton = (Button) getView().findViewById(R.id.closeTextInfoButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
        });
        if(!hasStarted) {
            infoTextView.setText(getArguments().getString("text"));
            hasStarted = true;
        } else {
            restoreStatus();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_museum_guide_show_text, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onPause(){
        super.onPause();
        saveStatus();

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void closeView() {
        text = null;
    }

    @Override
    public void saveStatus() {
        text = infoTextView.getText().toString();
    }

    @Override
    public void restoreStatus() {
        infoTextView.setText(text);
    }

    @Override
    public boolean hasStarted() {
        return hasStarted;
    }
}
