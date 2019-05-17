package crawler;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
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
		
		ArrayList<String> myStringArrays = new ArrayList<String>();
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
			// 블로그 내용 가져오기 테스트
			System.out.println(htmlList);
			System.out.println(s);
			

			
			multiMap.put(s, htmlList);
			myStringArrays.add(htmlList);
			
			i++;
		}
		String myString1 = myStringArrays.get(1);  // 배열 1번째 읽기
		String myString2 = myStringArrays.get(2);  
		System.out.println("list"+myString1);
		System.out.println("list"+myString2);
		

		// sc 이름으로 저장하기
		createCSV(multiMap);

		
	}
	
	public static int createCSV(Map<String, Object> multiMap){
	       
		   int resultCount =0; 
		    
		   try{
		    
		       BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:\\workspace\\workspace2\\Crawler\\"+sc1+".csv",true),"MS949"));
		    
		       Set<Entry<String, Object>> set = multiMap.entrySet();
		       Iterator<Entry<String, Object>> itr = set.iterator();
		       
		       while (itr.hasNext())

		       {
		    	Map.Entry<String, Object> e = (Map.Entry<String, Object>)itr.next();
		        System.out.println("url : " + e.getKey() + ", 내용 : " + e.getValue());
		        
		        fw.write(e.getKey()+",\""+e.getValue()+"\"");
		        fw.newLine();
		        resultCount++;
		        if(resultCount % 100 == 0) {
		            //log.info("resultCount :"+resultCount + "/" + list.size());
		         }
		        }
		       
		       fw.flush();
		       fw.close();
		      
		   }catch (Exception e) {
		     e.printStackTrace();
		   }
		       return resultCount;
		   }
	

	public static void main(String[] args) throws Exception{
		
		
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
					String value="";
					//셀이 빈값일경우를 위한 널체크
					if(cell==null){
						continue;
					}else{
						//타입별로 내용 읽기
						switch (cell.getCellType()){
						case XSSFCell.CELL_TYPE_FORMULA:
							value=cell.getCellFormula();
							break;
						case XSSFCell.CELL_TYPE_NUMERIC:
							value=cell.getNumericCellValue()+"";
							break;
						case XSSFCell.CELL_TYPE_STRING:
							value=cell.getStringCellValue()+"";
							break;
						case XSSFCell.CELL_TYPE_BLANK:
							//value=cell.getBooleanCellValue()+"";
							break;
						case XSSFCell.CELL_TYPE_ERROR:
							value=cell.getErrorCellValue()+"";
							break;
						}
					}
					System.out.println("각 셀 내용 :"+value);
				}
			}
		}	
	
	
		String sc = "테스트+경주";
		sc1 = sc;
		String res = null;
		for(int i = 0; i <1; i++) {
			getSearch(sc,i*10+1);
		}
			
	}
		
}
