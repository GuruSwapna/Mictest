package com.example.enchanterswapna.mictest;

import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView txtSpeechInput;


    Button bt1;
    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);


        bt1=(Button)findViewById(R.id.btn1);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSearchClick(view);
            }
        });


        // hide the action bar
      //  getActionBar().hide();

        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });
    }
    public void onSearchClick(View v)
    {
        try {
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            String term = txtSpeechInput.getText().toString();
            intent.putExtra(SearchManager.QUERY, term);
            startActivity(intent);
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

        private void promptSpeechInput() {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                    getString(R.string.speech_prompt));
            try {
                startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
            } catch (ActivityNotFoundException a) {
                Toast.makeText(getApplicationContext(),
                        getString(R.string.speech_not_supported),
                        Toast.LENGTH_SHORT).show();
            }
        }

        /**
         * Receiving speech input
         * */
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            switch (requestCode) {
                case REQ_CODE_SPEECH_INPUT: {
                    if (resultCode == RESULT_OK && null != data) {

                        ArrayList<String> result = data
                                .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                        txtSpeechInput.setText(result.get(0));
                    }
                    break;
                }

            }

//        if (!('webkitSpeechRecognition' in window)) {
//            upgrade();
//        } else{
//            var recognition = new webkitSpeechRecognition();
//            recognition.continuous = true;
//            recognition.interimResults = true;
//
//            recognition.onstart = function() { ...}
//            recognition.onresult = function(event) { ...}
//            recognition.onerror = function(event) { ...}
//            recognition.onend = function() { ...}
//        }
//        function startButton(event) {
//        final_transcript = '';
//        recognition.lang = select_dialect.value;
//        recognition.start();
//
//        recognition.onresult = function(event) {
//            var interim_transcript = '';
//
//            for (var i = event.resultIndex; i < event.results.length; ++i) {
//                if (event.results[i].isFinal) {
//                    final_transcript += event.results[i][0].transcript;
//                } else {
//                    interim_transcript += event.results[i][0].transcript;
//                }
//            }
//            final_transcript = capitalize(final_transcript);
//            final_span.innerHTML = linebreak(final_transcript);
//            interim_span.innerHTML = linebreak(interim_transcript);
//        };
//
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
