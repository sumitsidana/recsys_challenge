package postProcessing;
import java.io.*;
import java.util.*;

public class MergeCFActiveItems {
	public static void main(String [] args) throws IOException{

		Map<String,Boolean> itemStatus = new LinkedHashMap<String,Boolean>();

		try (BufferedReader br = new BufferedReader(new FileReader(new File(args[0])))) {
			String line;
			while ((line = br.readLine()) != null) {
				String [] array = line.split("\t");
				String itemId = array[0];
				boolean status = false;
				if(array[12].equals("1"))
				{
					status = true;
				}
				else{
					status = false;
				}
				itemStatus.put(itemId, status);
			}
		}
		PrintWriter printWriter = new PrintWriter ("/Users/sumitsidana/recommendersystemchallenge/output/als_v2_1");
		try (BufferedReader br = new BufferedReader(new FileReader(new File(args[1])))) {
			String line;
			while ((line = br.readLine()) != null) {
				int indexofTab = line.indexOf("\t");
				String userString = line.substring(0, indexofTab);
				String itemString = line.substring(indexofTab+1);
				if(itemString.equals("1053452,2268722,1007923,2778525,1244196,1729618,657183,581327,2791339,1386412,2663343,1805804,1078303,536047,1053542,2432923,278589,79531,1928254,2446769,1984327,1583705,1133414,2242152,1742926,1201171,2532610,784737,1233470,830073")){
					printWriter.println(line);
				}
				else{
					printWriter.print(userString+"\t");
					String [] array = itemString.split(",");
					for(int i = 0 ; i < array.length ; i++){
						if(itemStatus.containsKey(array[i])){
						if(itemStatus.get(array[i])){
							printWriter.print(array[i]+",");
						}
						}
						else{
							System.out.println(array[i]);
						}
					}
					printWriter.println();
				}
			}
			printWriter.close();
		}
	}
}
