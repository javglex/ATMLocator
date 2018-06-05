package com.atmlocator.jeg.atmlocator.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.atmlocator.jeg.atmlocator.Controllers.PostServices;
import com.atmlocator.jeg.atmlocator.Models.ATMInfo;
import com.atmlocator.jeg.atmlocator.R;
import com.atmlocator.jeg.atmlocator.Utils.AtmListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by javier gonzalez on 4/19/2017.
 * Handles displaying list of available atms using recycleview
 */

public class AtmListFragment extends Fragment{

    private List<ATMInfo> atmList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AtmListAdapter mAdapter;
    private Button btnRefresh;

    public static AtmListFragment newInstance(double lat, double lng){

        AtmListFragment newFrag = new AtmListFragment();
        Bundle args = new Bundle();
        args.putDouble("lat",lat);
        args.putDouble("lng",lng);
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
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        return rootView;
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState){
        recyclerView = (RecyclerView)view.findViewById(R.id.atm_recycler_view);
        btnRefresh=(Button) view.findViewById(R.id.btn_refresh);
        mAdapter = new AtmListAdapter(atmList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        requestLocation();  //request location on start

        //if failed give option to user to refresh
        btnRefresh.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view){
                requestLocation();
            }
        }));


    }


    private void requestLocation(){
        PostServices.getInstance(getActivity()).requestLocations(getArguments().getDouble("lat"),getArguments().getDouble("lng"),new PostServices.VolleyCallback(){
            @Override
            public void onSuccess(List<ATMInfo> result){
                prepareAtmListData(result);
                btnRefresh.setVisibility(View.GONE);  //if succeed, hide refresh button
            }
        });
    }

    private void prepareAtmListData(List<ATMInfo> list) {
        atmList.clear();
        atmList.addAll(list);
        Log.d("prepareAtmListData"," "+list.size());
        mAdapter.notifyDataSetChanged();
    }



}
