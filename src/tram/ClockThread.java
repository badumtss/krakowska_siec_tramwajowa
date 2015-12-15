package tram;

import java.time.LocalTime;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ClockThread extends Thread{
	public static LocalTime thisSec=LocalTime.now();//tutaj mo¿na ustawiæ sobie godzinê np NOON zamiast now
	public static JPanel panel;
	
    JLabel clockLabelVal=new JLabel(String.format("%s",thisSec));
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
			}
		}catch(Exception e){
			  System.out.print(e);
		}
	}
	

}
