package com.acme.catalogue;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.acme.catalogue.adapter.NavDrawerListAdapter;
import com.acme.catalogue.model.NavDrawerItem;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    List<SoapParam> lp = new ArrayList<SoapParam>();
    private String NAMESPACE = "http://rattrapage-2.estiam.com/api/albums.php/";
    private String URL = "http://rattrapage-2.estiam.com/api/albums.php?wsdl";
    private String SOAP_ACTION = "http://rattrapage-2.estiam.com/api/products.php/isUserBindTo";
    private String METHOD_NAME = "isUserBindTo";
    private String TAG = "PGGURU";
    List<SoapParam> soapParams = null;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ListView mDrawerProductList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String username;
    private String albumTitle;
    private String photo;
    private String userId;

    private int albumId;
    android.support.v7.app.ActionBar actionBar;
    // nav drawer title
    private CharSequence mDrawerTitle;
    Bundle extras;
    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private ArrayList<NavDrawerItem> navDrawerProductItems;
    private NavDrawerListAdapter adapter;

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        setContentView(R.layout.activity_main);
        System.setProperty("http.keepAlive", "false");
        PackageManager m = getPackageManager();
        String s = getPackageName();
        extras = getIntent().getExtras();

        this.photo = getIntent().getStringExtra("photo");
        this.username = ((AppData) this.getApplication()).getUser().getUsername();
        this.userId = ((AppData) this.getApplication()).getUser().getId() + "";


        mTitle = mDrawerTitle = getTitle();

        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);


        navDrawerItems = new ArrayList<NavDrawerItem>();

        // adding nav drawer items to array
        // Home

        navDrawerItems.add(new NavDrawerItem(this.username, this.photo));
        // Find People
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1), true, "9"));
        // Photos
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));
        // Communities, Will add a counter here
        // navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1), true, "22"));
        // Pages
        //navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
        // What's hot, We  will add a counter here
        // navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1), true, "50+"));


        // Recycle the typed array
        navMenuIcons.recycle();


        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());


        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems, true);
        mDrawerList.setAdapter(adapter);


        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                actionBar.setTitle(Html.fromHtml("<font color='#FFFFFF'>" + mTitle + "</font>"));
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                actionBar.setTitle(Html.fromHtml("<font color='#FFFFFF'>" + mDrawerTitle + "</font>"));
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };

        //ActionBar actionBartest = getActionBar();
        // enabling action bar app icon and behaving it as toggle button
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        // getActionBar().setHomeButtonEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(2);
        }


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


    /**
     * Slide menu item click listener
     */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /***
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        if (extras != null) {
            boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
            menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
            return super.onPrepareOptionsMenu(menu);
        }
        return false;
    }

    //@TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        actionBar.setTitle(Html.fromHtml("<font color='#FFFFFF'>" + mTitle + "</font"));
        //this.getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        if (extras != null) {
            mDrawerToggle.syncState();
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    /**
     * Diplaying fragment view for selected nav drawer list item
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 1: {
                if (((AppData) getApplication()).isNetworkActif()) {
                    fragment = new HomeFragment();
                } else {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Pas de connexion")
                            .setMessage("Vérifiez vos paramètres de connexion")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                break;
            }
            case 2: {
                //menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
                fragment = new AlbumFragment(this);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).addToBackStack(null).commit();
            }
            break;
            case 3: {
                if (((AppData) getApplication()).isNetworkActif()) {
                    File login = new File(getApplicationContext().getFilesDir().getAbsolutePath() + "/user_albums.xml");
                    login.delete();
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.putExtra("message", "Synchronisation en cours");
                    startActivity(intent);
                } else {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Pas de connexion")
                            .setMessage("Vérifiez vos paramètres de connexion")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }

            }
            case 5: {
                finish();
            }
            break;


            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).addToBackStack(null).commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(Html.fromHtml("<font color='#FFFFFF'>" + navMenuTitles[position] + "</font>"));
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }

    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onBackPressed() {
        FragmentManager f=getFragmentManager();
        if (getFragmentManager().getBackStackEntryCount() > 1 ){
            getFragmentManager().popBackStack();
        } else {
            finish();
            System.exit(0);
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

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void getSoap(List<SoapParam> params) {

        //Create request
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        //Property which holds input parameters
        for (int i = 0; i < soapParams.size(); i++) {
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
            boolean isAbonne = false;
            //Invole web service
            if (((AppData) getApplication()).isNetworkActif()) {
                androidHttpTransport.call(SOAP_ACTION, envelope);
                //Get the response
                SoapObject response = (SoapObject) envelope.bodyIn;
                //Assign it to fahren static variable
                String returnObj = response.getPropertyAsString("return");
                isAbonne = Boolean.parseBoolean(returnObj);

            }

            if (isAbonne || !((AppData) getApplication()).isNetworkActif()) {

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        setTitle(albumTitle);
                        Fragment fragment = new PhotosFragment(albumId, MainActivity.this);
                        FragmentManager fragmentManager = getFragmentManager();
                        try {
                            fragmentManager.beginTransaction()
                                    .replace(R.id.frame_container, fragment).addToBackStack(null).commit();
                        } catch (Exception x) {
                            String t = x.getMessage();
                        }

                    }
                });

            } else {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Abonnement")
                                .setMessage("Vous n'êtes pas abonné à cet album, voulez vous le faire maintenant?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        NAMESPACE = "http://rattrapage-2.estiam.com/api/albums.php/";
                                        URL = "http://rattrapage-2.estiam.com/api/albums.php?wsdl";
                                        SOAP_ACTION = "http://rattrapage-2.estiam.com/api/products.php/bindUserTo";
                                        METHOD_NAME = "bindUserTo";

                                        lp = new ArrayList<SoapParam>();
                                        SoapParam sp = new SoapParam();
                                        sp.setParamName("userId");
                                        sp.setParamValue(userId);
                                        sp.setType(String.class);
                                        lp.add(sp);

                                        sp = new SoapParam();
                                        sp.setParamName("albumId");
                                        sp.setParamValue(albumId + "");
                                        sp.setType(String.class);
                                        lp.add(sp);

                                        soapParams = lp;

                                        //Do something with the position value passed back
                                        AsyncCallWS task = new AsyncCallWS();
                                        //Call execute

                                        task.execute();
                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // do nothing
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onProductItemPicked(int position, int productId, String productName, Activity ac) {
        //Do something with the position value passed back
        int pos = position;
        setTitle(productName);
        Fragment fragment = new AlbumFragment(productId, this);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, fragment).addToBackStack(null).commit();


    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onAlbumItemPicked(int position, int albumId, String albumTitle, Activity ac) {
        lp = new ArrayList<SoapParam>();
        SoapParam sp = new SoapParam();
        sp.setParamName("userId");
        sp.setParamValue(userId);
        sp.setType(String.class);
        lp.add(sp);

        sp = new SoapParam();
        sp.setParamName("albumId");
        sp.setParamValue(albumId + "");
        sp.setType(String.class);
        lp.add(sp);

        soapParams = lp;
        this.albumId = albumId;
        this.albumTitle = albumTitle;

        NAMESPACE = "http://rattrapage-2.estiam.com/api/albums.php/";
        URL = "http://rattrapage-2.estiam.com/api/albums.php?wsdl";
        SOAP_ACTION = "http://rattrapage-2.estiam.com/api/products.php/isUserBindTo";
        METHOD_NAME = "isUserBindTo";

        //Do something with the position value passed back
        AsyncCallWS task = new AsyncCallWS();
        //Call execute

        task.execute();


    }

}

