package com.hybhub.duplicated;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.stream.Stream;

public class FindDuplicatesOperation implements Runnable {

	private String path;
	private Path rootPath;
	private HashMap<String, String[]> filesHashMap;
	
	public FindDuplicatesOperation(String path) {
		if(path == null || path.length() == 0){
			throw new IllegalArgumentException("Path can't be : >" + path + "<");
		}
		this.path = path;
		filesHashMap = new HashMap<String, String[]>();
	}
	@Override
	public void run() {
		rootPath = Paths.get(path);
		
		if( ! Files.exists(rootPath)){
			System.out.println("Path : " + path + " doesn't exit on the current system !");
		}
		
		try(Stream<Path> streamPath = Files.walk(rootPath)) {
			
			for (Iterator<Path> iterator = streamPath.iterator(); iterator.hasNext();) {
				Path p = iterator.next();
				if(Files.isDirectory(p)){
					continue;
				}

				String[] match = filesHashMap.get(p.getFileName().toString());

				if(match == null){
					filesHashMap.put(p.getFileName().toString(), new String[]{ p.toString() });					
				}
				else {
					String[] matchResized = Arrays.copyOf(match, match.length + 1);
					matchResized[match.length] = p.toString();
					filesHashMap.replace(p.getFileName().toString(), matchResized);
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (Iterator<Entry<String, String[]>> iterator = filesHashMap.entrySet().iterator(); iterator.hasNext();) {
			Entry<String, String[]> entry = iterator.next();
			if(entry.getValue().length > 1 ){
				System.out.println("Found one duplicated file : " + entry.getKey());
				for (int i = 0; i < entry.getValue().length; i++) {
					System.out.print(entry.getValue()[i] + " ");
				}
			}
		}
		
	}

}
