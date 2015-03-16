package parts;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class ProdTempView extends JFrame {
	private PartsModel model;
	private JPanel mainPanel, secondaryPanel;
	private JButton addButton, editButton, deleteButton;
	private GridBagLayout prodTempLayout;
	private DefaultListModel listModel;
	private JList prodTempList;
	private JTextField templateInfo, templateID, templateIDText, productNum,
			productNumText, productDesc, productDescText, quantity,
			quantityText, partID, partIDText;
	private GridBagConstraints g;
	private String copy = "";
	private Font bigFont;

	public ProdTempView(PartsModel otherMod) {
		this.model = otherMod;
		mainPanel = new JPanel();
		prodTempLayout = new GridBagLayout();
		g = new GridBagConstraints();
		mainPanel.setLayout(prodTempLayout);
		bigFont = new Font("Serif", Font.PLAIN, 20);

		listModel = new DefaultListModel();
		prodTempList = new JList(listModel);
		prodTempList.setFont(bigFont);
		prodTempList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		prodTempList.setVisibleRowCount(10);

		g.fill = GridBagConstraints.BOTH;
		g.gridx = 0;
		g.gridy = 0;
		g.weighty = 0.95;
		g.gridwidth = 3;
		mainPanel.add(prodTempList, g);

		addButton = new JButton("Add Template");
		addButton.setFont(bigFont);
		g.fill = GridBagConstraints.NONE;
		g.gridx = 0;
		g.gridy = 1;
		g.weightx = 0.5;
		g.weighty = 0.05;
		g.anchor = GridBagConstraints.LAST_LINE_START;
		mainPanel.add(addButton, g);

		editButton = new JButton("Edit Template");
		editButton.setFont(bigFont);
		g.gridx = 1;
		g.gridy = 1;
		g.weightx = 0.5;
		g.weighty = 0.05;
		g.anchor = GridBagConstraints.PAGE_END;
		mainPanel.add(editButton, g);

		deleteButton = new JButton("Delete Template");
		deleteButton.setFont(bigFont);
		g.gridx = 2;
		g.gridy = 1;
		g.weightx = 0.5;
		g.weighty = 0.05;
		g.anchor = GridBagConstraints.LAST_LINE_END;
		mainPanel.add(deleteButton, g);

		secondaryPanel = new JPanel(prodTempLayout);
		secondaryPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		g.gridx = 0;
		g.gridy = 2;
		g.gridwidth = 4;
		g.ipadx = 250;
		g.anchor = GridBagConstraints.PAGE_END;
		mainPanel.add(secondaryPanel, g);

		templateInfo = new JTextField("Product Template Information", 50);
		templateInfo.setHorizontalAlignment(JTextField.CENTER);
		templateInfo.setFont(bigFont);
		templateInfo.setEditable(false);
		g.gridx = 0;
		g.gridy = 0;
		g.gridwidth = 4;
		g.anchor = GridBagConstraints.PAGE_END;
		secondaryPanel.add(templateInfo, g);

		templateID = new JTextField("         Item ID: ");
		templateID.setHorizontalAlignment(JTextField.RIGHT);
		templateID.setFont(bigFont);
		templateID.setEditable(false);
		g.gridx = 0;
		g.gridy = 1;
		g.gridwidth = 2;
		secondaryPanel.add(templateID, g);

		templateIDText = new JTextField("", 10);
		templateIDText.setHorizontalAlignment(JTextField.CENTER);
		templateIDText.setFont(bigFont);
		templateIDText.setEditable(false);
		g.gridx = 2;
		g.gridy = 1;
		g.gridwidth = 2;
		secondaryPanel.add(templateIDText, g);

		productNum = new JTextField("         Product #: ");
		productNum.setHorizontalAlignment(JTextField.RIGHT);
		productNum.setFont(bigFont);
		productNum.setEditable(false);
		g.gridx = 0;
		g.gridy = 2;
		g.gridwidth = 2;
		secondaryPanel.add(productNum, g);

		productNumText = new JTextField("", 10);
		productNumText.setHorizontalAlignment(JTextField.CENTER);
		productNumText.setFont(bigFont);
		productNumText.setEditable(true);
		g.gridx = 2;
		g.gridy = 2;
		g.gridwidth = 2;
		secondaryPanel.add(productNumText, g);

		productDesc = new JTextField("Product Description: ");
		productDesc.setHorizontalAlignment(JTextField.RIGHT);
		productDesc.setFont(bigFont);
		productDesc.setEditable(false);
		g.gridx = 0;
		g.gridy = 3;
		g.gridwidth = 2;
		secondaryPanel.add(productDesc, g);

		productDescText = new JTextField("", 10);
		productDescText.setHorizontalAlignment(JTextField.CENTER);
		productDescText.setFont(bigFont);
		productDescText.setEditable(true);
		g.gridx = 2;
		g.gridy = 3;
		g.gridwidth = 2;
		secondaryPanel.add(productDescText, g);
		
		/*
		quantity = new JTextField("              Quantity: ");
		quantity.setHorizontalAlignment(JTextField.RIGHT);
		quantity.setFont(bigFont);
		quantity.setEditable(false);
		g.gridx = 0;
		g.gridy = 4;
		g.gridwidth = 2;
		secondaryPanel.add(quantity, g);

		quantityText = new JTextField("", 10);
		quantityText.setHorizontalAlignment(JTextField.CENTER);
		quantityText.setFont(bigFont);
		quantityText.setEditable(true);
		g.gridx = 2;
		g.gridy = 4;
		g.gridwidth = 2;
		secondaryPanel.add(quantityText, g);

		partID = new JTextField("                Part ID: ");
		partID.setHorizontalAlignment(JTextField.RIGHT);
		partID.setFont(bigFont);
		partID.setEditable(false);
		g.gridx = 0;
		g.gridy = 5;
		g.gridwidth = 2;
		secondaryPanel.add(partID, g);

		partIDText = new JTextField("", 10);
		partIDText.setHorizontalAlignment(JTextField.CENTER);
		partIDText.setFont(bigFont);
		partIDText.setEditable(false);
		g.gridx = 2;
		g.gridy = 5;
		g.gridwidth = 2;
		secondaryPanel.add(partIDText, g);*/
	}

	public void fillIn(String prodTemp) {
		this.copy = prodTemp;
		Scanner scan = new Scanner(prodTemp);
		scan.next(); // skip ID until implemented. will be changed

		productNumText.setText(scan.next());
		StringBuilder sb = new StringBuilder();
		sb.append(scan.next());
		String tempDesc;
		while(scan.hasNext()){
			sb.append(" ");
			sb.append(scan.next());
		}
		tempDesc = sb.toString();
		productDescText.setText(tempDesc);
		
		//quantityText.setText(scan.next());
		scan.close();
	}

	public int checkInput(int mode) {
		if (model.checkProdTemp(productNumText.getText(),
				productDescText.getText(), mode) == 1) {
			return 1;
		}
		if (mode == 1) {
			model.addProdTemp(productNumText.getText(),
					productDescText.getText());
		}
		if (mode == 2) {
			Scanner scan = new Scanner(this.copy);
			// again ID needs to be implemented
			scan.next();
			String copyProdNum = scan.next();
			StringBuilder sb = new StringBuilder();
			sb.append(scan.next());
			while(scan.hasNext()){
				sb.append(" ");
				sb.append(scan.next());
			}
			String copyDesc = sb.toString();
			scan.close();

			int check = model.editTemplate(productNumText.getText(),
					productDescText.getText(),
					copyProdNum, copyDesc);
			if (check == 0){
				model.addProdTemp(productNumText.getText(), productDescText.getText());
			}
		}
		this.fieldCleanUp();
		return 0;
	}

	public void refreshList() {
		listModel.clear();
		for (int n = 0; n < 12; n++) {
			String text = model.refreshProdTempList(n);
			if (text.equalsIgnoreCase(" ")) {
				n = 12;
			} else {
				listModel.addElement(text);
			}
		}
	}

	public void registerListeners(PartsButtonController pbc,
			TemplateListController tlc) {
		addButton.addActionListener(pbc);
		editButton.addActionListener(pbc);
		deleteButton.addActionListener(pbc);
		prodTempList.addListSelectionListener(tlc);
	}

	public JList getProdTemplateList() {
		return this.prodTempList;
	}

	public void fieldCleanUp() {
		templateIDText.setText("");
		productNumText.setText("");
		productDescText.setText("");
		//quantityText.setText("");
		copy = "";
	}

	public JPanel getProdTempPanel() {
		return this.mainPanel;
	}

}
