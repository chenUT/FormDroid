package com.chen.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
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

public class MainActivity extends FragmentActivity {

    private String mFormId;

    private static boolean _formLoaded = false;

    private TextView formContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button save = (Button)findViewById(R.id.save);
        final Button load = (Button)findViewById(R.id.load_sample_inspect);
        final Button delete = (Button)findViewById(R.id.delete);

        formContent = (TextView)findViewById(R.id.form_json_content);

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteForm();
            }
        });
    }

    private void deleteForm(){
        if(!_formLoaded){
            return;
        }

        FormContext.getInstance().deleteForm(mFormId);
        getSupportFragmentManager().popBackStack("form", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        _formLoaded = false;
        setFormDisplayContent("");
    }

    private void load(){
        if(_formLoaded){
            return ;
        }

        Form f = FormFactory.getInstance().loadFromAssets("form_inspect.json");

        String currentString = f.toJsonString();

        setFormDisplayContent(currentString);

        mFormId = f.getFormId();

        Fragment temp = FormCoreFragment.newInstance(f.getFormId());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, temp)
                .addToBackStack("form")
                .commit();

        _formLoaded = true;
    }

    private void save(){
        if(!_formLoaded){
            return;
        }
        try {
            FormContext.getInstance().persisForm(mFormId);
            getSupportFragmentManager().popBackStack("form", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            _formLoaded = false;
            setFormDisplayContent("");
        } catch (InvalidFormIdException e) {
            Toast.makeText(getApplicationContext(), "Error saving form", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void setFormDisplayContent(String content){
        formContent.setText(content);
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
