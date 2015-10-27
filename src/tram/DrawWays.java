package tram;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;

//klasa s³u¿¹ca do rysowania dróg(ways)
public class DrawWays extends JFrame{
	public final static int MapWidth = 1350, MapHeight = 700;
	public static double maxLong = 20.1194, minLong = 19.8798, maxLat = 50.1032, minLat = 50.0109;
	public static double xScale = MapWidth / (maxLong - minLong), yScale = MapHeight/ (maxLat - minLat);
	
	public DrawWays() {
		setLayout(null);
		setSize(MapWidth, MapHeight);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	public void paint(Graphics g) {
		
		super.paint(g);
		Graphics g2d = (Graphics2D) g;
		for (String t : ParserXML.ways.keySet()) {
			Way way=ParserXML.ways.get(t);
			int x1 = 0, x2 = 0, y1 = 0, y2 = 0;
			for (int i = 0; i < way.nodes.size()-1; i++) {

						Coordinate p = ParserXML.nodes.get(way.nodes.get(i)).coord;
						Coordinate p2 = ParserXML.nodes.get(way.nodes.get(i+1)).coord;
						x1 = p.x;
						y1 = p.y;
						x2 = p2.x;
						y2 = p2.y;
						g2d.drawLine(x1 + 30, y1 + 730, x2 + 30, y2 + 730);
			}			
		}
	}
}
