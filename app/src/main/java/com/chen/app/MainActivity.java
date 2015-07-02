package com.chen.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.chen.formdroid.core.internal.FormCoreFragment;
import com.chen.formdroid.core.template.form.Form;
import com.chen.formdroid.core.template.form.FormFactory;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button save = (Button)findViewById(R.id.save);
        Button load = (Button)findViewById(R.id.load);

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
    }


    private void load(){
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
        Form f = FormFactory.newFormByJson(jsonStr);
        Fragment temp = FormCoreFragment.newInstance(f.getFormId());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, temp)
                .commit();
    }

    private void save(){

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
