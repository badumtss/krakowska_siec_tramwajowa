package tram;

//klasa przechowuj�ca wsp�rz�dne nod�w oraz konwertuj�ca je do wsp�rz�dnych na ekranie
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
