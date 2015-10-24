
public class Cordinate {
	int x,y;
	double lat,lon;
	
	public Cordinate(double lat, double lon) {
		this.lat = lat;
		this.lon = lon;
		this.x = lonToX(lon);
		this.y = latToY(lat);
	}
	public int lonToX(Double Longitude) {
		return (int) ((Longitude - ReadXMLFile.minLong) * ReadXMLFile.xScale);
	}

	public int latToY(Double Latitude) {
		return (int) (-(Latitude - ReadXMLFile.minLat) * ReadXMLFile.yScale);
	}
}
