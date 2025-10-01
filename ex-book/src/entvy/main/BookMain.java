package entvy.main;

import entvy.dto.Member;
import entvy.mariadb.CRUD;

public class BookMain {

	public static void main(String[] args) {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			
			CRUD crud = new CRUD();
			
			// TEST SQL
//			String sql = """
//					select b.*, r.rentdate
//					from rental r
//					join book b on r.bookid = b.bookid
//					where r.rentdate >= '2020'
//					order by b.bookid asc
//					""";
//			
//			// SQL 출력
//			crud.sqlTest(sql);
			
			// Create - 테이블 생성 
//			crud.createTable("member");
//			crud.createTable("book");
//			crud.createTable("rental");
			
			// Read - 테이블 조회
//			crud.selectTable("member");
//			crud.selectTable("book");
//			crud.selectTable("rental");
			
			// Update - 테이블 데이터 수정
//			Member updatedMember = new Member(1, "홍길동", "010-1234-5678", "서울시 강남구");
//			crud.updateData("member", updatedMember, "김철수", "부산시 해운대구");

			// Delete - 테이블 데이터 삭제
//			crud.deleteData("rental");
//			crud.deleteData("book");
//			crud.deleteData("member");
			
			// Data Insert - 테이블 데이터 삽입
//			crud.insertData("rental", rental);
			
			// Table Drop - 테이블 삭제
//			crud.dropTable("rental");
//			crud.dropTable("book");
//			crud.dropTable("member");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
