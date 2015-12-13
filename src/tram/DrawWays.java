package tram;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import org.openstreetmap.gui.jmapviewer.*;


//klasa s³u¿¹ca do rysowania dróg(ways)
public class DrawWays {
	Style wayStyle;
	Color col=Color.black;//kolor linii
	JMapViewer map;
	public DrawWays(JMapViewer mapp) {
		wayStyle=new Style();
        wayStyle.setColor(col);
        wayStyle.setBackColor(col);
        wayStyle.setStroke(new BasicStroke(0));
        map=mapp;
        Layer line=new Layer("line");
        paint(line);
	}
	public void paint(Layer line){
		
		for (String t : ParserXML.ways.keySet()) {
			Way way=ParserXML.ways.get(t);
			
			for (int i = 0; i < way.nodes.size()-1; i++) {

						Coordinate p = ParserXML.nodes.get(way.nodes.get(i)).coord;
						Coordinate p2 = ParserXML.nodes.get(way.nodes.get(i+1)).coord;
						List <Coordinate> list=new ArrayList <Coordinate>(Arrays.asList(p,p2,p2));
						map.addMapPolygon(new MapPolygonImpl(line,"",list,wayStyle));
			}			
		}
	}
}
