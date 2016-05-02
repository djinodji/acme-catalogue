package com.acme.catalogue;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    List<SoapParam> lp;
    private  String NAMESPACE = "http://rattrapage-2.estiam.com/api/users.php/";
    private  String URL = "http://rattrapage-2.estiam.com/api/users.php?wsdl";
    private  String SOAP_ACTION = "http://rattrapage-2.estiam.com/api/users.php/connectUser";
    private  String METHOD_NAME = "connectUser";
    private String TAG = "PGGURU";
    List<SoapParam> soapParams=null;
    EditText passET;
    FileOutputStream outputStream;
    private String id="";
    private String photo="";
    private  String username="";
    Serializer serializer = new Persister();
    ProgressDialog progress;
    Bundle extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'> Acme catalogue</font>"));
        StringBuilder text = new StringBuilder();
         extras = getIntent().getExtras();
        File login=new File(getApplicationContext().getFilesDir().getAbsolutePath()+"/user_albums.xml");
        //login.delete();


        if (login.exists()&& extras==null) {
            setContentView(R.layout.activity_login);
            try {

                Serializer serializer2 = new Persister();
                File source = new File(LoginActivity.this.getFilesDir().getAbsolutePath()+"/user_albums.xml");

                AppData data = serializer.read(AppData.class, source, false);
                ((AppData) this.getApplication()).setAlbums(data.getAlbums());
                ((AppData) this.getApplication()).setUser(data.getUser());


                id=((AppData) this.getApplication()).getUser().getId()+"";
                username=((AppData) this.getApplication()).getUser().getUsername();
                photo=((AppData) this.getApplication()).getUser().getPhoto();


                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("photo", photo);
                intent.putExtra("userId", id);
                startActivity(intent);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        else{
            setContentView(R.layout.activity_login);
            Button buttonC= (Button) findViewById(R.id.cancelButton);
            buttonC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    System.exit(0);
                }
            });
        Button button= (Button) findViewById(R.id.loginButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((AppData) getApplication()).isNetworkActif()) {
                    EditText emailET = (EditText) findViewById(R.id.emailText);
                    passET = (EditText) findViewById(R.id.passwordText);
                    if (extras!=null)
                    {

                        progress = ProgressDialog.show(LoginActivity.this, getIntent().getStringExtra("message"), "Veuillez patienter");
                    }
                    else {
                        progress = ProgressDialog.show(LoginActivity.this, "Connexion en cours", "Veuillez patienter");
                    }
                    lp = new ArrayList<SoapParam>();
                    SoapParam sp = new SoapParam();
                    sp.setParamName("email");

                    sp.setParamValue(emailET.getText().toString());
                    sp.setType(String.class);
                    lp.add(sp);

                    SoapParam sp2 = new SoapParam();
                    sp2.setParamName("password");

                    sp2.setParamValue(Mdh5.getMd5(passET.getText().toString()));
                    sp2.setType(String.class);
                    lp.add(sp2);
                    soapParams = lp;
                    //SoapHelper sh=new SoapHelper(NAMESPACE, URL, SOAP_ACTION,METHOD_NAME, TAG, lp);
                    //List<User> c= new ArrayList<>();
                    //sh.task.execute();


                    AsyncCallWS task = new AsyncCallWS();
                    //Call execute

                    task.execute();
                }
                else {
                    new AlertDialog.Builder(LoginActivity.this)
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

        });

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
    public void getSoap(List<SoapParam> params) {
        //Create request
         NAMESPACE = "http://rattrapage-2.estiam.com/api/users.php/";
         URL = "http://rattrapage-2.estiam.com/api/users.php?wsdl";
         SOAP_ACTION = "http://rattrapage-2.estiam.com/api/users.php/connectUser";
         METHOD_NAME = "connectUser";
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
        androidHttpTransport.debug = true;
        try {
            try {
                ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
                headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
                //Invole web service
                androidHttpTransport.call(SOAP_ACTION, envelope, headerPropertyArrayList);
            }
            catch (Exception e)
            {
               // ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
               // headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
                //Invole web service
                //androidHttpTransport.call(SOAP_ACTION, envelope, headerPropertyArrayList);
            }
            //Get the response
            Object o=envelope.bodyIn;
            SoapObject response = (SoapObject) envelope.bodyIn;
            List<User> c= new ArrayList<>();
            SoapToObject so=new SoapToObject();
            c= (List<User>)(List<?>)(SoapToObject.to(User.class, response));

            if (c.size()==1)
            {
                java.net.URL url = new URL (c.get(0).getPhoto());
                InputStream input = url.openStream();
                OutputStream output = new FileOutputStream(getApplicationContext().getFilesDir().getAbsolutePath() + c.get(0).getPhoto().substring( c.get(0).getPhoto().lastIndexOf("/"), c.get(0).getPhoto().length()));

                try {
                    byte[] buffer = new byte[1200];
                    int bytesRead = 0;
                    while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0) {
                        output.write(buffer, 0, bytesRead);
                    }



                } finally {


                    output.close();
                }


                try {
                    User u=c.get(0);
                    u.setPhoto(getApplicationContext().getFilesDir().getAbsolutePath() + c.get(0).getPhoto().substring( c.get(0).getPhoto().lastIndexOf("/"), c.get(0).getPhoto().length()));
                    u.setPassword(passET.getText().toString());
                    ((AppData) this.getApplication()).setUser(u);

                    soapParams=new ArrayList<SoapParam>();
                    List<SoapParam> lp=new ArrayList<SoapParam>();
                    SoapParam sp = new SoapParam();
                    sp.setParamName("userId");
                    sp.setParamValue(c.get(0).getId()+"");
                    sp.setType(String.class);
                    lp.add(sp);
                    soapParams = lp;
                    NAMESPACE = "http://rattrapage-2.estiam.com/api/albums.php/";
                    URL = "http://rattrapage-2.estiam.com/api/albums.php?wsdl";
                    SOAP_ACTION = "http://rattrapage-2.estiam.com/api/albums.php/getAlbumsByUser";
                    METHOD_NAME = "getAlbumsByUser";
                   TAG = "PGGURU";

                    SoapObject request2 = new SoapObject(NAMESPACE, METHOD_NAME);
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
                        request2.addProperty(param);
                    }

                    SoapSerializationEnvelope envelope2 = new SoapSerializationEnvelope(
                            SoapEnvelope.VER11);
                    envelope2.dotNet = true;
                    //Set output SOAP object
                    envelope2.setOutputSoapObject(request2);
                    //Create HTTP call object
                    HttpTransportSE androidHttpTransport2 = new HttpTransportSE(URL);



                    androidHttpTransport2.call(SOAP_ACTION, envelope2);
                    //Get the response
                    SoapObject response2 = (SoapObject) envelope2.bodyIn;

                    List<Album> c2= new ArrayList<>();
                    SoapToObject so2=new SoapToObject();
                    c2= (List<Album>)(List<?>)(SoapToObject.to(Album.class, response2));

                    List<AlbumSerialisable> albumSerialisables= new ArrayList<AlbumSerialisable>();
                    List<Image> images= new ArrayList<Image>();

                    for (int i=0; i<c2.size(); i++)
                    {
                        soapParams=new ArrayList<SoapParam>();
                        List<SoapParam> lp2=new ArrayList<SoapParam>();
                        SoapParam sp2 = new SoapParam();
                        sp2.setParamName("albumId");
                        sp2.setParamValue(c2.get(i).getId() + "");
                        sp2.setType(String.class);
                        lp2.add(sp2);
                        soapParams = lp2;
                        NAMESPACE = "http://rattrapage-2.estiam.com/api/images.php/";
                         URL = "http://rattrapage-2.estiam.com/api/images.php?wsdl";
                        SOAP_ACTION = "http://rattrapage-2.estiam.com/api/images.php/getImages";
                         METHOD_NAME = "getImages";


                        request = new SoapObject(NAMESPACE, METHOD_NAME);
                        //Property which holds input parameters
                        for (int t=0; t<soapParams.size(); t++)
                        {
                            PropertyInfo param = new PropertyInfo();
                            //Set Name
                            param.setName(soapParams.get(t).getParamName());
                            //Set Value
                            param.setValue(soapParams.get(t).getParamValue());
                            //Set dataType
                            param.setType(soapParams.get(t).getType());
                            request.addProperty(param);
                        }

                         envelope = new SoapSerializationEnvelope(
                                SoapEnvelope.VER11);
                        envelope.dotNet = true;
                        //Set output SOAP object
                        envelope.setOutputSoapObject(request);
                        //Create HTTP call object
                         HttpTransportSE androidHttpTransport3 = new HttpTransportSE(URL);

                        androidHttpTransport3.debug=true;
                            //Invole web service
                        try {
                            androidHttpTransport3.call(SOAP_ACTION, envelope);
                        }
                        catch (Exception b)
                        {
                        androidHttpTransport3.call(SOAP_ACTION, envelope);
                        }
                            //Get the response

                            //Assign it to fahren static variable
                            //SoapObject returnObj = (SoapObject)response.getProperty("return");
                        if (envelope.bodyIn!=null) {
                            response = (SoapObject) envelope.bodyIn;
                            List<Image> c3 = new ArrayList<>();
                            SoapToObject so3 = new SoapToObject();

                            c3 = (List<Image>) (List<?>) (SoapToObject.to(Image.class, response));

                            for (int y=0; y<c3.size(); y++)
                            {
                                try {
                                    url = new URL(c3.get(y).getPath());

                                    c3.get(y).setPath(Uri.fromFile(new File(getApplicationContext().getFilesDir().getAbsolutePath() + "/" + c3.get(y).getId() + c3.get(y).getPath().substring(c3.get(y).getPath().lastIndexOf("."), c3.get(y).getPath().length()))).toString());

                                    input = url.openStream();
                                    output = new FileOutputStream(getApplicationContext().getFilesDir().getAbsolutePath() + "/" + c3.get(y).getId() + c3.get(y).getPath().substring(c3.get(y).getPath().lastIndexOf("."), c3.get(y).getPath().length()));

                                    try {
                                        byte[] buffer = new byte[1800];
                                        int bytesRead = 0;
                                        while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0) {
                                            output.write(buffer, 0, bytesRead);
                                        }
                                    } finally {

                                        output.close();
                                    }
                                }catch (Exception z)
                                {
                                    String bb="ghgh";
                                }
                            }

                            albumSerialisables.add(new AlbumSerialisable(c2.get(i), c3));
                            androidHttpTransport.reset();
                            androidHttpTransport3.reset();
                        }
                    }

                    ((AppData) this.getApplication()).setAlbums(albumSerialisables);
                    ((AppData) this.getApplication()).setIsFirstStart(true);
                    AppData gg=((AppData) this.getApplication());


                    File result = new File(LoginActivity.this.getFilesDir().getAbsolutePath()+"/user_albums.xml");

                    serializer.write(((AppData) this.getApplication()), result);

                   progress.dismiss();
                    Intent intent = new Intent(this, MainActivity.class);
                    String message = c.get(0).getUsername();
                    intent.putExtra("username", message);
                    intent.putExtra("photo", getApplicationContext().getFilesDir().getAbsolutePath() + c.get(0).getPhoto().substring( c.get(0).getPhoto().lastIndexOf("/"), c.get(0).getPhoto().length()));
                    intent.putExtra("userId", c.get(0).getId());

                    startActivity(intent);

                }
                catch (Exception f)
                {
                    String t=f.getMessage();
                }



            }
            else {
                return;
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
