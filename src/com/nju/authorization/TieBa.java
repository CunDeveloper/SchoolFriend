package com.nju.authorization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.nju.model.TieBaSchoolInfo;
import com.nju.service.TieBaService;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class TieBa {

	private static final String BASE_URL ="http://tieba.baidu.com";
	private static final String TEST_URL = "http://tieba.baidu.com/f/index/forumpark?cn=%B1%B1%BE%A9%D4%BA%D0%A3&ci=0&pcn=%B8%DF%B5%C8%D4%BA%D0%A3&pci=0&ct=1&rn=20&pn=1";
	private OkHttpClient client = null;
	
	public TieBa(){
		client = new OkHttpClient();
	}
	
	public String html(String url) throws IOException {
		Request request = new Request.Builder()
				.url(url)
				.build();
		Response response = client.newCall(request).execute();
		String html = response.body().string();
		return html;
	}
	public List<String> getInfoUrl(String html) {
		List<String> infosUrl = new ArrayList<String>();
		Document document = Jsoup.parse(html);
		Elements pageElements = document.getElementsByClass("pagination");
		for(Element pageEle:pageElements){
			Elements aEles = pageEle.getElementsByTag("a");
			for(Element aEle:aEles){
				infosUrl.add(aEle.attr("href"));
			}
		}
		return infosUrl;
	}
	public String  baInfo(String html) throws IOException{
		Document document = Jsoup.parse(html);
		Elements elements = document.getElementsByClass("ba_info");
		for (Element element:elements) {
			Elements eleImg = element.getElementsByClass("ba_pic");
			for(Element img:eleImg) {
				System.out.println(img.attr("src"));
			}
			String str = element.text();
			String[] strs = str.split(" ");
			for(String stra:strs){
				System.out.println(stra);
			}
			 
		}
		return "";
	}
	
	public List<TieBaSchoolInfo> schoolsUrl(String html) throws IOException {
		List<TieBaSchoolInfo> schoolInfos = new ArrayList<TieBaSchoolInfo>();
		Document document = Jsoup.parse(html);
		Elements elements = document.getElementsByClass("class_list");
		TieBaSchoolInfo schoolInfo = null;
		for (Element ulEle:elements) {
			Elements aEle = ulEle.getElementsByTag("a");
			for(Element a:aEle){
				schoolInfo = new TieBaSchoolInfo();
				schoolInfo.setFirstUrl(BASE_URL+a.attr("href"));
				schoolInfo.setName(a.text());
				schoolInfos.add(schoolInfo);
			}
		}
		return schoolInfos;
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		TieBa tieBa = new TieBa();
		//tieBa.test();
		String html = tieBa.html(TEST_URL);
		List<TieBaSchoolInfo> schoolUrls = tieBa.schoolsUrl(html);
//		new TieBaService().save(schoolUrls);
		TieBaService service = new TieBaService();
		for(TieBaSchoolInfo info:schoolUrls){
			int id=service.queryId(info.getName());
			String tempHtml = tieBa.html(info.getFirstUrl());
			//System.out.println(info.getFirstUrl());
			List<String> urlInfos = tieBa.getInfoUrl(tempHtml);
			Set set = new HashSet();
			if(urlInfos.size()>2){
				int last = urlInfos.size()-1;
				int third = last-2;
				//String lastStr = urlInfos.get(last);
				String thirdStr = urlInfos.get(third);
				int i = thirdStr.lastIndexOf("=")+1;
				int j = thirdStr.length();
				String subStr = thirdStr.substring(i, j);
				System.out.println(subStr);
			}
			//System.out.println(urlInfos.size()+"==");
			service.saveAllUrls(urlInfos, id);
		}
	}

}
