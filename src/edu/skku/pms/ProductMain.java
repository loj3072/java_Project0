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
		btnAdd = new Button("�߰�");
		btnUpdate = new Button("����");
		btnSearch = new Button("�˻�");
		btnClear= new Button("�����");
		btnDelete = new Button("����");
		list = new List();
		
		Panel inputs = new Panel();
		Panel inputLables = new Panel();
		Panel inputFields = new Panel();
		Panel inputBtns = new Panel();
		inputLables.setLayout(new GridLayout(4,1,15,15));
		Label lblPcode = new Label("��ǰ��ȣ:");
		lblPcode.setAlignment(Label.CENTER);
		Label lblTitle = new Label("�� ǰ ��:");
		lblTitle.setAlignment(Label.CENTER);
		Label lblPrice = new Label("��    ��:");
		lblPrice.setAlignment(Label.CENTER);
		Label lblColor = new Label("��    ��:");
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
	/** �̺�Ʈ�� ����մϴ�. */
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
			lblStatus.setText("��ĭ�� ��� ä���ּ���.");
			return;
		}
		try {
			mgr.add(new Product(Integer.parseInt(pcode),title,Integer.parseInt(price),color));
			lblStatus.setText("��� ����");			
			showList();
			clearField();
		} catch (NumberFormatException e) {
			lblStatus.setText("��ǰ�ڵ�� ������ ���ڷ� �Է����ּ���");
			return;
		} catch (DuplicateException e) {
			lblStatus.setText(e.toString());
		}
	}
	
	private void updateRecord() {
		// ���� �ϼ���
		String pcode=txtPcode.getText().trim();
		String title=txtTitle.getText().trim();
		String price=txtPrice.getText().trim();
		String color=txtColor.getText().trim();
		
		if(pcode.equals("")||title.equals("")||price.equals("")||color.equals("")) {
			lblStatus.setText("��� �ִ� �׸��� �ֽ��ϴ�.");
			return;
		}
		
		Product p=new Product(Integer.parseInt(pcode), title, Integer.parseInt(price), color);
		
		try {
			mgr.update(p);
			lblStatus.setText("���� ����");
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
		// ���� �ϼ���
		Product p=null;
		String pcode=txtPcode.getText().trim();
		if(!(pcode.equals(""))) {
			try {
				p=mgr.search(Integer.parseInt(pcode));
				lblStatus.setText("�˻� ����");
				showList();
				clearField();
			} catch (NumberFormatException e) {
				lblStatus.setText("��ǰ�ڵ�� ���ڷ� �Է����ּ���");
			} catch (RecordNotFoundException e) {
				lblStatus.setText(e.toString());
			}
		}else {
			lblStatus.setText("������ �߸��Ǿ����ϴ�");
			return;
		}
		
		if(p==null) {
			lblStatus.setText("ã�� �� �����ϴ�");
			return;
		}
		
		txtPcode.setText(String.valueOf(p.getPcode()));
		txtTitle.setText(p.getTitle());
		txtPrice.setText(String.valueOf(p.getPrice()));
		txtColor.setText(p.getColor());
		
		
	}
	private void deleteRecord(){
		// ���� �ϼ���
		String pcode=txtPcode.getText().trim();
		String title=txtTitle.getText().trim();
		String price=txtPrice.getText().trim();
		String color=txtColor.getText().trim();
		
		
		if(pcode.equals(String.valueOf(pcode))) {
			try {
				mgr.delete(Integer.parseInt(pcode));
				lblStatus.setText("���� ����");
				showList();
				clearField();
			} catch (NumberFormatException e) {
				lblStatus.setText("��ǰ�ڵ�� ������ ���ڷ� �Է����ּ���");
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
	
	/** AWT List ������Ʈ�� ���������� ǥ���մϴ�. */
	private void showList(){
		java.util.List<Product> ee=mgr.getList();
		list.removeAll();
		for(Product e:ee){
			list.add(e.toString());
		}
	}
	/** ���������� �Է��ϴ� TextField�� ������ ����ϴ�. */
	private void clearField(){
		// ���� �ϼ���
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

















