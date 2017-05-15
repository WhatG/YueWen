package com.examplel.awesome_men.yuewen.CusViews;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.examplel.awesome_men.yuewen.R;

/**
 * Created by longer on 2017/5/13.
 */

public class EditableDialog{
    private final String TAG = "EditableDialog";
    private EditText editText;
    private Handler handler;
    private AlertDialog.Builder builder;
    private DialogInterface.OnClickListener buttonsListener = null;
    private int buttonCount;

    public EditableDialog(Context context){
        builder = new AlertDialog.Builder(context);
        editText = new EditText(context);
    }

    public void setHandler(Handler handler){
        this.handler = handler;
    }

    public void setTitle(String title){
       builder.setTitle(title);
    }

    public void addButton(String actionName){
        if(buttonCount==2){
            Log.e(TAG,"buttonCount limit 2");
            return;
        }
        if(buttonsListener == null){
            buttonsListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if(handler == null){
                        return;
                    }
                    handler.sendEmptyMessage(i);
                }
            };
        }
        if(buttonCount==0){
            builder.setPositiveButton(actionName,buttonsListener);
        }
        else{
            builder.setNegativeButton(actionName,buttonsListener);
        }
        buttonCount++;
    }

    public void showAlert(){
        builder.setView(editText);
        builder.show();
    }
}
