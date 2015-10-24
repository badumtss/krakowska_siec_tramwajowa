import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JFrame;

import org.w3c.dom.Document;

@SuppressWarnings("serial")
public class ReadXMLFile extends JFrame {
	public final static int MapWidth = 1350, MapHeight = 700;
	public static double maxLong = 20.1194, minLong = 19.8798, maxLat = 50.1032, minLat = 50.0109;
	public static double xScale = MapWidth / (maxLong - minLong), yScale = MapHeight/ (maxLat - minLat);
	Points p = new Points();
	Way w = new Way();
	Relation r = new Relation();
	
	Relacja relacja5 = new Relacja("5");
	
	public static Document doc;

	public ReadXMLFile() {
		setLayout(null);
		setSize(MapWidth, MapHeight);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}

	public static void main(String argv[]) {
		new ReadXMLFile();
	}

	public void paint(Graphics g) {
		
		super.paint(g);
		Graphics g2d = (Graphics2D) g;
		for (String t : Relation.relacja.keySet()) {
			Relacja rel = new Relacja(t);
			int x1 = 0, x2 = 0, y1 = 0, y2 = 0;
			for (int i = 0; i < rel.w.size(); i++) {
				for(int j=0; j<rel.w.get(i).size(); j++ )
				{
					if (j + 1 < rel.w.get(i).size())
					{
						Cordinate p = Points.list.get(rel.w.get(i).get(j).toString());
						Cordinate p2 = Points.list.get(rel.w.get(i).get(j+1).toString());
						x1 = p.x;
						y1 = p.y;
						x2 = p2.x;
						y2 = p2.y;
						g2d.drawLine(x1 + 30, y1 + 730, x2 + 30, y2 + 730);
					}
				}
		}
	}
	/*	super.paint(g);
		Graphics g2d = (Graphics2D) g;
		int x1 = 0, x2 = 0, y1 = 0, y2 = 0;
		for (String i : Way.polaczenia.keySet()) {
			LinkedList<Long> listaPunktow = Way.polaczenia.get(i);
			int listaPktowSize = listaPunktow.size();
			for (int j = 0; j < listaPktowSize; j++) {
				if (j + 1 < listaPktowSize) {
					Point p = Points.list.get(listaPunktow.get(j).toString());
					Point p2 = Points.list.get(listaPunktow.get(j + 1).toString());
					x1 = p.x;y1 = p.y;
					x2 = p2.x;y2 = p2.y;
					g2d.drawLine(x1 + 30, y1 + 30, x2 + 30, y2 + 30);
				}
			}
		}
		*/
	}
}
