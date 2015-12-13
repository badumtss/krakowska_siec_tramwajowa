package tram;

import java.util.LinkedList;

import org.openstreetmap.gui.jmapviewer.MapMarkerDot;

public class Relation {
	
	public String nr,from,to;//nr linii, sk�d, dok�d
	public LinkedList<String> nodes;//lista nazw(id) nod�w zawartych w relacji
	public String currDraw; //id obecnie rysowanego noda
	public int currDrawIt;	//iterator po li�cie nod�w
	public MapMarkerDot marker; //alkualnie narysowany znaczek
	
	Relation(){
		nodes=new LinkedList<String>();
	}
	public void addWay(String way){
		nodes.addAll(ParserXML.ways.get(way).nodes); //dodanie drogi w postaci listy nod�w
	}
	public void setDraw(){//ustawienie pocz�tkowe (w konstruktorze nie da rady bo na pocz�tku nie ma nod�w)
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
