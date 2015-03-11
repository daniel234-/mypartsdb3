package parts;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PartsListController implements ListSelectionListener
{
	private PartsModel model;
	private PartsView view;
	private JList list;
	
	public PartsListController(PartsView view, PartsModel model)
	{
		this.view = view;
		this.model = model;
	}

	public void valueChanged(ListSelectionEvent e) 
	{
		list = view.getPartsList();
		String text = model.getPart(list.getSelectedIndex());
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
