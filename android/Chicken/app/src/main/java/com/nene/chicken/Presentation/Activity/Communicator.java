package com.nene.chicken.Presentation.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class Communicator {
	private static String findRouteUrl = "http://map.naver.com/findroute2/findWalkRoute.nhn?call=route2&output=json&coord_type=naver&search=0&";
    private static String searchPositionUrl =  "https://openapi.naver.com/v1/search/local.json?";


//    urlType - 1:findRouteUrl  2:searchPositionUrl
	public static void getHttp(int urlType, String url, final Handler mHandler) {
        String webUrl = "";

        if(urlType == 1){
            webUrl = findRouteUrl;
        }else{
            webUrl = searchPositionUrl;
        }

		final String httpUrl = webUrl + url;
//		MLog.d(httpUrl);

		Thread t = new Thread() {
			public void run() {
				String jsonString = ReadHTML(httpUrl);
				if (jsonString == null) {
					jsonString = "";
				}

				Message msg = mHandler.obtainMessage();
				Bundle bundle = new Bundle();
				bundle.putString("jsonString", jsonString);
				msg.setData(bundle);
				mHandler.sendMessage(msg);
			}
		};
		t.start();
	}

	private static String ReadHTML(String address) {
		return ReadHTML(address, 3000);
	}

	private static String ReadHTML(String address, int timeout) {
		String html = new String();

		try {
			URL url = new URL(address);

			String clientId = "ipifACa9CBcwO3KA5ERI";//애플리케이션 클라이언트 아이디값";
			String clientSecret = "toI2TO5ffH";//애플리케이션 클라이언트 시크릿값";

			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("X-Naver-Client-Id", clientId);
            urlConnection.setRequestProperty("X-Naver-Client-Secret", clientSecret);

			if (urlConnection != null) {
				urlConnection.setConnectTimeout(timeout);
				urlConnection.setUseCaches(false);
				if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
					BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
					while (true) {
						String buf = br.readLine();
						if (buf == null)
							break;
						html += buf;
					}
					br.close();
					urlConnection.disconnect();
				} else {
					return null;
				}
			} else {
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;

		} finally {

		}

		return html;
	}

}
