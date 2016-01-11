package tram;

import java.time.LocalTime;
import java.util.ArrayList;

import timeTableParser.Schedule;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.openstreetmap.gui.jmapviewer.JMapViewer;

public class ClockThread extends Thread{
	public static LocalTime thisSec=LocalTime.now();//tutaj mo¿na ustawiæ sobie godzinê np NOON zamiast now
	public static JPanel panel;
    public static ArrayList<DrawThread> threads=new ArrayList<DrawThread>();
    JLabel clockLabelVal=new JLabel(String.format("%s",thisSec));
    Schedule schedule;
    JMapViewer map;
    
    ClockThread(String path,JMapViewer map){
    	this.schedule=new Schedule(path);
    	this.map=map;
    }
	public void run(){
		thisSec=thisSec.minusNanos(thisSec.getNano());
		try{
			for(;;){
				 thisSec=thisSec.plusSeconds(1);
				 panel.remove(clockLabelVal);
				 clockLabelVal=new JLabel(String.format("%s",thisSec));
				 panel.add(clockLabelVal);
				 panel.updateUI();
				Thread.sleep(1000);
				if (thisSec.getSecond()==0){
					for(String i : schedule.schedules.keySet()){
						for(int j=0;j<schedule.schedules.get(i).size();j++){
							if (schedule.schedules.get(i).get(j).equals(thisSec)){

								DrawThread t=new DrawThread(ParserXML.relations.get(i),map);
				            	t.start();
				            	threads.add(t);
				            	break;
							}
							if (schedule.schedules.get(i).get(j).isAfter(thisSec)){
								break;
							}
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void stopThreads(){
        for(int i=0;i<threads.size();i++){
       	 
        	if(threads.get(i).isAlive())
        		{threads.get(i).stop=1;
        		if (threads.get(i).getState()==Thread.State.TIMED_WAITING)threads.get(i).interrupt(); }
        	             	
        }
	}
	public void removeEmptythreads(){
		ArrayList<DrawThread> emptylist=new ArrayList<DrawThread>();
		  for(DrawThread i : threads){
			  if(!i.isAlive())emptylist.add(i);
		  }
		  for(DrawThread i : emptylist){
			  threads.remove(i);
		  }
	}
	
}
