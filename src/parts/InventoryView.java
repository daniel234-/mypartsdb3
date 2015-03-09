package parts;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
	private JPanel mainPanel;
	private PartsModel model;
	private JButton addButton;
	private JButton editButton;
	private JButton deleteButton;
	private GridBagLayout inventoryLayout;
	private DefaultListModel listModel;
	private JList inventoryList;
	private JTextField field1, field2, field3, field4, field5, field6, field7, field8;
	private GridBagConstraints g = new GridBagConstraints();
	private String copy = "";
	private Font bigFont = new Font("Serif", Font.PLAIN, 20);
	private String[] locations = {"Facility 1 Warehouse 1", "Facility 1 Warehouse 2", "Facility 2","Unknown"};
	private JComboBox locationList = new JComboBox(locations);
	
	public InventoryView(PartsModel model)
	{
		//super("Inventory System");
		this.model = model;
		mainPanel = new JPanel();
		inventoryLayout = new GridBagLayout();
		GridBagConstraints g = new GridBagConstraints();
		mainPanel.setLayout(inventoryLayout);
		//this.setLayout(inventoryLayout);
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
		//this.add(inventoryList,g);
		
		addButton = new JButton("Add Item");
		addButton.setFont(bigFont);
		g.fill = GridBagConstraints.NONE;
		g.gridx = 0;
		g.gridy = 1;
		g.weightx = 0.5;
		g.weighty = 0.05;
		g.anchor = GridBagConstraints.LAST_LINE_START;
		mainPanel.add(addButton, g);
		//this.add(addButton,g);
		
		editButton = new JButton("Edit Item");
		editButton.setFont(bigFont);
		g.fill = GridBagConstraints.NONE;
		g.gridx = 1;
		g.gridy = 1;
		g.weightx = 0.5;
		g.weighty = 0.05;
		g.anchor = GridBagConstraints.PAGE_END;
		mainPanel.add(editButton, g);
		//this.add(editButton,g);
		
		deleteButton = new JButton("Delete Item");
		deleteButton.setFont(bigFont);
		g.fill = GridBagConstraints.NONE;
		g.gridx = 2;
		g.gridy = 1;
		g.weightx = 0.5;
		g.weighty = 0.05;
		g.anchor = GridBagConstraints.LAST_LINE_END;
		mainPanel.add(deleteButton, g);
		//this.add(deleteButton,g);
		
		//Panel contents
		JPanel panel = new JPanel(inventoryLayout);
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		g.gridx = 0;
		g.gridy = 2;
		g.gridwidth = 4;
		g.ipadx = 130;
		g.anchor = GridBagConstraints.PAGE_END;
		mainPanel.add(panel, g);
		//this.add(panel,g);
		
		field1 = new JTextField("Item Information",29);
		field1.setHorizontalAlignment(JTextField.CENTER);
		field1.setFont(bigFont);
		field1.setEditable(false);
		g.gridx = 0;
		g.gridy = 0;
		g.gridwidth = 4;
		//g.anchor = GridBagConstraints.PAGE_END;
		panel.add(field1,g);
		
		field2 = new JTextField("         Item ID:");
		field2.setHorizontalAlignment(JTextField.RIGHT);
		field2.setFont(bigFont);
		field2.setEditable(false);
		g.gridx = 0;
		g.gridy = 1;
		g.gridwidth = 2;
		panel.add(field2,g);
		
		field3 = new JTextField("",10);
		field3.setHorizontalAlignment(JTextField.CENTER);
		field3.setFont(bigFont);
		field3.setEditable(false);
		g.gridx = 2;
		g.gridy = 1;
		g.gridwidth = 2;
		panel.add(field3,g);
		
		field4 = new JTextField("           Part:");
		field4.setHorizontalAlignment(JTextField.RIGHT);
		field4.setFont(bigFont);
		field4.setEditable(false);
		g.gridx = 0;
		g.gridy = 2;
		g.gridwidth = 2;
		panel.add(field4,g);
		
		field5 = new JTextField("",10);
		field5.setHorizontalAlignment(JTextField.CENTER);
		field5.setFont(bigFont);
		field5.setEditable(true);
		g.gridx = 2;
		g.gridy = 2;
		g.gridwidth = 2;
		panel.add(field5,g);
		
		field6 = new JTextField(" Location:");
		field6.setHorizontalAlignment(JTextField.RIGHT);
		field6.setFont(bigFont);
		field6.setEditable(false);
		g.gridx = 0;
		g.gridy = 3;
		g.gridwidth = 2;
		panel.add(field6,g);
		
		locationList.setSelectedIndex(3);
		g.gridx = 2;
		g.gridy = 3;
		g.gridwidth = 2;
		panel.add(locationList,g);
		
		field7 = new JTextField("         Quanity:");
		field7.setHorizontalAlignment(JTextField.RIGHT);
		field7.setFont(bigFont);
		field7.setEditable(false);
		g.gridx = 0;
		g.gridy = 4;
		g.gridwidth = 2;
		panel.add(field7,g);
		
		field8 = new JTextField("",10);
		field8.setHorizontalAlignment(JTextField.CENTER);
		field8.setFont(bigFont);
		field8.setEditable(true);
		g.gridx = 2;
		g.gridy = 4;
		g.gridwidth = 2;
		panel.add(field8,g);
		//End Panel contents
		
		this.refreshList();
	}
	
	public void fieldFillIn(String part)
	{
		this.copy = part;
		Scanner scan = new Scanner(part);
		
		field3.setText(scan.next());
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
		field3.setText("");
		field5.setText("");
		field8.setText("");
		locationList.setSelectedIndex(3);
		this.copy = "";
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
			/*String locationname = scan.next() + " ";
			locationname += scan.next();
			if(locationname.equalsIgnoreCase("Facility 1"))
			{
				locationname += " " + scan.next() + " ";
				locationname += scan.next();
			}*/
			
			String copyamount = scan.next();
			scan.close();
			int check = model.editItem(field3.getText(),field5.getText(),field8.getText(),copyid,copypartname,copyamount);//locationname,copyamount);
			if(check==0)
			{
				model.addItem(field5.getText(),field8.getText());
			}
		}
		this.fieldCleanUp();
		return 0;
	}
	
	public void registerListeners(PartsButtonController controller1, InventoryListController controller2, PartsLocationController controller3) 
	{
		addButton.addActionListener(controller1);
		editButton.addActionListener(controller1);
		deleteButton.addActionListener(controller1);
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
