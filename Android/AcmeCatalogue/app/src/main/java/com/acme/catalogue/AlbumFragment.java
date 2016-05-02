package com.acme.catalogue;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.acme.catalogue.adapter.AlbumListAdapter;
import com.acme.catalogue.model.AlbumItem;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Djinodji on 3/7/2016.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class AlbumFragment extends Fragment implements AdapterView.OnItemClickListener {

    List<SoapParam> lp=new ArrayList<SoapParam>();
    private  String NAMESPACE = "http://rattrapage-2.estiam.com/api/albums.php/";
    private  String URL = "http://rattrapage-2.estiam.com/api/albums.php?wsdl";
    private  String SOAP_ACTION = "http://rattrapage-2.estiam.com/api/albums.php/getAlbums";
    private  String METHOD_NAME = "getAlbums";
    private String TAG = "PGGURU";
    List<SoapParam> soapParams=new ArrayList<SoapParam>();
    ProgressDialog progress;
    private CharSequence mDrawerTitle;
    Bundle extras;
    // used to store app title
    private CharSequence mTitle;
    private String userId;
    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private RelativeLayout mDrawerLayout;
    private ListView mDrawerList;
    private ArrayList<AlbumItem> navDrawerItems;
    private AlbumListAdapter adapter;
    View rootView;
    Activity activity;
    public  boolean isComeFromProduct=false;
    private int productId;
    public AlbumFragment(){}
    public AlbumFragment(int productId, Activity ac){
        isComeFromProduct=true;
        this.productId=productId;this.activity=ac;
        SoapParam sp = new SoapParam();
        sp.setParamName("productId");
        sp.setParamValue(productId + "");
        sp.setType(String.class);
        lp.add(sp);
        soapParams = lp;

        AsyncCallWS task = new AsyncCallWS();
        //Call execute

        task.execute();
    }

    public AlbumFragment( Activity ac){

        this.userId=((AppData)ac.getApplication()).getUser().getId()+"";this.activity=ac;
        SoapParam sp = new SoapParam();
        sp.setParamName("userId");
        sp.setParamValue(userId);
        sp.setType(String.class);
        lp.add(sp);
        soapParams = lp;
        SOAP_ACTION = "http://rattrapage-2.estiam.com/api/albums.php/getAlbumsByUser";
        METHOD_NAME = "getAlbumsByUser";
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        try{

            ((MainActivity) activity).onAlbumItemPicked(position, ((AlbumItem) adapter.getItem(position)).getId(), ((AlbumItem) adapter.getItem(position)).getTitle(), activity);
        }
        catch (ClassCastException cce){
            String b= cce.toString();
        }
    }
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        //this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        rootView= inflater.inflate(R.layout.fragment_album, container, false);
        mTitle = mDrawerTitle =getActivity().getTitle();



        mDrawerLayout = (RelativeLayout)rootView.findViewById(R.id.drawer_layout_album);
        mDrawerList = (ListView)rootView.findViewById(R.id.list_album);

        navDrawerItems = new ArrayList<AlbumItem>();

        // adding nav drawer items to array
        // Home

        // navDrawerItems.add(new NavDrawerItem(this.username, this.photo));
        // Find People

        //SoapHelper sh=new SoapHelper(NAMESPACE, URL, SOAP_ACTION,METHOD_NAME, TAG, lp);
        //List<User> c= new ArrayList<>();
        //sh.task.execute();
        //AsyncCallWS task = new AsyncCallWS();
        //Call execute

        //task.execute();
        //mViewPager.setPageMarginDrawable(R.color.colorPrimary);
        List<Album> albums=new ArrayList<Album>();
        if (!isComeFromProduct) {
            for (int k = 0; k < ((AppData) getActivity().getApplication()).getAlbums().size(); k++) {
                Album a = new Album();
                a.setId(((AppData) getActivity().getApplication()).getAlbums().get(k).getId());
                a.setLast_update_time(((AppData) getActivity().getApplication()).getAlbums().get(k).getLast_update_time());
                a.setNom((((AppData) getActivity().getApplication()).getAlbums().get(k).getNom()));
                a.setPhotos_count((((AppData) getActivity().getApplication()).getAlbums().get(k).getPhotos_count()));
                a.setStatut((((AppData) getActivity().getApplication()).getAlbums().get(k).getStatut()));
                albums.add(a);
            }

            for (int y = 0; y < albums.size(); y++) {
                navDrawerItems.add(new AlbumItem(albums.get(y).getId(), albums.get(y).getNom(), albums.get(y).getLast_update_time(), albums.get(y).getPhotos_count() > 1 ? albums.get(y).getPhotos_count() + " photos" : albums.get(y).getPhotos_count() + " photo"));

            }
            adapter = new AlbumListAdapter(rootView.getContext(),
                    navDrawerItems);
            mDrawerList.setAdapter(adapter);

            ProgressBar spinner = (ProgressBar) rootView.findViewById(R.id.progressBar_album);
            spinner.setVisibility(View.GONE);
        }
        return rootView;
    }


    private class AsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            Log.i(TAG, "doInBackground");
            getSoap(lp);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i(TAG, "onPostExecute");
            // tv.setText(fahren + "° F");
        }

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");
            // tv.setText("Calculating...");
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            Log.i(TAG, "onProgressUpdate");
        }

    }



    public void getSoap(List<SoapParam> params) {
        //Create request


        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        //Property which holds input parameters
        for (int i=0; i<soapParams.size(); i++)
        {
            PropertyInfo param = new PropertyInfo();
            //Set Name
            param.setName(soapParams.get(i).getParamName());
            //Set Value
            param.setValue(soapParams.get(i).getParamValue());
            //Set dataType
            param.setType(soapParams.get(i).getType());
            request.addProperty(param);
        }

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        //Set output SOAP object
        envelope.setOutputSoapObject(request);
        //Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            //Invole web service
            androidHttpTransport.call(SOAP_ACTION, envelope);
            //Get the response
            SoapObject response = (SoapObject) envelope.bodyIn;
            //Assign it to fahren static variable
            //SoapObject returnObj = (SoapObject)response.getProperty("return");
            List<Album> c= new ArrayList<>();
            SoapToObject so=new SoapToObject();
            c= (List<Album>)(List<?>)(SoapToObject.to(Album.class, response));




            for (int y=0; y<c.size();y++)
            {
                navDrawerItems.add(new AlbumItem(c.get(y).getId(), c.get(y).getNom(), c.get(y).getLast_update_time(), c.get(y).getPhotos_count()>1? c.get(y).getPhotos_count()+" photos" : c.get(y).getPhotos_count()+" photo"));

            }


            if (c.size()>0)

            {
                try {
                    adapter = new AlbumListAdapter(rootView.getContext(),
                            navDrawerItems);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                mDrawerList.setAdapter(adapter);
                            } catch (Exception e) {
                                String n = e.getMessage();
                            }

                        }
                    });
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            ProgressBar spinner = (ProgressBar) rootView.findViewById(R.id.progressBar_album);
                            spinner.setVisibility(View.GONE);
                        }
                    });
                    // progress.dismiss();
                } catch (Exception e) {
                    String f = e.getMessage();
                }
            }
            else
            {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            TextView emptyMessage=(TextView)rootView.findViewById(R.id.emptyMessage);
                            emptyMessage.setVisibility(View.VISIBLE);
                            ProgressBar spinner = (ProgressBar) rootView.findViewById(R.id.progressBar_album);
                            spinner.setVisibility(View.GONE);
                        } catch (Exception e) {
                            String n = e.getMessage();
                        }

                    }
                });
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);


        mDrawerList.setOnItemClickListener(this);


    }


}
