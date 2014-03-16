package importer;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Joseph Karras
 */
public class POIHandler extends DefaultHandler {

	private List<POI> pois = null;
	private POI poi = null;
	public List<POI> getPOIList() {
		return pois;
	}

	public POIHandler() { }

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase("node")) {
			String lat = attributes.getValue("lat");
			String lon = attributes.getValue("lon");
			if(pois == null)
				pois = new ArrayList<POI>();
			this.poi = new POI();
			this.poi.setLat(lat);
			this.poi.setLon(lon);
		}
		else if (qName.equalsIgnoreCase("tag")) {
			String tagValue = attributes.getValue("v");
			String tagKey = attributes.getValue("k");
			switch (tagKey) {
				case "amenity":
					poi.setCategory(tagValue);
					break;
				case "name":
					poi.setName(tagValue);
					break;
				default:
					break;
			}
		}
	}

	public int total() {
		if(this.pois != null)
			return this.pois.size();
		return 0;
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equalsIgnoreCase("node")) {
			pois.add(poi);
			poi = null;
		}
	}
}