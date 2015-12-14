package tram;


import java.util.LinkedList;

public class Relation {
	
	public String nr,from,to;//nr linii, sk�d, dok�d
	public LinkedList<String> nodes;//lista nazw(id) nod�w zawartych w relacji
	public LinkedList<TramDraw> drawings;
	
	Relation(){
		nodes=new LinkedList<String>();
		drawings= new LinkedList<TramDraw>();
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
	public void nextDraw(int nr){
		drawings.get(nr).nextDraw(nodes);
	}

}
