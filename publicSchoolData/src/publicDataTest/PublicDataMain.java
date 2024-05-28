package publicDataTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class PublicDataMain {
	public static Scanner input = new Scanner(System.in);
	public static void main(String[] args) {
		//웹 접속을 통해서 버스정보를 ArrayList에 담아옴.
		ArrayList<DataInfo> dataList = null;
		ArrayList<DataInfo> dataSelectList = null;
		boolean exitFlag = false;
		while(!exitFlag) {
			System.out.println("1.웹정보가져오기 2.저장하기 3.테이블읽어오기 4.수정하기 5.삭제하기 6.검색하기 7.종료");
			System.out.print("선택>>");
			int count = Integer.parseInt(input.nextLine());
			switch (count) {
			case 1:
				dataList = webConnection(); 
				break;
			case 2:
				if(dataList.size() < 1) {
					System.out.println("공공데이터로부터 가져온 자료가 없습니다.");
					continue;
				}
				insertDataInfo(dataList);
				break;
			case 3:
				dataSelectList = selectDataInfo();
				printDataInfo(dataSelectList);
				break;
			case 4:
				int sn = updateInputSn();
				if(sn != 0) {
					updateDataInfo(sn);
				}
				break;
			case 5:
				deleteDataInfo();
				break;
			case 6:
				searchDataInfo();
				break;
			case 7:
				System.out.println("종료");
				exitFlag = true;
				break;
			default:
				break;
			}
		}
		System.out.println("the end");		
	}

	public static ArrayList<DataInfo> webConnection() {		
		// 1. 요청 url 생성
		ArrayList<DataInfo> list = new ArrayList<>();
        StringBuilder urlBuilder = new StringBuilder(
                "https://apis.data.go.kr/B552584/UlfptcaAlarmInqireSvc/getUlfptcaAlarmInfo");
        try {
            urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8")
                    + "=zubZsWyZ0K2Fr68GbdsDwSk2QAbk74mDgrFYHNAbHDkR7dMcqCjJoFLM2yp1sJEDTEfz8Eb7PxeYiE0e8kxZMQ%3D%3D");
            urlBuilder.append("&" + URLEncoder.encode("returnType", "UTF-8") + "="
                    + URLEncoder.encode("xml", "UTF-8")); 
            urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "="
                    + URLEncoder.encode("100", "UTF-8")); 
            urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "="
                    + URLEncoder.encode("1", "UTF-8"));      
            urlBuilder.append("&" + URLEncoder.encode("year", "UTF-8") + "="
                    + URLEncoder.encode("2020", "UTF-8")); 
            urlBuilder.append("&" + URLEncoder.encode("itemCode", "UTF-8") + "="
                    + URLEncoder.encode("PM10", "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 2. 서버주소에 Connection 객체 생성 연결
        URL url = null;
        HttpURLConnection conn = null;
        try {
            url = new URL(urlBuilder.toString());
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            System.out.println("Response code: " + conn.getResponseCode());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 3. 요청내용을 전송 및 응답처리
        BufferedReader br = null;
        try {
            int statusCode = conn.getResponseCode();
            System.out.println(statusCode);
            if (statusCode >= 200 && statusCode <= 300) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            Document doc = parseXML(conn.getInputStream());
            // a. field 태그객체 목록으로 가져온다.
            NodeList descNodes = doc.getElementsByTagName("item");
            // b. Corona19Data List객체 생성
            //List<DataInfo> list = new ArrayList<>();
            // c. 각 item 태그의 자식태그에서 정보 가져오기
            for (int i = 0; i < descNodes.getLength(); i++) {
                // item
                Node item = descNodes.item(i);
                DataInfo data = new DataInfo();
                // item 자식태그에 순차적으로 접근
                for (Node node = item.getFirstChild(); node != null; node = node.getNextSibling()) {
                    System.out.println(node.getNodeName() + " : " + node.getTextContent());

                    switch (node.getNodeName()) {
	                    case "clearVal":
	                        data.setClearVal(Integer.parseInt(node.getTextContent()));
	                        break;
	                    case "sn":
	                        data.setSn(Integer.parseInt(node.getTextContent()));
	                        break;
	                    case "districtName":
	                        data.setDistrictName(node.getTextContent());
	                        break;
	                    case "dataDate":
	                        data.setDataDate(node.getTextContent());
	                        break;
	                    case "issueVal":
	                        data.setIssueVal(Integer.parseInt(node.getTextContent()));
	                        break;
	                    case "issueTime":
	                        data.setIssueTime(node.getTextContent());
	                        break;
	                    case "clearDate":
	                        data.setClearDate(node.getTextContent());
	                        break;
	                    case "issueDate":
	                        data.setIssueDate(node.getTextContent());
	                        break;
	                    case "moveName":
	                        data.setMoveName(node.getTextContent());
	                        break;
	                    case "clearTime":
	                        data.setClearTime(node.getTextContent());
	                        break;
	                    case "issueGbn":
	                        data.setIssueGbn(node.getTextContent());
	                        break;
	                    case "itemCode":
	                        data.setItemCode(node.getTextContent());
	                        break;
                    }
                }
                // d. List객체에 추가
                list.add(data);
            }
            // e.최종확인
            for (DataInfo d : list) {
                System.out.println(d.toString());
            }
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
	}
	
	private static Document parseXML(InputStream inputStream) {
		DocumentBuilderFactory objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder objDocumentBuilder = null;
        Document doc = null;
        try {
            objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();
            doc = objDocumentBuilder.parse(inputStream);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) { // Simple API for XML e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
	}
	
	//공공데이터를 테이블로 저장하기
	public static void insertDataInfo(ArrayList<DataInfo> dataList) {
		if(dataList.size() < 1) {
			System.out.println("입력할 데이터가 없습니다.");
			return;
		}
		//저장하기 전에 테이블에 있는 모든 내용을 삭제
		deleteDataInfo();
		
		Connection con = null;
		PreparedStatement pstmt = null;		
		
		try {
			con = DBUtil.makeConnection();
			for(DataInfo data : dataList) {
				String sql = "insert into datainfo values(?,?,?,?,?,?,?,?,?,?,?,?,null)";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, data.getSn());
				pstmt.setString(2, data.getDistrictName());
				pstmt.setString(3, data.getDataDate());
				pstmt.setInt(4, data.getIssueVal());
				pstmt.setString(5, data.getIssueTime());
				pstmt.setString(6, data.getClearDate());
				pstmt.setString(7, data.getIssueDate());
				pstmt.setString(8, data.getMoveName());
				pstmt.setString(9, data.getClearTime());
				pstmt.setString(10, data.getIssueGbn());
				pstmt.setString(11, data.getItemCode());
				pstmt.setInt(12, data.getClearVal());
								
				int i = pstmt.executeUpdate();
				
				if(i == 1) {
					System.out.println(data.getDistrictName() + ": 데이터 등록 완료.");
				} else {
					System.out.println("데이터 등록 실패.");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt!=null) pstmt.close();
				if(con!=null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}
	
	//데이터정보 가져오기
	public static ArrayList<DataInfo> selectDataInfo() {
		ArrayList<DataInfo> dataInfoList = null;
		String sql = "select * from datainfo";
        Connection con = null; 
        PreparedStatement pstmt = null; 
        ResultSet rs = null; 
        try {
            con = DBUtil.makeConnection();
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            dataInfoList = new ArrayList<DataInfo>();
            while(rs.next()) {
            	DataInfo vo = new DataInfo();
                vo.setSn(rs.getInt("sn"));
                vo.setDistrictName(rs.getString("districtName"));
                vo.setDataDate(rs.getString("dataDate"));
                vo.setIssueVal(rs.getInt("issueVal"));
                vo.setIssueTime(rs.getString("issueTime"));
                vo.setClearDate(rs.getString("clearDate"));
                vo.setIssueDate(rs.getString("issueDate"));
                vo.setMoveName(rs.getString("moveName"));
                vo.setClearTime(rs.getString("clearTime"));
                vo.setIssueGbn(rs.getString("issueGbn"));
                vo.setItemCode(rs.getString("itemCode"));
                vo.setClearVal(rs.getInt("clearVal"));
                vo.setCurdate(rs.getString("curdate"));
                dataInfoList.add(vo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(rs != null) {
                    rs.close();
                }
                if(pstmt != null) {
                    pstmt.close();
                }
                if(con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dataInfoList;
	} 

	//정보 출력하기
	public static void printDataInfo(ArrayList<DataInfo> dataInfoSelectList) {
		if(dataInfoSelectList.size() < 1) {
			System.out.println("출력할 정보가 없습니다.");
			return;
		}
		for(DataInfo data: dataInfoSelectList) {
			System.out.println(data.toString());
		}
	}
	
	//수정할 sn 번호를 보여주기
	public static int updateInputSn() {
		ArrayList<DataInfo> busInfoSelectList = selectDataInfo();
		printDataInfo(busInfoSelectList);
		System.out.print("update sn>>");
		int sn = Integer.parseInt(input.nextLine());
		return sn;
	}
	
	//정보 수정하기
	public static void updateDataInfo(int sn) {
		String sql = "UPDATE datainfo SET curdate = sysdate where sn = ?";
        Connection con = null; 
        PreparedStatement pstmt = null; 
        try {
            con = DBUtil.makeConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, sn);
            int value = pstmt.executeUpdate();

            if(value == 1) {
                System.out.println(sn + " : 수정완료");
            }else {
                System.out.println(sn + " : 수정실패");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(pstmt != null) {
                    pstmt.close();
                }
                if(con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
	}
	
	//공공데이터 정보 삭제하기
	public static void deleteDataInfo() {
		int count = getCountDataInfo();
		if(count == 0) {
			System.out.println("버스정보내용이 없습니다.");
			return;
		}
		String sql = "delete from datainfo";
		Connection con = null;
		PreparedStatement pstmt = null;						
        try {
            con = DBUtil.makeConnection();
            pstmt = con.prepareStatement(sql);
            int value = pstmt.executeUpdate();

            if(value != 0) {
                System.out.println("모든 정보 삭제완료");
            }else {
                System.out.println("모든 정보 삭제실패");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(pstmt != null) {
                    pstmt.close();
                }
                if(con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
		
	}
	
	public static int getCountDataInfo() {
		int count = 0;
	    String sql = "select count(*) as cnt from datainfo";
	    Connection con = null; 
	    PreparedStatement pstmt = null; 
	    ResultSet rs = null; 
	    try {
	        con = DBUtil.makeConnection();
	        pstmt = con.prepareStatement(sql);
	        rs = pstmt.executeQuery();
	
	        if(rs.next()) {
	            count = rs.getInt("cnt");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if(rs != null) {
	                rs.close();
	            }
	            if(pstmt != null) {
	                pstmt.close();
	            }
	            if(con != null) {
	                con.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return count;
	}
	
	//검색하기
	public static void searchDataInfo() {
		ArrayList<DataInfo> dataInfoList = null;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("검색할 지역 >> ");
		String name = sc.nextLine();
		
		String sql = "select * from datainfo where DISTRICTNAME = ? order by sn";
	    Connection con = null; 
	    PreparedStatement pstmt = null; 	    
	    ResultSet rs = null; 
	    dataInfoList = new ArrayList<DataInfo>();
	    try {
	        con = DBUtil.makeConnection();
	        pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, name);
	        rs = pstmt.executeQuery();
	
	        while(rs.next()) {
	        	DataInfo vo = new DataInfo();
                vo.setSn(rs.getInt("sn"));
                vo.setDistrictName(rs.getString("districtName"));
                vo.setDataDate(rs.getString("dataDate"));
                vo.setIssueVal(rs.getInt("issueVal"));
                vo.setIssueTime(rs.getString("issueTime"));
                vo.setClearDate(rs.getString("clearDate"));
                vo.setIssueDate(rs.getString("issueDate"));
                vo.setMoveName(rs.getString("moveName"));
                vo.setClearTime(rs.getString("clearTime"));
                vo.setIssueGbn(rs.getString("issueGbn"));
                vo.setItemCode(rs.getString("itemCode"));
                vo.setClearVal(rs.getInt("clearVal"));
                vo.setCurdate(rs.getString("curdate"));
                dataInfoList.add(vo);
	        }
	        if(dataInfoList.size() < 1) {
	        	System.out.println("검색한 지역정보가 없습니다.");
	        }
			for(DataInfo data: dataInfoList) {
				System.out.println(data.toString());
			}
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if(rs != null) {
	                rs.close();
	            }
	            if(pstmt != null) {
	                pstmt.close();
	            }
	            if(con != null) {
	                con.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
		
		
	}
	
}
