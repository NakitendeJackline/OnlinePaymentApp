package com.pegasus.pegpay;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static android.R.attr.id;
import static com.pegasus.pegpay.AccountManager.saveAccountBalance;
import static com.pegasus.pegpay.AccountManager.setFullName;
import static com.pegasus.pegpay.R.id.parent;

public class LoginFragment extends Fragment {
    EditText Field1, Field2;
    View view;

    static AQuery aq;
    Spinner accountTypeSpinner;
    //static TopUpActivity ac;

    String error = null;

    ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_fragment, container, false);
        aq = new AQuery(view);
        SingupLoginActivity ac = (SingupLoginActivity) getActivity();

        Field1 = (EditText)view.findViewById(R.id.txt_username);
        Field2 = (EditText)view.findViewById(R.id.txt_password);


        aq.id(R.id.btn_submit).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processLogin();
            }
        });



        return view;
    }


    private void processLogin() {

        HashMap<String, Object> params = new HashMap<>();

            String Field1 = aq.id(R.id.txt_username).getText().toString().trim();

            String Field2 = aq.id(R.id.txt_password).getText().toString().trim();



            if (TextUtils.isEmpty(Field1) ) {//.isEmpty()
                showErrorToast("Please enter your Phone Number");
                return;
            }
            if (TextUtils.isEmpty(Field2)) { //.isEmpty()
                showErrorToast("Please enter your Password");
                return;
            }
            AccountManager.savePhone(getActivity(), Field1 );

            AccountManager.savePassword(getActivity(), Field2);


            params.put("phone", Field1);

            params.put("password", Field2);

        postRegistrationData(params);
    }
    private void postRegistrationData(final HashMap<String, Object> params) {

        AsyncTask<Void, Void, Void> execute = new AsyncTask<Void, Void, Void>() {
            QueryRequest req = new QueryRequest();

            PegPayResponse resp = new PegPayResponse();
            String AccessId = "PEGPAY_APP";
            String Password = "F3D05B8DC763605BCEEA27D11D0E8346";
            String BankCode = "PSSM";


            @Override
            protected void onPreExecute() {
                dialog = new ProgressDialog(getActivity());
                dialog.setIndeterminate(true);
                dialog.setMessage(" Please wait....");
                dialog.show();
                TelephonyManager mngr = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);

                String Field1 = params.get("phone").toString();

                String Field2 = params.get("password").toString();

                String encryptedstring = md5(Field2);

                req.setBankCode("PSSM");
                req.setPassword("F3D05B8DC763605BCEEA27D11D0E8346");
                req.setAccessId("PEGPAY_APP");
                req.setField1(Field1);
                req.setField2(encryptedstring.toUpperCase());
                Log.d("Data", "Bd : "+ req.getField1());
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {

                if (req != null)
                {
                    RequestMapper mapper = new RequestMapper();
                    resp = mapper.CustomerLogin(req);
                    Log.d("Response", "StatusCode: "+ resp.getStatusCode());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                if (resp.getStatusCode().equals("0")&&resp.getStatusDesc().equals("SUCCESS"))
                {

                    AccountManager.setFullName(getActivity(), resp.getField1());
                    AccountManager.saveEmail(getActivity(), resp.getField3());
                    saveAccountBalance(getActivity(), resp.getField6());
                    double accBalance = 0;
                    try
                    {
                        accBalance = Double.parseDouble(resp.getField6());
                    }                    catch(Exception ee)
                    {

                    }
                    saveAccountBalance(getActivity(),String.valueOf((int)accBalance));
                    Log.d("Response", "StatusCode: "+ resp.getStatusCode());
                    //Put the value

                    Intent inte = new Intent(getActivity(), DashboardActivity.class);
                  //  inte.putExtras(bundle);
                    startActivity(inte);
                    getActivity().finish();
                }
                else
                {
                    Toast.makeText(getActivity(), resp.getStatusDesc(), Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();
            }
        }.execute();

    }

    private void showErrorToast(String error){
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
    }

    public final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
