package com.market.page;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.market.commons.MarketFont;
import com.market.dao.CartDao;
import com.market.dao.OrderDao;
import com.market.main.GuestWindow;
import com.market.main.MainWindow;
import com.market.vo.MemberVo;
import com.market.vo.OrderVo;

import book_market2.CartItemVo;
import book_market2.CartMgm;

public class CartOrderBillPage extends JPanel {
	MemberVo orderMember;
	CartMgm cm;
	JPanel shippingPanel;
	JPanel radioPanel;
	JPanel mPagePanel;
	MainWindow main;
	CartDao cartDao;

	public CartOrderBillPage(JPanel panel, CartMgm cm, MemberVo orderMember, MainWindow main, CartDao cartDao) {
		this.main = main;
		this.cm = cm;
		this.orderMember = orderMember;
		this.mPagePanel = panel;
		this.cartDao = cartDao;
		setLayout(null);

		Rectangle rect = panel.getBounds();
		System.out.println(rect);
		setPreferredSize(rect.getSize());

		shippingPanel = new JPanel();
		shippingPanel.setBounds(0, 0, 700, 500);
		shippingPanel.setLayout(null);
		panel.add(shippingPanel);
		
		printBillInfo(orderMember);
	}

	public void printBillInfo(MemberVo orderMember) {

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		String strDate = formatter.format(date);

		JPanel panel01 = new JPanel();
		panel01.setBounds(0, 0, 500, 30);
		JLabel label01 = new JLabel("--------------------- 배송 받을 고객 정보 -----------------------");
		MarketFont.getFont(label01);
		panel01.add(label01);
		shippingPanel.add(panel01);

		JPanel panel02 = new JPanel();
		panel02.setBounds(0, 30, 500, 30);
		JLabel label02 = new JLabel("고객명 : " + orderMember.getName() + "             연락처 :      " + orderMember.getPhone());
		label02.setHorizontalAlignment(JLabel.LEFT);
		MarketFont.getFont(label02);
		panel02.add(label02);
		shippingPanel.add(panel02);

		JPanel panel03 = new JPanel();
		panel03.setBounds(0, 60, 500, 30);
		JLabel label03 = new JLabel("배송지 : " + orderMember.getAddr() + "                 발송일 :       " + strDate);
		label03.setHorizontalAlignment(JLabel.LEFT);
		MarketFont.getFont(label03);
		panel03.add(label03);
		shippingPanel.add(panel03);

		JPanel printPanel = new JPanel();
		printPanel.setBounds(0, 100, 500, 300);
		printCart(printPanel);
		shippingPanel.add(printPanel);
	}

