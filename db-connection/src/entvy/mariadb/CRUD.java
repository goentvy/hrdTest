package entvy.mariadb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import entvy.dto.Album;
import entvy.dto.Artist;
import entvy.dto.Book;
import entvy.dto.Employee;
import entvy.dto.GolfMember;
import entvy.dto.Lesson;
import entvy.dto.Member;
import entvy.dto.Rental;
import entvy.dto.Sale;
import entvy.dto.ShopMember;
import entvy.dto.Usage;

public class CRUD {
	Connection conn = null;
	PreparedStatement pstmt = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	public CRUD() {}
	
	// SQL 테스트 메소드
	public void sqlTest(String sql) {
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql);
	         ResultSet rs = pstmt.executeQuery()) {

	    	ResultSetMetaData meta = rs.getMetaData();
	        int columnCount = meta.getColumnCount();

	        // 컬럼 이름 출력
	        for (int i = 1; i <= columnCount; i++) {
	            System.out.printf("%-20s", meta.getColumnName(i));
	        }
	        System.out.println();

	        // 행 데이터 출력
	        while (rs.next()) {
	            for (int i = 1; i <= columnCount; i++) {
	                System.out.printf("%-20s", rs.getString(i));
	            }
	            System.out.println();
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	// 테이블 생성
	public void createTable(String type) {
		String member = """
			CREATE TABLE Member(
				MemberID INT PRIMARY KEY,
				Name VARCHAR(255) NOT NULL,
				Phone VARCHAR(255),
				Address VARCHAR(255)
			);
		""";
		
		String rental = """
			CREATE TABLE Rental(
				RentalID INT,
				MemberID INT,
				BookID INT,
				RentDate YEAR,
				ReturnDate YEAR,
				PRIMARY KEY(RentalID),
				FOREIGN KEY(MemberID) REFERENCES member(MemberID),
				FOREIGN KEY(BookID) REFERENCES book(BookID)
			);
		""";
		
		String book = """
			CREATE TABLE Book(
				BookID INT AUTO_INCREMENT PRIMARY KEY,
				Title VARCHAR(255) NOT NULL,
				Author VARCHAR(255),
				Publisher VARCHAR(255),
				Price INT,
				PubYear YEAR
			);
		""";
		
		String employee = """
			CREATE TABLE Employee (
			    EmpNo INT PRIMARY KEY AUTO_INCREMENT,
			    EmpName VARCHAR(30) NOT NULL UNIQUE,
			    Dept VARCHAR(20) NOT NULL,
			    HireDate DATE NOT NULL,
			    Salary INT CHECK(Salary >= 2000000)
			);	
		""";
		
		String shopmember = """
		CREATE TABLE ShopMember (
			    CustNo INT PRIMARY KEY AUTO_INCREMENT,
			    CustName VARCHAR(30) NOT NULL,
			    Phone VARCHAR(13) UNIQUE,
			    Address VARCHAR(50),
			    JoinDate DATE NOT NULL,
			    Grade CHAR(1) CHECK(Grade IN ('A', 'B', 'C')),
			    City CHAR(2)
			);
		""";
		
		String sale = """
			CREATE TABLE Sale (
			    SaleNo INT PRIMARY KEY AUTO_INCREMENT,
			    CustNo INT,
			    PCost INT,
			    Amount INT,
			    Price INT,
			    PCode CHAR(3),
			    FOREIGN KEY (CustNo) REFERENCES ShopMember(CustNo)
			);	
		""";
		
		String artist = """
			CREATE TABLE Artist (
			    ArtistNo INT PRIMARY KEY AUTO_INCREMENT,
			    ArtistName VARCHAR(30) NOT NULL UNIQUE,
			    DebutDate DATE NOT NULL,
			    Genre VARCHAR(20) NOT NULL,
			    Agency VARCHAR(30)
			);
		""";
		
		String album = """
			CREATE TABLE Album (
			    AlbumNo INT PRIMARY KEY AUTO_INCREMENT,
			    ArtistNo INT,
			    AlbumTitle VARCHAR(50) NOT NULL,
			    ReleaseDate DATE NOT NULL,
			    Sales INT CHECK(Sales >= 0),
			    FOREIGN KEY (ArtistNo) REFERENCES Artist(ArtistNo)
			);
		""";
		
		String golfmember = """
			CREATE TABLE GolfMember (
			    MNo INT PRIMARY KEY AUTO_INCREMENT,
			    MName VARCHAR(30) NOT NULL,
			    Phone VARCHAR(13) UNIQUE,
			    JoinDate DATE NOT NULL,
			    Grade CHAR(1) CHECK (`Grade` IN ('A', 'B', 'C'))
			);
		""";
		
		String lesson = """
			CREATE TABLE Lesson (
			    LNo INT PRIMARY KEY AUTO_INCREMENT, 
			    MNo INT,
			    Coach VARCHAR(30) NOT NULL, 
			    StartDate DATE NOT NULL, 
			    Fee INT CHECK (Fee >= 0), 
			    FOREIGN KEY (MNo) REFERENCES GolfMember(MNo) 
			);
		""";

		String usage = """
			CREATE TABLE `Usage` (
			    UNo INT PRIMARY KEY AUTO_INCREMENT, 
			    MNo INT, 
			    UDate DATE NOT NULL,
			    UTime INT CHECK (UTime > 0), 
			    Cost INT CHECK (Cost >= 0), 
			    FOREIGN KEY (MNo) REFERENCES GolfMember(MNo) 
			);
		""";
		
		try(Connection conn = DBConnection.getConnection();
			Statement stmt = conn.createStatement()) {
			
			switch (type.toLowerCase()) {
	            case "member" -> stmt.executeUpdate(member);
	            case "rental" -> stmt.executeUpdate(rental);
	            case "book" -> stmt.executeUpdate(book);
	            case "employee" -> stmt.executeUpdate(employee);
	            case "shopmember" -> stmt.executeUpdate(shopmember);
	            case "sale" -> stmt.executeUpdate(sale);
	            case "artist" -> stmt.executeUpdate(artist);
	            case "album" -> stmt.executeUpdate(album);
	            case "golfmember" -> stmt.executeUpdate(golfmember);
	            case "lesson" -> stmt.executeUpdate(lesson);
	            case "usage" -> stmt.executeUpdate(usage);
	            default -> System.out.println("지원하지 않는 테이블 타입입니다: " + type);
	        }
			System.out.println(type + " 테이블 생성 완료");
			
		} catch (Exception e) {
			System.out.println("테이블 생성 실패: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	// member 테이블 조회
	public void selectTable(String type) {
	    String sql = switch (type.toLowerCase()) {
	        case "member" -> "SELECT * FROM member";
	        case "rental" -> "SELECT * FROM rental";
	        case "book" -> "SELECT * FROM book";
	        case "employee" -> "SELECT * FROM employee";
	        case "shopmember" -> "SELECT * FROM shopmember";
	        case "sale" -> "SELECT * FROM sale";
	        case "artist" -> "SELECT * FROM artist";
	        case "album" -> "SELECT * FROM album";
	        case "golfmember" -> "SELECT * FROM golfmember";
	        case "lesson" -> "SELECT * FROM lesson";
	        case "usage" -> "SELECT * FROM `usage`";
	        default -> null;
	    };

	    if (sql == null) {
	        System.out.println("지원하지 않는 테이블입니다: " + type);
	        return;
	    }

	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql);
	         ResultSet rs = pstmt.executeQuery()) {

	        switch (type.toLowerCase()) {
	            case "member" -> {
	                while (rs.next()) {
	                    System.out.printf("회원번호: %d, 이름: %s, 연락처: %s, 주소: %s%n",
	                        rs.getInt("memberid"), rs.getString("name"),
	                        rs.getString("phone"), rs.getString("address"));
	                }
	            }
	            case "rental" -> {
	                while (rs.next()) {
	                    System.out.printf("대출번호: %d, 회원번호: %d, 도서번호: %d, 대출일자: %s, 반납일자: %s%n",
	                        rs.getInt("rentalid"), rs.getInt("memberid"),
	                        rs.getInt("bookid"), rs.getString("rentdate"),
	                        rs.getString("returndate"));
	                }
	            }
	            case "book" -> {
	                while (rs.next()) {
	                    System.out.printf("도서번호: %d, 도서명: %s, 저자: %s, 출판사: %s, 가격: %d, 출판년도: %s%n",
	                        rs.getInt("bookid"), rs.getString("title"),
	                        rs.getString("author"), rs.getString("publisher"),
	                        rs.getInt("price"), rs.getString("pubyear"));
	                }
	            }
	            case "employee" -> {
	                while (rs.next()) {
	                    System.out.printf("사번: %d, 이름: %s, 부서: %s, 입사일: %s, 급여: %d%n",
	                        rs.getInt("empno"), rs.getString("empname"),
	                        rs.getString("dept"), rs.getString("hiredate"),
	                        rs.getInt("salary"));
	                }
	            }
	            case "shopmember" -> {
	                while (rs.next()) {
	                    System.out.printf("고객번호: %d, 이름: %s, 연락처: %s, 주소: %s, 가입일: %s, 등급: %s, 지역: %s%n",
	                        rs.getInt("custno"), rs.getString("custname"),
	                        rs.getString("phone"), rs.getString("address"),
	                        rs.getString("joindate"), rs.getString("grade"),
	                        rs.getString("city"));
	                }
	            }
	            case "sale" -> {
	                while (rs.next()) {
	                    System.out.printf("판매번호: %d, 고객번호: %d, 원가: %d, 수량: %d, 판매가: %d, 상품코드: %s%n",
	                        rs.getInt("saleno"), rs.getInt("custno"),
	                        rs.getInt("pcost"), rs.getInt("amount"),
	                        rs.getInt("price"), rs.getString("pcode"));
	                }
	            }
	            case "artist" -> {
	                while (rs.next()) {
	                    System.out.printf("아티스트번호: %d, 이름: %s, 데뷔일: %s, 장르: %s, 소속사: %s%n",
	                        rs.getInt("artistno"), rs.getString("artistname"),
	                        rs.getString("debutdate"), rs.getString("genre"),
	                        rs.getString("agency"));
	                }
	            }
	            case "album" -> {
	                while (rs.next()) {
	                    System.out.printf("앨범번호: %d, 아티스트번호: %d, 앨범명: %s, 발매일: %s, 판매량: %d%n",
	                        rs.getInt("albumno"), rs.getInt("artistno"),
	                        rs.getString("albumtitle"), rs.getString("releasedate"),
	                        rs.getInt("sales"));
	                }
	            }
	            case "golfmember" -> {
	                while (rs.next()) {
	                    System.out.printf("회원번호: %d, 이름: %s, 연락처: %s, 가입일: %s, 등급: %s%n",
	                        rs.getInt("mno"), rs.getString("mname"),
	                        rs.getString("phone"), rs.getString("joindate"),
	                        rs.getString("grade"));
	                }
	            }
	            case "lesson" -> {
	                while (rs.next()) {
	                    System.out.printf("레슨번호: %d, 회원번호: %d, 코치: %s, 시작일: %s, 수강료: %d%n",
	                        rs.getInt("lno"), rs.getInt("mno"),
	                        rs.getString("coach"), rs.getString("startdate"),
	                        rs.getInt("fee"));
	                }
	            }
	            case "usage" -> {
	                while (rs.next()) {
	                    System.out.printf("이용번호: %d, 회원번호: %d, 이용일: %s, 이용시간: %d, 비용: %d%n",
	                        rs.getInt("uno"), rs.getInt("mno"),
	                        rs.getString("udate"), rs.getInt("utime"),
	                        rs.getInt("cost"));
	                }
	            }
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	// member 테이블 데이터 업데이트
	public void updateData(String type, Object data, String... condition) {
	    try (Connection conn = DBConnection.getConnection()) {
	        switch (type.toLowerCase()) {
	            case "member" -> {
	                String sql = "UPDATE member SET name = ?, address = ? WHERE name = ? AND address = ?";
	                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	                    Member member = (Member) data;
	                    pstmt.setString(1, member.getName());
	                    pstmt.setString(2, member.getAddress());
	                    pstmt.setString(3, condition[0]);
	                    pstmt.setString(4, condition[1]);
	                    int rows = pstmt.executeUpdate();
	                    System.out.printf("Member 수정된 행 수: %d%n", rows);
	                }
	            }
	            case "book" -> {
	                String sql = "UPDATE book SET title = ?, author = ? WHERE title = ?";
	                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	                    Book book = (Book) data;
	                    pstmt.setString(1, book.getTitle());
	                    pstmt.setString(2, book.getAuthor());
	                    pstmt.setString(3, condition[0]);
	                    int rows = pstmt.executeUpdate();
	                    System.out.printf("Book 수정된 행 수: %d%n", rows);
	                }
	            }
	            case "rental" -> {
	                String sql = "UPDATE rental SET returndate = ? WHERE rentalid = ?";
	                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	                    Rental rental = (Rental) data;
	                    pstmt.setString(1, rental.getReturnDate());
	                    pstmt.setInt(2, rental.getRentalID());
	                    int rows = pstmt.executeUpdate();
	                    System.out.printf("Rental 수정된 행 수: %d%n", rows);
	                }
	            }
	            case "employee" -> {
	                String sql = "UPDATE employee SET empname = ?, dept = ?, salary = ? WHERE empno = ?";
	                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	                    Employee emp = (Employee) data;
	                    pstmt.setString(1, emp.getEmpName());
	                    pstmt.setString(2, emp.getDept());
	                    pstmt.setInt(3, emp.getSalary());
	                    pstmt.setInt(4, emp.getEmpNo());
	                    int rows = pstmt.executeUpdate();
	                    System.out.printf("Employee 수정된 행 수: %d%n", rows);
	                }
	            }
	            case "shopmember" -> {
	                String sql = "UPDATE shopmember SET custname = ?, address = ?, grade = ? WHERE custno = ?";
	                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	                    ShopMember sm = (ShopMember) data;
	                    pstmt.setString(1, sm.getCustName());
	                    pstmt.setString(2, sm.getAddress());
	                    pstmt.setString(3, sm.getGrade());
	                    pstmt.setInt(4, sm.getCustNo());
	                    int rows = pstmt.executeUpdate();
	                    System.out.printf("ShopMember 수정된 행 수: %d%n", rows);
	                }
	            }
	            case "sale" -> {
	                String sql = "UPDATE sale SET pcost = ?, amount = ?, price = ? WHERE saleno = ?";
	                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	                    Sale sale = (Sale) data;
	                    pstmt.setInt(1, sale.getPCost());
	                    pstmt.setInt(2, sale.getAmount());
	                    pstmt.setInt(3, sale.getPrice());
	                    pstmt.setInt(4, sale.getSaleNo());
	                    int rows = pstmt.executeUpdate();
	                    System.out.printf("Sale 수정된 행 수: %d%n", rows);
	                }
	            }
	            case "artist" -> {
	                String sql = "UPDATE artist SET artistname = ?, genre = ?, agency = ? WHERE artistno = ?";
	                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	                    Artist artist = (Artist) data;
	                    pstmt.setString(1, artist.getArtistName());
	                    pstmt.setString(2, artist.getGenre());
	                    pstmt.setString(3, artist.getAgency());
	                    pstmt.setInt(4, artist.getArtistNo());
	                    int rows = pstmt.executeUpdate();
	                    System.out.printf("Artist 수정된 행 수: %d%n", rows);
	                }
	            }
	            case "album" -> {
	                String sql = "UPDATE album SET albumtitle = ?, sales = ? WHERE albumno = ?";
	                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	                    Album album = (Album) data;
	                    pstmt.setString(1, album.getAlbumTitle());
	                    pstmt.setInt(2, album.getSales());
	                    pstmt.setInt(3, album.getAlbumNo());
	                    int rows = pstmt.executeUpdate();
	                    System.out.printf("Album 수정된 행 수: %d%n", rows);
	                }
	            }
	            case "golfmember" -> {
	                String sql = "UPDATE golfmember SET mname = ?, phone = ?, grade = ? WHERE mno = ?";
	                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	                    GolfMember gm = (GolfMember) data;
	                    pstmt.setString(1, gm.getMName());
	                    pstmt.setString(2, gm.getPhone());
	                    pstmt.setString(3, gm.getGrade());
	                    pstmt.setInt(4, gm.getMNo());
	                    int rows = pstmt.executeUpdate();
	                    System.out.printf("GolfMember 수정된 행 수: %d%n", rows);
	                }
	            }
	            case "lesson" -> {
	                String sql = "UPDATE lesson SET coach = ?, fee = ? WHERE lno = ?";
	                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	                    Lesson lesson = (Lesson) data;
	                    pstmt.setString(1, lesson.getCoach());
	                    pstmt.setInt(2, lesson.getFee());
	                    pstmt.setInt(3, lesson.getLNo());
	                    int rows = pstmt.executeUpdate();
	                    System.out.printf("Lesson 수정된 행 수: %d%n", rows);
	                }
	            }
	            case "usage" -> {
	                String sql = "UPDATE usage SET utime = ?, cost = ? WHERE uno = ?";
	                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	                    Usage usage = (Usage) data;
	                    pstmt.setInt(1, usage.getUTime());
	                    pstmt.setInt(2, usage.getCost());
	                    pstmt.setInt(3, usage.getUNo());
	                    int rows = pstmt.executeUpdate();
	                    System.out.printf("Usage 수정된 행 수: %d%n", rows);
	                }
	            }
	            default -> System.out.println("지원하지 않는 타입입니다: " + type);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	// member 테이블 데이터 전체 삭제
	public void deleteData(String type) {
	    String sql = switch (type.toLowerCase()) {
	        case "member"      -> "DELETE FROM member";
	        case "book"        -> "DELETE FROM book";
	        case "rental"      -> "DELETE FROM rental";
	        case "employee"    -> "DELETE FROM employee";
	        case "shopmember"  -> "DELETE FROM shopmember";
	        case "sale"        -> "DELETE FROM sale";
	        case "artist"      -> "DELETE FROM artist";
	        case "album"       -> "DELETE FROM album";
	        case "golfmember"  -> "DELETE FROM golfmember";
	        case "lesson"      -> "DELETE FROM lesson";
	        case "usage"       -> "DELETE FROM usage";
	        default            -> null;
	    };

	    if (sql == null) {
	        System.out.println("지원하지 않는 테이블입니다: " + type);
	        return;
	    }

	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        int rows = pstmt.executeUpdate();
	        System.out.printf("%s 테이블에서 삭제된 행 수: %d%n", type, rows);

	    } catch (Exception e) {
	        System.out.println("삭제 실패: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	// 대량 데이터 삽입 Batch
	public void insertDataBatch(String type, List<?> dataList) {
	    try (Connection conn = DBConnection.getConnection()) {
	        conn.setAutoCommit(false); // 트랜잭션 시작

	        switch (type.toLowerCase()) {
	            case "member" -> {
	                String sql = "INSERT INTO member(memberid, name, phone, address) VALUES(?, ?, ?, ?)";
	                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	                    for (Object obj : dataList) {
	                        Member member = (Member) obj;
	                        pstmt.setInt(1, member.getMemberid());
	                        pstmt.setString(2, member.getName());
	                        pstmt.setString(3, member.getPhone());
	                        pstmt.setString(4, member.getAddress());
	                        pstmt.addBatch();
	                    }
	                    int[] results = pstmt.executeBatch();
	                    conn.commit();
	                    System.out.printf("Member batch 삽입 완료 (%d건)%n", results.length);
	                }
	            }

	            case "book" -> {
	                String sql = "INSERT INTO book(title, author, publisher, price, pubyear) VALUES(?, ?, ?, ?, ?)";
	                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	                    for (Object obj : dataList) {
	                        Book book = (Book) obj;
	                        pstmt.setString(1, book.getTitle());
	                        pstmt.setString(2, book.getAuthor());
	                        pstmt.setString(3, book.getPublisher());
	                        pstmt.setInt(4, book.getPrice());
	                        pstmt.setString(5, book.getPubYear());
	                        pstmt.addBatch();
	                    }
	                    int[] results = pstmt.executeBatch();
	                    conn.commit();
	                    System.out.printf("Book batch 삽입 완료 (%d건)%n", results.length);
	                }
	            }

	            case "rental" -> {
	                String sql = "INSERT INTO rental(rentalid, memberid, bookid, rentdate, returndate) VALUES(?, ?, ?, ?, ?)";
	                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	                    for (Object obj : dataList) {
	                        Rental rental = (Rental) obj;
	                        pstmt.setInt(1, rental.getRentalID());
	                        pstmt.setInt(2, rental.getMemberID());
	                        pstmt.setInt(3, rental.getBookID());
	                        pstmt.setString(4, rental.getRentDate());
	                        pstmt.setString(5, rental.getReturnDate());
	                        pstmt.addBatch();
	                    }
	                    int[] results = pstmt.executeBatch();
	                    conn.commit();
	                    System.out.printf("Rental batch 삽입 완료 (%d건)%n", results.length);
	                }
	            }

	            case "employee" -> {
	                String sql = "INSERT INTO employee(empname, dept, hiredate, salary) VALUES(?, ?, ?, ?)";
	                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	                    for (Object obj : dataList) {
	                        Employee emp = (Employee) obj;
	                        pstmt.setString(1, emp.getEmpName());
	                        pstmt.setString(2, emp.getDept());
	                        pstmt.setString(3, emp.getHireDate());
	                        pstmt.setInt(4, emp.getSalary());
	                        pstmt.addBatch();
	                    }
	                    int[] results = pstmt.executeBatch();
	                    conn.commit();
	                    System.out.printf("Employee batch 삽입 완료 (%d건)%n", results.length);
	                }
	            }

	            case "shopmember" -> {
	                String sql = "INSERT INTO shopmember(custname, phone, address, joindate, grade, city) VALUES(?, ?, ?, ?, ?, ?)";
	                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	                    for (Object obj : dataList) {
	                        ShopMember sm = (ShopMember) obj;
	                        pstmt.setString(1, sm.getCustName());
	                        pstmt.setString(2, sm.getPhone());
	                        pstmt.setString(3, sm.getAddress());
	                        pstmt.setString(4, sm.getJoinDate());
	                        pstmt.setString(5, sm.getGrade());
	                        pstmt.setString(6, sm.getCity());
	                        pstmt.addBatch();
	                    }
	                    int[] results = pstmt.executeBatch();
	                    conn.commit();
	                    System.out.printf("ShopMember batch 삽입 완료 (%d건)%n", results.length);
	                }
	            }

	            case "sale" -> {
	                String sql = "INSERT INTO sale(custno, pcost, amount, price, pcode) VALUES(?, ?, ?, ?, ?)";
	                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	                    for (Object obj : dataList) {
	                        Sale sale = (Sale) obj;
	                        pstmt.setInt(1, sale.getCustNo());
	                        pstmt.setInt(2, sale.getPCost());
	                        pstmt.setInt(3, sale.getAmount());
	                        pstmt.setInt(4, sale.getPrice());
	                        pstmt.setString(5, sale.getPCode());
	                        pstmt.addBatch();
	                    }
	                    int[] results = pstmt.executeBatch();
	                    conn.commit();
	                    System.out.printf("Sale batch 삽입 완료 (%d건)%n", results.length);
	                }
	            }

	            case "artist" -> {
	                String sql = "INSERT INTO artist(artistname, debutdate, genre, agency) VALUES(?, ?, ?, ?)";
	                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	                    for (Object obj : dataList) {
	                        Artist artist = (Artist) obj;
	                        pstmt.setString(1, artist.getArtistName());
	                        pstmt.setString(2, artist.getDebutDate());
	                        pstmt.setString(3, artist.getGenre());
	                        pstmt.setString(4, artist.getAgency());
	                        pstmt.addBatch();
	                    }
	                    int[] results = pstmt.executeBatch();
	                    conn.commit();
	                    System.out.printf("Artist batch 삽입 완료 (%d건)%n", results.length);
	                }
	            }

	            case "album" -> {
	                String sql = "INSERT INTO album(artistno, albumtitle, releasedate, sales) VALUES(?, ?, ?, ?)";
	                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	                    for (Object obj : dataList) {
	                        Album album = (Album) obj;
	                        pstmt.setInt(1, album.getArtistNo());
	                        pstmt.setString(2, album.getAlbumTitle());
	                        pstmt.setString(3, album.getReleaseDate());
	                        pstmt.setInt(4, album.getSales());
	                        pstmt.addBatch();
	                    }
	                    int[] results = pstmt.executeBatch();
	                    conn.commit();
	                    System.out.printf("Album batch 삽입 완료 (%d건)%n", results.length);
	                }
	            }

	            case "golfmember" -> {
	                String sql = "INSERT INTO golfmember(mname, phone, joindate, grade) VALUES(?, ?, ?, ?)";
	                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	                    for (Object obj : dataList) {
	                        GolfMember gm = (GolfMember) obj;
	                        pstmt.setString(1, gm.getMName());
	                        pstmt.setString(2, gm.getPhone());
	                        pstmt.setString(3, gm.getJoinDate());
	                        pstmt.setString(4, gm.getGrade());
	                        pstmt.addBatch();
	                    }
	                    int[] results = pstmt.executeBatch();
	                    conn.commit();
	                    System.out.printf("GolfMember batch 삽입 완료 (%d건)%n", results.length);
	                }
	            }

	            case "lesson" -> {
	                String sql = "INSERT INTO lesson(mno, coach, startdate, fee) VALUES(?, ?, ?, ?)";
	                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	                    for (Object obj : dataList) {
	                        Lesson lesson = (Lesson) obj;
	                        pstmt.setInt(1, lesson.getMNo());
	                        pstmt.setString(2, lesson.getCoach());
	                        pstmt.setString(3, lesson.getStartDate());
	                        pstmt.setInt(4, lesson.getFee());
	                        pstmt.addBatch();
	                    }
	                    int[] results = pstmt.executeBatch();
	                    conn.commit();
	                    System.out.printf("Lesson batch 삽입 완료 (%d건)%n", results.length);
	                }
	            }

	            case "usage" -> {
	                String sql = "INSERT INTO usage(mno, udate, utime, cost) VALUES(?, ?, ?, ?)";
	                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	                    for (Object obj : dataList) {
	                        Usage usage = (Usage) obj;
	                        pstmt.setInt(1, usage.getMNo());
	                        pstmt.setString(2, usage.getUDate());
	                        pstmt.setInt(3, usage.getUTime());
	                        pstmt.setInt(4, usage.getCost());
	                        pstmt.addBatch();
	                    }
	                    int[] results = pstmt.executeBatch();
	                    conn.commit();
	                    System.out.printf("Usage batch 삽입 완료 (%d건)%n", results.length);
	                }
	            }

	            default -> System.out.println("지원하지 않는 타입입니다: " + type);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	
	// 테이블 데이터 삽입
	public void insertData(String type, Object data) {
	    try (Connection conn = DBConnection.getConnection()) {
	        switch (type.toLowerCase()) {
	            case "member" -> {
	                String sql = "INSERT INTO member(memberid, name, phone, address) VALUES(?, ?, ?, ?)";
	                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	                    Member member = (Member) data;
	                    pstmt.setInt(1, member.getMemberid());
	                    pstmt.setString(2, member.getName());
	                    pstmt.setString(3, member.getPhone());
	                    pstmt.setString(4, member.getAddress());
	                    pstmt.executeUpdate();
	                    System.out.println("Member 삽입 완료");
	                }
	            }
	            case "book" -> {
	                String sql = "INSERT INTO book(title, author, publisher, price, pubyear) VALUES(?, ?, ?, ?, ?)";
	                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	                    Book book = (Book) data;
	                    pstmt.setString(1, book.getTitle());
	                    pstmt.setString(2, book.getAuthor());
	                    pstmt.setString(3, book.getPublisher());
	                    pstmt.setInt(4, book.getPrice());
	                    pstmt.setString(5, book.getPubYear());
	                    pstmt.executeUpdate();
	                    System.out.println("Book 삽입 완료");
	                }
	            }
	            case "rental" -> {
	                String sql = "INSERT INTO rental(rentalid, memberid, bookid, rentdate, returndate) VALUES(?, ?, ?, ?, ?)";
	                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	                    Rental rental = (Rental) data;
	                    pstmt.setInt(1, rental.getRentalID());
	                    pstmt.setInt(2, rental.getMemberID());
	                    pstmt.setInt(3, rental.getBookID());
	                    pstmt.setString(4, rental.getRentDate());
	                    pstmt.setString(5, rental.getReturnDate());
	                    pstmt.executeUpdate();
	                    System.out.println("Rental 삽입 완료");
	                }
	            }
	            case "employee" -> {
	                String sql = "INSERT INTO employee(empname, dept, hiredate, salary) VALUES(?, ?, ?, ?)";
	                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	                    Employee emp = (Employee) data;
	                    pstmt.setString(1, emp.getEmpName());
	                    pstmt.setString(2, emp.getDept());
	                    pstmt.setString(3, emp.getHireDate());
	                    pstmt.setInt(4, emp.getSalary());
	                    pstmt.executeUpdate();
	                    System.out.println("Employee 삽입 완료");
	                }
	            }
	            case "shopmember" -> {
	                String sql = "INSERT INTO shopmember(custname, phone, address, joindate, grade, city) VALUES(?, ?, ?, ?, ?, ?)";
	                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	                    ShopMember sm = (ShopMember) data;
	                    pstmt.setString(1, sm.getCustName());
	                    pstmt.setString(2, sm.getPhone());
	                    pstmt.setString(3, sm.getAddress());
	                    pstmt.setString(4, sm.getJoinDate());
	                    pstmt.setString(5, sm.getGrade());
	                    pstmt.setString(6, sm.getCity());
	                    pstmt.executeUpdate();
	                    System.out.println("ShopMember 삽입 완료");
	                }
	            }
	            case "sale" -> {
	                String sql = "INSERT INTO sale(custno, pcost, amount, price, pcode) VALUES(?, ?, ?, ?, ?)";
	                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	                    Sale sale = (Sale) data;
	                    pstmt.setInt(1, sale.getCustNo());
	                    pstmt.setInt(2, sale.getPCost());
	                    pstmt.setInt(3, sale.getAmount());
	                    pstmt.setInt(4, sale.getPrice());
	                    pstmt.setString(5, sale.getPCode());
	                    pstmt.executeUpdate();
	                    System.out.println("Sale 삽입 완료");
	                }
	            }
	            case "artist" -> {
	                String sql = "INSERT INTO artist(artistname, debutdate, genre, agency) VALUES(?, ?, ?, ?)";
	                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	                    Artist artist = (Artist) data;
	                    pstmt.setString(1, artist.getArtistName());
	                    pstmt.setString(2, artist.getDebutDate());
	                    pstmt.setString(3, artist.getGenre());
	                    pstmt.setString(4, artist.getAgency());
	                    pstmt.executeUpdate();
	                    System.out.println("Artist 삽입 완료");
	                }
	            }
	            case "album" -> {
	                String sql = "INSERT INTO album(artistno, albumtitle, releasedate, sales) VALUES(?, ?, ?, ?)";
	                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	                    Album album = (Album) data;
	                    pstmt.setInt(1, album.getArtistNo());
	                    pstmt.setString(2, album.getAlbumTitle());
	                    pstmt.setString(3, album.getReleaseDate());
	                    pstmt.setInt(4, album.getSales());
	                    pstmt.executeUpdate();
	                    System.out.println("Album 삽입 완료");
	                }
	            }
	            case "golfmember" -> {
	                String sql = "INSERT INTO golfmember(mname, phone, joindate, grade) VALUES(?, ?, ?, ?)";
	                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	                    GolfMember gm = (GolfMember) data;
	                    pstmt.setString(1, gm.getMName());
	                    pstmt.setString(2, gm.getPhone());
	                    pstmt.setString(3, gm.getJoinDate());
	                    pstmt.setString(4, gm.getGrade());
	                    pstmt.executeUpdate();
	                    System.out.println("GolfMember 삽입 완료");
	                }
	            }
	            case "lesson" -> {
	                String sql = "INSERT INTO lesson(mno, coach, startdate, fee) VALUES(?, ?, ?, ?)";
	                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	                    Lesson lesson = (Lesson) data;
	                    pstmt.setInt(1, lesson.getMNo());
	                    pstmt.setString(2, lesson.getCoach());
	                    pstmt.setString(3, lesson.getStartDate());
	                    pstmt.setInt(4, lesson.getFee());
	                    pstmt.executeUpdate();
	                    System.out.println("Lesson 삽입 완료");
	                }
	            }
	            case "usage" -> {
	                String sql = "INSERT INTO usage(mno, udate, utime, cost) VALUES(?, ?, ?, ?)";
	                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	                    Usage usage = (Usage) data;
	                    pstmt.setInt(1, usage.getMNo());
	                    pstmt.setString(2, usage.getUDate());
	                    pstmt.setInt(3, usage.getUTime());
	                    pstmt.setInt(4, usage.getCost());
	                    pstmt.executeUpdate();
	                    System.out.println("Usage 삽입 완료");
	                }
	            }
	            default -> System.out.println("지원하지 않는 타입입니다: " + type);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	// 테이블 삭제
	public void dropTable(String table) {
		table = table.toLowerCase();
		if(!table.matches("^[a-zA-Z0-9_]+$")) {
			throw new IllegalArgumentException("허용되지 않는 테이블 이름입니다.");
		}
		List<String> allowedTables = List.of("member", "book", "rental", "employee", "shopmember", "sale", "artist", "album", "golfmember", "lesson", "usage");
		if (!allowedTables.contains(table.toLowerCase())) {
		    throw new IllegalArgumentException("삭제가 허용되지 않은 테이블입니다.");
		}
		String sql = "DROP TABLE IF EXISTS " + table;
		
		try(Connection conn = DBConnection.getConnection();
			Statement stmt = conn.createStatement()) {
			
			stmt.executeUpdate(sql);
			System.out.println("테이블 삭제 완료. " + table);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
