
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ActivePersonalizedPopularity {

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
		PrintWriter printWriter = new PrintWriter (args[2]);
		try (BufferedReader br = new BufferedReader(new FileReader(new File(args[1])))) {
			String line;
			while ((line = br.readLine()) != null) {
				int indexofTab = line.indexOf("\t");
				String userString = line.substring(0, indexofTab);
				String itemString = line.substring(indexofTab+1);
				if(itemString.equals("2778525,1244196,1729618,657183,2791339,1386412,536047,1053542,278589,79531,1928254,2446769,1984327,1583705,784737,830073,"
						+ "1162250,343377,1140869,2106311,2002097,1092821,1443706,823512,1588611,1576126,1056667,2796479,1754395,460717")){
					printWriter.println(line);
				}
				else{
					String str = "2778525,1244196,1729618,657183,2791339,1386412,536047,1053542,278589,79531,"
							+ "1928254,2446769,1984327,1583705,784737,830073,1162250,343377,1140869,2106311,"
							+ "2002097,1092821,1443706,823512,1588611,1576126,1056667,2796479,1754395,460717";
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
