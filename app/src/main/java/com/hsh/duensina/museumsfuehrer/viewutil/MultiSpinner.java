package com.hsh.duensina.museumsfuehrer.viewutil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.hsh.duensina.museumsfuehrer.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 25.05.16.
 */
public class MultiSpinner extends Spinner implements DialogInterface.OnMultiChoiceClickListener, DialogInterface.OnCancelListener {

    private List<String> items;
    private boolean[] selected;
    private String defaultText;
    private MultiSpinnerListener listener;

    public MultiSpinner(Context context) {
        super(context);
    }

    public MultiSpinner(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
    }

    public MultiSpinner(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);
    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {
        StringBuffer spinnerBuffer = new StringBuffer();
        String spinnerText = "";
        ArrayAdapter<String> adapter;

        for(int i=0;i< items.size();i++){
            if(selected[i]){
                spinnerBuffer.append(items.get(i));
                spinnerBuffer.append(", ");
            }
        }
        spinnerText = spinnerBuffer.toString();

        if(spinnerText.length() > 2){
            spinnerText = spinnerText.substring(0, spinnerText.length() -2);
        } else{
            spinnerText = defaultText;
        }

        adapter = new ArrayAdapter<String>(getContext(),
                R.layout.custom_spinner_item,
                new String[] {spinnerText});
        setAdapter(adapter);
        listener.onItemsSelected(selected);
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int selectedItem, boolean isChecked) {
        if(isChecked){
            selected[selectedItem] = true;
        } else{
            selected[selectedItem] = false;
        }
    }

    @Override
    public boolean performClick(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMultiChoiceItems(items.toArray(new CharSequence[items.size()]), selected, this);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
           @Override
            public void onClick(DialogInterface dialog, int selectedItem){
               dialog.cancel();
           }
        });

        builder.setOnCancelListener(this);
        builder.show();
        return false;
    }

    public void setItems(List<String> items, String allText, MultiSpinnerListener listener){
        this.items = items;
        this.defaultText = allText;
        this.listener = listener;

        selected = new boolean[items.size()];
        for (int i = 0; i < selected.length; i++){
            selected[i] = true;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(), R.layout.custom_spinner_item, new String[]{allText});
        setAdapter(adapter);
    }

    public ArrayList<String> getSelectedItems(){
        ArrayList<String> selectedItems = new ArrayList<>();

        for(int i=0;i< items.size();i++){
            if(selected[i]){
                selectedItems.add(items.get(i));
            }
        }

        return selectedItems;
    }

    public ArrayList<Integer> getSelectedItemPositions(){
        ArrayList<Integer> selectedItems = new ArrayList<>();

        for(int i=0;i< items.size();i++){
            if(selected[i]){
                selectedItems.add(i);
            }
        }

        return selectedItems;
    }

    public void setSelectedPositions(ArrayList<Integer> pos){
        for(int i = 0;i<pos.size();i++){
            selected[pos.get(i)] = true;
        }
    }



    public interface MultiSpinnerListener {
        public void onItemsSelected(boolean[] selected);
    }
}
