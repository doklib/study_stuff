package crawler;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class CrawlerTestMain {
	
	static String sc1 = "";

	public static void getSearch(String sc, int pg) throws Exception {
		// TODO Auto-generated method stub

		CrawlerTest c = new CrawlerTest();
		
//		String url = "https://search.naver.com/search.naver?date_from=&date_option=0&date_to=&dup_remove=1&nso=&post_blogurl=&post_blogurl_without=&query=%ED%85%8C%EC%8A%A4%ED%8A%B8%20%EA%B2%BD%EC%A3%BC&sm=tab_pge&srchby=all&st=sim&where=post&start=11";
	//	String url = "https://search.naver.com/search.naver?date_from=&date_option=0&date_to=&dup_remove=1&nso=&post_blogurl=&post_blogurl_without=&query=%ED%85%8C%EC%8A%A4%ED%8A%B8&sm=tab_pge&srchby=all&st=sim&where=post&start=21";
		//String url = "https://search.naver.com/search.naver?where=post&query=테스트+경주";
			
		String url = "https://search.naver.com/search.naver?date_from=&date_option=0&date_to=&dup_remove=1&nso=&post_blogurl=&post_blogurl_without=&query="+sc+"&sm=tab_pge&srchby=all&st=sim&where=post&start="+pg;
		System.out.println(url);
		
		List<String> list = c.blog(url);
		
		ArrayList<Object> myStringArrays = new ArrayList<Object>();
		Map<String, Object> multiMap = new HashMap<>();

		
		
		
		int i = 0;
		for(String s : list) {
			String sb = c.getHtml(s);	
			// 블로그 내용 
			System.out.println("블로그 목록 URL : " + s);
			System.out.println(sb);
			

			// 링크 대상 페이지에 접근하기
			Document nextDoc = Jsoup.connect(s).get();
			// 상세 내용 추출하기
			String htmlList = nextDoc.select("div.se-component-content span").text();
			
			String dateTime = nextDoc.select("span.se_publishDate").text();
			// 블로그 내용 가져오기 테스트
			System.out.println(htmlList);
			System.out.println(s);
			
			Map<String, Object> contextMap = new HashMap<>();
			contextMap.put("url", s);
			contextMap.put("date", dateTime);
			contextMap.put("text", htmlList);
			
			myStringArrays.add(contextMap);
//			multiMap.put(s, contextMap);
			
			i++;
		}
		 
		
		

		// sc 이름으로 저장하기
		createCSV(myStringArrays);

		
	}
	
	public static int createCSV(ArrayList<Object> myStringArrays){
	       
		   int resultCount =0; 
		    
		   try{
		    
		       BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:\\workspace\\workspace2\\Crawler\\"+sc1+".csv",true),"MS949"));
		    
		       for(Object dom : myStringArrays) {
		    	   	    	   
		    	   
		    	   String a = "\""+((HashMap)dom).get("url")+"\",\""+((HashMap)dom).get("date")+"\",\""+appendDQ(((HashMap)dom).get("text"))+"\"";
		    	   fw.write(a);
		    	   fw.newLine();
		    	   resultCount++;
		       }
		       
		       
		       /*Set<Entry<String, Object>> set = ((Map<String, Object>) myStringArrays).entrySet();
		       Iterator<Entry<String, Object>> itr = set.iterator();
		       
		       while (itr.hasNext())

		       {
		    	Map.Entry<String, Object> e = (Map.Entry<String, Object>)itr.next();
		        System.out.println("no : " + e.getKey() + ", 내용 : " + e.getValue());
		        fw.write(e.getKey()+",\""+e.getValue()+"\"");
		        fw.newLine();
		        resultCount++;
		        if(resultCount % 100 == 0) {
		            //log.info("resultCount :"+resultCount + "/" + list.size());
		         }
		        }*/
		       
		       fw.flush();
		       fw.close();
		      
		   }catch (Exception e) {
		     e.printStackTrace();
		   }
		       return resultCount;
		   }
	

	private static String appendDQ(Object object) {
		// TODO Auto-generated method stub
		return "\"" + object + "\"";
	}

	public static void main(String[] args) throws Exception{
		
		String value="";
		
		FileInputStream fis=new FileInputStream("D:\\workspace\\workspace2\\Crawler\\검색어.xlsx");
		XSSFWorkbook workbook=new XSSFWorkbook(fis);
		int rowindex=0;
		int columnindex=0;
		//시트 수 (첫번째에만 존재하므로 0을 준다)
		//만약 각 시트를 읽기위해서는 FOR문을 한번더 돌려준다
		XSSFSheet sheet=workbook.getSheetAt(0);
		//행의 수
		int rows=sheet.getPhysicalNumberOfRows();
		for(rowindex=1;rowindex<rows;rowindex++){
			//행을읽는다
			XSSFRow row=sheet.getRow(rowindex);
			if(row !=null){
				//셀의 수
				int cells=row.getPhysicalNumberOfCells();
				for(columnindex=0;columnindex<=cells;columnindex++){
					//셀값을 읽는다
					XSSFCell cell=row.getCell(columnindex);
					//셀이 빈값일경우를 위한 널체크
					if(cell==null){
						continue;
					}else{
						//타입별로 내용 읽기
						switch (cell.getCellType()){
						case XSSFCell.CELL_TYPE_FORMULA:
							value+=cell.getCellFormula();
							break;
						case XSSFCell.CELL_TYPE_NUMERIC:
							value+=cell.getNumericCellValue()+"";
							break;
						case XSSFCell.CELL_TYPE_STRING:
							value+=cell.getStringCellValue()+"";
							break;
						case XSSFCell.CELL_TYPE_BLANK:
							//value=cell.getBooleanCellValue()+"";
							break;
						case XSSFCell.CELL_TYPE_ERROR:
							value+=cell.getErrorCellValue()+"";
							break;
						}
					}
					value += "+";
					System.out.println("각 셀 내용 :"+value);
				}
				
			}
		}	
	
		String encodeResult = URLEncoder.encode(value, "UTF-8");
		String sc = encodeResult;
				//value;
				//"테스트+경주";
		//"%EA%B2%BD%EA%B8%B0%EB%8F%84%2B20%EB%8C%80%20%20%20%20%EB%82%A8%EC%9E%90%2B%2B%ED%9C%B4%EC%96%91%2B%EA%B0%80%EC%A1%B1%2B%EB%B4%84%2B%EA%B5%AD%EB%82%B4%EC%97%AC%ED%96%89%2B";
		sc1 = value;
		String res = null;
		for(int i = 0; i <2; i++) {
			getSearch(sc,i*10+1);
		}
			
	}
		
}
