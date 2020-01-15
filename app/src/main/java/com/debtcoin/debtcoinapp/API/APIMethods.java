package com.debtcoin.debtcoinapp.API;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.debtcoin.debtcoinapp.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Lirio on 5/6/2018.
 */

public class APIMethods {
//    public JSONObject Token(Context mContext, HashMap<String, String> params) {
//        String jsonBody = (new JSONObject(params)).toString();
//        String result = (new RestAPI()).httpPostMethod(mContext.getResources().getString(R.string.API_URL)+ "confirmationcode", jsonBody);
//        S
//        hash.put("ResponseCode", split[0]);
//        hash.put("Data", split[1]);
//        return new JSONObject(hash);
//    }

    interface Service {
        @Multipart
        @POST("/")
        Call<ResponseBody> postImage(@Part MultipartBody.Part image, @Part("name") RequestBody name);
    }

}
