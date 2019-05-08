package crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.codec.binary.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class CrawlerTestMain {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		CrawlerTest c = new CrawlerTest();
		String url = "https://search.naver.com/search.naver?where=post&query=테스트";
		List<String> list = c.blog(url);
		
		
		int i = 0;
		for(String s : list) {
			String sb = c.getHtml(s);	// 블로그 내용 
			
			System.out.println("블로그 목록 URL : " + s);
			System.out.println(sb);
			

			//Document doc = Jsoup.parse(sb);
			Document nextDoc = Jsoup.connect(s).get();
			String html = nextDoc.select("div.se-component-content span").text();
			
			//Elements elements = doc.select("div.se-component-content span");
			
			/*for (Element element : elements) {
				Iterator<Element> iterElem = element.getElementsByTag("span").iterator();
				StringBuilder builder = new StringBuilder();
	            builder.append(iterElem.next().text());
	            String html1 = builder.toString();
				System.out.println(html1);
			}*/
			System.out.println(html);
			Files.write(Paths.get(i + ".html"), html.getBytes("UTF-8"));
			// 블로그 내용 가져오기 테스트
			
			
			i++;
		}

		
	}
	
}