	public void printCart(JPanel panel) {
		JPanel panel01 = new JPanel();
		panel01.setBounds(0, 0, 500, 5);
		JLabel label01 = new JLabel("      장바구니 상품 목록 :");
		MarketFont.getFont(label01);
		panel01.add(label01);
		panel.add(panel01);

		JPanel panel02 = new JPanel();
		panel02.setBounds(0, 20, 500, 5);
		JLabel label02 = new JLabel("---------------------------------------------------------------");
		MarketFont.getFont(label02);
		panel02.add(label02);
		panel.add(panel02);

		JPanel panel03 = new JPanel();
		panel03.setBounds(0, 25, 500, 5);
		JLabel label03 = new JLabel("                        도서 ID           |        수량           |      합계        ");
		MarketFont.getFont(label03);
		panel03.add(label03);
		panel.add(panel03);

		JPanel panel04 = new JPanel();
		panel04.setBounds(0, 30, 500, 5);

		JPanel panel05 = new JPanel(new GridLayout(cm.getSize(),1));
		int sum = 0;
		ArrayList<CartItemVo> cartList = cm.getCartItemList();
		for (int i = 0; i < cartList.size(); i++) { 
			CartItemVo item = cartList.get(i);
			
			panel05.setBounds(50, 25 + (i * 5), 500, 5);
			panel05.setBackground(Color.GRAY);

			JLabel label05 = new JLabel("               "+ item.getIsbn() + "                    "
					+ item.getQty() + "                    "
					+ item.getTotalPrice());
			MarketFont.getFont(label05);
			panel05.add(label05);
			panel.add(panel05);
			sum += item.getQty() * item.getTotalPrice();
		}

		JPanel panel06 = new JPanel();
		panel06.setBounds(0, 35 + (cm.getSize() * 5), 500, 5);
		JLabel label06 = new JLabel("--------------------------------------");
		MarketFont.getFont(label06);
		panel06.add(label06);
		panel.add(panel06);

		JPanel panel07 = new JPanel();
		panel07.setBounds(0, 40 + (cm.getSize() * 5), 500, 5);
		JLabel label07 = new JLabel("      주문 총금액 : " + sum + " 원");
		MarketFont.getFont(label07);
		panel07.add(label07);
		panel.add(panel07);
		
		
		/** 주문확정 버튼 **/
		JPanel panel08 = new JPanel();
		panel08.setBounds(0, 40 + (cm.getSize() * 5), 500, 5);
		JButton btnOrderFinish = new JButton("주문 확정"); 
		MarketFont.getFont(btnOrderFinish);
		panel08.add(btnOrderFinish);
		panel.add(panel08);
		
		btnOrderFinish.addActionListener(e->{
			int select = JOptionPane.showConfirmDialog(null, "주문을 확정 하시겠습니까?");
			if(select == 0) {
				// BOOKMARKET_ORDER 테이블에 저장할 데이터 생성
				//OID, ODATE, QTY리스트, ISBN리스트, MID, NAME, PHONE, ADDR
				//QTY리스트, ISBN리스트 --> CartDao에서 생성후 OrderVo 타입으로 리턴
				OrderVo orderVo = cartDao.getOrderVo(orderMember.getMid().toUpperCase());
				
				//OID, ODATE --> UUID, Calendar 클래스를 이용해 생성
				UUID uuid = UUID.randomUUID();
				Calendar cal = Calendar.getInstance();
				String oid = uuid.toString();
				String odate = cal.get(Calendar.YEAR)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DATE);
				orderVo.setOid(oid);
				orderVo.setOdate(odate);
				// MID, NAME, PHONE, ADDR--> orderMember 객체에서 가져옴
				orderVo.setMid(orderMember.getMid());
				orderVo.setName(orderMember.getName());
				orderVo.setPhone(orderMember.getPhone());
				orderVo.setAddr(orderMember.getAddr());
				
//				for(int i=0; i<orderVo.getQtyList().length; i++) {
//					System.out.print(orderVo.getOid()+"\t");
//					System.out.print(orderVo.getOdate()+"\t");
//					System.out.print(orderVo.getQtyList()[i]+"\t");
//					System.out.print(orderVo.getIsbnList()[i]+"\t");
//					System.out.print(orderVo.getMid()+"\t");
//					System.out.print(orderVo.getName()+"\t");
//					System.out.print(orderVo.getPhone()+"\t");
//					System.out.print(orderVo.getAddr()+"\n");
//				}
				
				OrderDao orderDao = new OrderDao();
				int result = orderDao.insertPre(orderVo);
				if(result >= 1) {
					//장바구니 비우기
//				cm.remove();
					int cartResult = cartDao.remove(orderMember.getMid().toUpperCase());
					if(cartResult >=1) {
						JOptionPane.showMessageDialog(null, "주문이 완료되었습니다!!");	
					}
					
				}
				
				
				//MainWindow 감추기
				main.setVisible(false);
				main.dispose(); //MainWindow 클래스의 모든 객체를 OS에 반환하고 close
				
				//GuestWindow 열기
				new GuestWindow("온라인 서점", 0, 0, 1000, 750);
			}
		});

	}


}



