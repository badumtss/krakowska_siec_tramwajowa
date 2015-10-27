package tram;

//klasa przechowuj¹ca wspó³rzêdne nodów oraz konwertuj¹ca je do wspó³rzêdnych na ekranie
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
		return (int) ((Longitude - DrawWays.minLong) * DrawWays.xScale);
	}

	public int latToY(Double Latitude) {
		return (int) (-(Latitude - DrawWays.minLat) * DrawWays.yScale);
	}
}
