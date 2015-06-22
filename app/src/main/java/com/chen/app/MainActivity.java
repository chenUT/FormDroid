package com.chen.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.chen.formdroid.core.fragments.FormCoreFragment;
import com.chen.formdroid.core.template.form.Form;
import com.chen.formdroid.core.template.form.FormFactory;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String jsonStr = "";
        try {
            InputStream in = getAssets().open("sample.json");

            byte[] buffer = new byte[in.available()];

            in.read(buffer);
            in.close();
            jsonStr = new String(buffer, "UTF-8");
        }
        catch(IOException ioe){
        }

        Log.d("test","1");
        Form f = FormFactory.newFormByJson(jsonStr);
        Log.d("test","2");
        Fragment temp = FormCoreFragment.newInstance(f.getFormId());

        Log.d("test","3");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, temp)
                .addToBackStack("temp_form")
                .commit();

        Log.d("test","4");
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
