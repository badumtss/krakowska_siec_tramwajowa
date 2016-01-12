package tram;

import java.util.LinkedList;

import org.openstreetmap.gui.jmapviewer.Coordinate;
//klasa pojedynczej drogi
public class Way {
	//lista nazw(id) punktów zawartych w drodze
	public LinkedList<String> nodesprint = new LinkedList<String>();
	public LinkedList<String> nodes = new LinkedList<String>();
	
	public void smooth(){
		nodesprint=(LinkedList<String>) nodes.clone();
		LinkedList<String> newnodes=new LinkedList<String>();
		newnodes=(LinkedList<String>)nodes.clone();
		int przes=0;
		for(int i=0;i<nodes.size()-1;i++){
			
			Coordinate[] c=new Coordinate[2];
			c[0] =ParserXML.nodes.get(nodes.get(i)).coord;
			c[1]=ParserXML.nodes.get(nodes.get(i+1)).coord;
			double dist=Speed.distance(c);
			if (dist>5){
				for(int j=0;j<(int)dist;j++){
					Node n = new Node();
					n.coord=new Coordinate(c[0].getLat()+(j*(c[1].getLat()-c[0].getLat())/(int)dist),c[0].getLon()+(j*(c[1].getLon()-c[0].getLon())/(int)dist));
					String name=nodes.get(i)+j;
					ParserXML.nodes.put(name, n);
					//System.out.println(i+" "+przes+" "+newnodes.size()+" "+nodes.size());
					przes++;
					newnodes.add(i+przes, name);
					
					
				}
				
			}
		}
		nodes=newnodes;
	}
}
