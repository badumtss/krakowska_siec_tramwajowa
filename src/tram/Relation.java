package tram;

import java.util.LinkedList;

import org.openstreetmap.gui.jmapviewer.MapMarkerDot;

public class Relation {
	
	public String nr,from,to;//nr linii, sk¹d, dok¹d
	public LinkedList<String> nodes;//lista nazw(id) nodów zawartych w relacji
	public String currDraw; //id obecnie rysowanego noda
	public int currDrawIt;	//iterator po liœcie nodów
	public MapMarkerDot marker; //alkualnie narysowany znaczek
	
	Relation(){
		nodes=new LinkedList<String>();
	}
	public void addWay(String way){
		nodes.addAll(ParserXML.ways.get(way).nodes); //dodanie drogi w postaci listy nodów
	}
	public void setDraw(){//ustawienie pocz¹tkowe (w konstruktorze nie da rady bo na pocz¹tku nie ma nodów)
		currDraw=nodes.getFirst();
		marker=new MapMarkerDot(nr,ParserXML.nodes.get(currDraw).coord);
		currDrawIt=0;
	}
	public void resetDraw(){// reset trasy
		currDraw=nodes.getFirst();
		marker.setCoordinate(ParserXML.nodes.get(currDraw).coord);
		currDrawIt=0;
	}
	public void nextDraw(){// zmiana noda przy rysowaniu na kolejny
		currDrawIt++;
		currDraw=nodes.get(currDrawIt);
		marker.setCoordinate(ParserXML.nodes.get(currDraw).coord);
		
	}
}
