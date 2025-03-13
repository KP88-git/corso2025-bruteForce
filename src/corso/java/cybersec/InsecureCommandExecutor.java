package corso.java.cybersec;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Collectors;

public class InsecureCommandExecutor {
    @SuppressWarnings("deprecation")
	public static void executeCommand(String userInput) {
        try {
            String command = "ping -c 4 " + bonificaInputIP(userInput);
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String bonificaInputIP(String userInput) {

    	System.out.println("userInput: "+ userInput);
    	String[] inputSplit = userInput.trim().split("\\.");
    	System.out.println("inputSplit: "+ Arrays.toString(inputSplit));
    	int octets[] = new int[4];
    	
    	if (inputSplit.length == 4) {
    		
        	for (int i = 0; i < inputSplit.length; i++) {
        		
        		octets[i] = Integer.parseInt(inputSplit[i]);
            	System.out.println("octets: "+ Arrays.toString(octets));
        		if (octets[i] < 0 || octets[i] > 255) 
        			throw new NumberFormatException("User input incorretto.");
     
        	}
    		
    	} else {
    		throw new NumberFormatException("User input incorretto.");
    	}
//    	String ipString = String.join(".", );
    	String ipString = Arrays.stream(octets) //
    	        .mapToObj(String::valueOf) //
    	        .collect(Collectors.joining("."));
    	
    	System.out.println("ipString: "+ ipString);
    	
		return ipString;
	}

	public static void main(String[] args) {
        if (args.length > 0) {
        	System.out.println("args: "+ Arrays.toString(args));
            executeCommand(args[0]);
        } else {
            System.out.println("Usage: java InsecureCommandExecutor <IP>");
        }
    }
}