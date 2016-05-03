package postProcess;
import java.io.*;
import java.util.*;
public class TargetUserPast {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Map<String,String> userItemList = new LinkedHashMap<String,String> ();
		try (BufferedReader br = new BufferedReader(new FileReader(new File(args[0])))) {
			String line;
			//br.readLine();
			while ((line = br.readLine()) != null) {
				String [] array = line.split("\t");
				userItemList.put(array[0], array[1]);
			}

		}
		try (BufferedReader br = new BufferedReader(new FileReader(new File(args[1])))) {
			String line;
			PrintWriter printWriter = new PrintWriter (args[2]);
			br.readLine();
			while ((line = br.readLine()) != null) {
				printWriter.print(line+"\t");
				if(userItemList.containsKey(line)){
					String itemList = userItemList.get(line);
					String itemListTemp = itemList.replace("{", "").replace("}", "");
					String [] array = itemListTemp.split(",",-1);
					for(int i = array.length -1 ; i>0 ;i--){
						String []itemValue = array[i].split("=");
						printWriter.print(itemValue[0]+",");
					}
					String []itemValue = array[0].split("=");
					printWriter.println(itemValue[0]);
				}
				else{
					printWriter.println();
				}
				//				else{
				//					printWriter.println("2778525,1244196,1729618,657183,2791339,1386412,536047,1053542,278589,79531,1928254,2446769,1984327,1583705,784737,"
				//							+ "830073,1162250,343377,1140869,2106311,2002097,1092821,1443706,823512,1588611,1576126,1056667,2796479,1754395,460717");
				//				}
			}
			printWriter.close();
		}
	}
}
