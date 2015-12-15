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
    	int delay=1;
    	int nr=rel.takeFree();
    	try{for(;delay!=0;){    		
  		    delay=drawTram(rel,nr);
    		Thread.sleep(delay);
    		}  
  		  }catch(Exception e){
  			  System.out.print(e);
  		  }
  	}
    	  
//rysowanie linii id�c list� nod�w po kolei
    public int drawTram(Relation rel,int tramNr){
    	int delay=1;
    	if(rel.nodes.getLast().equals(rel.drawings.get(tramNr).currDraw)){
    		map.removeMapMarker(rel.drawings.get(tramNr).marker);
    		rel.resetDraw(tramNr);
    		return 0;
    	}else{
	    	if(map.getMapMarkerList().contains(rel.drawings.get(tramNr).marker)){
	    		map.removeMapMarker(rel.drawings.get(tramNr).marker);
	    		delay=Speed.getDelay(rel.speed,rel.nextDraw(tramNr));
	    		
	    	}
	    	map.addMapMarker(rel.drawings.get(tramNr).marker);
	    	return delay;
    	}   	
    }
   
}
