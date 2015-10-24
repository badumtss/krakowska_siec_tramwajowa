import java.io.File;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

public class Points {
	public static HashMap<String, Cordinate> list = new HashMap<String, Cordinate>();
	
	public Points(){
		NodeList nList; // zawiera id, lat, lon. Dostêp po nList.item(i).getAttributes().getNamedItem("id").getNodeValue()
		try {
			File fXmlFile = new File("src/tram_lines.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			ReadXMLFile.doc = dBuilder.parse(fXmlFile);
			ReadXMLFile.doc.getDocumentElement().normalize();
			nList = ReadXMLFile.doc.getElementsByTagName("node");
			for(int i=0; i<nList.getLength(); i++) {
				NamedNodeMap nodeAttr = nList.item(i).getAttributes();
				double lat = Double.parseDouble(nodeAttr.getNamedItem("lat").getNodeValue());
				double lon = Double.parseDouble(nodeAttr.getNamedItem("lon").getNodeValue());
				String id = nodeAttr.getNamedItem("id").getNodeValue();
				Points.list.put(id,new Cordinate(lat,lon));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
