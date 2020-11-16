package com.example.project4148;

import android.app.AlertDialog;
import android.content.Context;

public class Loading_animation {

    Context parentcontext;
    AlertDialog dialog;

    public Loading_animation(Context parentcontext) {
        this.parentcontext = parentcontext;
    }

    public void startanimation()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(parentcontext);
        builder.setView(R.layout.loading_customlayout);

        dialog = builder.create();
        dialog.show();
    }
    public void stopanimation()
    {
        dialog.dismiss();
    }
}
