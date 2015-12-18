package emotion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.nju.util.Constant;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class Emotion {

    private OkHttpClient client = null;
    
	public Emotion(){
		client = new OkHttpClient();
	}
	
	public List<String> getCodes() {
		List<String> codes = new ArrayList<String>();
		Scanner scanner = null;
		try {
		    scanner = new Scanner(new File(".\\src\\emotion\\code.txt"));
			
			while(scanner.hasNextLine()) {
				String[] strs =scanner.nextLine().split("\\s+");
				String tempStr = strs[1];
				String[] results = tempStr.split("\\+");
				codes.add(results[1]);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(scanner !=null ){
				scanner.close();
			}
	
		}
		return codes;
		
	}
	
	public String html(String url) throws IOException {
		Request request = new Request.Builder()
				.url(url)
				.build();
		
		Response response = client.newCall(request).execute();
		String html = response.body().string();
		return html;
	}
	
	public Document document(String html) {
		return Jsoup.parse(html);
	}
	
	public void parseDoc(Document document) throws IOException {
		Elements trEles = document.getElementsByClass("row1");
		FileWriter writer = new FileWriter(new File(".\\src\\emotion\\info.txt"),true);
		StringBuilder builder = new StringBuilder();
		for(Element tr :trEles) {
			Elements tdEles = tr.children();
			
			for(Element td:tdEles) {
				if(td.text().equals("Emoji")){
					Element secondEle = tdEles.get(1);
					String text = secondEle.text();
					builder.append(text.substring(1,text.length()-1)+" ");
					builder.append(secondEle.getElementsByTag("img").get(0).attr("src")+" ");
					break;
				}
				else if(td.text().equals("C/C++/Java source code")) {
					Element secondEle = tdEles.get(1);
					String text = secondEle.text();
					builder.append(text);
					System.out.println(text);
					break;
				}
			}
		}
		 writer.write(builder.toString()+"\n");
		 writer.close();
	}
	
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Emotion emotion = new Emotion();
		List<String> codes = emotion.getCodes();
		for(String str:codes) {
			try {
				String html = emotion.html(Constant.EMOTION_URL+str+"/index.htm");
				emotion.parseDoc(emotion.document(html));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		 
	}

}
