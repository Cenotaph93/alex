package com.hsh.duensina.museumsfuehrer.view;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.hsh.duensina.museumsfuehrer.R;
import com.hsh.duensina.museumsfuehrer.presenter.IStartScanPresenter;
import com.hsh.duensina.museumsfuehrer.presenter.StartScanPresenter;

public class MuseumGuideStartScanFragment extends Fragment {

    private IStartScanPresenter presenter;
    private Button closeButton;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        presenter = new StartScanPresenter();
        // Start the Service that will scan for beacons near over the entire runtime
        presenter.startCurrentContextService();
    }

    @Override
    public void onStart(){
        super.onStart();
        closeButton = (Button) getView().findViewById(R.id.closeStartScanButton);
        // Set the app to background when the user hits the close button
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_museum_guide_start_scan, container, false);
    }


}
