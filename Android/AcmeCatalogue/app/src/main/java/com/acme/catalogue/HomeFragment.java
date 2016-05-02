package com.acme.catalogue;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
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

import com.acme.catalogue.adapter.ProductListAdapter;
import com.acme.catalogue.model.ProductItem;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Djinodji on 2/19/2016.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class HomeFragment extends Fragment implements AdapterView.OnItemClickListener {
    List<SoapParam>lp=new ArrayList<SoapParam>();
    private  String NAMESPACE = "http://rattrapage-2.estiam.com/api/products.php/";
    private  String URL = "http://rattrapage-2.estiam.com/api/products.php?wsdl";
    private  String SOAP_ACTION = "http://rattrapage-2.estiam.com/api/products.php/getProducts";
    private  String METHOD_NAME = "getProducts";
    private String TAG = "PGGURU";
    List<SoapParam> soapParams=null;

    private CharSequence mDrawerTitle;
    Bundle extras;
    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private RelativeLayout mDrawerLayout;
    private ListView mDrawerList;
    private ArrayList<ProductItem> navDrawerItems;
    private ProductListAdapter adapter;
    View rootView;
    private String userId;
    Activity activity;
    public HomeFragment(){}
    public HomeFragment(String userId){
        this.userId=((AppData)getActivity().getApplication()).getUser().getId()+"";
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        rootView= inflater.inflate(R.layout.fragment_home, container, false);
        mTitle = mDrawerTitle =getActivity().getTitle();

        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerLayout = (RelativeLayout)rootView.findViewById(R.id.drawer_layout_home);
        mDrawerList = (ListView)rootView.findViewById(R.id.list_product);

        navDrawerItems = new ArrayList<ProductItem>();

        // adding nav drawer items to array
        // Home

       // navDrawerItems.add(new NavDrawerItem(this.username, this.photo));
        // Find People


        //mViewPager.setPageMargin(5);

        SoapParam sp = new SoapParam();
        sp.setParamName("username");
        sp.setParamValue("5");
        sp.setType(double.class);
        lp.add(sp);
        soapParams = lp;
        //SoapHelper sh=new SoapHelper(NAMESPACE, URL, SOAP_ACTION,METHOD_NAME, TAG, lp);
        //List<User> c= new ArrayList<>();
        //sh.task.execute();
        AsyncCallWS task = new AsyncCallWS();
        //Call execute

        task.execute();
        //mViewPager.setPageMarginDrawable(R.color.colorPrimary);
        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try{

            ((MainActivity) activity).onProductItemPicked(position, ((ProductItem) adapter.getItem(position)).getId(),((ProductItem) adapter.getItem(position)).getTitle(),  activity);
        }
        catch (ClassCastException cce){
           String b= cce.toString();
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
            List<Product> c= new ArrayList<>();
            SoapToObject so=new SoapToObject();
            c= (List<Product>)(List<?>)(SoapToObject.to(Product.class, response));
            for (int y=0; y<c.size();y++)
            {
                navDrawerItems.add(new ProductItem(c.get(y).getId(), c.get(y).getNom(), c.get(y).getDescription(),c.get(y).getAlbums_count()>1? c.get(y).getAlbums_count()+" albums":c.get(y).getAlbums_count()+" album"));
            }
            for (int y=0; y<c.size();y++)
            {
                navDrawerItems.add(new ProductItem(c.get(y).getId(), c.get(y).getNom(), c.get(y).getDescription(),c.get(y).getAlbums_count()>1? c.get(y).getAlbums_count()+" albums":c.get(y).getAlbums_count()+" album"));
            }
            androidHttpTransport.reset();

            // Recycle the typed array
            navMenuIcons.recycle();


            //mDrawerList.setOnItemClickListener(new SlideMenuClickListener());


            // setting the nav drawer list adapter
            adapter = new ProductListAdapter(rootView.getContext(),
                    navDrawerItems);
            try {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        mDrawerList.setAdapter(adapter);

                    }
                });
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        ProgressBar spinner = (ProgressBar) rootView.findViewById(R.id.progressBar);
                        spinner.setVisibility(View.GONE);
                    }
                    });
            }
            catch (Exception e)
            {
                String f=e.getMessage();
            }


        }
        catch (Exception e) {
            e.printStackTrace();
        }
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
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);


        mDrawerList.setOnItemClickListener(this);


    }

}
