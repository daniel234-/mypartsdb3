package parts;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.Timestamp;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class InventoryView
{
	private JPanel mainPanel = null;
	private JPanel subPanel = null;
	private PartsModel model;
	private JButton addButton;
	private JButton editButton;
	private JButton deleteButton;
	private JButton createButton;
	private GridBagLayout inventoryLayout;
	private DefaultListModel listModel;
	private JList inventoryList;
	private JTextField field1, field2, field3, field4, field5, field6, field7, field8, field9, field10;
	private JTextField field11, field12, field13, field14, field15, field16, field17;
	private GridBagConstraints g = new GridBagConstraints();
	private String copy = "";
	private Font bigFont = new Font("Serif", Font.PLAIN, 20);
	private String[] locations = {"Facility 1 Warehouse 1", "Facility 1 Warehouse 2", "Facility 2","Unknown"};
	private JComboBox locationList = new JComboBox(locations);
	private Timestamp time = null;
	private int view = 1;
	
	public InventoryView(PartsModel model)
	{
		this.model = model;
		mainPanel = new JPanel();
		inventoryLayout = new GridBagLayout();
		GridBagConstraints g = new GridBagConstraints();
		mainPanel.setLayout(inventoryLayout);
		Font bigFont = new Font("Serif", Font.PLAIN, 20); 
		
		listModel = new DefaultListModel();
		inventoryList = new JList(listModel);
		inventoryList.setFont(bigFont);
        inventoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        inventoryList.setVisibleRowCount(10);
		g.fill = GridBagConstraints.BOTH;
		g.gridx = 0;
		g.gridy = 0;
		g.weighty = 0.95;
		g.gridwidth = 3;
		mainPanel.add(inventoryList, g);
		
		addButton = new JButton("Add Item");
		addButton.setFont(bigFont);
		g.fill = GridBagConstraints.NONE;
		g.gridx = 0;
		g.gridy = 1;
		g.weightx = 0.5;
		g.weighty = 0.05;
		g.anchor = GridBagConstraints.LAST_LINE_START;
		mainPanel.add(addButton, g);
		
		editButton = new JButton("Edit Item");
		editButton.setFont(bigFont);
		g.fill = GridBagConstraints.NONE;
		g.gridx = 1;
		g.gridy = 1;
		g.weightx = 0.5;
		g.weighty = 0.05;
		g.anchor = GridBagConstraints.PAGE_END;
		mainPanel.add(editButton, g);
		
		deleteButton = new JButton("Delete Item");
		deleteButton.setFont(bigFont);
		g.fill = GridBagConstraints.NONE;
		g.gridx = 2;
		g.gridy = 1;
		g.weightx = 0.5;
		g.weighty = 0.05;
		g.anchor = GridBagConstraints.LAST_LINE_END;
		mainPanel.add(deleteButton, g);
		
		createButton = new JButton("Create Product");
		createButton.setFont(bigFont);
		g.fill = GridBagConstraints.NONE;
		g.gridx = 1;
		g.gridy = 2;
		g.gridwidth = 3;
		g.anchor = GridBagConstraints.PAGE_END;
		mainPanel.add(createButton, g);
		
		this.refreshList();
		this.itemPanel();
	}
	
	public void itemPanel()
	{	
		view = 1;
		subPanel = new JPanel(inventoryLayout);
		subPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		g.gridx = 0;
		g.gridy = 3;
		g.gridwidth = 4;
		g.ipadx = 130;
		g.anchor = GridBagConstraints.PAGE_END;
		mainPanel.add(subPanel, g);
		
		field1 = new JTextField("Item Information",29);
		field1.setHorizontalAlignment(JTextField.CENTER);
		field1.setFont(bigFont);
		field1.setEditable(false);
		g.gridx = 0;
		g.gridy = 0;
		g.gridwidth = 4;
		subPanel.add(field1,g);
		
		field2 = new JTextField("         Item ID:");
		field2.setHorizontalAlignment(JTextField.RIGHT);
		field2.setFont(bigFont);
		field2.setEditable(false);
		g.gridx = 0;
		g.gridy = 1;
		g.gridwidth = 2;
		subPanel.add(field2,g);
		
		field3 = new JTextField("",10);
		field3.setHorizontalAlignment(JTextField.CENTER);
		field3.setFont(bigFont);
		field3.setEditable(false);
		g.gridx = 2;
		g.gridy = 1;
		g.gridwidth = 2;
		subPanel.add(field3,g);
		
		field4 = new JTextField("           Part:");
		field4.setHorizontalAlignment(JTextField.RIGHT);
		field4.setFont(bigFont);
		field4.setEditable(false);
		g.gridx = 0;
		g.gridy = 2;
		g.gridwidth = 2;
		subPanel.add(field4,g);
		
		field5 = new JTextField("",10);
		field5.setHorizontalAlignment(JTextField.CENTER);
		field5.setFont(bigFont);
		field5.setEditable(true);
		g.gridx = 2;
		g.gridy = 2;
		g.gridwidth = 2;
		subPanel.add(field5,g);
		
		field6 = new JTextField(" Location:");
		field6.setHorizontalAlignment(JTextField.RIGHT);
		field6.setFont(bigFont);
		field6.setEditable(false);
		g.gridx = 0;
		g.gridy = 3;
		g.gridwidth = 2;
		subPanel.add(field6,g);
		
		locationList.setSelectedIndex(3);
		g.gridx = 2;
		g.gridy = 3;
		g.gridwidth = 2;
		subPanel.add(locationList,g);
		
		field7 = new JTextField("         Quanity:");
		field7.setHorizontalAlignment(JTextField.RIGHT);
		field7.setFont(bigFont);
		field7.setEditable(false);
		g.gridx = 0;
		g.gridy = 4;
		g.gridwidth = 2;
		subPanel.add(field7,g);
		
		field8 = new JTextField("",10);
		field8.setHorizontalAlignment(JTextField.CENTER);
		field8.setFont(bigFont);
		field8.setEditable(true);
		g.gridx = 2;
		g.gridy = 4;
		g.gridwidth = 2;
		subPanel.add(field8,g);
	}
	
	public void productPanel()
	{	
		view = 2;
		subPanel = new JPanel(inventoryLayout);
		subPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		g.gridx = 0;
		g.gridy = 3;
		g.gridwidth = 4;
		g.ipadx = 130;
		g.anchor = GridBagConstraints.PAGE_END;
		mainPanel.add(subPanel, g);
		
		field1 = new JTextField("Create New Product",29);
		field1.setHorizontalAlignment(JTextField.CENTER);
		field1.setFont(bigFont);
		field1.setEditable(false);
		g.gridx = 0;
		g.gridy = 0;
		g.gridwidth = 4;
		subPanel.add(field1,g);
		
		field2 = new JTextField("       Product ID:");
		field2.setHorizontalAlignment(JTextField.RIGHT);
		field2.setFont(bigFont);
		field2.setEditable(false);
		g.gridx = 0;
		g.gridy = 1;
		g.gridwidth = 2;
		subPanel.add(field2,g);
		
		field3 = new JTextField("",10);
		field3.setHorizontalAlignment(JTextField.CENTER);
		field3.setFont(bigFont);
		field3.setEditable(true);
		g.gridx = 2;
		g.gridy = 1;
		g.gridwidth = 2;
		subPanel.add(field3,g);
		
		field4 = new JTextField("     Description:");
		field4.setHorizontalAlignment(JTextField.RIGHT);
		field4.setFont(bigFont);
		field4.setEditable(false);
		g.gridx = 0;
		g.gridy = 2;
		g.gridwidth = 2;
		subPanel.add(field4,g);
		
		field5 = new JTextField("",10);
		field5.setHorizontalAlignment(JTextField.CENTER);
		field5.setFont(bigFont);
		field5.setEditable(true);
		g.gridx = 2;
		g.gridy = 2;
		g.gridwidth = 2;
		subPanel.add(field5,g);
		
		field6 = new JTextField("         Item 1 ID:");
		field6.setHorizontalAlignment(JTextField.RIGHT);
		field6.setFont(bigFont);
		field6.setEditable(false);
		g.gridx = 0;
		g.gridy = 3;
		g.gridwidth = 2;
		subPanel.add(field6,g);
		
		field7 = new JTextField("",10);
		field7.setHorizontalAlignment(JTextField.CENTER);
		field7.setFont(bigFont);
		field7.setEditable(true);
		g.gridx = 2;
		g.gridy = 3;
		g.gridwidth = 2;
		subPanel.add(field7,g);
		
		field8 = new JTextField("     Item 1 Amount:");
		field8.setHorizontalAlignment(JTextField.RIGHT);
		field8.setFont(bigFont);
		field8.setEditable(false);
		g.gridx = 0;
		g.gridy = 4;
		g.gridwidth = 2;
		subPanel.add(field8,g);
		
		field9 = new JTextField("",10);
		field9.setHorizontalAlignment(JTextField.CENTER);
		field9.setFont(bigFont);
		field9.setEditable(true);
		g.gridx = 2;
		g.gridy = 4;
		g.gridwidth = 2;
		subPanel.add(field9,g);
		
		field10 = new JTextField("         Item 2 ID:");
		field10.setHorizontalAlignment(JTextField.RIGHT);
		field10.setFont(bigFont);
		field10.setEditable(false);
		g.gridx = 0;
		g.gridy = 5;
		g.gridwidth = 2;
		subPanel.add(field10,g);
		
		field11 = new JTextField("",10);
		field11.setHorizontalAlignment(JTextField.CENTER);
		field11.setFont(bigFont);
		field11.setEditable(true);
		g.gridx = 2;
		g.gridy = 5;
		g.gridwidth = 2;
		subPanel.add(field11,g);
		
		field12 = new JTextField("     Item 2 Amount:");
		field12.setHorizontalAlignment(JTextField.RIGHT);
		field12.setFont(bigFont);
		field12.setEditable(false);
		g.gridx = 0;
		g.gridy = 6;
		g.gridwidth = 2;
		subPanel.add(field12,g);
		
		field13 = new JTextField("",10);
		field13.setHorizontalAlignment(JTextField.CENTER);
		field13.setFont(bigFont);
		field13.setEditable(true);
		g.gridx = 2;
		g.gridy = 6;
		g.gridwidth = 2;
		subPanel.add(field13,g);
		
		field14 = new JTextField("         Item 3 ID:");
		field14.setHorizontalAlignment(JTextField.RIGHT);
		field14.setFont(bigFont);
		field14.setEditable(false);
		g.gridx = 0;
		g.gridy = 7;
		g.gridwidth = 2;
		subPanel.add(field14,g);
		
		field15 = new JTextField("",10);
		field15.setHorizontalAlignment(JTextField.CENTER);
		field15.setFont(bigFont);
		field15.setEditable(true);
		g.gridx = 2;
		g.gridy = 7;
		g.gridwidth = 2;
		subPanel.add(field15,g);
		
		field16 = new JTextField("     Item 3 Amount:");
		field16.setHorizontalAlignment(JTextField.RIGHT);
		field16.setFont(bigFont);
		field16.setEditable(false);
		g.gridx = 0;
		g.gridy = 8;
		g.gridwidth = 2;
		subPanel.add(field16,g);
		
		field17 = new JTextField("",10);
		field17.setHorizontalAlignment(JTextField.CENTER);
		field17.setFont(bigFont);
		field17.setEditable(true);
		g.gridx = 2;
		g.gridy = 8;
		g.gridwidth = 2;
		subPanel.add(field17,g);
	}
	
	public void removePanel()
	{
		mainPanel.remove(subPanel);
	}
	
	public void fieldFillIn(String part)
	{
		this.copy = part;
		Scanner scan = new Scanner(part);
		
		int itemid = Integer.parseInt(scan.next());
		time = model.prepareItem(itemid);
		field3.setText("" + itemid);
		field5.setText(scan.next());
		
		scan.next();
		String locationName = scan.next();
		
		if(locationName.equalsIgnoreCase("2"))
		{
			locationList.setSelectedIndex(2);
		}
		else
		{
			scan.next();
			locationName = scan.next();
			if(locationName.equalsIgnoreCase("1"))
			{
				locationList.setSelectedIndex(0);
			}
			else
			{
				locationList.setSelectedIndex(1);
			}
		}
		
		field8.setText(scan.next());
		scan.close();
	}
	
	public void fieldCleanUp()
	{
		if(view == 1)
		{
			field3.setText("");
			field5.setText("");
			field8.setText("");
			locationList.setSelectedIndex(3);
			this.copy = "";
		}
		if(view == 2)
		{
			field3.setText("");
			field5.setText("");
			field7.setText("");
			field9.setText("");
			field11.setText("");
			field13.setText("");
			field15.setText("");
			field17.setText("");
			this.copy = "";
		}
	}
	
	public JList getInventoryList() 
	{
		return this.inventoryList;
	}
	
	public int checkInput(int mode)
	{
		if(model.getLocation() == null || model.getLocation().isEmpty()){
			System.out.println("A location was not set");
			return 1;
		}
		if(view == 1)
		{
			if(model.checkItem(field5.getText(),field8.getText(),mode) == 1)
			{
				return 1;
			}
			if(mode==1)
			{
				model.addItem(field5.getText(),field8.getText());
			}
			if(mode==2)
			{
				Scanner scan = new Scanner(this.copy);
				String copyid = scan.next();
				String copypartname = scan.next();
				scan.next();
				String locationname = scan.next();
				if(locationname.equalsIgnoreCase("1"))
				{
					scan.next();
					scan.next();
				}
				
				String copyamount = scan.next();
				scan.close();
				int check = model.editItem(field3.getText(),field5.getText(),field8.getText(),copyid,copypartname,copyamount,time);//locationname,copyamount);
				if(check==0)
				{
					model.addItem(field5.getText(),field8.getText());
				}
			}
		}
		else
		{
			if(model.checkProduct(field3.getText(),field5.getText(),field7.getText(),field9.getText(),field11.getText(),field13.getText(),field15.getText(),field17.getText()) == 1)
			{
				return 1;
			}
			Scanner scan = new Scanner(this.copy);
			String copyproductid = scan.next();
			StringBuilder sb = new StringBuilder();
			sb.append(scan.next());
			while(scan.hasNext()){
				sb.append(" ");
				sb.append(scan.next());
			}
			String copydescription = sb.toString();
			String copyitemid1 = scan.next();
			String copyitemamount1 = scan.next();
			String copyitemid2 = "";
			String copyitemamount2 = "";
			String copyitemid3 = "";
			String copyitemamount3 = "";
			if(scan.hasNext())
			{
				copyitemid2 = scan.next();
				copyitemamount2 = scan.next();
				if(scan.hasNext())
				{
					copyitemid3 = scan.next();
					copyitemamount3 = scan.next();
				}
			}
			scan.close();
			model.addProduct(copyproductid,copydescription,copyitemid1,copyitemamount1,copyitemid2,copyitemamount2,copyitemid3,copyitemamount3);
		}
		this.fieldCleanUp();
		return 0;
	}
	
	public void registerListeners(PartsButtonController controller1, InventoryListController controller2, PartsLocationController controller3) 
	{
		addButton.addActionListener(controller1);
		editButton.addActionListener(controller1);
		deleteButton.addActionListener(controller1);
		createButton.addActionListener(controller1);
		inventoryList.addListSelectionListener(controller2);
		locationList.addActionListener(controller3);
	}

	public void refreshList() 
	{
		listModel.clear();
		model.itemListFill();
		for(int n = 0; n < 12; n++)
		{
			String text = model.refreshItemList(n);
			if(text.equalsIgnoreCase(" "))
			{
				n = 12;
			}
			else
			{
				listModel.addElement(model.refreshItemList(n));
			}
		}
	}
	public JPanel getInvPanel(){
		return mainPanel;
	}
}
