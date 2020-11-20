package com.smartiecards.network;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;


/**
 *
 * @author Admin
 */
public class WSConnector {

	public static final int CONNECTION_TIMEOUT = 10000; // 10 seconds
	public static final int WAIT_RESPONSE_TIMEOUT = 30000; // 30 seconds

	private static final String TAG = "WSConnector";

	public static HttpParams getConnectionParams() {

		// connection parameters
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters,
				CONNECTION_TIMEOUT);
		HttpConnectionParams
				.setSoTimeout(httpParameters, WAIT_RESPONSE_TIMEOUT);
		HttpConnectionParams.setTcpNoDelay(httpParameters, true);

		return httpParameters;
	}

	public static InputStream getInputStream(String getJsonGirl) {
		// TODO Auto-generated method stub

		HttpClient client = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet();

		// the service response
		HttpResponse response = null;

		try {
			// construct a URI object
			getRequest.setURI(new URI(getJsonGirl));

			// execute the request
			response = client.execute(getRequest);
			return response.getEntity().getContent();

		} catch (URISyntaxException e) {

			Log.e("WSConnector::getJson =>", e.toString(), e);
		} catch (ClientProtocolException e) {
			Log.e("WSConnector::getJson =>", e.toString(), e);
		} catch (IOException e) {
			Log.e("WSConnector::getJson =>", e.toString(), e);
		} catch (Exception ex) {
			Log.e("WSConnector::getJson =>", ex.toString(), ex);
		}
		return null;
	}



	public static String getStringByUrl(String serviceUrl) {

		HttpClient client = new DefaultHttpClient(getConnectionParams());
		HttpGet getRequest = new HttpGet();

		// the service response
		HttpResponse response = null;

		try {
			URL url = new URL(serviceUrl);
			String nullFragment = null;
			URI uri = new URI(url.getProtocol(), url.getHost(),
					url.getPath(), url.getQuery(), nullFragment);
			// System.out.println("URI " + uri.toString() + " is OK");

			Log.d(TAG, "myUrl :: " + uri.toString());
			// construct a URI object
			getRequest.setURI(new URI(uri.toString()));

			// execute the request
			response = client.execute(getRequest);

			String result = getStringFromInputStream(response.getEntity().getContent());

			return result;

		} catch (Exception e) {

			return "2";
		}
	}



	// public static JSONObject getRequest(String serviceUrl) {
	//
	// // http get client
	// HttpClient client = new DefaultHttpClient(getConnectionParams());
	// HttpGet getRequest = new HttpGet();
	//
	// // buffer reader to read the response
	// BufferedReader in = null;
	// // the service response
	// HttpResponse response = null;
	//
	// // result
	// StringBuffer buff = new StringBuffer("");
	// String line = "";
	//
	// try {
	// // construct a URI object
	// getRequest.setURI(new URI(serviceUrl));
	//
	// // execute the request
	// response = client.execute(getRequest);
	// in = new BufferedReader(new InputStreamReader(response.getEntity()
	// .getContent()));
	//
	// while ((line = in.readLine()) != null) {
	// buff.append(line);
	// }
	//
	// in.close();
	// } catch (URISyntaxException e) {
	// Log.e("WSConnector::getReponseJson =>", e.toString());
	// } catch (ClientProtocolException e) {
	// Log.e("WSConnector::getReponseJson =>", e.toString());
	// } catch (IOException e) {
	// Log.e("WSConnector::getReponseJson =>", e.toString());
	// }
	//
	// String json = buff.toString();
	// Log.i("Get response =>", json);
	// JSONObject jsonObject = null;
	// if (json != null && !json.equals("null")) {
	// try {
	// jsonObject = new JSONObject(json);
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// Log.e("WSConnector::getReponseJson =>", e.getMessage());
	// }
	// }
	//
	// // response, need to be parsed
	// return jsonObject;
	// }

	public static String getStringFromInputStream(InputStream content) {
		// TODO Auto-generated method stub
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(content));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}

	/**
	 * Gets the XML response sent by the web service in response.
	 *
	 * @param serviceUrl
	 * @return Response XML
	 */

	public static InputStream getJsonsInputStream(String serviceUrl) {

		HttpClient client = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet();

		// the service response
		HttpResponse response = null;

		try {

			URL url = new URL(serviceUrl);
			String nullFragment = null;
			URI uri = new URI(url.getProtocol(), url.getHost(),
					url.getPath(), url.getQuery(), nullFragment);


			Log.d(TAG, "myUrl :: " + uri.toString());


			getRequest.setURI(new URI(uri.toString()));

			response = client.execute(getRequest);
			return response.getEntity().getContent();
		} catch (URISyntaxException e) {
			Log.e("WSConnector::getXml =>", e.toString(), e);
		} catch (ClientProtocolException e) {
			Log.e("WSConnector::getXml =>", e.toString(), e);
		} catch (IOException e) {
			Log.e("WSConnector::getXml =>", e.toString(), e);
		} catch (Exception ex) {
			Log.e("WSConnector::getXml =>", ex.toString(), ex);
		}

		return null;
	}

	@SuppressLint("LongLogTag")
	public static byte[] bs(String serviceUrl) {

		// http get client
		HttpClient client = new DefaultHttpClient(getConnectionParams());
		HttpGet getRequest = new HttpGet();

		byte imageData[] = null;

		// buffer reader to read the response
		InputStream in = null;
		// the service response
		HttpResponse response = null;

		try {
			// construct a URI object
			getRequest.setURI(new URI(serviceUrl));

			// execute the request
			response = client.execute(getRequest);
			in = response.getEntity().getContent();

			imageData = new byte[in.available()];
			in.read(imageData);

			in.close();
		} catch (Exception e) {
			Log.e("WSConnector::getBinaryData =>", e.toString());
		}

		// response, need to be parsed
		return imageData;
	}




	public static String getJsonString(InputStream input) {
		// TODO Auto-generated method stub

		return getStringFromInputStream(input);

	}



	public static Bitmap getBitmapFromURL(String string) {
		try {
			Bitmap myBitmap = BitmapFactory.decodeStream(getInputStream(string));
			return myBitmap;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}



	public static String getResponse(String getResponseAboutUs,
									 List<NameValuePair> nameValuePairs) {
		// TODO Auto-generated method stub

		try{
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(getResponseAboutUs);

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);

			InputStream inputStream = response.getEntity().getContent();


			//response = client.execute(getRequest);


			return getStringFromInputStream(response.getEntity().getContent());



		}catch(Exception e){


		}
		return null;
	}















	/*** Function to check either mobile or wifi network is available or not. ***/

	public static boolean checkAvail(Context _context) {
		return (isWifiAvailable(_context) || isMobileNetworkAvailable(_context)  || isBluetoothNetworkAvailable(_context));
	}

	public static boolean isMobileNetworkAvailable(Context _context) {
		ConnectivityManager connecManager = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo myNetworkInfo = connecManager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (myNetworkInfo.isConnected()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isWifiAvailable(Context _context) {
		ConnectivityManager myConnManager = (ConnectivityManager) _context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo myNetworkInfo = myConnManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (myNetworkInfo.isConnected())
			return true;
		else
			return false;
	}


	public static boolean isBluetoothNetworkAvailable(Context _context) {
		ConnectivityManager connecManager = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo myNetworkInfo = connecManager
				.getNetworkInfo(ConnectivityManager.TYPE_BLUETOOTH);
		if (myNetworkInfo.isConnected()) {
			return true;
		} else {
			return false;
		}
	}






	public static String getStringHTTPnHTTPSResponse(String popularProducts) {
		// TODO Auto-generated method stub
		try{
			HostnameVerifier hostnameVerifier = SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;

			DefaultHttpClient client = new DefaultHttpClient();

			SchemeRegistry registry = new SchemeRegistry();
			SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
			socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
			registry.register(new Scheme("https", socketFactory, 443));
			registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(),80));
			SingleClientConnManager mgr = new SingleClientConnManager(client.getParams(), registry);
			DefaultHttpClient httpClient = new DefaultHttpClient(mgr, client.getParams());


			HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);

			Log.e(TAG , "URL popularProducts "+popularProducts);


			URL url1 = new URL(popularProducts);
			String nullFragment = null;
			URI uri = new URI(url1.getProtocol(), url1.getHost(),
					url1.getPath(), url1.getQuery(), nullFragment);

			HttpPost httpPost = new HttpPost(uri);

//			MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
//			entity.addPart("user_id", new StringBody("1"));
//			//  entity.addPart("field2", new StringBody(field2));
//			// entity.addPart("image", new FileBody(image));
//			httpPost.setEntity(entity );



			HttpResponse response = httpClient.execute(httpPost);

			HttpEntity resEntity = response.getEntity();
			String  responseStr = EntityUtils.toString(resEntity).trim();

			return responseStr;
		}catch (Exception e){

		}

		return "2";
	}














	public static HttpClient getNewHttpClient() {
		                    // Do not do this in production!!!
                    HostnameVerifier hostnameVerifier = SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;

                    DefaultHttpClient client = new DefaultHttpClient();

                    SchemeRegistry registry = new SchemeRegistry();
                    SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
                    socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
                    registry.register(new Scheme("https", socketFactory, 443));
					registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(),80));
                    SingleClientConnManager mgr = new SingleClientConnManager(client.getParams(), registry);
                    DefaultHttpClient httpClient = new DefaultHttpClient(mgr, client.getParams());

                    HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);

                    return httpClient;
	}


    public static String getNewStringResponse(String s) {

		String server_response = "";
//		try {
//
//		HttpClient httpclient = new DefaultHttpClient();
//		HttpGet httpget= new HttpGet(s);
//
//		HttpResponse response = httpclient.execute(httpget);
//
//		if(response.getStatusLine().getStatusCode()==200){
//			 server_response = EntityUtils.toString(response.getEntity());
//			Log.i("Server response", server_response );
//		} else {
//			Log.i("Server response", "Failed to get server response" );
//		}
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

//		HttpClient client = new DefaultHttpClient();
//
//		HttpGet request = new HttpGet(s);
//		ResponseHandler<String> handler = new BasicResponseHandler();
//		String response = "";
//		try {
//			response = client.execute(request, handler);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}


		try{

			HttpURLConnection urlConnection = null;
			URL url = new URL(s);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setReadTimeout(10000 /* milliseconds */ );
			urlConnection.setConnectTimeout(15000 /* milliseconds */ );
			urlConnection.setDoOutput(true);
			urlConnection.connect();

			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuilder sb = new StringBuilder();

			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			br.close();

			server_response = sb.toString();
			System.out.println("JSON: " + server_response);
		}catch (Exception e){

		}



		return server_response;
    }












	public static int TYPE_WIFI = 1;
	public static int TYPE_MOBILE = 2;
	public static int TYPE_NOT_CONNECTED = 0;


	public static int getConnectivityStatus(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if (null != activeNetwork) {
			if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
				return TYPE_WIFI;

			if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
				return TYPE_MOBILE;
		}
		return TYPE_NOT_CONNECTED;
	}

	public static String getConnectivityStatusString(Context context) {
		int conn = WSConnector.getConnectivityStatus(context);
		String status = null;
		if (conn == WSConnector.TYPE_WIFI) {
			status = "2";
		} else if (conn == WSConnector.TYPE_MOBILE) {
			status = "1";
		} else if (conn == WSConnector.TYPE_NOT_CONNECTED) {
			status = "0";
		}
		return status;
	}


}