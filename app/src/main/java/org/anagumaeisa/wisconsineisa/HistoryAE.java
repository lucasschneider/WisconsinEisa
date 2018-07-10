package org.anagumaeisa.wisconsineisa;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

public class HistoryAE extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_ae);

        TextView text = findViewById(R.id.historyAeText);
        text.setTextIsSelectable(true);
    }
}
