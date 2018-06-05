package com.atmlocator.jeg.atmlocator.Utils;

import android.app.Activity;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atmlocator.jeg.atmlocator.Fragments.AtmDetailsFragment;
import com.atmlocator.jeg.atmlocator.Models.ATMInfo;
import com.atmlocator.jeg.atmlocator.R;

import java.util.List;

/**
 * Created by javgon on 4/19/2017.
 * Adapter for atm list recycler view
 * sets up information for each row as well as a onclicklistener
 */

public class AtmListAdapter extends RecyclerView.Adapter<AtmListAdapter.MyViewHolder> {

    private List<ATMInfo> list;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvBranch, tvDistance, tvAddress;
        public ConstraintLayout linearInfo;

        public MyViewHolder(View view) {
            super(view);
            tvBranch = (TextView) view.findViewById(R.id.tv_branch);
            tvDistance = (TextView) view.findViewById(R.id.tv_distance);
            tvAddress = (TextView) view.findViewById(R.id.tv_address);
            linearInfo=(ConstraintLayout) view.findViewById(R.id.linear_info);
        }
    }


    public AtmListAdapter(List<ATMInfo> list) {
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.atm_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        ATMInfo atm = list.get(position);
        holder.tvBranch.setText(atm.getLocType());
        holder.tvDistance.setText(atm.getDistance());
        holder.tvAddress.setText(atm.getAddress()+ " " +atm.getCity() + " " +atm.getState()+" "+atm.getZip());

        holder.linearInfo.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view){
                showDetailAtm(view, list.get(position));
            }
        }));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }






    /**
     Showing atm details when its row is clicked
     */

    private void showDetailAtm(View view, ATMInfo info){
        //inflate card page

        AtmDetailsFragment nextFrag= AtmDetailsFragment.newInstance(info);
        if (((Activity)view.getContext()).getFragmentManager().findFragmentByTag("ATMDETAILS")==null) { //if fragment does not exist
            ((Activity)view.getContext()).getFragmentManager().beginTransaction()
                    .add(R.id.frag_container, nextFrag, "ATMDETAILS")
                    .addToBackStack(null)
                    .commit();
        }


    }
}