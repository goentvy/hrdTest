package entvy.dto;

import java.util.List;

import entvy.mariadb.CRUD;

public class BookDataList {
	public static void main(String[] args) {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			
			CRUD crud = new CRUD();
			
			// -- < 도서관리 Book > -- Member(회원) 테이블
			List<Member> members = List.of(
			    new Member(1, "이정재", "010-5555-5555", "LeeJungJae@naver.com"),
			    new Member(2, "김혜수", "010-1234-5678", "KimHyeSoo@gmail.com"),
			    new Member(3, "박서준", "010-8765-4321", "ParkSeoJun@daum.net"),
			    new Member(4, "전지현", "010-1111-2222", "JunJiHyun@naver.com"),
			    new Member(5, "송중기", "010-3333-4444", "SongJoongKi@hanmail.net"),
			    new Member(6, "한소희", "010-5555-6666", "HanSoHee@gmail.com"),
			    new Member(7, "정우성", "010-7777-8888", "JungWooSung@naver.com"),
			    new Member(8, "수지", "010-9999-0000", "Suzy@daum.net"),
			    new Member(9, "이병헌", "010-1212-3434", "LeeByungHun@hanmail.net"),
			    new Member(10, "아이유", "010-5656-7878", "IU@naver.com"),
			    new Member(11, "박보검", "010-9090-1010", "ParkBoGum@gmail.com"),
			    new Member(12, "김태리", "010-2323-4545", "KimTaeRi@daum.net"),
			    new Member(13, "조인성", "010-6767-8989", "JoInSung@naver.com"),
			    new Member(14, "윤아", "010-3030-5050", "Yoona@hanmail.net"),
			    new Member(15, "강동원", "010-7373-9191", "KangDongWon@gmail.com"),
			    new Member(16, "김우빈", "010-1414-1616", "KimWooBin@naver.com"),
			    new Member(17, "신민아", "010-1717-1919", "ShinMinAh@daum.net"),
			    new Member(18, "이민호", "010-2020-2222", "LeeMinHo@hanmail.net"),
			    new Member(19, "박신혜", "010-2323-2525", "ParkShinHye@gmail.com"),
			    new Member(20, "정해인", "010-2626-2828", "JungHaeIn@naver.com")
			);
			
			crud.insertDataBatch("member", members);
			
			// -- < 도서관리 Book > -- Book(도서) 테이블
		    List<Book> books = List.of(
		        new Book(1, "나혼자만레벨업", "추공", "디앤씨미디어", 7000, "2016"),
		        new Book(2, "해리포터와 마법사의 돌", "J.K. 롤링", "문학동네", 12000, "1997"),
		        new Book(3, "데미안", "헤르만 헤세", "민음사", 9800, "2000"),
		        new Book(4, "어린 왕자", "생텍쥐페리", "열린책들", 8500, "1943"),
		        new Book(5, "총, 균, 쇠", "재레드 다이아몬드", "문학사상", 15000, "2005"),
		        new Book(6, "미드나잇 라이브러리", "매트 헤이그", "인플루엔셜", 13800, "2021"),
		        new Book(7, "죽음에 관하여", "엘리자베스 퀴블러 로스", "청림출판", 11000, "1998"),
		        new Book(8, "파친코", "이민진", "문학동네", 16000, "2017"),
		        new Book(9, "1984", "조지 오웰", "민음사", 9000, "1949"),
		        new Book(10, "자기 앞의 생", "로맹 가리", "열린책들", 9500, "1975"),
		        new Book(11, "멋진 신세계", "올더스 헉슬리", "문예출판사", 9700, "1932"),
		        new Book(12, "월든", "헨리 데이비드 소로", "동서문화사", 10500, "1901"),
		        new Book(13, "지적 대화를 위한 넓고 얕은 지식", "채사장", "웨일북", 14500, "2014"),
		        new Book(14, "코스모스", "칼 세이건", "사이언스북스", 18000, "1980"),
		        new Book(15, "이기적 유전자", "리처드 도킨스", "을유문화사", 17000, "1976"),
		        new Book(16, "부의 추월차선", "엠제이 드마코", "토트", 16000, "2013"),
		        new Book(17, "초격차", "권오현", "쌤앤파커스", 15000, "2018"),
		        new Book(18, "트렌드 코리아 2025", "김난도", "미래의창", 19000, "2024"),
		        new Book(19, "나는 나로 살기로 했다", "김수현", "마음의숲", 13000, "2016"),
		        new Book(20, "불편한 편의점", "김호연", "나무옆의자", 13500, "2021")
		    );
	
		    crud.insertDataBatch("book", books);
			
			// -- < 도서관리 Book > -- Rental(대출) 테이블
			List<Rental> rentals = List.of(
			    new Rental(1, 1, 1, "2023", "2024"),
			    new Rental(2, 2, 3, "2022", "2023"),
			    new Rental(3, 3, 5, "2021", "2022"),
			    new Rental(4, 4, 7, "2020", "2021"),
			    new Rental(5, 5, 9, "2019", "2020"),
			    new Rental(6, 6, 2, "2023", "2024"),
			    new Rental(7, 7, 4, "2022", "2023"),
			    new Rental(8, 8, 6, "2021", "2022"),
			    new Rental(9, 9, 8, "2020", "2021"),
			    new Rental(10, 10, 10, "2019", "2020"),
			    new Rental(11, 11, 11, "2023", "2024"),
			    new Rental(12, 12, 13, "2022", "2023"),
			    new Rental(13, 13, 15, "2021", "2022"),
			    new Rental(14, 14, 17, "2020", "2021"),
			    new Rental(15, 15, 19, "2019", "2020"),
			    new Rental(16, 16, 12, "2023", "2024"),
			    new Rental(17, 17, 14, "2022", "2023"),
			    new Rental(18, 18, 16, "2021", "2022"),
			    new Rental(19, 19, 18, "2020", "2021"),
			    new Rental(20, 20, 20, "2019", "2020")
			);
	
			crud.insertDataBatch("rental", rentals);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
