package postProcessCFSGD;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class cfSGDPostProcessOutput {

	public static void main(String [] args) throws IOException{
		FileInputStream fstream = new FileInputStream(args[0]); //time stamp file
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String line;
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter
				("/home/sumit/recommendersystemchallenge/data/output/cf_sgd",true)));
		while((line = br.readLine())!=null){
			String lineTobeWritten ="";
			int indexofTab =  line.indexOf("\t");
			String userId = line.substring(0, indexofTab);
			
			String itemString = line.substring(indexofTab+1);
			if(itemString.equals("1053452,2268722,1007923,2778525,1244196,1729618,657183,581327,2791339,1386412,2663343,1805804,1078303,536047,1053542,2432923,278589,79531,1928254,2446769,1984327,1583705,1133414,2242152,1742926,1201171,2532610,784737,1233470,830073")){
				lineTobeWritten = userId+"\t"+itemString;
			}
			else{
				int indexofFirstDelimiter = itemString.indexOf(",(");
				String firstItemString = itemString.substring(0,indexofFirstDelimiter);
				String [] array = firstItemString.split(",");
				String firstItem = array[0];
				String secondItemOnwards = itemString.substring(indexofFirstDelimiter+2);
				array = secondItemOnwards.split(",\\(");
				String concatenated = "";
				for(int i = 0 ; i < array.length ; i++){
					String [] againArray = array[i].split(",");
					concatenated = concatenated + ","+ againArray[0];
				}
				lineTobeWritten = userId+"\t"+firstItem+concatenated;
			}
			out.println(lineTobeWritten);
		}
		out.close();
	}

}
