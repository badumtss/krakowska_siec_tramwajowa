package tram;

import java.util.LinkedList; 

import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
//klasa reprezentująca poszczególne markery dla relacjii
public class TramDraw {
	public String currDraw; //id obecnie rysowanego noda
	public Integer currDrawIt;	//iterator po liście nod�w
	public MapMarkerDot marker; //alkualnie narysowany znaczek
	public boolean isFree;
	
	public TramDraw(LinkedList<String> nodes, String nr){//konstruktor
		currDraw=nodes.getFirst();
		marker=new MapMarkerDot(nr,ParserXML.nodes.get(currDraw).coord);
		currDrawIt=0;
		isFree=true;
	}
	public void resetDraw(LinkedList<String> nodes){// reset trasy
		currDraw=nodes.getFirst();
		marker.setCoordinate(ParserXML.nodes.get(currDraw).coord);
		currDrawIt=0;
		isFree=true;
	}
	public void nextDraw(LinkedList<String> nodes){// zmiana noda przy rysowaniu na kolejny
		currDrawIt++;
		currDraw=nodes.get(currDrawIt);
		marker.setCoordinate(ParserXML.nodes.get(currDraw).coord);
		
	}
}
