package postProcessing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class UserItemSimilarity {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Map<String,Map<String,Object>>users = new LinkedHashMap<String,Map<String,Object>>();
		Map<String,Map<String,Object>>items = new LinkedHashMap<String,Map<String,Object>>();
		List<String> activeItems =  new ArrayList<String> ();
		//initialize

		try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) { //reading users file
			Map<String,Object> discipline = new LinkedHashMap<String,Object>();
			Map<String,Object> industry = new LinkedHashMap<String,Object> ();
			Map<String,Object> country = new LinkedHashMap<String,Object> ();
			Map<String, Object> career = new LinkedHashMap<String,Object> ();
			users.put("career", career);
			users.put("discipline", discipline);
			users.put("industry",industry);
			users.put("country",country);

			String line;

			while ((line = br.readLine()) != null) {
				String [] array = line.split("\t",-1);
				career = users.get("career");
				discipline = users.get("discipline");
				industry = users.get("industry");
				country = users.get("country");

				String c = array[2];
				if(c.equals("NULL")|| c.equals("0")){
					c = "3";
				}
				career.put(array[0],c);
				discipline.put(array[0], array[3]);
				industry.put(array[0],array[4]);
				country.put(array[0], array[5]);
				users.put("career", career);
				users.put("discipline", discipline);
				users.put("industry",industry);
				users.put("country",country);
			}

		}

		try (BufferedReader br = new BufferedReader(new FileReader(args[1]))) { //reading items file
			Map<String,Object> discipline = new LinkedHashMap<String,Object>();
			Map<String,Object> industry = new LinkedHashMap<String,Object> ();
			Map<String,Object> country = new LinkedHashMap<String,Object> ();
			Map<String, Object> career = new LinkedHashMap<String,Object> ();
			items.put("career", career);
			items.put("discipline", discipline);
			items.put("industry",industry);
			items.put("country",country);

			String line;

			while ((line = br.readLine()) != null) {
				String [] array = line.split("\t",-1);

				career = items.get("career");
				discipline = items.get("discipline");
				industry = items.get("industry");
				country = items.get("country");

				String c = array[2];
				if(c.equals("NULL")|| c.equals("0")){
					c = "3";
				}
				career.put(array[0],c);
				discipline.put(array[0], array[3]);
				industry.put(array[0],array[4]);
				country.put(array[0], array[5]);

				items.put("career", career);
				items.put("discipline", discipline);
				items.put("industry",industry);
				items.put("country",country);
			}

		}
//System.out.println("printing career of all items:" +items.get("career"));
		try (BufferedReader br = new BufferedReader(new FileReader(args[2]))) {
			String line;
			while ((line = br.readLine()) != null) {
				activeItems.add(line.replace("[", "").replace("]", ""));
			}
		}

		Map<String, Object>careerUsers = users.get("career");
		Map<String,Object>disciplineUsers = users.get("discipline");
		Map<String,Object>industryUsers = users.get("industry");
		Map<String,Object>countryUsers = users.get("country");
		Map<String, Object>careerItems = items.get("career");
		Map<String,Object>disciplineItems = items.get("discipline");
		Map<String,Object>industryItems = items.get("industry");
		Map<String,Object>countryItems = items.get("country");

		PrintWriter printWriter = new PrintWriter (args[4]);
		try (BufferedReader br = new BufferedReader(new FileReader(args[3]))) {
			String line;
			br.readLine();
			while ((line = br.readLine()) != null) {
				printWriter.print(line+"\t");
				for(int i = 0 ; i < activeItems.size() ; i++){
//					System.out.println("users career:"+careerUsers.get(line)+",items career:"+careerItems.get(activeItems.get(i))+","
//							+ "item:"+activeItems.get(i));
					if(careerUsers.get(line).equals(careerItems.get(activeItems.get(i)))
//							&& disciplineUsers.get(line).equals
//							(disciplineItems.get(activeItems.get(i)))
//							&& industryUsers.get(line).equals(industryItems.get(activeItems.get(i)))
							){
						printWriter.print(activeItems.get(i)+",");				
					}
				}
				printWriter.println();
			}
		}
		printWriter.close();



	}

}
