package parts;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class ProdTempView extends JFrame {
	private JPanel mainPanel, secondaryPanel;
	private JButton addButton, editButton, deleteButton;
	private GridBagLayout prodTempLayout;
	private DefaultListModel listModel;
	private JList prodTempList;
	private JTextField templateInfo, templateID, templateIDText, productNum,
			productNumText, productDesc, productDescText;
	private GridBagConstraints g;
	private String copy = "";
	private Font bigFont;

	public ProdTempView() {
		mainPanel = new JPanel();
		prodTempLayout = new GridBagLayout();
		g = new GridBagConstraints();
		mainPanel.setLayout(prodTempLayout);
		bigFont = new Font("Serif", Font.PLAIN, 20);

		listModel = new DefaultListModel();
		prodTempList = new JList();
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
		templateIDText.setEditable(true);
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
		
		productDesc = new JTextField("Product Description: " );
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
	}

	public JPanel getProdTempPanel() {
		return this.mainPanel;
	}

}
