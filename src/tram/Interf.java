package tram;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;


public class Interf {
	public static void main(String argv[]) {
		new ParserXML("src/tram/tram.xml");
        Graphic g=new Graphic();//otworzenie okna i wyœwietlenie
        drawWithDelay(g);
	}
	
	//testowe rysowanie przyk³adowej linii z opóŸnieniem
	public static void drawWithDelay(Graphic g){
		  ActionListener taskPerformer = new ActionListener() {
		      public void actionPerformed(ActionEvent evt) {
		          g.drawTram(ParserXML.relations.get(ParserXML.relations.keySet().toArray()[0]));
		      }
		  };
		  new Timer(100, taskPerformer).start();
	}
}
