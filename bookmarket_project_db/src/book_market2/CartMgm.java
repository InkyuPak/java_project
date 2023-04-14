package book_market2;

import java.util.ArrayList;
import java.util.Scanner;

public class CartMgm {
	ArrayList<BookVo> cartList;
	private ArrayList<CartItemVo> cartItemList;
	int count = 0;
	Scanner scan;
	
	public CartMgm() {
		cartList = new ArrayList<BookVo>();
		cartItemList = new ArrayList<CartItemVo>();
	}
	
	public CartMgm(Scanner scan) {
		this.scan = scan;
		cartList = new ArrayList<BookVo>();
		cartItemList = new ArrayList<CartItemVo>();
	}
	
	
	public int getSize() {
		return cartItemList.size();
	}
	
	public ArrayList<CartItemVo> getCartItemList(){
		return cartItemList;
	}
	
	/**
	 * ��ٱ��� ���
	 */
	public void showList() {
		if(getSize() !=0) {
			System.out.println("*****************************");
			System.out.println("\t��ٱ��� ����Ʈ");
			System.out.println("*****************************");
			for(CartItemVo item : cartItemList) {
				System.out.print(item.getIsbn()+"\t");
				System.out.print(item.getQty()+"\t");
				System.out.print(item.getQty() * item.getTotalPrice()+"\n");
			}
		} else {
			System.out.println("** ��ٱ��ϰ� ����ֽ��ϴ� **");
		}
	}
	/**
	 * ��ٱ��� �ֹ�����Ʈ ���
	 */
	public void showList(String order) {
		if(getSize() !=0) {
//			System.out.println("*****************************");
//			System.out.println("\t��ٱ��� ����Ʈ");
			
			int orderTotalPrice = 0;
			System.out.println("*****************************");
			for(CartItemVo item : cartItemList) {
				System.out.print(item.getIsbn()+"\t");
				System.out.print(item.getQty()+"\t");
				System.out.print(item.getQty() * item.getTotalPrice()+"\n");
				orderTotalPrice += item.getQty() * item.getTotalPrice();
			}
			System.out.println("*****************************");
			System.out.println("\t\t�ֹ� �� �ݾ� : "+orderTotalPrice);
		} else {
			System.out.println("** ��ٱ��ϰ� ����ֽ��ϴ� **");
		}
	}
	
	public void add(boolean check, CartItemVo item, BookVo book) {
		if(check) {
			item.setQty(item.getQty()+1);
		}else {
			CartItemVo item2 = new CartItemVo();
			item2.setIsbn(book.getIsbn());
			item2.setTitle(book.getTitle());
			item2.setQty(1);
			item2.setTotalPrice(book.getPrice());
			
			cartItemList.add(item2);
		}
	}
	
	/** 장바구니에 book 추가 **/
	public boolean insert(BookVo book) {
		boolean result = false;
		result = cartList.add(book);
		
		if(cartItemList.size() != 0) {						
			//for(�⺻)�� ����ϸ� book�� isbn�� cartItemList�� isbn ��
			boolean check = false;
			CartItemVo currItem = null;
			for(int i=0;i<cartItemList.size();i++) {
				CartItemVo item = cartItemList.get(i);
				if(item.getIsbn().equals(book.getIsbn())) {					
					currItem = item;
					check= true;
					i=cartItemList.size();
				}
			}//for
			
			add(check, currItem, book);	  //cartItemList�� �߰�
			result = true;
			
		}else {
			CartItemVo item = new CartItemVo();
			item.setIsbn(book.getIsbn());
			item.setTitle(book.getTitle());
			item.setQty(1);
			item.setTotalPrice(book.getPrice());
			
			cartItemList.add(item);
			result = true;
		}
		
		
//		if(currItem != null) {
//			System.out.println("currItem.getQty()===>>" + currItem.getQty());
//		}else {
//			CartItemVo item2 = new CartItemVo();
//			item2.setIsbn(book.getIsbn());
//			item2.setQty(1);
//			item2.setTotalPrice(book.getPrice());
//			
//			cartItemList.add(item2);
//		}
		
		return result;
	}
	/**
	 * ��ٱ��� ��ü ����
	 */
	public void remove() {
				cartItemList.clear();
	}
	
	public boolean remove(int idx) {
		return cartItemList.remove(cartItemList.get(idx));
	}
	/**
	 * ��ٱ��� �׸� ����
	 */
	public boolean remove(String isbn) {
		boolean result = false;
		int idx =-1;
		//1. cartItemList�� �ش�  �Ű����� isbn�� �����ϴ��� üũ
		for(int i=0; i<cartItemList.size(); i++) {
			CartItemVo item = cartItemList.get(i);
			if(item.getIsbn().equals(isbn)) {
				idx = i;
			}
		}
		//2. �ش� �ε����� ���� item�� ���� 
		if(idx != -1) {
			System.out.print("������ �����Ͻðڽ��ϱ�? Y | N >");
			String rconfirm = scan.next().toUpperCase();
			
			if(rconfirm.equals("Y")) {
				cartItemList.remove(idx);
				result = true;
			}
		}else {
			System.out.println("������ �׸��� �������� �ʽ��ϴ�.");
		}
		return result;
	}
	
	/**
	 * ���� ������Ʈ
	 */
	public void updateQty(String isbn, int qty) {
		int idx = -1;
		
		for(int i=0; i<getSize(); i++) {
			CartItemVo item = cartItemList.get(i);
			if(item.getIsbn().equals(isbn)) {
				idx = i;
			}
		}
		
		if(idx != -1) {
			//���� ����
			CartItemVo item = cartItemList.get(idx);
			if(item.getQty() >= qty) {
				item.setQty(item.getQty()-qty);
				if(item.getQty() == 0) {
					cartItemList.remove(idx);
				}
			} else {
				System.out.println("** ������ ������ �����մϴ� **");
			}
			
		} else {
			System.out.println("������ ������ ������ �������� �ʽ��ϴ�");
		}
	}
	
	public void updateQty(String isbn) {
		int idx = -1;
		
		for(int i=0; i<getSize(); i++) {
			CartItemVo item = cartItemList.get(i);
			if(item.getIsbn().equals(isbn)) {
				idx = i;
			}
		}
		
		if(idx != -1) {
			CartItemVo item = cartItemList.get(idx);
				item.setQty(item.getQty()-1);
		} 
	}
}//class










