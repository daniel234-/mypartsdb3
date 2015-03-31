package parts;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JList;

public class PartsButtonController implements ActionListener, WindowListener {
	private PartsModel model;
	private PartsView view;
	private InventoryView view2;
	private ProdTempView view3;
	private ProdTempDetailView view4;
	private Session session;
	private ChooseView view5;
	private JList list;

	public PartsButtonController(PartsView view, InventoryView view2,
			ProdTempView otherview3, ProdTempDetailView otherview4,
			PartsModel model, Session otherSession, ChooseView otherview5) {
		this.model = model;
		this.view = view;
		this.view2 = view2;
		this.view3 = otherview3;
		this.view4 = otherview4;
		this.session = otherSession;
		this.view5 = otherview5;
	}

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equalsIgnoreCase("Add Part")) {
			if (session.isCanAddParts()) {
				view.checkInput(1);
				view.refreshList();
			} else {
				view.invalidAddPermission();
			}
		}
		if (command.equalsIgnoreCase("Edit Part")) {
			if (session.isCanAddParts()) {
				view.checkInput(2);
				view.refreshList();
			} else {
				view.invalidEditPermission();
			}
		}
		if (command.equalsIgnoreCase("Delete Part")) {
			if (session.isCanDeleteParts()) {
				list = view.getPartsList();
				String text = model.getPart(list.getSelectedIndex());
				model.deletePart(text);
				view.fieldCleanUp();
				view.refreshList();
			} else {
				view.invalidDeletePermission();
			}
		}
		if (command.equalsIgnoreCase("Add Item")) {
			if (session.isCanAddInventory()) {
				view2.checkInput(1);
				view2.refreshList();
			} else {
				view2.invalidAddPermission();
			}
		}
		if (command.equalsIgnoreCase("Edit Item")) {
			if (session.isCanAddInventory()) {
				view2.checkInput(2);
				view2.refreshList();
			} else {
				view2.invalidEditPermission();
			}
		}
		if (command.equalsIgnoreCase("Delete Item")) {
			if (session.isCanDeleteInventory()) {
				list = view2.getInventoryList();
				String text = model.getItem(list.getSelectedIndex());
				model.deleteItem(text);
				view2.fieldCleanUp();
				view2.refreshList();
			} else {
				view2.invalidDeletePermission();
			}
		}
		if(command.equalsIgnoreCase("Create Product"))
		{
			view2.removePanel();
			view2.fieldCleanUp();
			view2.productPanel();
			view2.refreshList();
		}
		if (command.equals("Add Template")) {
			if (session.isCanAddProdTemp()) {
				view3.checkInput(1);
				view3.refreshList();
			} else {
				view3.invalidAddPermission();
			}
		}
		if (command.equals("Edit Template")) {
			if (session.isCanAddProdTemp()) {
				view3.checkInput(2);
				view3.refreshList();
			} else {
				view3.invalidEditPermission();
			}
		}
		if (command.equals("Delete Template")) {
			if (session.isCanDelProdTemp()) {
				list = view3.getProdTemplateList();
				String delete = model.getProdTemp(list.getSelectedIndex());
				model.deleteTemplate(delete);
				view3.fieldCleanUp();
				view3.refreshList();
			} else {
				view3.invalidDeletePermission();
			}
		}
		if (command.equals("Add Template Detail")) {
			// TODO
			view4.checkInput(1);
			view4.refreshList();
		}
		if (command.equals("Edit Template Detail")) {
			// TODO
			view4.checkInput(2);
			view4.refreshList();
		}
		if (command.equals("Delete Template Detail")) {
			// TODO
			list = view4.getProdDetailList();
			String delete = model.getProdDetail(list.getSelectedIndex());
			model.deleteProdDetail(delete);
			view4.fieldCleanUp();
			view4.refreshList();
		}
		if (command.equals("Log Out")){
			view5.dispose();
			session.sessionLogOut();
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		view5.dispose();
		System.out.println("closing window");
		session.sessionLogOut();		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}