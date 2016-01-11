package timeTableParser;

import java.time.LocalTime;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;

public class Schedule {
	ArrayList<String> filesInFolder;
	public HashMap<String, ArrayList<LocalTime>> schedules;
	
	public Schedule(String dirPath){
		filesInFolder=new ArrayList<String>();
		schedules=new HashMap<String, ArrayList<LocalTime>>();
		read(dirPath);
	}
	public void read(String dirPath) {
		final File folder = new File(dirPath);
		readFilesInFolder(folder);
		for (String fileName : filesInFolder) {
			String lineName = fileName.replace(".txt", "");
			schedules.put(lineName, readL(dirPath+"/" + fileName));
		}
	}
	
	public void readFilesInFolder(final File folder) {
		for (final File fileEntry : folder.listFiles()) {

			String name = fileEntry.getName();
			if (!fileEntry.isDirectory()) {
				filesInFolder.add(name);
			}
		}
	}
	
	ArrayList<LocalTime> readL(String path){
		ArrayList<LocalTime> schedule=new ArrayList<LocalTime>();
		try {
			InputStream inputStream = new FileInputStream(path);
			Reader inputStreamReader = new InputStreamReader(inputStream,
					"utf-8");
			BufferedReader br = new BufferedReader(inputStreamReader);
			String line;	
			while ((line = br.readLine()) != null) {
				String[] blocks=line.split("\\t");
				int hour=Integer.parseInt(blocks[0]);
				blocks=blocks[1].split(" ");
				for(String i : blocks){
					schedule.add(LocalTime.of(hour, Integer.parseInt(i)));
				}
			}
			br.close();
			
		} catch (Exception e) {
				e.printStackTrace();
		}
		return schedule;
	}

}
