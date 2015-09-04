package com.hybhub.duplicated;

public class Main {

	public static void main(String[] args) {
		if( args == null || args.length == 0){
			printHelper();
			System.exit(1);;
		}
		
		for (int i = 0; i < args.length; i++) {
			FindDuplicatesOperation fd = new FindDuplicatesOperation(args[i]);
			fd.run();
		}
	}

	private static void printHelper() {
		String usage = "Usage : duplicated [path ...] \n" +
				"Example : duplicated /tmp /media/movies";
		System.out.println(usage);
	}

}
