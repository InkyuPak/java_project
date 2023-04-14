package com.market.dao;

import java.util.ArrayList;

import com.market.vo.CartVo;
import com.market.vo.OrderVo;

public class CartDao extends DBConn{
	/**
	 * 주문테이블 데이터 생성 - 회원의 qty, isbn리스트
	 */
	public OrderVo getOrderVo(String mid) {
		OrderVo orderVo = new OrderVo();
		StringBuffer sql = new StringBuffer(50);
		sql.append(" select qty, isbn from bookmarket_cart where mid=? order by cdate desc");
		try {
			getPreparedStatement(sql.toString());
			pstmt.setString(1, mid);
			rs=pstmt.executeQuery();
			//데이터가 많을때는 커서가 이동하는 건 비추천 
			rs.last(); 
			int[] qtyList = new int[rs.getRow()];
			String[] isbnList = new String[rs.getRow()];
			rs.beforeFirst();
			int idx = 0;
			while(rs.next()) {
				//orderVo의 qtyList[], isbnList[] 데이터 저장
				qtyList[idx] = rs.getInt(1);
				isbnList[idx] = rs.getString(2);
				idx++;
			}
			orderVo.setQtyList(qtyList);
			orderVo.setIsbnList(isbnList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderVo;
	}
	
	public int remove(String mid) {
		int result = 0;
		StringBuffer sql = new StringBuffer();
		sql.append("delete from bookmarket_cart where mid=?");
		try {
			getPreparedStatement(sql.toString());
			pstmt.setString(1, mid);
			result=pstmt.executeUpdate();
		} catch (Exception e) {
		}
		return result;
	}
	/**
	 * 선택한 item 수량 늘리기 
	 */
	public int update(String cid, String status) {
		int result = 0;
		StringBuffer sql = new StringBuffer();
		if(status.equals("plus")) {
			sql.append("update bookmarket_cart set qty=qty+1 where cid =?");
		} else {
			sql.append("update bookmarket_cart set qty=qty-1 where cid =?");
		}
		try {
			getPreparedStatement(sql.toString());
			pstmt.setString(1, cid);
			result=pstmt.executeUpdate(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 선택한 item 삭제하기
	 */
	public int delete(String cid) {
		int result = 0;
		StringBuffer sql = new StringBuffer();
		sql.append("delete from bookmarket_cart where cid=?");
		
		try {
			getPreparedStatement(sql.toString());
			pstmt.setString(1, cid);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 로그인한 회원의 장바구니 카운트
	 */
	public int getSize(String mid) {
		int result =0;
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from bookmarket_cart where mid=?");
		
		try {
			getPreparedStatement(sql.toString());
			pstmt.setString(1, mid);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;		
	}
	
	/**
	 *	장바구니 추가 
	 */
	public int insert(CartVo cartVo) {
		System.out.println(cartVo.getMid());
		int result = 0;
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into bookmarket_cart values('c_'||to_char(sequ_bookmarket_cart_cid.nextval,'fm0000'),sysdate,1,?,?)");
		try {
			getPreparedStatement(sql.toString());
			pstmt.setString(1, cartVo.getIsbn());
			pstmt.setString(2, cartVo.getMid());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 장바구니 중복 도서 체크 
	 */
	public int insertCheck(CartVo cartVo) {
		int result = 0;
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from bookmarket_cart where isbn=? and mid=?");
		
		try {
			getPreparedStatement(sql.toString());
			pstmt.setString(1, cartVo.getIsbn());
			pstmt.setString(2, cartVo.getMid());
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				result=rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	/**
	 * 로그인한 회원의 장바구니 리스트 
	 */
	public ArrayList<CartVo> select(String mid){
		//"도서ID", "도서명", "단가", "수량", "총가격"
		ArrayList<CartVo> cartList = new ArrayList<CartVo>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select rownum rno, isbn, title, pirce, qty, total_price, sprice, total_sprice, cid from( ");
		sql.append(" select cid, c.isbn, title, pirce, qty, pirce*qty total_price,");
		sql.append(" to_char(pirce, 'l999,999') sprice, ");
		sql.append(" to_char(pirce*qty, 'l999,999') total_sprice ");
		sql.append(" from bookmarket_book b, bookmarket_cart c, bookmarket_member m ");
		sql.append(" where b.ISBN=c.isbn and c.mid=m.mid ");
		sql.append(" and m.mid=? order by isbn)");
		try {
			getPreparedStatement(sql.toString());
			pstmt.setString(1, mid);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				CartVo cart = new CartVo();
				cart.setRno(rs.getInt(1));
				cart.setIsbn(rs.getString(2));
				cart.setTitle(rs.getString(3));
				cart.setPrice(rs.getInt(4));
				cart.setQty(rs.getInt(5));
				cart.setTotal_price(rs.getInt(6));
				cart.setSprice(rs.getString(7));
				cart.setTotal_sprice(rs.getString(8));
				cart.setCid(rs.getString(9));
				cartList.add(cart);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cartList;
	}
}
