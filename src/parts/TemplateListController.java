package parts;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class TemplateListController implements ListSelectionListener {

	private PartsModel model;
	private ProdTempView view;
	private JList list;

	public TemplateListController(ProdTempView otherView, PartsModel otherMod) {
		view = otherView;
		model = otherMod;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		list = view.getProdTemplateList();
		String s = model.getProdTemp(list.getSelectedIndex());
		if(s.equalsIgnoreCase("N/A")){
			// nothing
		} else{
			view.fillIn(s);
		}
	}

}
