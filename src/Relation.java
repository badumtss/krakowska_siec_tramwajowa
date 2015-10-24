import java.util.HashMap;
import java.util.LinkedList;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Relation {
	NodeList rList;
	
	public static HashMap<String, LinkedList<LinkedList<Long>>> relacja = new HashMap<String, LinkedList<LinkedList<Long>>>();
	
		public Relation(){
			try {
				ReadXMLFile.doc.getDocumentElement().normalize();
				rList = ReadXMLFile.doc.getElementsByTagName("relation");
				for (int j = 0; j < rList.getLength(); j++) {
					Node node = rList.item(j);
					NamedNodeMap nodeAttr = node.getAttributes();
					NodeList childrens = node.getChildNodes();
					String id = nodeAttr.getNamedItem("id").getNodeValue();
					//System.out.print(id+ "  ");
					LinkedList <LinkedList<Long>> lista = new LinkedList<LinkedList<Long>>();
					for (int i = 0; i < childrens.getLength(); i++) {
						
						if (i % 4 != 0 && childrens.item(i).getNodeName() == "member") {
							String a = childrens.item(i).getAttributes().getNamedItem("type").getNodeValue();
							if(a.equals("way"))
							{	
								LinkedList<Long> listaPunktow = Way.polaczenia.get(childrens.item(i).getAttributes().getNamedItem("ref").getNodeValue());
								lista.add(listaPunktow);
							}
						}
						if (i % 3 != 0 && childrens.item(i).getNodeName() == "tag" && childrens.item(i).getAttributes().getNamedItem("k").getNodeValue().equals("ref"))
						{
							id = childrens.item(i).getAttributes().getNamedItem("v").getNodeValue();
							System.out.println(id);
						}
					}
						
					relacja.put(id, lista);
				}
			}
				catch (Exception e) {
					e.printStackTrace();
		}
		}
}

