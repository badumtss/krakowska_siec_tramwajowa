package tram;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.*;
import org.openstreetmap.gui.jmapviewer.Coordinate;
//klasa odczytuj�ca dane z pliku OSM
public class ParserXML {

	public static HashMap<String, Node> nodes= new HashMap<String, Node>(); //statyczna hashmapa z odczytanymi punktami
	public static HashMap<String, Way> ways= new HashMap<String, Way>(); //statyczna hashmapa z odczytanymi drogami
	public static HashMap<String, Relation> relations= new HashMap<String, Relation>(); //statyczna hashmapa z odczytanymi relacjami
	public boolean nodeflag,wayflag,rel,relway;

	ParserXML(String file){
		nodeflag=false;
		wayflag=false;
		rel=false;
		relway=false;
		readNodes(file);
		readWays(file);
		smoothWays();
		readRelations(file);
	}

	public void readNodes(String file){
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {
				public void startDocument(){};
				String nid="0";
				public void startElement(String uri, String localName,String qName,
										 Attributes attributes) throws SAXException {
					if (qName.equalsIgnoreCase("node")){
						Node pnod = new Node();
						pnod.coord=new Coordinate(Double.parseDouble(attributes.getValue("lat")),Double.parseDouble(attributes.getValue("lon")));
						nid=attributes.getValue("id");
						nodes.put(nid,pnod);
						nodeflag=true;
					}
					if (qName.equalsIgnoreCase("tag")&&//nodeflag==true&&
							attributes.getValue("k").equalsIgnoreCase("name")){
						nodes.get(nid).name=attributes.getValue("v");
						nodes.get(nid).stop=true;
						nodeflag=false;
					}
					if (!qName.equalsIgnoreCase("node")||!qName.equalsIgnoreCase("tag"))
						nodeflag=false;
				}
			};
			saxParser.parse(file, handler);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void readWays(String file){
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {
				public void startDocument(){};
				String pid="0";
				public void startElement(String uri, String localName,String qName,
										 Attributes attributes) throws SAXException {
					if (qName.equalsIgnoreCase("way")&&
							!attributes.getValue(0).equalsIgnoreCase("-1061451")&&
							!attributes.getValue(0).equalsIgnoreCase("-1061868")) {
						Way pway=new Way();
						pid=attributes.getValue(0);
						ways.put(pid,pway);
						wayflag=true;
					}
					if (qName.equalsIgnoreCase("nd")&&wayflag==true) {
						ways.get(pid).nodes.add(attributes.getValue(0));
					}
					if ((!qName.equalsIgnoreCase("nd"))&&(!qName.equalsIgnoreCase("way"))){
						wayflag=false;
					}
				}
			};
			saxParser.parse(file, handler);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void readRelations(String file){
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {
				public void startDocument(){};
				Relation prel;
				public void startElement(String uri, String localName,String qName,
										 Attributes attributes) throws SAXException {
					if (qName.equalsIgnoreCase("relation")){
						prel=new Relation();
						rel=true;
					}
					if (qName.equalsIgnoreCase("member")&&
							attributes.getValue(0).equalsIgnoreCase("way")&&
							(attributes.getValue(2).equalsIgnoreCase("")||
									attributes.getValue(2).equalsIgnoreCase("forward"))&&
							!attributes.getValue(1).equalsIgnoreCase("-1061451")&&
							!attributes.getValue(1).equalsIgnoreCase("-1061868")&&relway==false){
						prel.addWay(attributes.getValue(1));
					}
					if (qName.equalsIgnoreCase("member")&&
							attributes.getValue(0).equalsIgnoreCase("node")&&
							attributes.getValue(2).equalsIgnoreCase("stop_exit_only")){
						relway=true;
					}
					if (qName.equalsIgnoreCase("tag")&&
							rel==true&&
							attributes.getValue(0).equalsIgnoreCase("ref")){
						prel.nr=attributes.getValue(1);
					}
					if (qName.equalsIgnoreCase("tag")&&
							rel==true&&
							attributes.getValue(0).equalsIgnoreCase("from")){
						prel.from=attributes.getValue(1);
					}
					if (qName.equalsIgnoreCase("tag")&&
							rel==true&&
							attributes.getValue(0).equalsIgnoreCase("to")){
						prel.to=attributes.getValue(1);
						prel.addDraw();
						if(relations.containsKey(prel.nr)){
							relations.put("-"+prel.nr, prel);
						}else
							relations.put(prel.nr, prel);
						rel=false;
						relway=false;
					}

				}
			};
			saxParser.parse(file, handler);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Relation getRelByInt(int i){
		return relations.get(relations.keySet().toArray()[i]);
	}
	public static Node getNodByInt(int i){
		return nodes.get(nodes.keySet().toArray()[i]);
	}
	public static Way getWayByInt(int i){
		return ways.get(ways.keySet().toArray()[i]);
	}
	public static void smoothWays(){
		for(String i: ways.keySet()){
			ways.get(i).smooth();
		}
	}
}
