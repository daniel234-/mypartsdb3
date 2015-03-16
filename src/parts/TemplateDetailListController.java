package parts;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class TemplateDetailListController implements ListSelectionListener{

	private JList list;
	private PartsModel model;
	private ProdTempDetailView view;

	public TemplateDetailListController(ProdTempDetailView otherView,
			PartsModel otherModel) {
		this.view = otherView;
		this.model = otherModel;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		list = view.getProdDetailList();
		String s = model.getProdDetail(list.getSelectedIndex());
		if (s.equalsIgnoreCase("N/A")){
			
		} else{
			view.fillIn(s);
		}
		
	}

}
