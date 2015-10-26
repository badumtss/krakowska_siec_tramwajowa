package tram;


public class Coordinate {
	int x,y;
	double lat,lon;
	
	public Coordinate(double lat, double lon) {
		this.lat = lat;
		this.lon = lon;
		this.x = lonToX(lon);
		this.y = latToY(lat);
	}
	public int lonToX(Double Longitude) {
		return (int) ((Longitude - Drawing.minLong) * Drawing.xScale);
	}

	public int latToY(Double Latitude) {
		return (int) (-(Latitude - Drawing.minLat) * Drawing.yScale);
	}
}
