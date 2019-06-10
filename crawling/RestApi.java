package crawler;

import java.io.BufferedReader; 
import java.io.InputStreamReader; 
import java.net.HttpURLConnection; 
import java.net.URL;
import java.net.URLEncoder;




public class RestApi {
	
	private final String USER_AGENT = "Mozilla/5.0";

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		/*[ 개체명인식 REST API ] ############################################
		- 요청방식 : GET
		- Content-type : application/json;charset=utf-8
		- Parameter
		  • nerText : 내일 연세 대학교의 교명은 1957년 연희대학교와 세브란스 의과대학의 통합 후에 공식적으로 사용되었다.
		  • condition : nerT (개체+++명 추출인지 개체명 학습인지 구분)
		- 결과
		 [{"word":"내일","etymon":"내일","entity":"O","begin":0,"end":2,"pos":"MAG","score":0.6134346958371462,"flag":"M"},{"word":"연세 대학교","etymon":"연세대학교
		","entity":"ORG","begin":3,"end":9,"pos":"NNP","score":0.0,"flag":"D"},...,{"word":".","etymon":".","entity":"O","begin":56,"end":57,"pos":"SF","score":0.9319105463337434,"flag":"M"}]
*/
		
		RestApi http = new RestApi();
		
		System.out.println("GET으로 데이터 가져오기"); 
		String nerText = "내일 연세 대학교의 교명은 1957년 연희대학교와 세브란스 의과대학의 통합 후에 공식적으로 사용되었다.";
		String encodeResult = URLEncoder.encode(nerText, "UTF-8");
		String param = encodeResult;
		
	}
	
	private void sendGet(String targetUrl) throws Exception { 
		URL url = new URL(targetUrl); 
		HttpURLConnection con = (HttpURLConnection) url.openConnection(); 
		con.setRequestMethod("GET"); // optional default is GET 
		con.setRequestProperty("User-Agent", USER_AGENT); // add request header 
		
		int responseCode = con.getResponseCode(); 
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream())); 
		String inputLine; StringBuffer response = new StringBuffer(); 
		
		while ((inputLine = in.readLine()) != null) { 
				response.append(inputLine); 
		} 
		in.close(); 
		
		// print result 
		System.out.println("HTTP 응답 코드 : " + responseCode); 
		System.out.println("HTTP body : " + response.toString()); 
	
	}


}
