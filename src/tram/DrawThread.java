package tram;
// obsługa wielowątkowa tramwajów
import org.openstreetmap.gui.jmapviewer.JMapViewer;

public class DrawThread extends Thread {
	JMapViewer map;
	Relation rel;
	DrawThread(Relation rels, JMapViewer maps){
		rel=rels;
		map=maps;
	}
	public void run(){
		drawRel(rel);
	}
	// opóźnianie i pętla rysująca
    public void drawRel(Relation rel){
    	int nr=rel.takeFree();
    	try{for(;drawTram(rel,nr)==1;){    		
  		    Thread.sleep(100);}  
  		  }catch(Exception e){
  			  System.out.print(e);
  		  }
  	}
    	  
//rysowanie linii id�c list� nod�w po kolei
    public int drawTram(Relation rel,int tramNr){
    	if(rel.nodes.getLast().equals(rel.drawings.get(tramNr).currDraw)){
    		map.removeMapMarker(rel.drawings.get(tramNr).marker);
    		rel.resetDraw(tramNr);
    		return 0;
    	}else{
	    	if(map.getMapMarkerList().contains(rel.drawings.get(tramNr).marker)){
	    		map.removeMapMarker(rel.drawings.get(tramNr).marker);
	    		rel.nextDraw(tramNr);
	    	}
	    	map.addMapMarker(rel.drawings.get(tramNr).marker);
	    	return 1;
    	}   	
    }
   
}
