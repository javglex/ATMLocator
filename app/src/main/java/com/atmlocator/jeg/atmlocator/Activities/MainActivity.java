package com.atmlocator.jeg.atmlocator.Activities;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.atmlocator.jeg.atmlocator.Controllers.PostServices;
import com.atmlocator.jeg.atmlocator.Fragments.InitPageFragment;
import com.atmlocator.jeg.atmlocator.R;
import com.atmlocator.jeg.atmlocator.Utils.LocationService;


/**
 * Created by Javier Gonzalez on 4/18/2017.
 * Main Activity will be the hub for all fragments in this particular project.
 * All fragments will refer to this view
 */

public class MainActivity extends AppCompatActivity {

    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayInitPage();

    }

    private void displayInitPage(){
        //inflate card page

        InitPageFragment nextFrag= InitPageFragment.newInstance();

        if (getFragmentManager().findFragmentByTag("INIT")==null) { //if fragment does not exist
            getFragmentManager().beginTransaction()
                    .replace(R.id.frag_container, nextFrag, "INIT")
                    .addToBackStack(null)
                    .commit();
        }



    }

}
