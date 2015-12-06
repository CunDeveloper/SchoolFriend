package com.nju.authorization;

import com.nju.util.Constant;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.List;

public class Authorization {

	private Logger logger = Logger.getLogger(Authorization.class);
	private OkHttpClient client = null;
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	private static final String IT = "lt";
	private static final String EVENT_ID = "_eventId";
	private static final String SUBMIT = "submit";

	public Authorization()
	{
		client = new OkHttpClient();
		CookieManager cookieManager = new CookieManager();
		CookieHandler.setDefault(cookieManager);
		cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
		client.setCookieHandler(cookieManager);
	}

	/**
	 * 获取隐藏字段It
	 * @return
	 * @throws IOException
	 */
	public  String getIt() throws IOException {
		Request request = new Request.Builder()
				.url(Constant.XUE_XIN_LOGIN_URL)
				.build();
		Response response = client.newCall(request).execute();
		System.out.println(response.code());
		Document doc = Jsoup.parse(response.body().string());
		Elements elements = doc.getElementsByAttributeValue("type", "hidden");
		Element element = elements.first();
		return element.val();
	}

	/**
	 * 获得首页面的HTML
	 * @param url
	 * @return 
	 */
	public String getHomeHtml(String url){
		Request request = new Request.Builder()
				.url(url)
				.build();
		try {
			Response response = client.newCall(request).execute();
			if(response.code() == Constant.HTTP_OK) {
				return response.body().string();
			}
			else{
				return Constant.HTTP_ERROR;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
			return Constant.HTTP_URL_ERROR;
		}
	}
	
	public  String getIt(Document doc) throws IOException {
		Elements elements = doc.getElementsByAttributeValue("type", "hidden");
		Element element = elements.first();
		return element.val();
	}
	
	public  String getItT(String html) throws IOException {
		Document doc = Jsoup.parse(html);
		Elements elements = doc.getElementsByAttributeValue("type", "hidden");
		Element element = elements.first();
		return element.val();
	}
	
	/**
	 * 登录验证
	 * @param It
	 * @param username
	 * @param password
	 * @return
	 * @throws IOException
	 */
	public  String  postForm(String It,String username,String password){
		RequestBody formBody = new FormEncodingBuilder()
				.add(USERNAME,username)
				.add(PASSWORD,password)
				.add(IT,It)
				.add(EVENT_ID,"submit")
				.add(SUBMIT,"登  录")
				.build();
		Request request = new Request.Builder()
				.url(Constant.XUE_XIN_LOGIN_URL+Constant.XUE_XIN_SERVICE)
				.post(formBody)
				.build();
		try {
			Response response = client.newCall(request).execute();
			if (response.code() == Constant.HTTP_OK) {
				return response.body().string();
			} else {
				return Constant.HTTP_ERROR;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
			return Constant.HTTP_URL_ERROR;
		}
		 
	}

	public  String  postFormWithCaptcha(String It,String username,String password,String captcha) throws IOException {
		RequestBody formBody = new FormEncodingBuilder()
				.add(USERNAME,username)
				.add(PASSWORD,password)
				.add(IT,It)
				.add(EVENT_ID,"submit")
				.add(SUBMIT,"登  录")
				.add("captcha",captcha)
				.build();
		Request request = new Request.Builder()
				.url(Constant.XUE_XIN_LOGIN_URL+Constant.XUE_XIN_SERVICE)
				.post(formBody)
				.build();
		try {
			Response response = client.newCall(request).execute();
			if (response.code() == Constant.HTTP_OK) {
				return response.body().string();
			} else {
				return Constant.HTTP_ERROR;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
			return Constant.HTTP_URL_ERROR;
		}
	}
	
	/**
	 * 获取验证码
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public byte[] getCaptcha(String url) throws IOException {
		Request request = new Request.Builder()
				.url(url)
				.build();
		Response response = client.newCall(request).execute();
		return response.body().bytes();
	}

	/**
	 *
	 * @param html
	 * @return
	 * @throws IOException
	 */
	public String getXueXinDoc(Document document) {
		Element regrightDiv = document.getElementById("regrightBG");
		Elements elements = regrightDiv.getElementsByTag("a");
		String url ="";
		for(Element element:elements){
			if(element.attr("title").equals("学信档案")){
				url = element.attr("href");
				break;
			}
		}
		if (!url.equals("")){
			Request request = new Request.Builder()
					.url(url).build();
			try {
				Response response = client.newCall(request).execute();
				if(response.code() == Constant.HTTP_OK) {
					return response.body().string();
				} else{
					return Constant.HTTP_ERROR;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e.getMessage());
				return Constant.HTTP_URL_ERROR;
			}
			
		}else{
			return null;
		}
	}
	
	public Document getDocument(String html) {
		return Jsoup.parse(html);
	}
	/**
	 * 判读是否需要输入验证码
	 * @param html
	 * @return 需要验证码返回true，不需要返回false
	 */
	public boolean isNeedCaptcha(Document doc) {
		Element captchaEle = doc.getElementById("captcha");
		if (captchaEle != null) {
			return true;
		} else{
			return false;
		}
	}
	
	public Document getTabDocElement(Document document) throws IOException{
		Element ulElement = document.getElementById("leftList");
		Elements ulElementChild = ulElement.children();
		Element secondLiElement = ulElementChild.get(1);
		Elements secondLiElementChild = secondLiElement.children();
		Request request = new Request.Builder()
				.url(Constant.XUE_XIN_BASE_URL + secondLiElementChild.get(0).attr("href")).build();
		Response response = client.newCall(request).execute();
		String content = response.body().string();
		Document doc = Jsoup.parse(content);
		logger.info(doc.getElementsByTag("script").text());
		return doc;
	}

	/**
	 *
	 * @param html
	 * @return
	 * @throws IOException
	 */
	public ArrayList<UserInfo> getUserInfo(Document document) throws IOException {
		String html = getXueXinDoc(document);
		if(html == null) {
			Document doc = getTabDocElement(document);
			return saveInfo(doc);
		}
		else{
			if(html.equals(Constant.HTTP_ERROR) || html.equals(Constant.HTTP_ERROR)) {
				return null;
			} else{
				System.out.println(html);
			    return saveInfo(parseHtml(html));
			}
		}
	}
	
	 

	private ArrayList<UserInfo> saveInfo(Document doc) throws IOException {
		Element div = doc.getElementById("tabs");
		Elements divChildren = div.children();
		Element ul = divChildren.first();
		Elements ulChidren = ul.children();
		ArrayList<UserInfo> userInfoList = new ArrayList<UserInfo>();
		UserInfo userInfo = null;
		System.out.println("size++"+ulChidren.size());
		for (Element element : ulChidren) {
			userInfo =new UserInfo();
			userInfo.setLabel(element.text());
			userInfoList.add(userInfo);
		}

		Elements tabsDiv = doc.getElementsByClass("tabsDiv");
		for(int j =0 ;j < tabsDiv.size();j++){
			Element element = tabsDiv.get(j);
			UserInfo info = userInfoList.get(j);
			Elements tables=element.getElementsByTag("table");
			for(Element table:tables){
				Elements trs = table.getElementsByTag("tr");
				for (Element tr : trs){
					Elements ths = tr.children();
					int length=ths.size();
					for(int i=0;i<length;){
						String label = ths.get(i).text().split("：")[0];
						if(label.equals("姓名")){
							i=i+1;
							info.setName(ths.get(i).text());
						}else if(label.equals("性别")){
							i=i+1;
							info.setSex(ths.get(i).text());

						}else if(label.equals("院校名称")){
							i=i+1;
							info.setYuanXiaoName(ths.get(i).text());
						}else if(label.equals("分院")){
							i=i+1;
							info.setFenYuan(ths.get(i).text());
						}else if(label.equals("专业名称")){
							i=i+1;
							info.setMajor(ths.get(i).text());

						}else if(label.equals("入学日期")){
							i=i+1;
							info.setDate(ths.get(i).text());
						}
						i++;
					}
				}
			}
		}
		return userInfoList;
	}
	
	/**
	 *
	 * @param html
	 * @return
	 * @throws IOException
	 */
	public ArrayList<UserInfo> getUserInfo2(Document document) throws IOException {
		Document doc = getTabDocElement(document);
		Element div = doc.getElementById("tabs");
		Elements divChildren = div.children();
		Element ul = divChildren.first();
		Elements ulChidren = ul.children();
		ArrayList<UserInfo> userInfoList = new ArrayList<UserInfo>();
		UserInfo userInfo = null;
		System.out.println("size++"+ulChidren.size());
		for (Element element : ulChidren) {
			userInfo =new UserInfo();
			userInfo.setLabel(element.text());
			userInfoList.add(userInfo);
		}

		Elements tabsDiv = doc.getElementsByClass("tabsDiv");
		logger.info(tabsDiv.html());
		for(int j =0 ;j < tabsDiv.size();j++){
			Element element = tabsDiv.get(j);
			UserInfo info = userInfoList.get(j);
			Elements tables=element.getElementsByTag("table");
			for(Element table:tables){
				Elements trs = table.getElementsByTag("tr");
				for (Element tr : trs){
					Elements ths = tr.children();
					for(Element ele:ths){
						String label = ele.text().split("：")[0];
						if(label.equals("姓名")){
							info.setName(ele.siblingElements().get(0).text());
						}else if(label.equals("性别")){
							info.setSex(ele.siblingElements().get(0).text());
						}else if(label.equals("院校名称")){
							info.setYuanXiaoName(ele.siblingElements().get(0).text());
						}else if(label.equals("分院")){
							info.setFenYuan(ele.siblingElements().get(0).text());
						}else if(label.equals("专业名称")){
							info.setMajor(ele.siblingElements().get(0).tagName());
						}else if(label.equals("入学日期")){
							info.setDate(ele.siblingElements().get(0).text());
						}
					}
				}
			}
		}
		return userInfoList;
	}
	
	
	public Document parseHtml(String html) {
		return Jsoup.parse(html);
	}
	
	/**
	 * 判读输入的用户名或密码是否错误
	 * <div id="status" class="errors">您输入的用户名或密码有误。</div>
	 * @param doc
	 * @return true,表示错误，false 表示正确
	 */
	
	public boolean isErrorUsernameOrPassword(Document doc) {
		Element element = doc.getElementById("status");
		if (element !=null) {
			return true;
		} else{
			return false;
		}
	}
	
	 
}