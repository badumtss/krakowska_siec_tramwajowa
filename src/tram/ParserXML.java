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
	public static HashMap<String, Relation> relations= new HashMap<String, Relation>();
	public boolean nodeflag,wayflag,rel;
	
	ParserXML(String file){
		nodeflag=false;
		wayflag=false;
		rel=false;
		readNodes(file);
		readWays(file);
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
					if (qName.equalsIgnoreCase("tag")&&nodeflag==true&&
						attributes.getValue(0).equalsIgnoreCase("name")){
						nodes.get(nid).name=attributes.getValue(1);
						nodes.get(nid).stop=true;
						nodeflag=false;
					}
					else nodeflag=false;			
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
					if (qName.equalsIgnoreCase("way")) {
						Way pway=new Way();
						pid=attributes.getValue(0);
						ways.put(pid,pway);
						wayflag=true;
					}	
					if (qName.equalsIgnoreCase("nd")&&wayflag==true) {
						ways.get(pid).nodes.add(attributes.getValue(0));
						ways.get(pid).it++;
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
				String rid;
				public void startElement(String uri, String localName,String qName, 
						Attributes attributes) throws SAXException {
					if (qName.equalsIgnoreCase("relation")){
						Relation prel=new Relation();
						rid=attributes.getValue(0);
						relations.put(rid,prel);
						rel=true;
					}
					if (qName.equalsIgnoreCase("member")&&
						attributes.getValue(0).equalsIgnoreCase("way")&&
						attributes.getValue(2).equalsIgnoreCase("")){
						relations.get(rid).addWay(attributes.getValue(1));
					}
					if (qName.equalsIgnoreCase("tag")&&
						rel==true&&
						attributes.getValue(0).equalsIgnoreCase("ref")){
						relations.get(rid).nr=attributes.getValue(1);
					}
					if (qName.equalsIgnoreCase("tag")&&
							rel==true&&
							attributes.getValue(0).equalsIgnoreCase("from")){
							relations.get(rid).from=attributes.getValue(1);
					}
					if (qName.equalsIgnoreCase("tag")&&
							rel==true&&
							attributes.getValue(0).equalsIgnoreCase("to")){
							relations.get(rid).to=attributes.getValue(1);
							rel=false;
					}
						
				}
			};
			saxParser.parse(file, handler);
		} catch (Exception e) {
			       e.printStackTrace();
			     }
	}
	
	
}
