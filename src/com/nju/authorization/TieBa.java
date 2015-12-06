package com.nju.authorization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.nju.model.SchoolInfo;
import com.nju.model.TieBaProvinceSchoolInfo;
import com.nju.service.TieBaService;
import com.nju.util.Constant;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class TieBa {
	
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
	/**
	 * 获取页面下面的学校链接
	 * @param html
	 * @return
	 */
	public List<String> getSchoolUrl(String html) {
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
	/**
	 * 爬取学校的信息，包括图标等。
	 * @param html
	 * @return
	 * @throws IOException
	 */
	public List<SchoolInfo>  baInfo(String html) throws IOException{
		Document document = Jsoup.parse(html);
		Elements elements = document.getElementsByClass("ba_info");
		List<SchoolInfo> schoolInfos = new ArrayList<SchoolInfo>();
		SchoolInfo schoolInfo = null;
		for (Element element:elements) {
			schoolInfo = new SchoolInfo();
			Elements eleImg = element.getElementsByClass("ba_pic");
			for(Element img:eleImg) {
				schoolInfo.setIconUrl(img.attr("src"));
			}
			Elements eleName = element.getElementsByClass("ba_name");
			for(Element name:eleName) {
				String schoolName = name.text();
				
				schoolInfo.setName(schoolName.substring(0,schoolName.length()-1));
			}
			Elements eleDesc = element.getElementsByClass("ba_desc");
			for(Element desc:eleDesc) {
				schoolInfo.setDesc(desc.text());
			}
			schoolInfos.add(schoolInfo);
		}
		return schoolInfos;
	}
	/**
	 * 获取各个省份的第一个贴吧url
	 * @param html
	 * @return
	 * @throws IOException
	 */
	public List<TieBaProvinceSchoolInfo>  getProvinceSchoolInfos(String html) throws IOException {
		List<TieBaProvinceSchoolInfo> schoolInfos = new ArrayList<TieBaProvinceSchoolInfo>();
		Document document = Jsoup.parse(html);
		Elements elements = document.getElementsByClass("class_list");
		TieBaProvinceSchoolInfo schoolInfo = null;
		for (Element ulEle:elements) {
			Elements aEle = ulEle.getElementsByTag("a");
			for(Element a:aEle){
				schoolInfo = new TieBaProvinceSchoolInfo();
				schoolInfo.setFirstUrl(Constant.TIE_BA_BASE_URL+a.attr("href"));
				schoolInfo.setName(a.text());
				schoolInfos.add(schoolInfo);
			}
		}
		return schoolInfos;
	}
	
	/**
	 * 保存所有高等院校的URL
	 * @param schoolUrls
	 * @param service
	 * @throws IOException
	 */
	private void saveAllUrls(List<TieBaProvinceSchoolInfo> provoinceSchoolInfos,TieBaService service) throws IOException {
		for(TieBaProvinceSchoolInfo info:provoinceSchoolInfos){
			int id=service.queryId(info.getName());
			String tempHtml = html(info.getFirstUrl());
			//System.out.println(info.getFirstUrl());
			List<String> urlInfos = getSchoolUrl(tempHtml);
		    List<String> allUrls = generateAllUrls(urlInfos);
			service.saveAllUrls(allUrls, id);
		}
	}
	/**
	 * 生成所有高等院校的贴吧urls
	 * @param urlInfos
	 * @return
	 */
	private  List<String> generateAllUrls(List<String> urlInfos) {
	    List<String> allUrls = new ArrayList<String>();
	    if(urlInfos.size()>=1) {
	    	int last = urlInfos.size()-1;
			String lastStr = urlInfos.get(last);
			int subLen = lastStr.lastIndexOf("=")+1;
			int length = lastStr.length();
			String strUrlNum = lastStr.substring(subLen,length);
			String urlStr = lastStr.substring(0,subLen);
			int urlNum = Integer.valueOf(strUrlNum);
		    for(int i =2;i<=urlNum;i++) {
		    	allUrls.add(Constant.TIE_BA_BASE_URL+urlStr+i);
		    }
	    }
	    return allUrls;
	}

	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		TieBa tieBa = new TieBa();
		TieBaService service = new TieBaService();
		String html = tieBa.html(Constant.TIE_BA_START_URL);
		
		List<TieBaProvinceSchoolInfo> provinceSchoolInfos = tieBa.getProvinceSchoolInfos(html);
		tieBa.saveAllUrls(provinceSchoolInfos, service);
//		for(TieBaProvinceSchoolInfo info:provinceSchoolInfos){
//			String tempHtml = tieBa.html(info.getFirstUrl());
//			List<String> tempSchoolUrls = tieBa.getInfoUrl(tempHtml);
//			int id=service.queryId(info.getName());
//			service.saveAllUrls(tempSchoolUrls, id);
//		}
		for(TieBaProvinceSchoolInfo info:provinceSchoolInfos) {
			List<SchoolInfo> schoolInfos = tieBa.baInfo(tieBa.html(info.getFirstUrl()));
			int id=service.queryId(info.getName());
			service.saveScholInfo(schoolInfos, id);
			List<String> urls = service.queryUrlsById(id);
			for(String str:urls){
				schoolInfos = tieBa.baInfo(tieBa.html(str));
				service.saveScholInfo(schoolInfos,id);
			}
		}
//		List<SchoolInfo> schoolInfos =tieBa.baInfo(html);
//		for(SchoolInfo schoolInfo:schoolInfos) {
//			System.out.println("name="+schoolInfo.getName()+"desc="+schoolInfo.getDesc()+"icon="+schoolInfo.getIconUrl());
//		}
//		service.saveScholInfo(schoolInfos, 10);
////		new TieBaService().save(schoolUrls);
	}
	
}
