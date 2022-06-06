package com.example.bookrecommender;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Test extends AppCompatActivity {

    private String url = "https://recommendationsfrombook.herokuapp.com/predict?";
    private String postBodyString;
    private MediaType mediaType;
    private RequestBody requestBody;
    private Button button;
    private TextView result;
    private EditText inputBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();// Убрать ActionBar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        //Рвзвернуть игру на весь экран - нач
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        result = findViewById(R.id.textView2);
        button = findViewById(R.id.button);
        inputBook = findViewById(R.id.edit_message1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!inputBook.getText().toString().isEmpty()) {
                    String url1 = url + "book=" + inputBook.getText().toString().trim();
                    postRequest("your message here", url1);
                    result.setText("Searching...");
                } else {
                    Toast.makeText(Test.this, "Please, enter the title of book!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //Рвзвернуть игру на весь экран - кон
    }

    private RequestBody buildRequestBody(String msg) {
        postBodyString = msg;
        mediaType = MediaType.parse("text/plain");
        requestBody = RequestBody.create(postBodyString, mediaType);
        return requestBody;
    }

    private String changeCharset(String s) {
        try {
            byte[] inputS = s.getBytes("Unicode");
            String outputS = new String(inputS, "UTF-8");
            return "Обработали;" + outputS;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private void postRequest(String message, String URL) {
        RequestBody requestBody = buildRequestBody(message);
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request
                .Builder()
                .post(requestBody)
                .url(URL)
                .build();
        try {
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(final Call call, final IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Test.this, "Something went wrong:" + " " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            call.cancel();
                        }
                    });

                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                //String s= changeCharset(response.body().string());
                                //  s = changeCharset(s);
                                String m = response.body().string();
                                byte[] inputS = m.getBytes("Unicode");
                                String s = new String(inputS, "UTF-8");

                                String s1 = s.substring(s.indexOf("\"") + 1, s.lastIndexOf("\""));
                                String[] recommendations = s1.split(";");
                                String text = "";
                                if (recommendations.length > 1) {

                                    for (int i = 0; i < recommendations.length - 1; i++) {
                                        if (i == recommendations.length - 1) {
                                            text += (i + 1) + ". " + recommendations[i];
                                        } else {
                                            text += (i + 1) + ". " + recommendations[i] + ";\n";
                                        }
                                    }
                                } else {
                                    text = recommendations[0];
                                }
                                result.setText(text);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });


                }
            });
        } catch (Exception e) {
            System.out.println("error");
        }
    }

    //Системная кнопка "Назад" - начало
    @Override
    public void onBackPressed() {
        try {
            Intent intent = new Intent(com.example.bookrecommender.Test.this, MainActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {

        }
    }
    //Системная кнопка "Назад" - конец
}
