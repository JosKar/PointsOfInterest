package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class Geolocation {

	public static String[] googleReverseGeocode(String lan, String lon) {
		String formatted_address = null;
		String address = "";
		try {
			URL url = new URL("http://maps.googleapis.com/maps/api/geocode/json?latlng=" + lan + "," + lon + "&sensor=false");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

			String output;
			String out="";
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				out+=output;
			}
			
			// Converting Json formatted string into JSON object
			JSONObject json = (JSONObject) JSONSerializer.toJSON(out);
			JSONArray results=json.getJSONArray("results");
			JSONObject rec = results.getJSONObject(0);
			JSONArray address_components=rec.getJSONArray("address_components");
			for(int i=0;i<address_components.size();i++){
			JSONObject rec1 = address_components.getJSONObject(i);
			JSONArray types=rec1.getJSONArray("types");
			String comp=types.getString(0);

			if(comp.equals("locality")){
				System.out.println("city ————-"+rec1.getString("long_name"));
				address += rec1.getString("long_name");
			}
			else if(comp.equals("country")){
				System.out.println("country ———-"+rec1.getString("long_name"));
			}
			}
			formatted_address = rec.getString("formatted_address");
			System.out.println("formatted_address————–"+formatted_address);
			conn.disconnect();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] parts = formatted_address.split(",");
		return parts;
	}
}