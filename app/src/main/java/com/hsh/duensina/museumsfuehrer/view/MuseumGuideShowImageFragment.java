package com.hsh.duensina.museumsfuehrer.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hsh.duensina.museumsfuehrer.MuseumGuideShowInformationActivity;
import com.hsh.duensina.museumsfuehrer.R;
import com.hsh.duensina.museumsfuehrer.entity.ImageFragmentEntity;
import com.hsh.duensina.museumsfuehrer.presenter.IShowImagePresenter;
import com.hsh.duensina.museumsfuehrer.presenter.ShowImagePresenter;

public class MuseumGuideShowImageFragment extends Fragment implements IMuseumGuideShowImage, IMuseumGuideView {

    private IShowImagePresenter presenter;
    private TextView textView;
    private ImageView imageView;
    private Button closeButton;
    private Animation fadeInAnim;
    private static boolean hasStarted = false;

    public MuseumGuideShowImageFragment() {

    }

    public static MuseumGuideShowImageFragment newInstance() {
        MuseumGuideShowImageFragment fragment = new MuseumGuideShowImageFragment();

        return fragment;
    }

    @Override
    public void onStart(){
        super.onStart();
        textView = (TextView) getView().findViewById(R.id.textViewImage);
        textView.setMovementMethod(new ScrollingMovementMethod());
        imageView = (ImageView) getView().findViewById(R.id.imageView);
        closeButton = (Button) getView().findViewById(R.id.closeImageButton);
        fadeInAnim = AnimationUtils.loadAnimation(MuseumGuideShowInformationActivity.getContext(), R.anim.fadein);

        //TODO: das hier auch für Home- und Back-Button implementieren
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeView();
            }
        });

        if(!hasStarted) {
            setPresenter();
            showInformation(getArguments().getString("url"), getArguments().getString("text"));
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
    public void onPause(){
        super.onPause();
        saveStatus();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_museum_guide_show_image, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void setTextView(String text){
        textView.setText(text);
    }

    @Override
    public void setImageView(Bitmap bmp) {
        imageView.startAnimation(fadeInAnim);
        imageView.setImageBitmap(bmp);
    }

    @Override
    public void setPresenter(){
        this.presenter = new ShowImagePresenter(this);
    }

    @Override
    public void showInformation(String url, String text){
        presenter.showInformation(url, text);
    }

    @Override
    public void restoreStatus(){
        setTextView(ImageFragmentEntity.getImageText());
        imageView.setImageDrawable(ImageFragmentEntity.getImage());
        presenter = ImageFragmentEntity.getPresenter();
    }

    @Override
    public void saveStatus(){
        ImageFragmentEntity.saveStatus(presenter, imageView.getDrawable(), textView.getText().toString());
    }

    @Override
    public void closeView() {
        ImageFragmentEntity.resetStatus();
        hasStarted = false;
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    @Override
    public boolean hasStarted() {
        return hasStarted;
    }
}
