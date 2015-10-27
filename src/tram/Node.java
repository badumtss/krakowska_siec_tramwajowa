package tram;
//klasa pojedynczego puntu na mapie
public class Node {
	Coordinate coord; //wspolrzêdne
	boolean stop; //czy przystanek
	String name; //nazwa przystanku
	Node(){
		stop=false;
		name=null;
	}
}
