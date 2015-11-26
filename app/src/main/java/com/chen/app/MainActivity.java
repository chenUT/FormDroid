package com.chen.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chen.formdroid.FormContext;
import com.chen.formdroid.fdcore.internal.FormCoreFragment;
import com.chen.formdroid.fdcore.template.form.Form;
import com.chen.formdroid.fdcore.template.form.FormFactory;
import com.chen.formdroid.exceptions.InvalidFormIdException;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFormActivty();
            }
        });
    }

    private void startFormActivty(){
        Intent intent = new Intent(this, FormHolderActivity.class);
        startActivity(intent);
    }
}
