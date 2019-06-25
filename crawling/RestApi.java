package crawler;

import java.io.BufferedReader; 
import java.io.InputStreamReader; 
import java.net.HttpURLConnection; 
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;




public class RestApi {
	
	private final String USER_AGENT = "Mozilla/5.0";

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		/*[ 개체명인식 REST API ] ############################################
		- URL : http://58.123.178.130:4942/nerView/nerResult.do
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
		
		//System.out.println("GET으로 데이터 가져오기"); 
		String nerText = "내일 연세 대학교의 교명은 1957년 연희대학교와 세브란스 의과대학의 통합 후에 공식적으로 사용되었다.";
		
		String nerTextResult = null;
		ArrayList<String> nerTextArray = new ArrayList<String>();
		ArrayList<Object> wordArrayList = new ArrayList<Object>();
		
		if(nerText.length()>3) {
			
			for(int i = 0;i<nerText.length()/3;i++){
				nerTextArray.add(nerText.substring(3*i, 3*(i+1)));
			}
		}else {
			nerTextArray.add(nerText.substring(0, nerText.length()));
		}
		
		for(String array : nerTextArray) {
			String words = array;
			
			String encodeResult = URLEncoder.encode(words, "UTF-8");
			String param = encodeResult;
			Object wordArray = http.sendGetEnt("http://58.123.178.130:4942/nerView/nerResult.do?nerText="+param+"&condition=nerT"); 
			wordArrayList.add(wordArray);
			
			int oo = 1;
			
			
			
		}
		
		
		
		/*String encodeResult = URLEncoder.encode(nerTextResult, "UTF-8");
		String param = encodeResult;
		http.sendGet("http://58.123.178.130:4942/nerView/nerResult.do?nerText="+param+"&condition=nerT"); 
*/		
		
	}
	
	public ArrayList<HashMap<String, String>> sendGetEnt(String targetUrl) throws Exception { 
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
		//System.out.println("HTTP 응답 코드 : " + responseCode); 
		//System.out.println("HTTP body : " + response.toString()); 
		
		//return response.toString();
		
		JsonParser jsonParser1 = new JsonParser();
		JsonElement jsonElement1 = jsonParser1.parse(response.toString());
		//String name1 =  jsonElement1.getAsJsonObject().get("resultList").getAsJsonArray().get(0).getAsJsonObject().get("pos").toString();
		JsonArray resultListArray1 =  jsonElement1.getAsJsonObject().get("resultList").getAsJsonArray();

		ArrayList<HashMap<String, String>> wordArray = new ArrayList<HashMap<String, String>>();
		ArrayList<HashMap<String, String>> wordArrayDu = new ArrayList<HashMap<String, String>>();
		
        for(int i1=0; i1<resultListArray1.size(); i1++){
        	 
            //System.out.println("resultList"+i+" ===========================================");
             
            //배열 안에 있는것도 JSON형식 이기 때문에 JSON Object 로 추출
        	JsonObject resultListObject1 = (JsonObject) resultListArray1.get(i1);
        	HashMap<String, String> wordMap = new HashMap<String,String>();
            if(resultListObject1.get("entity").toString().equals("\"TRP\"")||resultListObject1.get("entity").toString().equals("\"GEO\"")) {
            	wordMap.put("word", resultListObject1.get("word").toString());
            	wordMap.put("entity", resultListObject1.get("entity").toString());
            	wordArray.add(wordMap);
            	/*for(int i=0; i<wordArrayDu.size(); i++) {
            		if(!wordArray.contains(wordArrayDu.get(i)))
            			wordArray.add(wordArrayDu.get(i));
            	} 
            		*/
            }
            

        }
		
		
		return wordArray;

	}
	
	public String sendGet(String targetUrl) throws Exception { 
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
		//System.out.println("HTTP 응답 코드 : " + responseCode); 
		//System.out.println("HTTP body : " + response.toString()); 
		
	
		return response.toString();
	}


}
