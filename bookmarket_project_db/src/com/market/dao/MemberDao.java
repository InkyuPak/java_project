package com.market.dao;

import com.market.vo.MemberVo;

public class MemberDao extends DBConn{
	/**
	 * 로그인 체크(select ~ count) 체크를 할땐 숫자로 체크하는게 좋다
	 * String으로 체크하면 DB와 자바에서의 null의미가 다르기 때문에!
	 */
	public int select(String mid, String pass) {
		int result = 0;
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from bookmarket_member where mid=? and pass=?");
		try {
			getPreparedStatement(sql.toString());
			pstmt.setString(1, mid.toUpperCase());
			pstmt.setString(2, pass);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public MemberVo select(String mid) {
		MemberVo member = new MemberVo();
		StringBuffer sql = new StringBuffer();
		sql.append(" select mid, pass, name, addr, phone, mdate from bookmarket_member ");
		sql.append(" where mid=?");
		
		try {
			getPreparedStatement(sql.toString());
			pstmt.setString(1, mid);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				member.setMid(rs.getString(1));
				member.setPass(rs.getString(2));
				member.setName(rs.getString(3));
				member.setAddr(rs.getString(4));
				member.setPhone(rs.getString(5));
				member.setMdate(rs.getString(6));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return member;
	}
}
