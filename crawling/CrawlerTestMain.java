package crawler;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class CrawlerTestMain {


	public static void getSearch(String sc, int pg) throws Exception {
		// TODO Auto-generated method stub

		CrawlerTest c = new CrawlerTest();
		
//		String url = "https://search.naver.com/search.naver?date_from=&date_option=0&date_to=&dup_remove=1&nso=&post_blogurl=&post_blogurl_without=&query=%ED%85%8C%EC%8A%A4%ED%8A%B8%20%EA%B2%BD%EC%A3%BC&sm=tab_pge&srchby=all&st=sim&where=post&start=11";
	//	String url = "https://search.naver.com/search.naver?date_from=&date_option=0&date_to=&dup_remove=1&nso=&post_blogurl=&post_blogurl_without=&query=%ED%85%8C%EC%8A%A4%ED%8A%B8&sm=tab_pge&srchby=all&st=sim&where=post&start=21";
		//String url = "https://search.naver.com/search.naver?where=post&query=테스트+경주";
			
		String url = "https://search.naver.com/search.naver?date_from=&date_option=0&date_to=&dup_remove=1&nso=&post_blogurl=&post_blogurl_without=&query="+sc+"&sm=tab_pge&srchby=all&st=sim&where=post&start="+pg;
		System.out.println(url);
		
		List<String> list = c.blog(url);
		ArrayList<String> myStringArrays = new ArrayList<String>();
		String html = "";
		//String blogUrl = "";
		int i = 0;
		for(String s : list) {
			String sb = c.getHtml(s);	
			// 블로그 내용 
			
			System.out.println("블로그 목록 URL : " + s);
			System.out.println(sb);
			

			// 링크 대상 페이지에 접근하기
			Document nextDoc = Jsoup.connect(s).get();
			// 상세 내용 추출하기"|"+
			html += "\""+ nextDoc.select("div.se-component-content span").text() + "\",";
			String htmlList = nextDoc.select("div.se-component-content span").text();
			
			// 블로그 내용 가져오기 테스트
			System.out.println(htmlList);
			// ".html"이라는 이름으로 저장하기
			//URI uri = ClassLoader.getSystemResource(s).toURI();
			//String mainPath = Paths.get(uri).toString();
			System.out.println(s);
			String s1 = s.replace(":", "");
			s1 = s1.replace("?", "");
			s1 = s1.replace("/", "");
//			s = s.replaceAll("(?i:https?://([^/]+)/.*)", "$1");
//			s = s.replaceAll("(https?:([^\\D]+).*)", "$1");

			System.out.println(s1);
			
			//Files.write(Paths.get(s1 + ".txt"), html.getBytes("UTF-8"));
			myStringArrays.add(htmlList);
			
			
			
			
			
			i++;
		}
		String myString1 = myStringArrays.get(1);  // 배열 1번째 읽기
		String myString2 = myStringArrays.get(2);  
		System.out.println("list"+myString1);
		System.out.println("list"+myString2);
		//Files.write(Paths.get(sc + ".csv"), html.getBytes("UTF-8"));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:\\workspace\\workspace2\\Crawler\\"+sc+".csv"),"MS949"));
		writer.write(html);
		

		
	}
	

	public static void main(String[] args) throws Exception{ 
		String sc = "테스트";
		String res = null;
		for(int i = 0; i <1; i++) {
			getSearch(sc,i*10+1);
		}
			
	}
	
	

	
}
