package navernewsdb;

import java.util.ArrayList;

import dao.NaverInfoDao;
import dto.NaverDto;

public class MainClass {

	public static void main(String[] args) {
		System.out.println("navernews db");
		//connect DB
		NaverInfoDao dao = new NaverInfoDao(); 		
		NaverDto dto = new NaverDto();
		
		//네이버 정보 받아서 DB에 넣기
		NaverApi nApi = new NaverApi();
		String jsonData = nApi.searchNews("오미크론");//이 검색어를 DB에 저장
		ArrayList<NaverDto> nList = nApi.getListJson(jsonData);
		for(NaverDto d:nList) {
			dao.insertDtoNaverInfo(d);
		} 
		
		
		//data 보기
		ArrayList<NaverDto> list = dao.selectAllDto();
		for(NaverDto d : list) {
			System.out.println(d.getTitle());
			System.out.println(d.getLink());
			System.out.println(d.getPubDate());
		}
	}

}
