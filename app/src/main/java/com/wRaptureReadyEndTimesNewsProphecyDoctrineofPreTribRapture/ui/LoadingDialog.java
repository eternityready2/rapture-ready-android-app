package com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.ui;

// LoadingDialog.java
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.R;

public class LoadingDialog extends Dialog {

    public LoadingDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.loading_dialog);
        setCancelable(false);

        if (getWindow() != null) {
            getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
    }
}
