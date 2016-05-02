package com.acme.catalogue;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.acme.catalogue.adapter.ImageSlideAdapter;
import com.acme.catalogue.utils.CirclePageIndicator;
import com.acme.catalogue.utils.PageIndicator;

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
public class PhotosFragment extends Fragment {
    //public static final String ARG_ITEM_ID = "home_fragment";
    List<SoapParam> lp=new ArrayList<SoapParam>();
    private  String NAMESPACE = "http://rattrapage-2.estiam.com/api/images.php/";
    private  String URL = "http://rattrapage-2.estiam.com/api/images.php?wsdl";
    private  String SOAP_ACTION = "http://rattrapage-2.estiam.com/api/images.php/getImages";
    private  String METHOD_NAME = "getImages";
    private String TAG = "PGGURU";
    List<SoapParam> soapParams=new ArrayList<SoapParam>();
    private static final long ANIM_VIEWPAGER_DELAY = 7000;
    private static final long ANIM_VIEWPAGER_DELAY_USER_VIEW = 9000;
    AppData app;
    // UI References
    private ViewPager mViewPager;
    TextView imgNameTxt;
    TextView imgDescriptionTxt;
    PageIndicator mIndicator;
    Bundle savedInstanceState;
    AlertDialog alertDialog;

    List<Photo> products=new ArrayList<Photo>();
    boolean stopSliding = false;
    String message;
     View rootView;
    private Runnable animateViewPager;
    private Handler handler = new Handler();

   // String url = "http://192.168.3.119:8080/products.json";
    Activity activity;

    //Activity activity;
    private int photoId;

    public PhotosFragment(int albumId, Activity ac) {
        this.photoId = albumId;
        activity=ac;
        SoapParam sp = new SoapParam();
        sp.setParamName("albumId");
        sp.setParamValue(albumId + "");
        sp.setType(String.class);
        lp.add(sp);
        soapParams = lp;
        AsyncCallWS task = new AsyncCallWS();
        //Call execute

        task.execute();
    }

    public PhotosFragment() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setRetainInstance(true);
        rootView = inflater.inflate(R.layout.fragment_photos, container, false);

        mViewPager = (ViewPager) rootView.findViewById(R.id.view_pager);
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            private boolean moved;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_CANCEL:
                        break;

                    case MotionEvent.ACTION_UP:
                        // calls when touch release on ViewPager
                        if (products != null && products.size() != 0) {
                            stopSliding = false;
                            runnable(products.size());
                            handler.postDelayed(animateViewPager,
                                    ANIM_VIEWPAGER_DELAY_USER_VIEW);
                        }
                        break;

                    case MotionEvent.ACTION_MOVE:
                        // calls when ViewPager touch
                        if (handler != null && stopSliding == false) {
                            stopSliding = true;
                            handler.removeCallbacks(animateViewPager);
                        }
                        break;
                }


                return false;
            }
        });


        mIndicator = (CirclePageIndicator) rootView.findViewById(R.id.indicator);
        imgNameTxt = (TextView) rootView.findViewById(R.id.img_name);
        imgDescriptionTxt=(TextView)rootView.findViewById(R.id.img_description);
        mIndicator.setOnPageChangeListener(new PageChangeListener());
        mViewPager.setOnPageChangeListener(new PageChangeListener());
        final CheckBox diapo =(CheckBox)rootView.findViewById(R.id.diapo_check);
        diapo.setChecked(true);

        diapo.setOnClickListener(new View.OnClickListener() {

                                     @Override
                                     public void onClick(View v) {
                                         if(diapo.isChecked()){
                                             stopSliding=false;
                                             runnable(products.size());
                                             //Re-run callback
                                             handler.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
                                         }else{
                                             stopSliding=true;
                                         }
                                     }
                                 }
        );

        app=((AppData) activity.getApplication());
        try {


            List<Image> il=new ArrayList<Image>();
            List<AlbumSerialisable> als=app.getAlbums();
            for (int g=0; g<als.size(); g++)
            {
                if (app.getAlbums().get(g).getId()==photoId)
                {
                    il= app.getAlbums().get(g).getPhotos();
                }
            }
            if (!(products.size()>0)) {
                for (int i = 0; il != null && i < il.size(); i++) {
                    Photo pp = new Photo();
                    pp.setId(il.get(i).getId());
                    pp.setImageUrl(il.get(i).getPath());
                    pp.setName(il.get(i).getNom());
                    pp.setDescription(il.get(i).getDescription());
                    products.add(pp);
                }
            }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    mViewPager.setAdapter(new ImageSlideAdapter(activity, products,
                            PhotosFragment.this));

                    mIndicator.setViewPager(mViewPager);
                    if (products.size()>0) {
                        imgNameTxt.setText(products.get(mViewPager.getCurrentItem()).getName());
                        imgDescriptionTxt.setText(products.get(mViewPager.getCurrentItem()).getDescription());

                        runnable(products.size());
                        //Re-run callback
                        handler.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
                    }
                    else {
                        imgNameTxt.setText("Aucune photo dans cet album");
                    }
                }



            });
        }
        catch (Exception v)
        {
            String f=v.getMessage();
        }

                //.getName()
               // + ((Photo) products.get(mViewPager.getCurrentItem()))
                //.getName());
        //runnable(products.size());
        //Re-run callback
        //handler.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);

        return rootView;
    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                if (products != null) {
                    imgNameTxt.setText(products.get(mViewPager.getCurrentItem()).getName());
                    imgDescriptionTxt.setText(products.get(mViewPager.getCurrentItem()).getDescription());
                          //  + ((Photo) products.get(mViewPager
                          //  .getCurrentItem())).getName());
                }
            }
        }

    }

    public void runnable(final int size) {
        handler = new android.os.Handler();
        animateViewPager = new Runnable() {
            public void run() {
                if (!stopSliding) {
                    if (mViewPager.getCurrentItem() == size - 1) {
                        mViewPager.setCurrentItem(0);
                    } else {
                        mViewPager.setCurrentItem(
                                mViewPager.getCurrentItem() + 1, true);
                    }
                    handler.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
                }
            }
        };
    }
    @Override
    public void onResume() {
        if (products == null) {
            //sendRequest();
        } else {
            //mViewPager.setAdapter(new ImageSlideAdapter(activity, products,
                 //   PhotosFragment.this));

            //mIndicator.setViewPager(mViewPager);
            //imgNameTxt.setText(products.get(mViewPager.getCurrentItem()).getName());

        }
        super.onResume();
    }
    private class AsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            Log.i(TAG, "doInBackground");
            try {
                getSoap(lp);
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    public void getSoap(List<SoapParam> params) throws Exception {
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
        app=((AppData) activity.getApplication());
        if (app.isNetworkActif()) {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            //Invole web service
            androidHttpTransport.call(SOAP_ACTION, envelope);
            //Get the response
            SoapObject response = (SoapObject) envelope.bodyIn;
            //Assign it to fahren static variable
            //SoapObject returnObj = (SoapObject)response.getProperty("return");
            List<Image> c= new ArrayList<>();
            SoapToObject so=new SoapToObject();
            c= (List<Image>)(List<?>)(SoapToObject.to(Image.class, response));

        }


    }


}
