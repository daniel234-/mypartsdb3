package parts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.*;

public class AuthenticationView extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenuItem chooseUserTom, chooseUserSue, chooseUserRagnar, quit;
	private JTextField userName, userNameText;
	private JTextField password;
	private JPasswordField passWordText;
	private JButton logIn;
	private JPanel panel;
	private GridBagConstraints g;
	private Authenticator a;
	private Session session = null;

	public AuthenticationView() {		

		panel = new JPanel(new GridBagLayout());
		g = new GridBagConstraints();
		userName = new JTextField("Enter User Email: ", 20);
		userName.setHorizontalAlignment(JTextField.RIGHT);
		userName.setEditable(false);
		g.gridx = 0;
		g.gridy = 1;
		g.gridwidth = 2;
		panel.add(userName, g);

		userNameText = new JTextField(20);
		userNameText.setEditable(true);
		g.gridx = 2;
		g.gridy = 1;
		g.gridwidth = 2;
		panel.add(userNameText, g);

		password = new JTextField("Enter Password: ", 20);
		password.setHorizontalAlignment(JTextField.RIGHT);
		password.setEditable(false);
		g.gridx = 0;
		g.gridy = 4;
		g.gridwidth = 2;
		panel.add(password, g);

		passWordText = new JPasswordField(20);
		passWordText.setEditable(true);
		g.gridx = 2;
		g.gridy = 4;
		g.gridwidth = 2;
		panel.add(passWordText, g);

		logIn = new JButton("Log In");
		g.gridx = 0;
		g.gridy = 5;
		g.gridwidth = 2;
		panel.add(logIn, g);
		panel.setBackground(Color.WHITE);
		add(panel);
	}

	public void registerListeners(Authenticator other) {
		logIn.addActionListener(other);
	}

	public String getUserEmailText() {
		return this.userNameText.getText();
	}

	public char[] getUserPasswordText() {
		return this.passWordText.getPassword();
	}

	public void invalidCredentials() {
		JOptionPane.showMessageDialog(logIn,
				"The credentials entered were invalid/Or a user is logged in");
	}

	public void resetPassword(char otherPassWord[]) {
		String backToZero = new String(otherPassWord);
		this.passWordText.setText(backToZero);
		this.passWordText.setText("");
		this.userNameText.setText("");
	}

	public void getCredentials(Authenticator a) {
		try {
			String s = this.userNameText.getText();
			session = a.checkCredentials(this.userNameText.getText(),
					this.passWordText.getPassword());
			PartGateway pdg = null;
			pdg = new PartGatewaySQL();
			Dimension minimumSize = new Dimension(600, 600);
			PartsModel model = new PartsModel(pdg);
			ChooseView chView = new ChooseView(model, session, s);

			PartsButtonController controller1 = new PartsButtonController(
					chView.getPartsView(), chView.getInvView(),
					chView.getProdTempView(), chView.getTempDetailView(),
					model, session, chView);
			PartsListController controller2 = new PartsListController(
					chView.getPartsView(), model);
			InventoryListController controller3 = new InventoryListController(
					chView.getInvView(), model);
			PartsUnitPartController controller4 = new PartsUnitPartController(
					model);
			PartsLocationController controller5 = new PartsLocationController(
					model);
			TemplateListController tempListCont = new TemplateListController(
					chView.getProdTempView(), model);
			TemplateDetailListController tdlc = new TemplateDetailListController(
					chView.getTempDetailView(), model);
			//TabbedController tbc = new TabbedController(chView, session, chView.getInvView(), chView.getProdTempView());
			chView.registerViewListeners(controller1, controller2, controller4,
					controller3, controller5, tempListCont, tdlc);
			chView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			chView.setSize(600, 600);
			chView.setMinimumSize(minimumSize);
			chView.setResizable(true);
			chView.setVisible(true);
		} catch (Exception e) {
			invalidCredentials();
		}
	}

}
