package parts;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;

public class PartsButtonController implements ActionListener
{
	private PartsModel model;
	private PartsView view;
	private InventoryView view2;
	private ProdTempView view3;
	private ProdTempDetailView view4;
	private JList list;
	
	public PartsButtonController(PartsView view, InventoryView view2, ProdTempView otherview3, 
			ProdTempDetailView otherview4, PartsModel model)
	{
		this.model = model;
		this.view = view;
		this.view2 = view2;
		this.view3 = otherview3;
		this.view4 = otherview4;
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
			view2.removePanel();
			view2.itemPanel();
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
		if(command.equalsIgnoreCase("Create Product"))
		{
			view2.removePanel();
			view2.fieldCleanUp();
			view2.productPanel();
			view2.refreshList();
		}
		if(command.equals("Add Template")){
			//TODO
			view3.checkInput(1);
			view3.refreshList();
		}
		if(command.equals("Edit Template")){
			//TODO
			view3.checkInput(2);
			view3.refreshList();
		}
		if(command.equals("Delete Template")){
			//TODO
			list = view3.getProdTemplateList();
			String delete = model.getProdTemp(list.getSelectedIndex());
			model.deleteTemplate(delete);
			view3.fieldCleanUp();
			view3.refreshList();
		}
		if(command.equals("Add Template Detail")){
			//TODO
			view4.checkInput(1);
			view4.refreshList();
		}
		if(command.equals("Edit Template Detail")){
			//TODO
			view4.checkInput(2);
			view4.refreshList();
		}
		if(command.equals("Delete Template Detail")){
			//TODO
			list = view4.getProdDetailList();
			String delete = model.getProdDetail(list.getSelectedIndex());
			model.deleteProdDetail(delete);
			view4.fieldCleanUp();
			view4.refreshList();
		}
	}
}