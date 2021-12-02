package comn;

public class Util2 {
	public static final String NAVER_CI_FILE = "C:/dev/네이버API_시크릿코드.txt";
	
    static String[] code =Util.readLineFile("C:/dev/DBcode.txt").split("\\n");
    private static final String USERNAME = code[0];//DBMS접속 시 아이디
    private static final String PASSWORD = code[1];//DBMS접속 시 비밀번호
    private static final String URL = code[2];//DBMS접속할 DB명
	
    public static String getUsername() {
		return USERNAME;
	}
	public static String getPassword() {
		return PASSWORD;
	}
	public static String getUrl() {
		return URL;
	}
    
    
}
