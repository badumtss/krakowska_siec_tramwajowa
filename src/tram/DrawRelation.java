package tram;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;

public class DrawRelation extends JFrame{
	public final static int MapWidth = 1350, MapHeight = 700;
	public static double maxLong = 20.1263, minLong = 19.8739, maxLat = 50.1043, minLat = 50.0117;
	public static double xScale = MapWidth / (maxLong - minLong), yScale = MapHeight/ (maxLat - minLat);
	public String number;
	
	public DrawRelation(String nr) {
		number=nr;
		setLayout(null);
		setSize(MapWidth, MapHeight);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
public void paint(Graphics g) {
		
		super.paint(g);
		Graphics g2d = (Graphics2D) g;
		for (String t : ParserXML.relations.keySet()) {
			if(ParserXML.relations.get(t).nr.equalsIgnoreCase(number)){
				Relation rel=ParserXML.relations.get(t);
				int x1 = 0, x2 = 0, y1 = 0, y2 = 0;
				for (int i = 0; i < rel.nodes.size()-1; i++) {
	
							Coordinate p = ParserXML.nodes.get(rel.nodes.get(i)).coord;
							Coordinate p2 = ParserXML.nodes.get(rel.nodes.get(i+1)).coord;
							x1 = p.x;
							y1 = p.y;
							x2 = p2.x;
							y2 = p2.y;
							g2d.drawLine(x1 + 30, y1 + 730, x2 + 30, y2 + 730);
				}
			}
		}	
	}
}
