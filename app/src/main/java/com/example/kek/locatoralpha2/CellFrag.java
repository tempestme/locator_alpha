package com.example.kek.locatoralpha2;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by kek on 07.03.2017.
 */

public class CellFrag extends Fragment {


    public static CellFrag newInstance(){
        CellFrag fragment = new CellFrag();
        return fragment;

    }
    public CellFrag(){
    }

    GsmCellLocation gsm;
    TelephonyManager tm;
    TextView cid;
    TextView lac;
    TextView mmc;
    TextView mnc;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View RootView = inflater.inflate(R.layout.cell_fragment,container, false);
        this.cid = (TextView) RootView.findViewById(R.id.cidTv);
        this.lac = (TextView) RootView.findViewById(R.id.lacTv);
        this.mmc = (TextView) RootView.findViewById(R.id.mmcTv);
        this.mnc = (TextView) RootView.findViewById(R.id.mncTv);
        getData();
        return RootView;
    }

    private void getData() {
        this.tm =(TelephonyManager)getContext().getSystemService(Context.TELEPHONY_SERVICE);
        this.gsm = (GsmCellLocation)tm.getCellLocation();
        String networkOperator = tm.getNetworkOperator();
        mmc.setText(networkOperator.substring(0,3));
        mnc.setText(networkOperator.substring(3));
        cid.setText(Integer.toString(gsm.getCid()));
        lac.setText(Integer.toString(gsm.getLac()));
        //psc.setText(Integer.toString(gsm.getPsc()));
    }

}
