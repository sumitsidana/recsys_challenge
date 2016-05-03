package postProcess;

import java.io.*;
public class decreaseItem {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		FileInputStream fstream = new FileInputStream(args[0]); //time stamp file
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String line;
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter
				("/home/sumit/recommendersystemchallenge/data/output/als_v2_submit",true)));
		String lineToBeWritten = "";
		while((line = br.readLine())!=null){
			lineToBeWritten = "";
			int indexofTab = line.indexOf("\t");
			String userId = line.substring(0, indexofTab);
			out.print(userId+"\t");
			String itemString = line.substring(indexofTab+1);
			String [] array = itemString.split(",");
			for(int i = 0 ; i < 29 ; i++){
				out.print(array[i]+",");
			}
			out.println(array[29]);
		}
		out.close();

	}

}
