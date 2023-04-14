package com.market.dao;

import com.market.vo.OrderVo;

public class OrderDao extends DBConn {

	/**
	 * 데이터 추가 - PreparedStatement
	 */
	public int insertPre(OrderVo orderVo) {
		int result = 0;
		StringBuffer sql = new StringBuffer();
		sql.append("insert into bookmarket_order(oid, odate,qty,isbn,mid,name,phone,addr)");
		sql.append(" values(?,?,?,?,?,?,?,?)");
		try {
//			for(int i =0; i<orderVo.getQtyList().length; i++) {
//				getPreparedStatement(sql.toString()); // for문안에 넣어두면 마지막 쿼리만 실행된다 -- 생각
//			}
			getPreparedStatement(sql.toString());
			for(int i =0; i<orderVo.getQtyList().length; i++) {
				pstmt.setString(1, orderVo.getOid());
				pstmt.setString(2, orderVo.getOdate());
				pstmt.setInt(3, orderVo.getQtyList()[i]);
				pstmt.setString(4, orderVo.getIsbnList()[i]);
				pstmt.setString(5, orderVo.getMid());
				pstmt.setString(6, orderVo.getName());
				pstmt.setString(7, orderVo.getPhone());
				pstmt.setString(8, orderVo.getAddr());
				pstmt.addBatch();
				pstmt.clearParameters();
			}
			
			result = pstmt.executeBatch().length;
			pstmt.clearParameters();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
	/**
	 * 데이터 추가 - Statement 
	 */
	public int insert(OrderVo orderVo) {
		int result =0;
		getStatement();
		try {
			for(int i=0; i<orderVo.getQtyList().length;i++) {
				StringBuffer sql = new StringBuffer(50);
				sql.append("insert into bookmarket_order(oid, odate,qty,isbn,mid,name,phone,addr) ");
				sql.append(" values(");
				sql.append("'"+orderVo.getOid()+"', ");
				sql.append("'"+orderVo.getOdate()+"', ");
				sql.append(orderVo.getQtyList()[i]+", ");
				sql.append("'"+orderVo.getIsbnList()[i]+"', ");
				sql.append("'"+orderVo.getMid()+"', ");
				sql.append("'"+orderVo.getName()+"', ");
				sql.append("'"+orderVo.getPhone()+"', ");
				sql.append("'"+orderVo.getAddr()+"'");
				sql.append(")");
				result = stmt.executeUpdate(sql.toString());
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
