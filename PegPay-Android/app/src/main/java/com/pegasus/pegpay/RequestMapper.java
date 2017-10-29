package com.pegasus.pegpay;

import android.util.Log;

import org.ksoap2.serialization.SoapObject;


/**
 * Created by Jackie on 18-Oct-17.
 */

public class RequestMapper {
    private String ACTION_REGISTERCUSTOMER = "RegisterMerchant";
    private String ACTION_LOGIN = "Login";
    private String ACTION_TRANSACT = "Transact";
    private  String ACTION_TOPUP = "MakeTopUp";
  private DMZ dmz = new DMZ();

    public PegPayResponse RegiserCustomer(SystemRequest request)
    {
       PegPayResponse resp  = new PegPayResponse();
        try {
            SoapObject object = dmz.ProcessRequest(ACTION_REGISTERCUSTOMER, request);

            resp.setStatusCode(object.getPropertyAsString("StatusCode"));
            resp.setStatusDesc(object.getPropertyAsString("StatusDesc"));
            resp.setPegPayId(object.getPropertyAsString("PegPayId"));
        }
        catch(Exception ee)
        {
            resp.setStatusCode("100");
            resp.setStatusDesc("ERROR: "+ee.getMessage());
            ee.printStackTrace();
            Log.d("ERROR",ee.getMessage());
        }


        return resp;
    }

    public PegPayResponse CustomerLogin(QueryRequest request)
    {
        PegPayResponse resp = new PegPayResponse();
        try{
            SoapObject object = dmz.ProcessRequest(ACTION_LOGIN, request);

            String statusCode = object.getPropertyAsString("StatusCode");
            resp.setStatusCode(object.getPropertyAsString("StatusCode"));
            resp.setStatusDesc(object.getPropertyAsString("StatusDesc"));
            if(statusCode.equals("0") )
            {
                resp.setPegPayId(object.getPropertyAsString("PegPayId"));
                resp.setField1(object.getPropertyAsString("Field1"));
                resp.setField2(object.getPropertyAsString("Field2"));
                resp.setField3(object.getPropertyAsString("Field3"));
                resp.setField4(object.getPropertyAsString("Field4"));
                resp.setField5(object.getPropertyAsString("Field5"));
                resp.setField6(object.getPropertyAsString("Field6"));
            }

            Log.d("Data", "Bd : "+ resp.getField2());
        }
        catch (Exception ee){
            resp.setStatusCode("100");
            resp.setStatusDesc("ERROR: "+ee.getMessage());
            ee.printStackTrace();
            Log.d("ERROR",ee.getMessage());
        }

        return resp;

    }

    public PegPayResponse CustomerTopUp(SystemRequest request)
    {
        PegPayResponse resp = new PegPayResponse();
        try{
            SoapObject object = dmz.ProcessRequest(ACTION_TOPUP, request);

            resp.setStatusCode(object.getPropertyAsString("StatusCode"));
            resp.setStatusDesc(object.getPropertyAsString("StatusDesc"));
            resp.setPegPayId(object.getPropertyAsString("PegPayId"));

        }
        catch (Exception ee){
            resp.setStatusCode("100");
            resp.setStatusDesc("ERROR: "+ee.getMessage());
            ee.printStackTrace();
            Log.d("ERROR",ee.getMessage());
        }

        return resp;

    }



    }

