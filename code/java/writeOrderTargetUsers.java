package postProcessCFSGD;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;
public class writeOrderTargetUsers {

	public static void main(String [] args) throws IOException{
		FileInputStream fstream = new FileInputStream(args[0]); //time stamp file
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String line;
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter
				("/home/sumit/recommendersystemchallenge/data/output/cf_sgd_submit",true)));

		Map<Long,String> userItems = new TreeMap<Long,String>();
		while((line = br.readLine())!=null){
			int indexofTab = line.indexOf("\t");
			String userId = line.substring(0, indexofTab);
			String restOfString = line.substring(indexofTab+1);
			userItems.put(Long.parseLong(userId), restOfString);
		}
		for(Map.Entry<Long,String> entry : userItems.entrySet()){
			out.println(entry.getKey()+"\t"+entry.getValue());
		}
		out.close();

	}

}
