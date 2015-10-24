import java.util.HashMap;
import java.util.LinkedList;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Way {
	NodeList wList;
	public static HashMap<String, LinkedList<Long>> polaczenia = new HashMap<String, LinkedList<Long>>();

	public Way() {
		try {
			ReadXMLFile.doc.getDocumentElement().normalize();
			wList = ReadXMLFile.doc.getElementsByTagName("way");
			for (int j = 0; j < wList.getLength(); j++) {
				Node node = wList.item(j);
				NamedNodeMap nodeAttr = node.getAttributes();
				NodeList childrens = node.getChildNodes();
				String id = nodeAttr.getNamedItem("id").getNodeValue();
				LinkedList<Long> k = new LinkedList<Long>();

				for (int i = 0; i < childrens.getLength(); i++) {
					if (i % 2 != 0 && childrens.item(i).getNodeName() == "nd") {
						k.add(Long.parseLong(childrens.item(i).getAttributes().getNamedItem("ref")
								.getNodeValue()));
					}
				}

				polaczenia.put(id, k);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
