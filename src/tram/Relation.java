package tram;

import java.util.LinkedList;

public class Relation {
	
	public String nr,from,to;
	public LinkedList<String> nodes;
	Relation(){
		nodes=new LinkedList<String>();
	}
	public void addWay(String way){
		nodes.addAll(ParserXML.ways.get(way).nodes);
	}
}
