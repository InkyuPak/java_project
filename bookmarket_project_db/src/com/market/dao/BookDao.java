package com.market.dao;

import java.util.ArrayList;

import com.market.vo.BookVo;


public class BookDao extends DBConn{
	
	public int insert(BookVo bookVo) {
		int result =0;
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into bookmarket_book values");
		sql.append(" ('ISBN_'||TO_CHAR(SEQU_BOOKMARKET_BOOK_ISBN.NEXTVAL, 'FM0000'),?,?,?,?,?,?,NULL,SYSDATE)");
		try {
			getPreparedStatement(sql.toString());
			pstmt.setString(1, bookVo.getTitle());
			pstmt.setInt(2, bookVo.getPrice());
			pstmt.setString(3, bookVo.getAuthor());
			pstmt.setString(4, bookVo.getIntro());
			pstmt.setString(5, bookVo.getPart());
			pstmt.setString(6, bookVo.getPdate());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 도서 전체 리스트 조회
	 */
	public ArrayList<BookVo> select(){
		ArrayList<BookVo> bookList = new ArrayList<BookVo>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from bookmarket_book order by bdate desc");
		
		try {
			getPreparedStatement(sql.toString());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BookVo book = new BookVo();
				book.setIsbn(rs.getString(1));
				book.setTitle(rs.getString(2));
				book.setPrice(rs.getInt(3));
				book.setAuthor(rs.getString(4));
				book.setIntro(rs.getString(5));
				book.setPart(rs.getString(6));
				book.setPdate(rs.getString(7));
				book.setImg(rs.getString(8));
				book.setBdate(rs.getString(9));
				
				bookList.add(book);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bookList;
	}
}
