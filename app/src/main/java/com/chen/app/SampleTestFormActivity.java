package com.chen.app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chen.formdroid.FormContext;
import com.chen.formdroid.fdcore.internal.FormCoreFragment;
import com.chen.formdroid.fdcore.template.form.Form;
import com.chen.formdroid.fdcore.template.form.FormFactory;
import com.chen.formdroid.exceptions.InvalidFormIdException;

import java.io.IOException;
import java.io.InputStream;


/**
 * Activity to load a sample form from asset and for testing purpose
 *
 */
public class SampleTestFormActivity extends ActionBarActivity {

    private String mFormId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button save = (Button)findViewById(R.id.save);
        final Button load = (Button)findViewById(R.id.load);
        final Button delete = (Button)findViewById(R.id.delete);

        clearFormCache();

        load();
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

    /**
     * sample form has an id of a we clearToDefault it first
     */
    private void clearFormCache(){
        FormContext.getInstance().removeFromCache("a");
    }

    private void deleteForm(){
        FormContext.getInstance().deleteForm(mFormId);
        getSupportFragmentManager().popBackStack("form", FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    private void load(){
        String jsonStr = "";
        try {
            InputStream in = getAssets().open("sample_test.json");
            byte[] buffer = new byte[in.available()];
            in.read(buffer);
            in.close();
            jsonStr = new String(buffer, "UTF-8");
        }
        catch(IOException ioe){
        }
        Form f = FormFactory.getInstance().loadFormByJson(jsonStr);
        mFormId = f.getFormId();
        Fragment temp = FormCoreFragment.newInstance(f.getFormId());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, temp)
                .addToBackStack("form")
                .commit();
    }

    private void save(){
        try {
            FormContext.getInstance().persisForm(mFormId);
            getSupportFragmentManager().popBackStack("form", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } catch (InvalidFormIdException e) {
            Toast.makeText(getApplicationContext(), "Error saving form", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
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
