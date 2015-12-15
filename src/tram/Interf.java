package tram;

public class Interf {
	static int speed=1000;
	public static void main(String argv[]) {
		new ParserXML("src/tram/tram.xml");
        Graphic g=new Graphic();//otworzenie okna i wy�wietlenie
        Thread c=new ClockThread();
        c.start();
	}
	

}
