package br.com.bengalamobile;

import static java.lang.String.valueOf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class GravarPin {

	private static final String URL = "http://192.168.1.4/insert-token-location.php";

	public static boolean addToken(long iMEI) {
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(URL);

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			nameValuePairs.add(new BasicNameValuePair("token", valueOf(iMEI)));

			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity entity = httpResponse.getEntity();
			
			InputStream is = entity.getContent();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while((line = reader.readLine()) != null) {
				sb.append(line);
			}
			is.close();
			
			String result = sb.toString().replaceAll("\b\t\n", "");
			Integer affectedRows = Integer.parseInt(result);
			
			if (affectedRows >= 0) {
				return true;
			} else {
				return false;
			}
		} catch (UnsupportedEncodingException uee) {
			uee.printStackTrace();
			return false;
		} catch (ClientProtocolException cpe) {
			cpe.printStackTrace();
			return false;
		} catch (IllegalStateException ise) {
			ise.printStackTrace();
			return false;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean addTokenWithCoordinates(List<NameValuePair> basicPairs) {
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(URL);

			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					basicPairs);

			httpPost.setEntity(formEntity);

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity entity = httpResponse.getEntity();
			
			InputStream is = entity.getContent();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while((line = reader.readLine()) != null) {
				sb.append(line);
			}
			is.close();
			
			String result = sb.toString().replaceAll("\b\t\n", "");
			Integer affectedRows = Integer.parseInt(result);
			
			if (affectedRows >= 0) {
				return true;
			} else {
				return false;
			}
		} catch (UnsupportedEncodingException uee) {
			uee.printStackTrace();
		} catch (ClientProtocolException cpe) {
			cpe.printStackTrace();
		} catch (IllegalStateException ise) {
			ise.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return false;
	}

}
