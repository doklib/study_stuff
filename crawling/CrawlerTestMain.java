package crawler;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class CrawlerTestMain {
	
	
	static String sc1 = "";
	static Date time1 = new Date();
	static ArrayList<Object> searchMapArray = new ArrayList<Object>();
	static ArrayList<Object> combineArray = new ArrayList<Object>();

	public static void getSearch(String sc, int pg, int index) throws Exception {
		// TODO Auto-generated method stub

		CrawlerTest c = new CrawlerTest();
		
		String url = "https://search.naver.com/search.naver?date_from=&date_option=0&date_to=&dup_remove=1&nso=&post_blogurl=&post_blogurl_without=&query="+sc+"&sm=tab_pge&srchby=all&st=sim&where=post&start="+pg;
		//System.out.println(url);
		//System.out.println(pg+"번호");
		
		List<Object> urlList = c.blog(url);
	
		
		
		ArrayList<Object> myStringArrays = new ArrayList<Object>();
		Map<String, Object> multiMap = new HashMap<>();

		int i = 0;
		for(Object ss : urlList) {
			String s = (String) ((HashMap<String,String>) ss).get("url");
			//String sb = c.getHtml(s);	
			
			
			// 블로그 내용 
			//System.out.println("블로그 목록 URL : " + s);
			//System.out.println(sb);
			

			// 링크 대상 페이지에 접근하기
			Document nextDoc;
			try {
				nextDoc = Jsoup.connect(s).get();
			
			// 상세 내용 추출하기
			String htmlList = nextDoc.select("div.se-component span").text();
			if(htmlList.equals("")) {
				htmlList = nextDoc.select("div.se_component span").text();
			}
			/*else if(htmlList.equals("")){
				htmlList = nextDoc.select("div. span").text();
		
			}*/
			
			if(!htmlList.equals("")&&htmlList.length()<30000) {
			//debug gg	
			
			String titleFin	= (String) ((HashMap<String,String>) ss).get("title");
			if(titleFin.equals("")) {
				titleFin = nextDoc.select("div.se_textView").text();
			}
			
			titleFin = titleFin.replace(",", "");
			
			String dateTime = nextDoc.select("span.se_publishDate").text();
			
			SimpleDateFormat orginFormat = new SimpleDateFormat ( "yyyy. M. dd. HH:mm");
			SimpleDateFormat format = new SimpleDateFormat ( "yyyyMMdd");	
		
			if(dateTime.contains("전")) {	
				Date time = new Date();
				dateTime = orginFormat.format(time);
			}
			if(!dateTime.equals("")) {
			Date orginDate = orginFormat.parse(dateTime);
			dateTime = format.format(orginDate);
			}
			//System.out.println(dateTime);	
			
			// 블로그 내용 가져오기 테스트
			//System.out.println(s);
			
			
			String htmlList2 = htmlList.replace(",", "");
			htmlList2 = htmlList2.replaceAll("[\\{\\}\\[\\]\\/?.,;:|\\)*~`!^\\-_+<>@\\#$%&\\\\\\=\\(\\'\\\"]", "");
			//[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]  ^[ㄱ-ㅎ가-힣]*$ [^ㄱ-ㅎ|ㅏ-ㅣ|가-힣]

			//System.out.println(htmlList2);
			
			
			Map<String, Object> contextMap = new HashMap<>();
			contextMap.put("url", s);
			contextMap.put("date", dateTime);
			contextMap.put("text", htmlList2);
			contextMap.put("title", titleFin);
			
			myStringArrays.add(contextMap);
			
			}
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}
		 
		
		

		// sc 이름으로 저장하기
		createCSV(myStringArrays, index);

		
	}
	
	public static void createCSV(ArrayList<Object> myStringArrays, int index){
	       
		   
		    
		   try{
			   
			   HashMap<String,Object> searched = (HashMap<String, Object>) combineArray.get(index);
			   String addrOut =  (String) searched.get("addrs");
			   String ageOut = (String) searched.get("ages");
    		   String sexOut = (String) searched.get("sexs");
    		   String purposeOut = (String) searched.get("purposes");
    		   String companionOut = (String) searched.get("companions");
    		   String seasonOut = (String) searched.get("seasons");
    		   //String travelOut = (String) searched.get("Travel");
    		  
			   
			   
		       BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:\\workspace\\workspace2\\Crawler\\"+"결과"+".csv",true),"MS949"));
		    
		       for(Object dom : myStringArrays) {	
	    		   String a = addrOut+","+ageOut+","+sexOut+","+purposeOut+","+companionOut+","+seasonOut
//	    				   +""+ ","+travelOut
	    				   +","+((HashMap)dom).get("url")+","+((HashMap)dom).get("title")+","+((HashMap)dom).get("date")+",\""+((HashMap)dom).get("text")+"\"";
	    		   fw.write(a);
	    		   fw.newLine();
	    		    
			       }
		       
		       fw.flush();
		       fw.close();
		      
		   }catch (Exception e) {
		     e.printStackTrace();
		   }
	}
		       
	



	public static void main(String[] args) throws Exception{
		
		String value = null;
			int index=0;
			ArrayList<Object> combineArray = null;
		
			//readExcel();
			combineArray = searchCombin();
			
			for(Object arr2 : combineArray) {	
				index = combineArray.indexOf(arr2);
		 		value = ((HashMap)arr2).get("addrs")+"+"+((HashMap)arr2).get("ages")+"+"+((HashMap)arr2).get("sexs")
		 				   +"+"+((HashMap)arr2).get("purposes")+"+"+((HashMap)arr2).get("companions")+"+"+((HashMap)arr2).get("seasons");
		 		  
			
			String encodeResult = URLEncoder.encode(value, "UTF-8");
			String sc = encodeResult;
			sc1 = value;
			
	//		CrawlerTest c = new CrawlerTest();
	//		String url = "https://search.naver.com/search.naver?date_from=&date_option=0&date_to=&dup_remove=1&nso=&post_blogurl=&post_blogurl_without=&query="+sc+"&sm=tab_pge&srchby=all&st=sim&where=post&start=1";
	//		int totalNum = Integer.parseInt(c.blogTotal(url));
			System.out.println(index);
	//			for(int i = 0; i <totalNum/1000; i++) {
				for(int i = 0; i <4; i++) {	
					getSearch(sc,i*10+1,index);
				
				}
			}
		}
	
	public static ArrayList<Object> searchCombin() {
		
		/*String [] addrs = {"서울특별시", "부산광역시"
				, "경기도", "강원도", "충청도", "전라도", "경상도", "제주도"};"강원도", "충청도", "전라도", "경상도", "제주도"
		String [] ages = {"10대", "20대", "30대+40대", "50대+60대+70대+80대"};
		String [] sexs = {"남자", "여자"};
		String [] purposes = {"자연", "휴양", "역사", "문화", "축제", "레포츠", "맛집", "캠핑"};
		String [] companions = {"부모님", "자녀", "연인", "친구"};
		String [] seasons = {"봄", "여름", "가을", "겨울"};*/
		
		String [] addrs = {"인천광역시", "대전광역시"};
		String [] ages = {"10대", "20대", "30대+40대", "50대+60대+70대+80대"};
		String [] sexs = {"남자", "여자"};
		String [] purposes = {"자연", "휴양", "역사", "문화", "축제", "레포츠", "맛집", "캠핑"};
		String [] companions = {"부모님", "자녀", "연인", "친구"};
		String [] seasons = {"봄", "여름", "가을", "겨울"};
		
	//	ArrayList<Object> combineArray = new ArrayList<Object>();
		
		int randomAddr [] = new int[addrs.length];
		Random  r = new Random();
		
		for(int i=0;i<addrs.length;i++)
		{
			randomAddr[i] = r.nextInt(addrs.length);
			for(int j=0; j<i; j++)
			{
				if(randomAddr[i]==randomAddr[j])
				{
					i--;
				}
			}	
		}
		
		for(int a = 0; a < addrs.length; a++) {
			
			for(int b = 0; b < ages.length; b++) {
				for(int c = 0; c < sexs.length; c++) {
					for(int d = 0; d < purposes.length; d++) {
						for(int e = 0; e < companions.length; e++) {
							for(int f = 0; f < seasons.length; f++) {
								Map<String, String> combine = new HashMap<>();
								combine.put("seasons", seasons[f]);
								combine.put("companions", companions[e]);
								combine.put("purposes", purposes[d]);
								combine.put("sexs", sexs[c]);
								combine.put("ages", ages[b]);
								combine.put("addrs", addrs[randomAddr[a]]);
								combineArray.add(combine);
								//System.out.println(combineArray);
							}
						}
					}
				}
			}
	
		}
		//System.out.println(combineArray);
		return combineArray;
	}
			
	
	public static void readExcel() throws IOException{
	    
		
		String value="";
		
		FileInputStream fis=new FileInputStream("D:\\workspace\\workspace2\\Crawler\\검색어.xlsx");
		XSSFWorkbook workbook=new XSSFWorkbook(fis);
         
         
		int rowindex=0;
		//시트 수 (첫번째에만 존재하므로 0을 준다)
		//만약 각 시트를 읽기위해서는 FOR문을 한번더 돌려준다
		XSSFSheet sheet=workbook.getSheetAt(0);
		//행의 수
		int rows=sheet.getPhysicalNumberOfRows();
		for(rowindex=1;rowindex<rows;rowindex++){
			//행을읽는다
			XSSFRow row=sheet.getRow(rowindex);
			
			Map<String, Object> searchMap = new HashMap<>();
			searchMap.put("Addr", String.valueOf(row.getCell(0)));
			searchMap.put("Age", String.valueOf(row.getCell(1)));
			searchMap.put("Sex", String.valueOf(row.getCell(2)));
			searchMap.put("Purpose", String.valueOf(row.getCell(3)));
			searchMap.put("Companion", String.valueOf(row.getCell(4)));
			searchMap.put("Season", String.valueOf(row.getCell(5)));
			searchMap.put("Travel", String.valueOf(row.getCell(6)));
			searchMap.put("rownum", rowindex);
			searchMapArray.add(searchMap);

		//System.out.println(searchMapArray);
		}	
		
		
	
	}	
}

