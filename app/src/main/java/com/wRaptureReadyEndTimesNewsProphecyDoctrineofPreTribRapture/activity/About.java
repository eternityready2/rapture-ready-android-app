package com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.imageview.ShapeableImageView;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.BuildConfig;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.R;

public class About extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ShapeableImageView imageView = findViewById(R.id.about_close);
        TextView textView = findViewById(R.id.tv2);

        textView.setText(getString(R.string.version, BuildConfig.VERSION_NAME));
        imageView.setOnClickListener(v -> finish());

        TextView link = findViewById(R.id.about_web_link);
        link.setOnClickListener( v -> startActivity( new Intent(Intent.ACTION_VIEW, Uri.parse("https://eternityready.com")) ) );
    }

    @Override
    protected void onStart() {
        super.onStart();
        View view = findViewById(R.id.about_activity_root);

        ViewCompat.setOnApplyWindowInsetsListener(view, (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            mlp.topMargin = insets.top;
            mlp.leftMargin = insets.left;
            mlp.bottomMargin = insets.bottom;
            mlp.rightMargin = insets.right;
            v.setLayoutParams(mlp);

            return WindowInsetsCompat.CONSUMED;
        });
    }
}