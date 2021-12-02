package navernewsdb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import comn.Util;
import comn.Util2;
import dto.NaverDto;

public class NaverApi {

	public static ArrayList<NaverDto> getParsingData(String responseBody) {
		ArrayList<NaverDto> list = new ArrayList<NaverDto>();
		Gson gson = new Gson();
		JsonObject jsonObject = new Gson().fromJson(responseBody, JsonObject.class);
		JsonArray jsonArray = jsonObject.getAsJsonArray("items");

		for (JsonElement em : jsonArray) {
			NaverDto dto = gson.fromJson(em, NaverDto.class);
			list.add(dto);

		}
		return list;
	}

	public static String naverSrc(String str, String str2) {
		String[] code = Util.readLineFile(Util2.NAVER_CI_FILE).split("\\n");
//		String[] cArr = code.split("\\n");

		String clientId = code[0]; // 애플리케이션 클라이언트 아이디값"
		String clientSecret = code[1]; // 애플리케이션 클라이언트 시크릿값"

		String text = null;
		try {
			text = URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("검색어 인코딩 실패", e);
		}

		String apiURL = "https://openapi.naver.com/v1/search/" + str2 + "?query=" + text; // json 결과
		// String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text;
		// // xml 결과

		Map<String, String> requestHeaders = new HashMap<>();
		requestHeaders.put("X-Naver-Client-Id", clientId);
		requestHeaders.put("X-Naver-Client-Secret", clientSecret);
		String responseBody = get(apiURL, requestHeaders);
		return responseBody;
	}

	//json data 파싱해서 전달
	public ArrayList<NaverDto>getListJson(String responseBody){
		//json 파싱
		Gson gson = new Gson();
		ArrayList<NaverDto>list = new ArrayList<NaverDto>();
//      NaverApiDTO dto = gson.fromJson(responseBody, NaverApiDTO.class);
//      System.out.println(dto.getTitle());
		JsonObject jsonObject = new Gson().fromJson(responseBody, JsonObject.class);
		JsonArray jsonArray = jsonObject.getAsJsonArray("items");
//		System.out.println(jsonArray.get(0));
		for (JsonElement em : jsonArray) {
			NaverDto dto = gson.fromJson(em, NaverDto.class);
			list.add(dto);
//			System.out.println(dto.getTitle());
//			System.out.println(dto.getLink());
//			System.out.println(dto.getPubDate());
		}
		return list;
	}
	public static String searchNews(String str) {
		return naverSrc(str, "news");
	}

	public static String searchBlog(String str) {
		return naverSrc(str, "blog");
	}

	public static String searchBook(String str) {
		return naverSrc(str, "book");
	}

	public static String searcDoc(String str) {
		return naverSrc(str, "doc");
	}
	
	public static String searchMovie(String str) {
		return naverSrc(str, "movie");
	}

	private static String get(String apiUrl, Map<String, String> requestHeaders) {
		HttpURLConnection con = connect(apiUrl);
		try {
			con.setRequestMethod("GET");
			for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
				con.setRequestProperty(header.getKey(), header.getValue());
			}

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
				return readBody(con.getInputStream());
			} else { // 에러 발생
				return readBody(con.getErrorStream());
			}
		} catch (IOException e) {
			throw new RuntimeException("API 요청과 응답 실패", e);
		} finally {
			con.disconnect();
		}
	}

	private static HttpURLConnection connect(String apiUrl) {
		try {
			URL url = new URL(apiUrl);
			return (HttpURLConnection) url.openConnection();
		} catch (MalformedURLException e) {
			throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
		} catch (IOException e) {
			throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
		}
	}

	private static String readBody(InputStream body) {
		InputStreamReader streamReader = new InputStreamReader(body);

		try (BufferedReader lineReader = new BufferedReader(streamReader)) {
			StringBuilder responseBody = new StringBuilder();

			String line;
			while ((line = lineReader.readLine()) != null) {
				responseBody.append(line);
			}

			return responseBody.toString();
		} catch (IOException e) {
			throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
		}
	}

}