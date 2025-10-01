package entvy.main;

import entvy.mariadb.CRUD;

public class GolfMain {

	public static void main(String[] args) {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			
			CRUD crud = new CRUD();
			
			// TEST SQL
			String sql = """
					select b.*, r.rentdate
					from rental r
					join book b on r.bookid = b.bookid
					where r.rentdate >= '2020'
					order by b.bookid asc
					""";
			
			// SQL 출력
			crud.sqlTest(sql);
			
			// Create - 테이블 생성 
//			crud.createTable("golfmember");
//			crud.createTable("lesson");
//			crud.createTable("usage");
			// Read - 테이블 조회
//			crud.selectTable("golfmember");
//			crud.selectTable("lesson");
//			crud.selectTable("usage");
			// Update - 테이블 데이터 수정
//			crud.updateData("golfmember");
			// Delete - 테이블 데이터 삭제
//			crud.deleteData("usage");
			// Data Insert - 테이블 데이터 삽입
//			crud.insertData("usage", usage);
			// Table Drop - 테이블 삭제
//			crud.dropTable("usage");
//			crud.dropTable("lesson");
//			crud.dropTable("golfmember");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
