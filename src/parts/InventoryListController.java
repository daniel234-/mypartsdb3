package parts;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class InventoryListController implements ListSelectionListener
{
	private PartsModel model;
	private InventoryView view;
	private JList list;
	
	public InventoryListController(InventoryView view, PartsModel model)
	{
		this.view = view;
		this.model = model;
	}

	public void valueChanged(ListSelectionEvent e) 
	{
		list = view.getInventoryList();
		String text = model.getItem(list.getSelectedIndex());
		if(text.equalsIgnoreCase("N/A"))
		{
			//do nothing
		}
		else
		{
			view.fieldFillIn(text);
		}
	}
}