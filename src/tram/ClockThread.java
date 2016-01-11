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
    public ArrayList<DrawThread> threads=new ArrayList<DrawThread>();
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
				            	break;
							}
							if (schedule.schedules.get(i).get(j).isAfter(thisSec)){
								break;
							}
						}
					}
					for(DrawThread t:threads){
						if(!t.isAlive()){
							threads.remove(t);
						}
					}
				}
			}
		}catch(Exception e){
			  System.out.print(e);
		}
	}
	public int getIntensity(){
		int hours=thisSec.getHour();
        int intensity = 1; //0 - wczesnie rano i pozno w nocy; 2 - godziny szczytu; 1 - pozostale

        if((hours < 6 ) || (hours >= 19))
            intensity = 0;

        else if((hours >=6 && hours < 9 ) || (hours >= 14 && hours < 19))
            intensity = 2;
        return intensity;
	}
	
}
