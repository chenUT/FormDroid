package com.chen.app;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.chen.formdroid.fdcore.internal.FormCoreFragment;
import com.chen.formdroid.fdcore.template.form.Form;
import com.chen.formdroid.fdcore.template.form.FormFactory;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class FormHolderActivity extends AppCompatActivity {

    private Form mForm;
    private String mFormId;

    private ProgressDialog mDialog;
    private Button mSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_holder);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSubmit = (Button)findViewById(R.id.submit);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               submitForm();
            }
        });

        startFormView();
    }

    private void submitForm(){
        String result = mForm.toJsonString();
        mDialog = ProgressDialog.show(this, "", "Submitting...");
        new SubmitTask().execute(result);
    }

    private void startFormView(){
        mForm  = FormFactory.getInstance().loadFromAssets("form_survey.json");
        mFormId = mForm.getFormId();
        getSupportActionBar().setTitle(mForm.getTitle());
        Fragment temp = FormCoreFragment.newInstance(mForm.getFormId());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, temp)
                .addToBackStack("form")
                .commit();
    }

    private void exitSubmitted(){
        Snackbar.make(mSubmit, "Survey Submitted :)", Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
        finish();
    }

    private class SubmitTask extends AsyncTask<String, Void, Void>{

        private final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        String post(String url, String json) throws IOException {
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("X-Auth-Token","formdroiddemo")
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        }

        @Override
        protected Void doInBackground(String... params) {
            String json = params[0];
            try {
                String uri ="http://ec2-52-24-157-144.us-west-2.compute.amazonaws.com:8080/api/form/result";
                post(uri, json);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(FormHolderActivity.this.mDialog != null){
                FormHolderActivity.this.mDialog.dismiss();
            }
            exitSubmitted();
        }
    }
}
