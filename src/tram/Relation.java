package tram;


import java.util.LinkedList;

import org.openstreetmap.gui.jmapviewer.Coordinate;

public class Relation {
	
	public String nr,from,to;//nr linii, sk�d, dok�d
	public LinkedList<String> nodes;//lista nazw(id) nod�w zawartych w relacji
	public LinkedList<TramDraw> drawings;
	public int speed;
	
	Relation(){
		nodes=new LinkedList<String>();
		drawings= new LinkedList<TramDraw>();
		speed=Interf.speed;
	}
	public void addWay(String way){
		nodes.addAll(ParserXML.ways.get(way).nodes); //dodanie drogi w postaci listy nod�w
	}
	public void addDraw(){
		drawings.add(new TramDraw(nodes,nr));
	}
	public int takeFree(){
		int j=0;
		for(TramDraw i : drawings){
			if (i.isFree){
				i.isFree=false;
				return j;
			}
			j++;
		}
		addDraw();
		return drawings.size()-1;
	}
	public void resetDraw(int nr){
		drawings.get(nr).resetDraw(nodes);
	}
	public Coordinate[] nextDraw(int nr){
		return drawings.get(nr).nextDraw(nodes);
	}

}
