package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import comn.Util2;
import dto.NaverDto;
 
public class NaverInfoDao {
 
    private static Connection conn; //DB 커넥션 연결 객체
    
    public NaverInfoDao() {
        try {
            System.out.println("생성자");
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(Util2.getUrl(), Util2.getUsername(), Util2.getPassword());
            System.out.println("연결 성공");
        } catch (Exception e) {
            System.out.println("연결 실패 ");
            e.printStackTrace();
            try {
                conn.close();
            } catch (SQLException e1) {    }
        }
        
        
    }

    //mainclass에서 받은 데이터 dto로 넘겨서 삽입 메소드
    public void insertDtoNaverInfo(NaverDto dto){
        //쿼리문 준비
        String sql = "INSERT INTO naver_info (title, url, date) VALUES(?,?,?)";
        
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dto.getTitle());
            pstmt.setString(2, dto.getLink());
            pstmt.setString(3, dto.getPubDate());

            
            int result = pstmt.executeUpdate();
            if(result==1) {
                System.out.println("데이터 삽입 성공!");
                
            }
            
        } catch (Exception e) {
            System.out.println("naver_info 데이터 삽입 실패!");
            e.printStackTrace();
        }    finally {
            try {
                if(pstmt!=null && !pstmt.isClosed()) {
                    pstmt.close();
                }
            } catch (Exception e2) {}
        }
        
        
    }   
    
    
  //조건에 맞는 행을 db에서 1개 행만 가져오는 메서드
    public void selectOne(int id) {
        String sql = "select * from naver_info where num = ?";
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                System.out.println("num: " + rs.getInt("num"));
                System.out.println("title: " + rs.getString("title"));
                System.out.println("url: " + rs.getString("url"));
                System.out.println("date: " + rs.getString("date"));
                
            }
            
            
            
        } catch (Exception e) {
            System.out.println("select 메서드 예외발생");
            e.printStackTrace();
        }    finally {
            try {
                if(pstmt!=null && !pstmt.isClosed()) {
                    pstmt.close();
                }
            } catch (Exception e2) {}
        }
    }
    
  //db에서 모든 행 가져오는 메서드
    public void selectAll() {
        String sql = "SELECT * FROM naver_info";
        PreparedStatement pstmt = null;
        
    	
        try {
        	pstmt = conn.prepareStatement(sql);  
            ResultSet rs = pstmt.executeQuery();
            
            while(rs.next()) {
                System.out.println("num: " + rs.getInt("num"));
                System.out.println("title: " + rs.getString("title"));
                System.out.println("url: " + rs.getString("url"));
                System.out.println("date: " + rs.getString("date"));
     
           }
        } catch (Exception e) {
            System.out.println("select 메서드 예외발생");
            e.printStackTrace();
        } finally {
            try {
                if(pstmt!=null && !pstmt.isClosed()) {
                    pstmt.close();
                }
            } catch (Exception e2) {}
        }
        
    }
    
    
    //db에서 모든 행 가져오는 메서드 dto에서 가져오기
    public ArrayList<NaverDto> selectAllDto() {
    ArrayList<NaverDto> list = new ArrayList<NaverDto>();
        String sql = "SELECT * FROM naver_info";
        PreparedStatement pstmt = null;
        
    	
        try {
        	pstmt = conn.prepareStatement(sql);  
            ResultSet rs = pstmt.executeQuery();
            
            while(rs.next()) {
            	NaverDto dto = new NaverDto();//dto 안에 넣어야 행 갯수만큼 만들어짐
            	dto.setTitle(rs.getString("title"));
            	dto.setLink(rs.getString("url"));
            	dto.setPubDate(rs.getString("date"));
            	list.add(dto);
           }
        } catch (Exception e) {
            System.out.println("select 메서드 예외발생");
            e.printStackTrace();
        } finally {
            try {
                if(pstmt!=null && !pstmt.isClosed()) {
                    pstmt.close();
                }
            } catch (Exception e2) {}
        }
        return list; 
    }
    
//조건에 맞는 행을 DB에서 수정(갱신)    하는 메서드
    public void updateNaverInfo(int id) {
        String sql = "UPDATE naver_info SET title =? WHERE num =?";
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,"제목수정");
            pstmt.setInt(2,id);
            pstmt.executeUpdate();
            System.out.println("수정된 id: " + id);
            
        } catch (Exception e) {
            System.out.println("update 예외 발생");
            e.printStackTrace();
        }    finally {
            try {
                if(pstmt!=null && !pstmt.isClosed()) {
                    pstmt.close();
                }
            } catch (Exception e2) {}
        }
    }
}
