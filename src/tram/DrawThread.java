package tram;
import java.awt.Color;

import org.openstreetmap.gui.jmapviewer.Coordinate;
// obsługa wielowątkowa tramwajów
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;


public class DrawThread extends Thread {
	JMapViewer map;
	Relation rel;
	int delay;
	int stop;
	int speed;
	public static Coordinate center=new Coordinate(50.0620,19.9368);
	DrawThread(Relation rels, JMapViewer maps){
		rel=rels;
		map=maps;
		delay=1;
		stop=0;
		speed=Interf.speed;
	}
	public void run(){
		drawRel(rel);
	}
	// opóźnianie i pętla rysująca
    public void drawRel(Relation rel){
    	int nr=rel.takeFree();
    	for(;delay!=0;){
  		    delay=drawTram(rel,nr);
  		    try{
    		Thread.sleep(delay);
    		}catch(Exception e){
  			  //System.out.print(e);
  		  	}
    	}  
  		  
  	}
    	  
//rysowanie linii id�c list� nod�w po kolei
    public synchronized int drawTram(Relation rel,int tramNr){
    	int delay=1;
    /*	for(MapMarker i : map.getMapMarkerList()){
    		if (i.getCoordinate().equals(ParserXML.nodes.get(rel.nodes.get(rel.drawings.get(tramNr).currDrawIt+1)).coord)){
    			return 1000;
    		}
    	}*/
    	//if(checkCollision(map,rel.drawings.get(tramNr).marker,ParserXML.nodes.get(rel.nodes.get(rel.drawings.get(tramNr).currDrawIt+1)).coord))
    	//	return 1000;
    	if(rel.nodes.getLast().equals(rel.drawings.get(tramNr).currDraw)||stop==1){
    		
    		map.removeMapMarker(rel.drawings.get(tramNr).marker);
    		rel.resetDraw(tramNr);
    		return 0;
    	}else{
	    	if(map.getMapMarkerList().contains(rel.drawings.get(tramNr).marker)){
	    		map.removeMapMarker(rel.drawings.get(tramNr).marker);
	    		delay=Speed.getDelay(calculateSpeed(rel.drawings.get(tramNr).currDraw),rel.nextDraw(tramNr));
	    		if (ParserXML.nodes.get(rel.drawings.get(tramNr).currDraw).stop){
	    			if (!ParserXML.nodes.get(rel.drawings.get(tramNr).currDraw).name.equalsIgnoreCase(rel.drawings.get(tramNr).lastStop))	    				
	    			delay+=calculateStopDelay(rel.drawings.get(tramNr));
	    		}
	    		
	    	}
	    	map.addMapMarker(rel.drawings.get(tramNr).marker);
	    	return delay;
    	}   	
    }
	public int getIntensity(){
		int hours=ClockThread.thisSec.getHour();
        int intensity = 1; //0 - wczesnie rano i pozno w nocy; 2 - godziny szczytu; 1 - pozostale

        if((hours < 6 ) || (hours >= 19))
            intensity = 0;

        else if((hours >=6 && hours < 9 ) || (hours >= 14 && hours < 19))
            intensity = 2;
        return intensity;
	}
	
   double calculateSpeed(String node){
	   Coordinate[] co={ParserXML.nodes.get(node).coord,center};
	   double dist=Speed.distance(co);
	   
	   return speed-getIntensity()*(5/dist);
   }
   int calculateStopDelay(TramDraw td){
	   Coordinate[] co={ParserXML.nodes.get(td.currDraw).coord,center};
	   double dist=Speed.distance(co);
	   int delay=8;
		if (td.currDrawIt > (rel.nodes.size()/5)) delay=10;
		if (td.currDrawIt > (2*rel.nodes.size()/5)) delay=12;
		if (td.currDrawIt > (3*rel.nodes.size()/5)) delay=10;
		if (td.currDrawIt > (4*rel.nodes.size()/5)) delay=8;
	   return (int)(delay+getIntensity()*(2/dist))*1000/Interf.acc;
   }
   public synchronized boolean  checkCollision(JMapViewer map, MapMarker marker, Coordinate c){
	   for(int i=0;i< map.getMapMarkerList().size();i++){
		   if(map.getMapMarkerList().get(i)!=marker){
		   Coordinate c1=map.getMapMarkerList().get(i).getCoordinate();
		   if (c1.equals(c))
   			return true;
		   }
	   }
	   return false;
   }
}
