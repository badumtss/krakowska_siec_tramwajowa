package tram;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.*;


public class ParserXML {

	public static HashMap<String, Node> nodes= new HashMap<String, Node>();
	public static HashMap<String, Way> ways= new HashMap<String, Way>();
	
	ParserXML(String file){
		readNodes(file);
		readWays(file);
	}

	public static void readNodes(String file){
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {
			public void startDocument(){};
			
			public void startElement(String uri, String localName,String qName, 
	                Attributes attributes) throws SAXException {
				if (qName.equalsIgnoreCase("node")){
					Node pnod = new Node();
					pnod.coord=new Coordinate(Double.parseDouble(attributes.getValue("lat")),Double.parseDouble(attributes.getValue("lon")));
					nodes.put(attributes.getValue("id"),pnod);
					}
				}
			};
			saxParser.parse(file, handler);
		} catch (Exception e) {
		       e.printStackTrace();
		     }
	}
	
	public static void readWays(String file){
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {
			public void startDocument(){};
			String pid="0";
			public void startElement(String uri, String localName,String qName, 
	                Attributes attributes) throws SAXException {
				if (qName.equalsIgnoreCase("way")) {
					Way pway=new Way();
					pid=attributes.getValue(0);
					ways.put(pid,pway);
					
				}
				if (qName.equalsIgnoreCase("nd")) {
					ways.get(pid).nodes.add(attributes.getValue(0));
					ways.get(pid).it++;
				}
			}
			};
			saxParser.parse(file, handler);
		} catch (Exception e) {
		       e.printStackTrace();
		     }
	}
	
	
}
