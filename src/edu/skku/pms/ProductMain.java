package edu.skku.pms;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;


public class ProductMain implements ActionListener,ItemListener{
	private Frame frame;
	private List list;
	private TextField txtPcode;
	private TextField txtTitle;
	private TextField txtPrice;
	private TextField txtColor;
	private Label lblStatus;
	private Button btnAdd;
	private Button btnSearch;
	private Button btnUpdate;
	private Button btnDelete;
	private Button btnClear;
	IProductMgr mgr=new ProductMgrImpl();
	
	public ProductMain(){
		frame = new Frame("Product Manager");
		lblStatus = new Label();
		lblStatus.setBackground(Color.LIGHT_GRAY);
		lblStatus.setForeground(Color.BLUE);
		txtPcode = new TextField();
		txtTitle = new TextField();
		txtPrice = new TextField();
		txtColor = new TextField();
		btnAdd = new Button("추가");
		btnUpdate = new Button("수정");
		btnSearch = new Button("검색");
		btnClear= new Button("지우기");
		btnDelete = new Button("삭제");
		list = new List();
		
		Panel inputs = new Panel();
		Panel inputLables = new Panel();
		Panel inputFields = new Panel();
		Panel inputBtns = new Panel();
		inputLables.setLayout(new GridLayout(4,1,15,15));
		Label lblPcode = new Label("상품번호:");
		lblPcode.setAlignment(Label.CENTER);
		Label lblTitle = new Label("상 품 명:");
		lblTitle.setAlignment(Label.CENTER);
		Label lblPrice = new Label("가    격:");
		lblPrice.setAlignment(Label.CENTER);
		Label lblColor = new Label("색    상:");
		lblColor.setAlignment(Label.CENTER);
		inputLables.add(lblPcode);
		inputLables.add(lblTitle);
		inputLables.add(lblPrice);
		inputLables.add(lblColor);
		inputFields.setLayout(new GridLayout(4,1));
		inputFields.add(txtPcode);
		inputFields.add(txtTitle);
		inputFields.add(txtPrice);
		inputFields.add(txtColor);
		inputBtns.setLayout(new GridLayout(1,4));
		inputBtns.add(btnAdd);
		inputBtns.add(btnUpdate);
		inputBtns.add(btnDelete);
		inputBtns.add(btnSearch);
		inputBtns.add(btnClear);

		inputs.setLayout(new BorderLayout());
		inputs.add(inputLables, BorderLayout.WEST);
		inputs.add(inputFields, BorderLayout.CENTER);
		inputs.add(inputBtns, BorderLayout.SOUTH);
		
		Panel center = new Panel();
		center.setLayout(new BorderLayout());
		center.add(list);
		center.add(lblStatus, BorderLayout.SOUTH);
		
		
		frame.add(inputs, BorderLayout.NORTH);
		frame.add(center);
		frame.setSize(300, 400);
		frame.setVisible(true);
		showList();

	}
	/** 이벤트를 등록합니다. */
	public void addEvent(){
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		txtPcode.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				lblStatus.setText("");
			}
		});
		
		btnAdd.addActionListener(this);
		btnClear.addActionListener(this);
		btnDelete.addActionListener(this);
		btnSearch.addActionListener(this);
		btnUpdate.addActionListener(this);
		list.addItemListener(this);
			
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnAdd){
			addRecord();
		}else if(e.getSource()==btnUpdate){
			updateRecord();
		}else if(e.getSource()==btnSearch){
			searchRecord();
		}else if(e.getSource()==btnClear){
			clearField();
		}else if(e.getSource()==btnDelete){
			deleteRecord();
		}
	}
	private void addRecord(){
		String pcode=txtPcode.getText().trim();
		String title=txtTitle.getText().trim();
		String price=txtPrice.getText().trim();
		String color=txtColor.getText().trim();
		if(pcode.equals("") ||title.equals("") ||color.equals("")||price.equals("")){
			lblStatus.setText("빈칸을 모두 채워주세요.");
			return;
		}
		try {
			mgr.add(new Product(Integer.parseInt(pcode),title,Integer.parseInt(price),color));
			lblStatus.setText("등록 성공");			
			showList();
			clearField();
		} catch (NumberFormatException e) {
			lblStatus.setText("상품코드와 가격은 숫자로 입력해주세요");
			return;
		} catch (DuplicateException e) {
			lblStatus.setText(e.toString());
		}
	}
	
	private void updateRecord() {
		// 구현 하세요
		String pcode=txtPcode.getText().trim();
		String title=txtTitle.getText().trim();
		String price=txtPrice.getText().trim();
		String color=txtColor.getText().trim();
		
		if(pcode.equals("")||title.equals("")||price.equals("")||color.equals("")) {
			lblStatus.setText("비어 있는 항목이 있습니다.");
			return;
		}
		
		Product p=new Product(Integer.parseInt(pcode), title, Integer.parseInt(price), color);
		
		try {
			mgr.update(p);
			lblStatus.setText("수정 성공");
			showList();
			clearField();
		} catch (RecordNotFoundException e) {
			lblStatus.setText(e.toString());
			showList();
			clearField();
		}
		showList();
		clearField();
		
	}
	private void searchRecord(){
		// 구현 하세요
		Product p=null;
		String pcode=txtPcode.getText().trim();
		if(!(pcode.equals(""))) {
			try {
				p=mgr.search(Integer.parseInt(pcode));
				lblStatus.setText("검색 성공");
				showList();
				clearField();
			} catch (NumberFormatException e) {
				lblStatus.setText("상품코드는 숫자로 입력해주세요");
			} catch (RecordNotFoundException e) {
				lblStatus.setText(e.toString());
			}
		}else {
			lblStatus.setText("선택이 잘못되었습니다");
			return;
		}
		
		if(p==null) {
			lblStatus.setText("찾을 수 없습니다");
			return;
		}
		
		txtPcode.setText(String.valueOf(p.getPcode()));
		txtTitle.setText(p.getTitle());
		txtPrice.setText(String.valueOf(p.getPrice()));
		txtColor.setText(p.getColor());
		
		
	}
	private void deleteRecord(){
		// 구현 하세요
		String pcode=txtPcode.getText().trim();
		String title=txtTitle.getText().trim();
		String price=txtPrice.getText().trim();
		String color=txtColor.getText().trim();
		
		
		if(pcode.equals(String.valueOf(pcode))) {
			try {
				mgr.delete(Integer.parseInt(pcode));
				lblStatus.setText("삭제 성공");
				showList();
				clearField();
			} catch (NumberFormatException e) {
				lblStatus.setText("상품코드와 가격은 숫자로 입력해주세요");
				return;
			} catch (RecordNotFoundException e) {
				lblStatus.setText(e.toString());
			}
		}
		showList();
		clearField();
		
	}
	public void itemStateChanged(ItemEvent e) {
			String str=list.getSelectedItem();
			String [] st=str.split("   ");
			txtPcode.setText(st[0]);
			txtTitle.setText(st[1]);
			txtPrice.setText(st[2]);
			txtColor.setText(st[3]);
	}
	
	/** AWT List 컴포넌트에 직원정보를 표시합니다. */
	private void showList(){
		java.util.List<Product> ee=mgr.getList();
		list.removeAll();
		for(Product e:ee){
			list.add(e.toString());
		}
	}
	/** 직원정보를 입력하는 TextField의 내용을 지움니다. */
	private void clearField(){
		// 구현 하세요
		txtPcode.setText(" ");
		txtTitle.setText(" ");
		txtPrice.setText(" ");
		txtColor.setText(" ");
	}
	public static void main(String[] args){
		ProductMain ui = new ProductMain();
		ui.addEvent();
	}
	
}

















