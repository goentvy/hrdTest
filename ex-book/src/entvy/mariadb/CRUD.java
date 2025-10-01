package entvy.mariadb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import entvy.tableclass.Book;
import entvy.tableclass.Member;
import entvy.tableclass.Rental;

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
		
		try(Connection conn = DBConnection.getConnection();
			Statement stmt = conn.createStatement()) {
			
			switch (type.toLowerCase()) {
	            case "member" -> stmt.executeUpdate(member);
	            case "rental" -> stmt.executeUpdate(rental);
	            case "book" -> stmt.executeUpdate(book);
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
	                    pstmt.setString(3, condition[0]); // 기존 name
	                    pstmt.setString(4, condition[1]); // 기존 address
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
	                    pstmt.setString(3, condition[0]); // 기존 title
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
	            default -> System.out.println("지원하지 않는 타입입니다: " + type);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	// member 테이블 데이터 전체 삭제
	public void deleteData(String type) {
	    String sql = switch (type.toLowerCase()) {
	        case "member" -> "DELETE FROM member";
	        case "book" -> "DELETE FROM book";
	        case "rental" -> "DELETE FROM rental";
	        default -> null;
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

                default -> System.out.println("지원하지 않는 타입입니다: " + type);
            }

        } catch (Exception e) {
        	try {
                if (conn != null) conn.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }

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
		List<String> allowedTables = List.of("member", "book", "rental");
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
