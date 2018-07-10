package org.anagumaeisa.wisconsineisa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AboutAE extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_ae);

        TextView text = findViewById(R.id.aboutAeText);
        text.setTextIsSelectable(true);
    }
}
