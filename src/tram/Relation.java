package tram;

import java.util.LinkedList;

public class Relation {
	
	public String nr,from,to;//nr linii, sk�d, dok�d
	public LinkedList<String> nodes;//lista nazw(id) nod�w zawartych w relacji
	Relation(){
		nodes=new LinkedList<String>();
	}
	public void addWay(String way){
		nodes.addAll(ParserXML.ways.get(way).nodes); //dodanie drogi w postaci listy nod�w
	}
}
