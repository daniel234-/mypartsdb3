package parts;

import java.awt.*;
import java.util.Scanner;

import javax.swing.*;

public class ProdTempDetailView {

	private PartsModel mod;
	private JPanel mainPanel, secondaryPanel;
	private JButton addButton, editButton, deleteButton;
	private GridBagLayout prodDetailLayout;
	private DefaultListModel listModel;
	private JList prodDetailList;
	private JTextField templateDetailInfo, prodTempID, prodTempIDText, partID,
			partIDText, quantity, quantityText;
	private GridBagConstraints g;
	private String copy = "";
	private Font littleFont, bigFont;

	public ProdTempDetailView(PartsModel otherModel) {
		this.mod = otherModel;
		mainPanel = new JPanel();
		prodDetailLayout = new GridBagLayout();
		g = new GridBagConstraints();
		mainPanel.setLayout(prodDetailLayout);
		littleFont = new Font("Serif", Font.PLAIN, 15);
		bigFont = new Font("Serif", Font.PLAIN, 20);

		listModel = new DefaultListModel();
		prodDetailList = new JList(listModel);
		prodDetailList.setFont(littleFont);
		prodDetailList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		prodDetailList.setVisibleRowCount(10);

		g.fill = GridBagConstraints.BOTH;
		g.gridx = 0;
		g.gridy = 0;
		g.weighty = 0.95;
		g.gridwidth = 3;
		mainPanel.add(prodDetailList, g);

		addButton = new JButton("Add Template Detail");
		addButton.setFont(littleFont);
		g.fill = GridBagConstraints.NONE;
		g.gridx = 0;
		g.gridy = 1;
		g.weightx = 0.5;
		g.weighty = 0.05;
		g.anchor = GridBagConstraints.LAST_LINE_START;
		mainPanel.add(addButton, g);

		editButton = new JButton("Edit Template Detail");
		editButton.setFont(littleFont);
		g.gridx = 1;
		g.gridy = 1;
		g.weightx = 0.5;
		g.weighty = 0.05;
		g.anchor = GridBagConstraints.PAGE_END;
		mainPanel.add(editButton, g);

		deleteButton = new JButton("Delete Template Detail");
		deleteButton.setFont(littleFont);
		g.gridx = 2;
		g.gridy = 1;
		g.weightx = 0.5;
		g.weighty = 0.05;
		g.anchor = GridBagConstraints.LAST_LINE_END;
		mainPanel.add(deleteButton, g);

		secondaryPanel = new JPanel(prodDetailLayout);
		secondaryPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		g.gridx = 0;
		g.gridy = 2;
		g.gridwidth = 4;
		g.ipadx = 250;
		g.anchor = GridBagConstraints.PAGE_END;
		mainPanel.add(secondaryPanel, g);

		templateDetailInfo = new JTextField("Template Detail Information", 50);
		templateDetailInfo.setHorizontalAlignment(JTextField.CENTER);
		templateDetailInfo.setFont(bigFont);
		templateDetailInfo.setEditable(false);
		g.gridx = 0;
		g.gridy = 0;
		g.gridwidth = 4;
		g.anchor = GridBagConstraints.PAGE_END;
		secondaryPanel.add(templateDetailInfo, g);

		prodTempID = new JTextField("         Template ID: ");
		prodTempID.setHorizontalAlignment(JTextField.RIGHT);
		prodTempID.setFont(bigFont);
		prodTempID.setEditable(false);
		g.gridx = 0;
		g.gridy = 1;
		g.gridwidth = 2;
		secondaryPanel.add(prodTempID, g);

		prodTempIDText = new JTextField("", 10);
		prodTempIDText.setHorizontalAlignment(JTextField.CENTER);
		prodTempIDText.setFont(bigFont);
		prodTempIDText.setEditable(false);
		g.gridx = 2;
		g.gridy = 1;
		g.gridwidth = 2;
		secondaryPanel.add(prodTempIDText, g);

		partID = new JTextField("         Part ID: ");
		partID.setHorizontalAlignment(JTextField.RIGHT);
		partID.setFont(bigFont);
		partID.setEditable(false);
		g.gridx = 0;
		g.gridy = 2;
		g.gridwidth = 2;
		secondaryPanel.add(partID, g);

		partIDText = new JTextField("", 10);
		partIDText.setHorizontalAlignment(JTextField.CENTER);
		partIDText.setFont(bigFont);
		partIDText.setEditable(false);
		g.gridx = 2;
		g.gridy = 2;
		g.gridwidth = 2;
		secondaryPanel.add(partIDText, g);

		quantity = new JTextField("         Quantity: ");
		quantity.setHorizontalAlignment(JTextField.RIGHT);
		quantity.setFont(bigFont);
		quantity.setEditable(false);
		g.gridx = 0;
		g.gridy = 3;
		g.gridwidth = 2;
		secondaryPanel.add(quantity, g);

		quantityText = new JTextField("", 10);
		quantityText.setHorizontalAlignment(JTextField.CENTER);
		quantityText.setFont(bigFont);
		quantityText.setEditable(true);
		g.gridx = 2;
		g.gridy = 3;
		g.gridwidth = 2;
		secondaryPanel.add(quantityText, g);
	}

	
	public void fillIn(String prodDetail){
		this.copy = prodDetail;
		Scanner scan = new Scanner(copy);
		/*skip id until implemented*/
		scan.next();
		scan.next();
		quantityText.setText(scan.next());
		scan.close();
	}
	public int checkInput(int mode) {
		if (mod.checkProdDetail(quantityText.getText(), mode) == 1) {
			return 1;
		}
		if (mode == 1) {
			mod.addProdDetail(quantityText.getText());
		}
		if (mode == 2) {
			Scanner scan = new Scanner(this.copy);
			scan.next();
			scan.next();
			String copyQuantity = scan.next();
			int check = mod.editProdDetail(quantityText.getText(), copyQuantity);
			if(check == 0){
				mod.addProdDetail(quantityText.getText());
			}
		}
		this.fieldCleanUp();
		return 0;
	}
	
	public void refreshList(){
		listModel.clear();
		for(int n = 0; n < 12; n++){
			String text = mod.refreshProdDetailList(n);
			if(text.equalsIgnoreCase(" ")){
				n = 12;
			} else{
				listModel.addElement(text);
			}
		}
	}

	public void registerListeners(PartsButtonController pbc, TemplateDetailListController tldc) {
		addButton.addActionListener(pbc);
		editButton.addActionListener(pbc);
		deleteButton.addActionListener(pbc);
		prodDetailList.addListSelectionListener(tldc);
	}

	public void fieldCleanUp() {
		prodTempIDText.setText("");
		partIDText.setText("");
		quantityText.setText("");
		copy = "";
	}

	public JList getProdDetailList() {
		return this.prodDetailList;
	}

	public JPanel getTempDetailPanel() {
		return this.mainPanel;
	}
}
