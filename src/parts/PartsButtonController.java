package parts;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;

public class PartsButtonController implements ActionListener
{
	private PartsModel model;
	private PartsView view;
	private InventoryView view2;
	private JList list;
	
	public PartsButtonController(PartsView view, InventoryView view2, PartsModel model)
	{
		this.model = model;
		this.view = view;
		this.view2 = view2;
	}

	public void actionPerformed(ActionEvent e) 
	{
		String command = e.getActionCommand();
		if(command.equalsIgnoreCase("Add Part"))
		{
			view.checkInput(1);
			view.refreshList();
		}
		if(command.equalsIgnoreCase("Edit Part"))
		{
			view.checkInput(2);
			view.refreshList();
		}
		if(command.equalsIgnoreCase("Delete Part"))
		{
			list = view.getPartsList();
			String text = model.getPart(list.getSelectedIndex());
			model.deletePart(text);
			view.fieldCleanUp();
			view.refreshList();
		}
		if(command.equalsIgnoreCase("Add Item"))
		{
			view2.checkInput(1);
			view2.refreshList();
		}
		if(command.equalsIgnoreCase("Edit Item"))
		{
			view2.checkInput(2);
			view2.refreshList();
		}
		if(command.equalsIgnoreCase("Delete Item"))
		{
			list = view2.getInventoryList();
			String text = model.getItem(list.getSelectedIndex());
			model.deleteItem(text);
			view2.fieldCleanUp();
			view2.refreshList();
		}
		if(command.equals("Add Product Template")){
			//TODO
		}
		if(command.equals("Edit Product Template")){
			//TODO
		}
		if(command.equals("Delete Product Template")){
			//TODO
		}
	}
}
