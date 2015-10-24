import java.util.LinkedList;


public class Relacja {
	String nr_linii, from, to;
	//LinkedList <Point> p;
	LinkedList <LinkedList <Long>> w;
	//LinkedList <Swiatla> swiatla;
	public Relacja(String nr_linii){
		this.nr_linii=nr_linii;
		this.from="from";
		this.to="to";
	//	p = Relation.relacja.get(nr_linii);
		w = Relation.relacja.get(nr_linii);
	}
	
}
