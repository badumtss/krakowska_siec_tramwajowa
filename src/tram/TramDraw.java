package tram;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.LinkedList; 

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.Style;
//klasa reprezentujÄ…ca poszczegĂłlne markery dla relacjii
public class TramDraw {
	public String currDraw; //id obecnie rysowanego noda
	public Integer currDrawIt;	//iterator po liĹ›cie nodďż˝w
	public MapMarkerDot marker; //alkualnie narysowany znaczek
	public boolean isFree;
	public Style style;
	public Color color=Color.yellow;
	public String lastStop;
	
	public TramDraw(LinkedList<String> nodes, String nr){//konstruktor
		currDraw=nodes.getFirst();	
		currDrawIt=0;
		isFree=true;
		style=new Style();
        style.setColor(Color.black);
        style.setBackColor(color);
        style.setStroke(new BasicStroke(0));
        marker=new MapMarkerDot(null,nr,ParserXML.nodes.get(currDraw).coord,style);
        lastStop=null;
	}
	public void resetDraw(LinkedList<String> nodes){// reset trasy
		currDraw=nodes.getFirst();
		marker.setCoordinate(ParserXML.nodes.get(currDraw).coord);
		currDrawIt=0;
		isFree=true;
	}
	public Coordinate[] nextDraw(LinkedList<String> nodes){// zmiana noda przy rysowaniu na kolejny
		Coordinate[] i=new Coordinate[2];
		i[0]=ParserXML.nodes.get(currDraw).coord;
		currDrawIt++;
		currDraw=nodes.get(currDrawIt);
		i[1]=ParserXML.nodes.get(currDraw).coord;
		marker.setCoordinate(ParserXML.nodes.get(currDraw).coord);
		if (currDrawIt > (nodes.size()/5)) style.setBackColor(Color.orange);
		if (currDrawIt > (2*nodes.size()/5)) style.setBackColor(Color.red);
		if (currDrawIt > (3*nodes.size()/5)) style.setBackColor(Color.orange);
		if (currDrawIt > (4*nodes.size()/5)) style.setBackColor(color);
		return i;
	}
}
