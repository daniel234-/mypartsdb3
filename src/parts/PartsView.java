package parts;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class PartsView
{
	private JPanel mainPanel;
	private PartsModel model;
	private JButton addButton;
	private JButton editButton;
	private JButton deleteButton;
	private GridBagLayout partLayout;
	private DefaultListModel listModel;
	private JList partsList;
	private JTextField field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12;
	private GridBagConstraints g = new GridBagConstraints();
	private String copy = "";
	private Font bigFont = new Font("Serif", Font.PLAIN, 20);
	private String[] units = {"Linear Feet", "Pieces", "Unknown"};
	private JComboBox unitList = new JComboBox(units);
	
	public PartsView(PartsModel model)
	{
		//super("Parts System");
		this.model = model;
		
		mainPanel = new JPanel();
		partLayout = new GridBagLayout();
		GridBagConstraints g = new GridBagConstraints();
		mainPanel.setLayout(partLayout);
		//this.setLayout(partLayout);
		Font bigFont = new Font("Serif", Font.PLAIN, 20); 
		
		listModel = new DefaultListModel();
		partsList = new JList(listModel);
		partsList.setFont(bigFont);
        partsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        partsList.setVisibleRowCount(12);
		g.fill = GridBagConstraints.BOTH;
		g.gridx = 0;
		g.gridy = 0;
		g.weighty = 0.95;
		g.gridwidth = 4;
		mainPanel.add(partsList, g);
		//this.add(partsList,g);
		
		addButton = new JButton("Add Part");
		addButton.setFont(bigFont);
		g.fill = GridBagConstraints.NONE;
		g.gridx = 0;
		g.gridy = 1;
		g.weightx = 0.5;
		g.weighty = 0.05;
		g.anchor = GridBagConstraints.LAST_LINE_START;
		mainPanel.add(addButton, g);
		//this.add(addButton,g);
		
		editButton = new JButton("Edit Part");
		editButton.setFont(bigFont);
		g.fill = GridBagConstraints.NONE;
		g.gridx = 1;
		g.gridy = 1;
		g.weightx = 0.5;
		g.weighty = 0.05;
		g.anchor = GridBagConstraints.PAGE_END;
		mainPanel.add(editButton, g);
		//this.add(editButton,g);
		
		deleteButton = new JButton("Delete Part");
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
		JPanel panel = new JPanel(partLayout);
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		g.gridx = 0;
		g.gridy = 2;
		g.gridwidth = 4;
		g.ipadx = 130;
		g.anchor = GridBagConstraints.PAGE_END;
		mainPanel.add(panel, g);
		//this.add(panel,g);
		
		field1 = new JTextField("Part Information",29);
		field1.setHorizontalAlignment(JTextField.CENTER);
		field1.setFont(bigFont);
		field1.setEditable(false);
		g.gridx = 0;
		g.gridy = 0;
		g.gridwidth = 4;
		g.anchor = GridBagConstraints.PAGE_END;
		panel.add(field1,g);
		
		field2 = new JTextField("         Part ID:");
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
		
		field4 = new JTextField("    Part Name:");
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
		
		field6 = new JTextField("Part Number:");
		field6.setHorizontalAlignment(JTextField.RIGHT);
		field6.setFont(bigFont);
		field6.setEditable(false);
		g.gridx = 0;
		g.gridy = 3;
		g.gridwidth = 2;
		panel.add(field6,g);
		
		field7 = new JTextField("",10);
		field7.setHorizontalAlignment(JTextField.CENTER);
		field7.setFont(bigFont);
		field7.setEditable(true);
		g.gridx = 2;
		g.gridy = 3;
		g.gridwidth = 2;
		panel.add(field7,g);

		field8 = new JTextField(" Unit Amount:");
		field8.setHorizontalAlignment(JTextField.RIGHT);
		field8.setFont(bigFont);
		field8.setEditable(false);
		g.gridx = 0;
		g.gridy = 5;
		g.gridwidth = 2;
		panel.add(field8,g);
		
		unitList.setSelectedIndex(2);
		g.gridx = 2;
		g.gridy = 5;
		g.gridwidth = 2;
		panel.add(unitList,g);

		field9 = new JTextField(" Part Vendor:");
		field9.setHorizontalAlignment(JTextField.RIGHT);
		field9.setFont(bigFont);
		field9.setEditable(false);
		g.gridx = 0;
		g.gridy = 6;
		g.gridwidth = 2;
		panel.add(field9,g);
		
		field10 = new JTextField("",10);
		field10.setHorizontalAlignment(JTextField.CENTER);
		field10.setFont(bigFont);
		field10.setEditable(true);
		g.gridx = 2;
		g.gridy = 6;
		g.gridwidth = 2;
		panel.add(field10,g);
		
		field11 = new JTextField("Part External #:");
		field11.setHorizontalAlignment(JTextField.RIGHT);
		field11.setFont(bigFont);
		field11.setEditable(false);
		g.gridx = 0;
		g.gridy = 7;
		g.gridwidth = 2;
		panel.add(field11,g);
		
		field12 = new JTextField("",10);
		field12.setHorizontalAlignment(JTextField.CENTER);
		field12.setFont(bigFont);
		field12.setEditable(true);
		g.gridx = 2;
		g.gridy = 7;
		g.gridwidth = 2;
		panel.add(field12,g);
		//End Panel contents
		
		this.refreshList();
	}
	
	public void fieldFillIn(String part)
	{
		this.copy = part;
		Scanner scan = new Scanner(part);
		
		field3.setText(scan.next());
		field5.setText(scan.next());
		field7.setText(scan.next());
		
		String unitName = scan.next();
		if(unitName.equalsIgnoreCase("Linear"))
		{
			unitList.setSelectedIndex(0);
			scan.next();
		}
		else
		{
			unitList.setSelectedIndex(1);
		}
		
		try
		{
			field10.setText(scan.next());
		}
		catch (NoSuchElementException e)
		{
			field10.setText("");
		}
		try
		{
			field12.setText(scan.next());
		}
		catch (NoSuchElementException e)
		{
			field12.setText("");
		}
		scan.close();
	}
	
	public int checkInput(int mode)
	{
		if(model.checkPart(field5.getText(),field7.getText(),field12.getText()) == 1)
		{
			return 1;
		}
		if(mode==1)
		{
			model.addPart(field5.getText(),field7.getText(),field10.getText(),field12.getText());
		}
		if(mode==2)
		{
			Scanner scan = new Scanner(this.copy);
			String copyid = scan.next();
			String copyname = scan.next();
			String copynumber = scan.next();
			
			String unitName = scan.next();
			if(unitName.equalsIgnoreCase("Linear"))
			{
				scan.next();
			}
			
			String copyvendor;
			String copyexternal;
			
			try
			{
				copyvendor = scan.next();
			}
			catch (NoSuchElementException e)
			{
				copyvendor = "";
			}
			
			try
			{
				copyexternal = scan.next();
			}
			catch (NoSuchElementException e)
			{
				copyexternal = "";
			}
			
			scan.close();
			int check = model.editPart(field3.getText(),field5.getText(),field7.getText(),field10.getText(),field12.getText(),copyid,copyname,copynumber,copyvendor,copyexternal);
			if(check==0)
			{
				model.addPart(field5.getText(),field7.getText(),field10.getText(),field12.getText());
			}
		}
		this.fieldCleanUp();
		return 0;
	}
	
	public void fieldCleanUp()
	{
		field3.setText("");
		field5.setText("");
		field7.setText("");
		field10.setText("");
		field12.setText("");
		unitList.setSelectedIndex(2);
		this.copy = "";
	}
	
	public void registerListeners(PartsButtonController controller1, PartsListController controller2, PartsUnitPartController controller3)//, PartsLocationController controller4) 
	{
		addButton.addActionListener(controller1);
		editButton.addActionListener(controller1);
		deleteButton.addActionListener(controller1);
		partsList.addListSelectionListener(controller2);
		unitList.addActionListener(controller3);
	}

	public void refreshList() 
	{
		listModel.clear();
		model.partListFill();
		for(int n = 0; n < 12; n++)
		{
			String text = model.refreshPartList(n);
			if(text.equalsIgnoreCase(" "))
			{
				n = 12;
			}
			else
			{
				listModel.addElement(model.refreshPartList(n));
			}
		}
	}

	public JList getPartsList() 
	{
		return this.partsList;
	}
	
	public JPanel getPartPanel(){
		return mainPanel;
	}
	public void invalidAddPermission() {
		JOptionPane.showMessageDialog(addButton,
				"You do not have permission to do that");
	}

	public void invalidEditPermission() {
		JOptionPane.showMessageDialog(editButton,
				"You do not have permission to do that");
	}

	public void invalidDeletePermission() {
		JOptionPane.showMessageDialog(deleteButton,
				"You do not have permission to do that");
	}
	
	
}
