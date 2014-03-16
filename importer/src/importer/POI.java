package importer;

import util.Geolocation;

/**
 * @author Joseph Karras
 */
public class POI {

	private String name = "";
	private String category = "";
	private String lat = "";
	private String lon ="";
	private String address = "";
	private String zipCode = "";
	private String city = "";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String cvsRow(boolean mustHasName) {
		if(mustHasName) {
			if(!this.hasName()) return null;
		}
		this.lanlonToAddress(this.getLat() , this.getLon());
		String row = new String(this.getLat()+","+this.getLat()+","+this.getName()+","+this.getAddress()+","+this.getZipCode()+","+this.getCity());
		return row;
	}

	public boolean hasName() {
		return this.name.length() >= 1;
	}

	private void lanlonToAddress(String lan, String lon) {
		String[] info = Geolocation.googleReverseGeocode(lan, lon);
		if(info != null ) {
			//TODO convert to greek char
			this.setAddress(info[0]);
			this.setZipCode(info[1].replaceAll("[^\\d]", ""));
			this.setCity("Θεσσαλονίκη");
		}
	}
}
