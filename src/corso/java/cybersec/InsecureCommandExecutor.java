package corso.java.cybersec;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class InsecureCommandExecutor {
	
    final static String IPV4_PATTERN =
            "^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.(?!$)|$)){4}$";
    private static final String IPV6_PATTERN = "([0-9a-f]{1,4}:){7}([0-9a-f]){1,4}";
    private static final String MAC_PATTERN = "^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$";
    

	
    @SuppressWarnings("deprecation")
	public static void executeCommand(String address, String addressType) {
        System.out.printf("Executing ping with address %s and type %s\n", address, addressType);
        try {
        	String command = null;
        	
        	switch(addressType) {
        	  case "IPV4":
                  command = "ping -c 4 " + address;
        	    break;
        	  case "IPV6":
                  command = "ping -6 -c 4 " + address;
        	    break;
        	  case "MAC":
                  throw new RuntimeException("Can't ping a mac address you silly goof. Or maybe you can. Too late to check now");
        	  default:                  
        		  throw new RuntimeException("ERROR! Unknown address type: "+addressType);
        	}
        	
        	
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



	public static void main(String[] args) {
        if (args.length > 0) {
        	System.out.println("args: "+ Arrays.toString(args));
        	String addressType = null;
        	String address = null;
        	
        	address = extractPattern(IPV4_PATTERN, args[0]);
        	if (address != null) {
        		addressType = "IPV4";
        	} else {
            	address = extractPattern(IPV6_PATTERN, args[0]);
            	if (address != null) {
            		addressType = "IPV6";
            	} else {
                	address = extractPattern(MAC_PATTERN, args[0]);
                	if (address != null) {
                		addressType = "MAC";
                	} 
            	}
        	}
        	
        	if (address != null && addressType != null) {
        		executeCommand(address, addressType);
        	} else {
                System.out.println("Usage: java InsecureCommandExecutor <IPV4|IPV6|MAC>");
        	}
        	
        	
        } else {
            System.out.println("Usage: java InsecureCommandExecutor <IPV4|IPV6|MAC>");
        }
    }
	
	private static String extractPattern(String patternString, String inputString) {


	    final Pattern pattern = Pattern.compile(patternString);

	        Matcher matcher = pattern.matcher(inputString.trim());
	        if (matcher.find()) {
	            return matcher.group();
	        } else{
	            return null;
	        }
	}
	
	
//    private static String bonificaInputIP4(String userInput) {
//
//    	System.out.println("userInput: "+ userInput);
//    	String[] inputSplit = userInput.trim().split("\\.");
//    	System.out.println("inputSplit: "+ Arrays.toString(inputSplit));
//    	int octets[] = new int[4];
//    	
//    	if (inputSplit.length == 4) {
//    		
//        	for (int i = 0; i < inputSplit.length; i++) {
//        		
//        		octets[i] = Integer.parseInt(inputSplit[i]);
//            	System.out.println("octets: "+ Arrays.toString(octets));
//        		if (octets[i] < 0 || octets[i] > 255) 
//        			throw new NumberFormatException("User input incorretto.");
//     
//        	}
//    		
//    	} else {
//    		throw new NumberFormatException("User input incorretto.");
//    	}
//    	String ipString = Arrays.stream(octets) //
//    	        .mapToObj(String::valueOf) //
//    	        .collect(Collectors.joining("."));
//    	
//    	System.out.println("ipString: "+ ipString);
//    	
//		return ipString;
//	}
}