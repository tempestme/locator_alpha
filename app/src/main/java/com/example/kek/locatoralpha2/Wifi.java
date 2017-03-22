package com.example.kek.locatoralpha2;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kek on 20.03.2017.
 */

public class Wifi extends AppCompatActivity {

    private static Element [] nets;
    private WifiManager wifiManager;
    private List<ScanResult> wifiList;
    public static View RootView;

    public void wifi(){

    }
    public void detectWifi(){
        LayoutInflater inflater;
        this.wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        this.wifiManager.startScan();
        this.wifiList = this.wifiManager.getScanResults();

        this.nets = new Element[wifiList.size()];
        for(int i = 0; i<wifiList.size(); i++){
            String item = wifiList.get(i).toString();
            String vector_item[] = item.split(",");
            String item_essid = vector_item[0];
            String item_capabilities = vector_item[2];
            String item_level = vector_item[3];
            String ssid = item_essid.split(": ")[1];
            String security = item_capabilities.split(": ")[1];
            String level = item_level.split(": ")[1];
            nets[i] = new Element(ssid,security,level);
        }

        //TODO:: переделать этот участок по человеески, красиво дорого богато.
        //WifiFrag.AdapterElements adapterElements = new WifiFrag.AdapterElements(this); //НАДО ЗАТРАИТЬ
        //Было AdapterElements adapterElements = new AdapterElements(this);
        //Аргумент должен заходить activity.
        AdapterElements adapterElements = new AdapterElements(this);
        ListView netList = (ListView)RootView.findViewById(R.id.wifiLV);
        netList.setAdapter(adapterElements);
    }

    public static class WifiFrag extends android.app.Fragment{

        public static WifiFrag newInstance(){

            return null;
        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            RootView = inflater.inflate(R.layout.wifi_fragment,container, false);
            //this.wifiList = (TextView) RootView.findViewById(R.id.wifiLV);
            return RootView;
        }
    }
    class AdapterElements extends ArrayAdapter<Object> {
        Activity context;

        public AdapterElements(Activity context) {
            super(context, R.layout.items, nets);
            this.context = context;
        }

        public View getView(int position, View convetrView, ViewGroup parent){
            LayoutInflater inflater = context.getLayoutInflater();

            View item = inflater.inflate(R.layout.items, null);

            TextView tvSsid = (TextView)item.findViewById(R.id.textViewSSID);
            tvSsid.setText(nets[position].getTitle());

            TextView tvSecurity = (TextView)item.findViewById(R.id.textViewSecurity);
            tvSecurity.setText(nets[position].getSecurity());

            TextView tvLevel = (TextView)item.findViewById(R.id.textViewSignal);
            String level = nets[position].getLevel();

            try{
                int i = Integer.parseInt(level);

                //tvLevel.setText(Integer.toString(i));
                if(i>-50){tvLevel.setText("high");}
                else if(i<=-50 && i>-80){tvLevel.setText("medium");}
                else if(i<=-80){tvLevel.setText("low");}
            }
            catch (NumberFormatException e){
                Log.d("TAG", "неподходящий формат строки, давай по новой");
            }
            return item;
        }

    }
}
