package tram;
//klasa pojedynczego puntu na mapie
import org.openstreetmap.gui.jmapviewer.Coordinate;
public class Node {
	Coordinate coord; //wspolrz�dne
	boolean stop; //czy przystanek
	String name; //nazwa przystanku
	Node(){
		stop=false;
		name=null;
	}
}
