package com.atmlocator.jeg.atmlocator.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.atmlocator.jeg.atmlocator.Models.ATMInfo;
import com.atmlocator.jeg.atmlocator.R;

import org.w3c.dom.Text;

/**
 * Created by Javier Gonzalez on 4/19/2017.
 * Simple fragment to display detailed information about ATM
 */

public class AtmDetailsFragment extends Fragment {

    private TextView tvAddress,tvName,tvDistance,tvAccess,tvServices,tvHours;
    private String address, name, distance, access, services,hours;


    public static AtmDetailsFragment newInstance(ATMInfo info){

        AtmDetailsFragment newFrag = new AtmDetailsFragment();
        Bundle args = new Bundle();
        args.putString("state",info.getState());
        args.putString("city",info.getCity());
        args.putString("distance",info.getDistance());
        args.putString("address",info.getAddress());
        args.putString("services",info.getServices());
        args.putString("access",info.getAccess());
        args.putString("name",info.getLabel());
        args.putString("zip",info.getZip());
        args.putString("hours",info.getHours());
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
        View rootView = inflater.inflate(R.layout.fragment_atm_details, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        tvAddress = (TextView) rootView.findViewById(R.id.tv_address);
        tvAccess= (TextView) rootView.findViewById(R.id.tv_access);
        tvName= (TextView) rootView.findViewById(R.id.tv_name);
        tvDistance=(TextView)rootView.findViewById(R.id.tv_distance);
        tvServices= (TextView)rootView.findViewById(R.id.tv_services);
        tvHours=(TextView)rootView.findViewById(R.id.tv_hours);

        address=getArguments().getString("address")+" "+getArguments().getString("city")+" "+getArguments().getString("state") + " "+getArguments().getString("zip");
        name=getArguments().getString("name");
        distance=getArguments().getString("distance");
        access=getArguments().getString("access");
        services=getArguments().getString("services");
        hours=getArguments().getString("hours");

        tvName.setText(name);
        tvAddress.setText(address);
        tvDistance.setText(distance);
        tvAccess.setText(access);
        tvServices.setText(services);
        tvHours.setText(hours);

        return rootView;
    }




}
