package tram;

import org.openstreetmap.gui.jmapviewer.Coordinate;

public class Speed {
	int speed=10;//m per s
	
	static int getDelay(double sp,Coordinate[] n1){
		double delay=Math.ceil((distance(n1)/(sp*0.28))*1000/Interf.acc);
		delay++;
		return (int)delay;
	}
	
	static double distance (Coordinate[] n) {//in meters
		final double R = 6371;//6378.137; // Radius of earth in KM
		double dLat = (n[0].getLat() - n[1].getLat()) * Math.PI / 180;
		double dLon = (n[0].getLon() - n[1].getLon()) * Math.PI / 180;
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(n[1].getLat() * Math.PI / 180) * Math.cos(n[0].getLat() * Math.PI / 180) *
		Math.sin(dLon / 2) * Math.sin(dLon / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double d = R * c;
		return d*1000;
	}
}
