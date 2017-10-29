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
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
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
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Zed on 4/16/2016.
 */
public class SignUpFragment extends Fragment {
    EditText RequestField1, RequestField2, RequestField3, RequestField4, RequestField5, RequestField7, RequestField8, RequestField10;
    Spinner RequestField6, RequestField9;
    View view;
    String dateString;
    static AQuery aq;
    Spinner accountTypeSpinner;
    static SingupLoginActivity ac;
    String AES = "AES";

    String error = null;

    ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.signup_fragment, container, false);
        aq = new AQuery(view);
        ac = (SingupLoginActivity)getActivity();

        RequestField1 = (EditText)view.findViewById(R.id.txt_phone_number);
        RequestField2 = (EditText)view.findViewById(R.id.txt_first_name);
        RequestField3 = (EditText)view.findViewById(R.id.txt_last_name);
        RequestField4 = (EditText)view.findViewById(R.id.txt_password);
        RequestField5 = (EditText)view.findViewById(R.id.txt_email);
        RequestField6 = (Spinner)view.findViewById(R.id.spn_sex);
        RequestField7 = (EditText)view.findViewById(R.id.txt_dob);
        RequestField8 = (EditText)view.findViewById(R.id.nationality);
        RequestField9 = (Spinner)view.findViewById(R.id.spn_id_type);
        RequestField10 = (EditText)view.findViewById(R.id.txt_id_number);

       /* String outputString = encrypt(RequestField1.getText().toString());*/
        Log.d("Data", "Phone Before: "+ RequestField1);

        aq.id(R.id.txt_select_business_licence).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dispatchPhotoSelectionEvent(3);
            }
        });
        aq.id(R.id.img_business_licence).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // dispatchPhotoSelectionEvent(3);
            }
        });

        accountTypeSpinner = (Spinner)view.findViewById(R.id.spn_type);
        accountTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 2){
                    aq.id(R.id.ln_non_individual_sme).visible();
                    aq.id(R.id.ln_individual).gone();
                }else{
                    aq.id(R.id.ln_non_individual_sme).gone();
                    aq.id(R.id.ln_individual).visible();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        aq.id(R.id.txt_dob).getEditText().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Calendar c = Calendar.getInstance();
                    int year = c.get(Calendar.YEAR);
                    int month = c.get(Calendar.MONTH);
                    int day = c.get(Calendar.DAY_OF_MONTH);

                    new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener(){
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            dateString = Utils.formatDate(year, monthOfYear, dayOfMonth, "d MMM, yyyy");
                            aq.id(R.id.txt_dob).text(dateString);
                        }
                    }, year, month, day).show();
                    return true;
                }
                return false;
            }
        });

        aq.id(R.id.btn_submit).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processRegistration();
            }
        });

        populateFields();

        accountTypeSpinner.setSelection(2);

        return view;
    }

    private void populateFields(){
        aq.id(R.id.spn_type).setSelection(1);
        if(AccountManager.isLoggedIn(getActivity())){
            String firstName = AccountManager.getFirstName(getActivity());
            String lastName = AccountManager.getLastName(getActivity());
            String email = AccountManager.getEmail(getActivity());
            String sex = AccountManager.getSex(getActivity());
            String avatarPath = AccountManager.getAvatarPath(getActivity());
            String idPath = AccountManager.getIDPath(getActivity());
            String dob = AccountManager.getDOB(getActivity());
            String idType = AccountManager.getIDType(getActivity());
            String idNumber = AccountManager.getIDNumber(getActivity());
            String phone = AccountManager.getPhone(getActivity());
            String licencePath = AccountManager.getLicencePath(getActivity());

            aq.id(R.id.txt_first_name).text(firstName);
            aq.id(R.id.txt_last_name).text(lastName);
            aq.id(R.id.txt_company_name).text(firstName + " " + lastName);
            aq.id(R.id.txt_email).text(email).enabled(false);
            aq.id(R.id.txt_company_email).text(email).enabled(false);
            aq.id(R.id.txt_id_number).text(idNumber);
            aq.id(R.id.txt_phone_number).text(phone);
            if(!TextUtils.isEmpty(dob)){
                aq.id(R.id.txt_dob).text(dob);
                dateString = dob;
            }

            if(!TextUtils.isEmpty(sex)){
                aq.id(R.id.spn_sex).setSelection(Utils.getSpinnerItemIndex(aq.id(R.id.spn_sex).getSpinner(), sex));
            }
            if(!TextUtils.isEmpty(idType)){
                aq.id(R.id.spn_id_type).setSelection(Utils.getSpinnerItemIndex(aq.id(R.id.spn_id_type).getSpinner(), idType));
            }
            /*if(!TextUtils.isEmpty(avatarPath)){
                File avatar = new File(avatarPath);
                if(avatar.exists()){
                    aq.id(R.id.img_avatar).image(avatar, 200);
                }
            }*/
           /* if(!TextUtils.isEmpty(idPath)){
                File id = new File(idPath);
                if(id.exists()){
                    aq.id(R.id.img_id).image(id, 200);
                }
            }*/
            if(!TextUtils.isEmpty(licencePath)){
                File licence = new File(licencePath);
                if(licence.exists()){
                    aq.id(R.id.img_business_licence).image(licence, 200);
                }
            }
        }
    }
    private void processRegistration() {
        int registrationType = aq.id(R.id.spn_type).getSelectedItemPosition();
        HashMap<String, Object> params = new HashMap<>();
        if (registrationType == 0) {
            showErrorToast("Please select account type");
            return;
        }

        if (registrationType == 1) {
           // dateString = "2000-01-01 00:00:50.070";
            String RequestField1 = aq.id(R.id.txt_phone_number).getText().toString().trim();
            String RequestField2 = aq.id(R.id.txt_first_name).getText().toString().trim();
            String RequestField3 = aq.id(R.id.txt_last_name).getText().toString().trim();
            String RequestField4 = aq.id(R.id.txt_password).getText().toString().trim();
            String RequestField5 = aq.id(R.id.txt_email).getText().toString().trim();
            int RequestField6 = aq.id(R.id.spn_sex).getSelectedItemPosition();
            String RequestField7 = aq.id(R.id.txt_dob).getText().toString().trim();
            String RequestField8 = aq.id(R.id.nationality).getText().toString().trim();
            int RequestField9 = aq.id(R.id.spn_id_type).getSelectedItemPosition();
            String RequestField10= aq.id(R.id.txt_id_number).getText().toString().trim();



            if (TextUtils.isEmpty(RequestField3) ) {//.isEmpty()
                showErrorToast("Please enter your Last number");
                return;
            }
            if (TextUtils.isEmpty(RequestField2)) { //.isEmpty()
                showErrorToast("Please enter your First name");
                return;
            }
            if (RequestField1.length() < 10) {
                showErrorToast("Please enter a valid phone number");
                return;
            }
            if (!TextUtils.isEmpty(RequestField5) && !Utils.isValidEmail(RequestField5)) { //.isEmpty()
                showErrorToast("Please enter a valid email address");
                return;
            }
            if (RequestField6 == 0) {
                showErrorToast("Please select your gender");
                return;
            }/* else {
                RequestField6 = aq.id(R.id.spn_sex).getSelectedItem().toString();
            }*/
            if (RequestField7 == null) {
                showErrorToast("Please select your date of birth");
                return;
            }
            if (RequestField9 == 0) {
                showErrorToast("Please select an ID type");
                return;
            } /*else {
                RequestField9 = aq.id(R.id.spn_id_type).getSelectedItem().toString();
            }*/
            if (TextUtils.isEmpty(RequestField10)) {//.isEmpty()
                showErrorToast("Please enter your ID Number");
                return;
            }


            AccountManager.savePhone(getActivity(), RequestField1 );
            AccountManager.saveFirstName(getActivity(), RequestField2);
            AccountManager.saveLastName(getActivity(), RequestField3);
            AccountManager.savePassword(getActivity(), RequestField4);
            AccountManager.saveEmail(getActivity(), RequestField5);
            AccountManager.saveSex(getActivity(), RequestField6);
            AccountManager.saveDOB(getActivity(), RequestField7);
            AccountManager.saveNationality(getActivity(), RequestField8);
            AccountManager.saveIDType(getActivity(), RequestField9);

            AccountManager.saveIDNumber(getActivity(), RequestField10);


            params.put("type", registrationType);
            params.put("phone", RequestField1);
            params.put("fname", RequestField2);
            params.put("lname", RequestField3);
            params.put("password", RequestField4);
            params.put("email", RequestField5);
            params.put("gender", RequestField6);
            params.put("dob", RequestField7);
            params.put("nationality", RequestField8);
            params.put("idType", RequestField9);
            params.put("idNumber", RequestField10);

            //Log.e("Images",dateString+" -#- "+ownerImage+" -#- "+IdImage);
        }

        AccountManager.saveAccountType(getActivity(), registrationType);

        postRegistrationData(params);
    }
    private void postRegistrationData(final HashMap<String, Object> params) {

        AsyncTask<Void, Void, Void> execute = new AsyncTask<Void, Void, Void>() {
            SystemRequest req = new SystemRequest();
            PegPayResponse resp = new PegPayResponse();
            String AccessId = "PEGPAY_APP";
            String Password = "F3D05B8DC763605BCEEA27D11D0E8346";
            String BankCode = "PSSM";
            String BranchCode = "MAIN";

            @Override
            protected void onPreExecute() {
                dialog = new ProgressDialog(getActivity());
                dialog.setIndeterminate(true);
                dialog.setMessage("Registration in process. Please wait....");
                dialog.show();
                TelephonyManager mngr = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
//AES ENCYPTION WORKING
               // String pass = "wSdZazrOJ6wGYl3h32E1lTqnnq1mxFeIJnqqzIO3glcRcU5TBhgmqcZrBF1vbth91TVwMFqBvoJmM8uPEkxYvcx8OzEKvFZkuJ0h3pKnEy5YgxJVjc9FZ6bB6mqD3xRJbD6dlyNeipSxGOBs4eUhdnSDwigrHeLm7326EMVlSCEHJFSDz8dXmXrVTIb1t1EEghupb6yP";


//                String RequestField1 = null;
//                try {
//                    RequestField1 = encrypt( params.get("phone").toString(), pass);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                String RequestField2 = null;
//                try {
//                    RequestField2 = encrypt( params.get("fname").toString(), pass);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                String RequestField3 = null;
//                try {
//                    RequestField3 = encrypt( params.get("lname").toString(), pass);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                String RequestField4 = null;
//                try {
//                    RequestField4 = encrypt( params.get("password").toString(), pass);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                String RequestField5 = null;
//                try {
//                    RequestField5 = encrypt( params.get("email").toString(), pass);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//
//                int RequestField6 = Integer.parseInt(params.get("gender").toString());
//
//                String RequestField7 = null;
//                try {
//                    RequestField7 = encrypt( params.get("dob").toString(), pass);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                String RequestField8 = null;
//                try {
//                    RequestField8 = encrypt(params.get("nationality").toString(), pass);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                int RequestField9 = Integer.parseInt(params.get("idType").toString());
//                String RequestField10 = null;
//                try {
//                    RequestField10 = encrypt( params.get("idNumber").toString(), pass);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                String RequestField1 = params.get("phone").toString();
                String RequestField2 =  params.get("fname").toString();
                String RequestField3 = params.get("lname").toString();
                String RequestField4 = params.get("password").toString();
                String RequestField5 = params.get("email").toString();
                int RequestField6 =Integer.parseInt( params.get("gender").toString());
                String RequestField7 = params.get("dob").toString();
                String RequestField8 = params.get("nationality").toString();
                int RequestField9 =Integer.parseInt( params.get("idType").toString());
                String RequestField10 = params.get("idNumber").toString();




                req.setBankCode("PSSM");
                req.setBranchCode("MAIN");
                req.setPassword("F3D05B8DC763605BCEEA27D11D0E8346");
                req.setAccessId("PEGPAY_APP");
                req.setRequestField1(RequestField1);
                req.setRequestField2(RequestField2);
                req.setRequestField3(RequestField3);
                req.setRequestField4(RequestField4);
                req.setRequestField5(RequestField5);
                req.setRequestField6(RequestField6);
                req.setRequestField7(RequestField7);
                req.setRequestField8(RequestField8);
                req.setRequestField9(RequestField9);
                req.setRequestField10(RequestField10);

                Log.d("Data", "Bd : "+ req.getRequestField7());
                super.onPreExecute();


            }

            @Override
            protected Void doInBackground(Void... params) {

                if (req != null) {
                    RequestMapper mapper = new RequestMapper();
                    resp = mapper.RegiserCustomer(req);
                    Log.d("Response", "StatusCode: "+ resp.getStatus());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                if (resp.getStatusCode() == "0") {
                    Toast.makeText(getActivity(), resp.getStatusDesc(), Toast.LENGTH_LONG).show();
                    ac.switchTab(1);


                   // Intent intent = new Intent(getActivity(), LoginFragment.class);
                   // startActivity(intent);



                //ac.switchTab(2);
                } else {

                   Toast.makeText(getActivity(), resp.getStatusDesc(), Toast.LENGTH_LONG).show();

                }
               dialog.dismiss();
            }
        }.execute();

    }

    //AES algorithm

    private String encrypt(String Data, String password) throws Exception{
        SecretKeySpec key = generateKey(password);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE,key);
        byte[] encVal = c.doFinal(Data.getBytes());
        String encryptedValue = Base64.encodeToString(encVal, Base64.DEFAULT);
        return encryptedValue;
    }

    private  SecretKeySpec generateKey(String password) throws Exception{
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = password.getBytes("UTF-8");
        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        return  secretKeySpec;
    }

    private void showErrorToast(String error){
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
    }
}
