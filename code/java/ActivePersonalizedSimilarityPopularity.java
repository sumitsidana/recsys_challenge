package postProcessing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ActivePersonalizedSimilarityPopularity {
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
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
		Map<String,String>userCareerBasedList = new LinkedHashMap<String,String> ();
		try (BufferedReader br = new BufferedReader(new FileReader(new File(args[2])))) {
			String line;
			while ((line = br.readLine()) != null) {
				int indexofTab = line.indexOf("\t");
				String userId = line.substring(0, indexofTab);
				String itemList = line.substring(indexofTab+1);
				userCareerBasedList.put(userId, itemList);
			}
		}
		PrintWriter printWriter = new PrintWriter (args[3]);
		try (BufferedReader br = new BufferedReader(new FileReader(new File(args[1])))) {
			String line;
			while ((line = br.readLine()) != null) {
				int indexofTab = line.indexOf("\t");
				String userString = line.substring(0, indexofTab);
				String itemString = line.substring(indexofTab+1);
				if(itemString.equals("")){
					printWriter.println(userString+"\t"+userCareerBasedList.get(userString));
				}
				else{
					String str = userCareerBasedList.get(userString);
					List<String> items = Arrays.asList(str.split("\\s*,\\s*"));
					int numItems = 30;
					
					printWriter.print(userString+"\t");
					
					String [] array = itemString.split(",");
					
					for(int i = 0 ; i < array.length ; i++){
						String item = array[i].trim();
						//System.out.println(itemStatus.containsKey("1606360"));
						if(itemStatus.containsKey(item)){
							if(itemStatus.get(item)){
//								System.out.println(item);
//								if(item.equals("1606360")){
//									System.out.println("itemString:"+itemString);
//									System.out.println(itemStatus.get(item));
//									
//								}
								printWriter.print(item+",");
								numItems--;
							}
						}
					}
					if(numItems <=0){
						printWriter.println();
						continue;
					}
					for(int i = 0 ; i < numItems-1; i++){
						printWriter.print(items.get(i)+",");
					}
					printWriter.println(items.get(numItems-1));
				}
			}
			printWriter.close();

		}
	}
}
