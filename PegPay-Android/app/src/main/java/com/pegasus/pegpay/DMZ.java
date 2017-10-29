package com.pegasus.pegpay;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

/**
 * Created by Jackie on 17-Oct-17.
 */

public class DMZ {

    private static final String URL = "https://test.pegasus.co.ug:8019/TestAppApi/Service.asmx?WSDL";
    private static final String NAMESPACE = "http://appapi.org/";
    private static final String AccessId = "PEGPAY_APP";
    private static final String Password = "F3D05B8DC763605BCEEA27D11D0E8346";
    private static final String BankCode = "PSSM";
    private static final String BranchCode = "MAIN";

    public SoapObject ProcessRequest(String method, SystemRequest request) throws SocketTimeoutException, UnknownHostException, Exception{

        SoapObject Requestt = new SoapObject(NAMESPACE, method);

        request.setBankCode("PSSM");
        request.setBranchCode("ALL");
        request.setPassword("F3D05B8DC763605BCEEA27D11D0E8346");
        request.setAccessId("PEGPAY_APP");

        SoapObject soapResponse = null;
        PropertyInfo pi = new PropertyInfo();
        pi.setName("request");
        pi.setValue(request);
        pi.setType(SystemRequest.class);
        Requestt.addProperty(pi);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.implicitTypes = true;
        // envelope.setAddAdornments(false);
        envelope.setOutputSoapObject(Requestt);
        envelope.addMapping(NAMESPACE, "Request", new SystemRequest().getClass());

        HttpTransportSE transportSE = new HttpTransportSE(URL, 36000);
        boolean connectionProblem = false;
        PassSslSites();
        transportSE.debug = true;
        transportSE.call(NAMESPACE + method, envelope);
        Log.v("SOAP REQUEST", transportSE.requestDump);
        Log.v("SOAP RESPONSE", transportSE.responseDump);
        System.out.println(transportSE.responseDump);
        soapResponse = (SoapObject) envelope.getResponse();

        return soapResponse;
    }

    public SoapObject ProcessRequest(String method, QueryRequest request) throws SocketTimeoutException, UnknownHostException, Exception{

        SoapObject Requestt = new SoapObject(NAMESPACE, method);

        request.setBankCode("PSSM");

        request.setPassword("F3D05B8DC763605BCEEA27D11D0E8346");
        request.setAccessId("PEGPAY_APP");

        SoapObject soapResponse = null;
        PropertyInfo pi = new PropertyInfo();
        pi.setName("request");
        pi.setValue(request);
        pi.setType(QueryRequest.class);
        Requestt.addProperty(pi);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.implicitTypes = true;
        // envelope.setAddAdornments(false);
        envelope.setOutputSoapObject(Requestt);
        envelope.addMapping(NAMESPACE, "Request", new QueryRequest().getClass());

        HttpTransportSE transportSE = new HttpTransportSE(URL, 36000);
        boolean connectionProblem = false;
        PassSslSites();
        transportSE.debug = true;
        transportSE.call(NAMESPACE + method, envelope);
        Log.v("SOAP REQUEST", transportSE.requestDump);
        Log.v("SOAP RESPONSE", transportSE.responseDump);
        System.out.println(transportSE.responseDump);
        soapResponse = (SoapObject) envelope.getResponse();

        return soapResponse;
    }


    private static TrustManager[] trustManagers;
    public static void PassSslSites() {

        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {

            @Override
            public boolean verify(String hostname, SSLSession session) {
                // TODO Auto-generated method stub
                return true;
            }
        });

        SSLContext context = null;

        if (trustManagers == null) {
            trustManagers = new TrustManager[] { new DupedCertificate() };
        }

        try {
            context = SSLContext.getInstance("TLS");
            context.init(null, trustManagers, new SecureRandom());
        } catch (NoSuchAlgorithmException e) {
            // TODO: handle exception
            //Log.e("allowAllSSl", e.toString());
        } catch (KeyManagementException e) {
            // TODO Auto-generated catch block
            //Log.e("allowAllSSl", e.toString());
        }

        HttpsURLConnection.setDefaultSSLSocketFactory(context
                .getSocketFactory());
    }
}
