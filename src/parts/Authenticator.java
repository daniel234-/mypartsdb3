package parts;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Authenticator implements ActionListener {

	private UserModel usermodel;
	private AuthenticationView view;
	private Session session;
	int index;
	boolean flag;

	public Authenticator(UserModel otherModel, AuthenticationView otherView) {
		this.usermodel = otherModel;
		this.view = otherView;
		this.session = new Session();
		index = -1;
		flag = false;
	}

	public Session checkCredentials(String emailText, char[] passWordText)
			throws Exception {
		if (session.getSessionStatus() == true){
			throw new Exception();
		}
		flag = false;
		String password = new String(passWordText);
		for (int i = 0; i < 3; i++) {
			if (emailText.equals(usermodel.getEmail(i))
					&& password.equals(usermodel.getPassword(i))) {
				for (int j = 0; j < passWordText.length; j++) {
					passWordText[j] = '0';
				}
				view.resetPassword(passWordText);
				session.getSession(usermodel.getUser(i));
				return session;
			}
		}
		for (int i = 0; i < passWordText.length; i++) {
			passWordText[i] = '0';
		}
		view.resetPassword(passWordText);
		index = -1;
		throw new Exception();
	}

	public Session returnSession() {
		return this.session;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Log In")) {
			view.getCredentials(this);
		}
	}
}
