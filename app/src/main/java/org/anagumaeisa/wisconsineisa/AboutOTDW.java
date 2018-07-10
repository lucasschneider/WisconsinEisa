package org.anagumaeisa.wisconsineisa;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;

public class AboutOTDW extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_otdw);

        TextView text = findViewById(R.id.aboutOtdwText);
        text.setTextIsSelectable(true);
    }
}
