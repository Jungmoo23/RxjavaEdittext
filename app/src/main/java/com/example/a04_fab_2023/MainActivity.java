package com.example.a04_fab_2023;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    EditText editText;
    private static final String TAG = "MainActivity";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.main_text);
        editText = (EditText) findViewById(R.id.edit);

//        editText.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                Log.d(TAG,"keycode ");
//                switch (keyCode){
//                    case KeyEvent.KEYCODE_ENTER:
//                        Log.d(TAG,"keycode 완료");
//                        break;
//
//                }
//                return true;
//            }
//
//        });


//        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//
//                switch(actionId){
//                    case EditorInfo.IME_ACTION_SEARCH:
//                        Log.d(TAG,"keycode IME_ACTION_SEARCH");
//                        break;
//                    default:
//                        Log.d(TAG,"keycode IME_ACTION_Endter");
//                        editText.setText("");
//                        return false;
//                }
//                return true;
//            }
//        });



        Observable.just(textView.getText().toString())
                .map(jm -> "안녕 나는:: "+jm)
                .subscribe(
                        name -> textView.setText(name)
                );


    }

    @Override
    protected void onResume() {
        super.onResume();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG,"1 "+s+" "+start+" "+count+" "+ after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG,"2 "+s+" "+start+" "+count+" ");
                Observable.just(textView.getText().toString())
                        .map(check -> ""+s)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                e -> {
                                    textView.setText(e);
                                    Log.d(TAG,"tesxttt: "+e);
                                }
                        );
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG,"3 "+s);
            }
        });
    }
}