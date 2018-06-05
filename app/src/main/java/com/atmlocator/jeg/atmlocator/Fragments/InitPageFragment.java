package com.atmlocator.jeg.atmlocator.Fragments;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.atmlocator.jeg.atmlocator.Controllers.PostServices;
import com.atmlocator.jeg.atmlocator.Models.ATMInfo;
import com.atmlocator.jeg.atmlocator.R;
import com.atmlocator.jeg.atmlocator.Utils.LocationService;

import java.util.List;

/**
 * Created by javgon on 4/19/2017.
 * The initial view that is shown to the user. Contains button that gets atm locations
 */

public class InitPageFragment extends Fragment implements View.OnClickListener{

    Button btnFindLoc;

    public static InitPageFragment newInstance(){
        InitPageFragment newFrag = new InitPageFragment();
        Bundle args = new Bundle();
        newFrag.setArguments(args);
        return newFrag;
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_startup, container, false);
        /*
            Handle user pressing back button, should exist app if in init page
        */
        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){  //if back key pressed
                    getActivity().finish();
                    return true;
                }
                return false;
            }
        });

        btnFindLoc=(Button) rootView.findViewById(R.id.btn_find_locations);
        btnFindLoc.setOnClickListener(this);

        if (ContextCompat.checkSelfPermission(getActivity(), //if gps permission is not granted
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            //Request permission
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);


        }


        return rootView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_find_locations: //if button pressed, get location
                LocationService.getInstance(getActivity()).getLocation(new LocationService.VolleyCallback(){
                    @Override
                    public void onSuccess(Location location){
                        displayResults(location); //then display list of atms
                    }
                    @Override
                    public void onFail(String msg){
                        Log.e("FAIL", msg);
                        Toast.makeText(getActivity(), msg,
                                Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            default:
                break;
        }
    }



    private void displayResults(Location location){
        //inflate card page
        AtmListFragment nextFrag= AtmListFragment.newInstance(location.getLatitude(),location.getLongitude());

        if (getFragmentManager().findFragmentByTag("LIST")==null) { //if fragment does not exist
            getFragmentManager().beginTransaction()
                    .replace(R.id.frag_container, nextFrag, "LIST")
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(),"GPS permission granted",Toast.LENGTH_LONG).show();

                    //  get Location from your device by some method or code

                } else {
                    // show user that permission was denied. inactive the location based feature or force user to close the app
                }
                break;
        }
    }

}
