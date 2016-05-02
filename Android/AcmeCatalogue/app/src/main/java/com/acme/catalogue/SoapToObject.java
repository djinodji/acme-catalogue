package com.acme.catalogue;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class SoapToObject
{
    public static List<Object> to(Class c, SoapObject soapObject) throws Exception
    {



        List<Object> list= new ArrayList<Object>();

        Object sObject = soapObject.getProperty("return");
        SoapObject sO= new SoapObject();

        List collection = (List)sObject;


        for (int i = 0; i < collection.size(); i++)
        {
            Object obj = c.newInstance();
            sO = (SoapObject) collection.get(i);

            int propertyCount = sO.getPropertyCount();

            for (int j=0; j<propertyCount; j++) {

                PropertyInfo propertyInfo = new PropertyInfo();

                sO.getPropertyInfo(j, propertyInfo);
                //SoapObject returnObj = soapObject.getProperty("return");

                //SoapObject.class.cast(sObject);
                String s = propertyInfo.getName();
                try {
                    Class typeClass = obj.getClass().getDeclaredMethod(
                            "get"
                                    + propertyInfo.getName().substring(0, 1)
                                    .toUpperCase()
                                    + propertyInfo.getName().substring(1))
                            .getReturnType();

                    Method method = obj.getClass().getMethod(
                            "set"
                                    + propertyInfo.getName().substring(0, 1)
                                    .toUpperCase()
                                    + propertyInfo.getName().substring(1), new Class[]
                                    {typeClass});
                    String value = sO.getProperty(j).toString();


                    if (typeClass == String.class) {
                        method.invoke(obj, value);

                    } else if (typeClass == int.class) {
                        method.invoke(obj, Integer.parseInt(value));

                    }
                } catch (Exception e) {
                    String d = e.getStackTrace().toString();
                }



            }
            list.add(obj);
            //return list;
        }

        return list;
    }
}