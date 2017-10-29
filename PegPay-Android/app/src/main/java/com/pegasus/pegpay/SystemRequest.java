package com.pegasus.pegpay;

/**
 * Created by Jackie on 18-Oct-17.
 */


import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;


public class SystemRequest implements KvmSerializable {

    private String RequestField1;
    private String RequestField2;
    private String RequestField3;
    private String RequestField4;
    private String RequestField5;
    private String RequestField6;
    private String RequestField7;
    private String RequestField8;
    private String RequestField9;
    private String RequestField10;
    private  String RequestField11;
    private  String RequestField12;
    private  String RequestField13;
    private  String RequestField14;
    private  String RequestField15;
    private String BranchCode;
    private String BankCode;
    private String Password;
    private String AccessId;





    @Override
    public Object getProperty(int i) {

        Object obj = null;
        switch (i) {
            case 0:
                obj = this.BankCode;

                break;
            case 1:
                obj = this.BranchCode;

                break;
            case 2:
                obj = this.Password;

                break;
            case 3:
                obj = this.AccessId;

                break;
            case 4:
                obj = this.RequestField1;
                break;
            case 5:
                obj = this.RequestField2;
                break;
            case 6:
                obj = this.RequestField3;
                break;
            case 7:
                obj = this.RequestField4;
                break;
            case 8:
                obj = this.RequestField5;
                break;
            case 9:
                obj = this.RequestField6;
                break;
            case 10:
                obj = this.RequestField7;
                break;
            case 11:
                obj = this.RequestField8;
                break;
            case 12:
                obj = this.RequestField9;
                break;
            case 13:
                obj = this.RequestField10;
                break;

        }
        return obj;
    }




    @Override
    public int getPropertyCount() {
        return 14;
    }

    @Override
    public void setProperty(int position, Object value) {
        switch (position) {
            case 0:
                BankCode = value.toString();
                break;
            case 1:
                BranchCode = value.toString();
                break;
            case 2:
                Password = value.toString();
                break;
            case 3:
                AccessId = value.toString();
                break;
            case 4:
                RequestField1 = value.toString();
                break;
            case 5:
                RequestField2 = value.toString();
                break;
            case 6:
                RequestField3 = value.toString();
                break;
            case 7:
                RequestField4 = value.toString();
                break;
            case 8:
                RequestField5 = value.toString();
                break;
            case 9:
                RequestField6 = GetGender(value);
                break;
            case 10:
                RequestField7 = value.toString();
                break;
            case 11:
                RequestField8 = value.toString();
                break;
            case 12:
                RequestField9 = GetIdType(value);
                break;
            case 13:
                RequestField10 = value.toString();
                break;


        }
    }

    private String GetGender(Object value) {
        int chosen = (int) value;
        if (chosen == 1)
            return "MALE";
        else
            return "FEMALE";
    }

    private String GetIdType(Object value) {
        String idType = "";
        int type = (int) value;
        switch (type)
        {
            case 1:
                idType = "NATIONALID";
                break;
            case 2:
                idType = "COMPANYID";
                break;
            case 3:
                idType = "PASSPORT";
                break;
            case 4:
                idType = "DRIVERID";
                break;
        }
        return idType;
    }

    @Override
    public void getPropertyInfo(int val, Hashtable hashtable, PropertyInfo info) {

        switch (val) {
            case 0:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "BankCode";
                break;
            case 1:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "BranchCode";
                break;
            case 2:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Password";
                break;
            case 3:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "AccessId";
                break;
            case 4:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "RequestField1";
                break;
            case 5:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "RequestField2";
                break;
            case 6:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "RequestField3";
                break;
            case 7:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "RequestField4";
                break;
            case 8:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "RequestField5";
                break;
            case 9:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "RequestField6";
                break;
            case 10:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "RequestField7";
                break;
            case 11:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "RequestField8";
                break;
            case 12:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "RequestField9";
                break;
            case 13:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "RequestField10";
                break;


        }
    }


    public String getRequestField1() {
        return RequestField1;
    }

    public void setRequestField1(String requestField1) {
        RequestField1 = requestField1;
    }

    public String getRequestField2() {
        return RequestField2;
    }

    public void setRequestField2(String requestField2) {
        RequestField2 = requestField2;
    }

    public String getRequestField3() {
        return RequestField3;
    }

    public void setRequestField3(String requestField3) {
        RequestField3 = requestField3;
    }

    public String getRequestField4() {
        return RequestField4;
    }

    public void setRequestField4(String requestField4) {
        RequestField4 = requestField4;
    }

    public String getRequestField5() {
        return RequestField5;
    }

    public void setRequestField5(String requestField5) {
        RequestField5 = requestField5;
    }

    public String getRequestField6() {
        return RequestField6;
    }

    public void setRequestField6(int requestField6) {
        RequestField6 = GetGender(requestField6);
    }

    public String getRequestField7() {
        return RequestField7;
    }

    public void setRequestField7(String requestField7)  {
        RequestField7 = requestField7;
    }

    private String GetDate(String requestField7) {
        String birthDateString = "";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate;
        try {
            birthDate = df.parse(requestField7);
            birthDateString = df.format(birthDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return birthDateString;
    }

    public String getRequestField8() {
        return RequestField8;
    }

    public void setRequestField8(String requestField8) {
        RequestField8 = requestField8;
    }

    public String getRequestField9() {
        return RequestField9;
    }

    public void setRequestField9(int requestField9) {
        RequestField9 = GetIdType(requestField9);
    }

    public String getRequestField10() {
        return RequestField10;
    }

    public void setRequestField10(String requestField10) {
        RequestField10 = requestField10;
    }

    public String getBranchCode() {
        return BranchCode;
    }

    public void setBranchCode(String branchCode) {
        BranchCode = branchCode;
    }

    public String getBankCode() {
        return BankCode;
    }

    public void setBankCode(String bankCode) {
        BankCode = bankCode;
    }

    public String getAccessId() {
        return AccessId;
    }

    public void setAccessId(String accessId) {
        AccessId = accessId;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getRequestField11() {
        return RequestField11;
    }

    public void setRequestField11(String requestField11) {
        RequestField11 = requestField11;
    }

    public String getRequestField12() {
        return RequestField12;
    }

    public void setRequestField12(String requestField12) {
        RequestField12 = requestField12;
    }

    public String getRequestField13() {
        return RequestField13;
    }

    public void setRequestField13(String requestField13) {
        RequestField13 = requestField13;
    }

    public String getRequestField14() {
        return RequestField14;
    }

    public void setRequestField14(String requestField14) {
        RequestField14 = requestField14;
    }

    public String getRequestField15() {
        return RequestField15;
    }

    public void setRequestField15(String requestField15) {
        RequestField15 = requestField15;
    }
}
