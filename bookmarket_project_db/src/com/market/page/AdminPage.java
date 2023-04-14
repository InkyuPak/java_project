package com.market.page;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.market.commons.MarketFont;
import com.market.dao.BookDao;
import com.market.vo.BookVo;

public class AdminPage extends JPanel {
	BookDao bookDao;

	public AdminPage(JPanel panel, BookDao bookDao) {
		this.bookDao = bookDao;
		setLayout(null);

		Rectangle rect = panel.getBounds();
		setPreferredSize(rect.getSize());

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyMMddhhmmss");
		String strDate = formatter.format(date);

		JPanel idPanel = new JPanel();
		idPanel.setBounds(100, 0, 700, 50);
		JLabel idLabel = new JLabel(" 도서 등록 화면 [관리자] ");
		idLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		idPanel.add(idLabel);
		add(idPanel);

		JPanel namePanel = new JPanel();
		namePanel.setBounds(100, 50, 700, 50);
		JLabel nameLabel = new JLabel("도서명 : ");
		MarketFont.getFont(nameLabel);
		JTextField nameTextField = new JTextField(20);
		MarketFont.getFont(nameTextField);
		namePanel.add(nameLabel);
		namePanel.add(nameTextField);
		add(namePanel);

		JPanel pricePanel = new JPanel();
		pricePanel.setBounds(100, 100, 700, 50);
		JLabel priceLabel = new JLabel("가  격 : ");
		MarketFont.getFont(priceLabel);
		JTextField priceTextField = new JTextField(20);
		MarketFont.getFont(priceTextField);
		pricePanel.add(priceLabel);
		pricePanel.add(priceTextField);
		add(pricePanel);

		JPanel authorPanel = new JPanel();
		authorPanel.setBounds(100, 150, 700, 50);
		JLabel authorLabel = new JLabel("저  자 : ");
		MarketFont.getFont(authorLabel);
		JTextField authorTextField = new JTextField(20);
		MarketFont.getFont(authorTextField);
		authorPanel.add(authorLabel);
		authorPanel.add(authorTextField);
		add(authorPanel);

		JPanel descPanel = new JPanel();
		descPanel.setBounds(100, 200, 700, 50);
		JLabel descLabel = new JLabel("설  명 : ");
		MarketFont.getFont(descLabel);
		JTextField descTextField = new JTextField(20);
		MarketFont.getFont(descTextField);
		descPanel.add(descLabel);
		descPanel.add(descTextField);
		add(descPanel);

		JPanel categoryPanel = new JPanel();
		categoryPanel.setBounds(100, 250, 700, 50);
		JLabel categoryLabel = new JLabel("분  야 : ");
		MarketFont.getFont(categoryLabel);
		JTextField categoryTextField = new JTextField(20);
		MarketFont.getFont(categoryTextField);
		categoryPanel.add(categoryLabel);
		categoryPanel.add(categoryTextField);
		add(categoryPanel);

		JPanel datePanel = new JPanel();
		datePanel.setBounds(100, 300, 700, 50);
		JLabel dateLabel = new JLabel("출판일 : ");
		MarketFont.getFont(dateLabel);
		JTextField dateTextField = new JTextField(20);
		MarketFont.getFont(dateTextField);
		datePanel.add(dateLabel);
		datePanel.add(dateTextField);
		add(datePanel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(100, 350, 700, 50);
		add(buttonPanel);
		JLabel okLabel = new JLabel("  등 록  ");
		MarketFont.getFont(okLabel);
		JButton okButton = new JButton();
		okButton.add(okLabel);
		buttonPanel.add(okButton);

		okButton.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				
				//BookVo에 데이터 생성
				BookVo bookVo = new BookVo();
				bookVo.setTitle(nameTextField.getText().trim());
				bookVo.setPrice(Integer.parseInt(priceTextField.getText().trim()));
				bookVo.setAuthor(authorTextField.getText().trim());
				bookVo.setIntro(descTextField.getText().trim());
				bookVo.setPart(categoryTextField.getText().trim());
				bookVo.setPdate(dateTextField.getText());
				
				//BookDao에 insert 메소드 추가 ->BookMarket_book테이블에 데이터 추가
				int result = bookDao.insert(bookVo);
				if(result == 1) {
					JOptionPane.showMessageDialog(null, "등록 완료");
					nameTextField.setText("");
					priceTextField.setText("");
					authorTextField.setText("");
					descTextField.setText("");
					categoryTextField.setText("");
					dateTextField.setText("");
				}
			}
		});

		JLabel noLabel = new JLabel("  취 소  ");
		MarketFont.getFont(noLabel);
		JButton noButton = new JButton();
		noButton.add(noLabel);
		buttonPanel.add(noButton);

		noButton.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {

				nameTextField.setText("");
				priceTextField.setText("");
				authorTextField.setText("");
				descTextField.setText("");
				categoryTextField.setText("");
				dateTextField.setText("");
			}
		});
	}

//	public static void main(String[] args) {
//
//		JFrame frame = new JFrame();
//		frame.setBounds(0, 0, 1000, 750);
//		frame.setLayout(null);
//
//		JPanel mPagePanel = new JPanel();
//		mPagePanel.setBounds(0, 150, 1000, 750);
//
//		frame.add(mPagePanel);
//		mPagePanel.add(new AdminPage(mPagePanel, BookDao bookDao));
//		frame.setVisible(true);
//	}
}