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
        wayStyle.setStroke(new BasicStroke(1));
        map=mapp;
        Layer line=new Layer("line");
        paint(line);
        //paintrel(line,"-1054505");
        //paintway(line,"-1056692");
	}
	public void paint(Layer line){
		
		for (String t : ParserXML.ways.keySet()) {
			paintway(line,t);		
		}
	}
	public void paintway(Layer line,String id){
		Way way=ParserXML.ways.get(id);
		
		for (int i = 0; i < way.nodesprint.size()-1; i++) {

					Coordinate p = ParserXML.nodes.get(way.nodesprint.get(i)).coord;
					Coordinate p2 = ParserXML.nodes.get(way.nodesprint.get(i+1)).coord;
					List <Coordinate> list=new ArrayList <Coordinate>(Arrays.asList(p,p2,p2));
					map.addMapPolygon(new MapPolygonImpl(line,"",list,wayStyle));
		}			

	}
	public void paintrel(Layer line,String id){
		for (int i = 0; i < ParserXML.relations.get(id).nodes.size()-1; i++) {

			Coordinate p = ParserXML.nodes.get(ParserXML.relations.get(id).nodes.get(i)).coord;
			Coordinate p2 = ParserXML.nodes.get(ParserXML.relations.get(id).nodes.get(i+1)).coord;
			List <Coordinate> list=new ArrayList <Coordinate>(Arrays.asList(p,p2,p2));
			map.addMapPolygon(new MapPolygonImpl(line,"",list,wayStyle));
		}
	}
}
