package parts;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class ChooseView extends JFrame {

	private JTabbedPane tabPane;
	private PartsView partView;
	private InventoryView invView;
	private ProdTempView prodTempView;

	public ChooseView(PartsModel otherModel) {
		tabPane = new JTabbedPane();

		invView = new InventoryView(otherModel);
		partView = new PartsView(otherModel);
		prodTempView = new ProdTempView(otherModel);
		
		tabPane.addTab("View Inventory", invView.getInvPanel());
		tabPane.addTab("View Parts", partView.getPartPanel());
		tabPane.addTab("View Product Templates", prodTempView.getProdTempPanel());
		
		add(tabPane);
	}

	public void registerViewListeners(PartsButtonController pbc,
			PartsListController plc, PartsUnitPartController puc,
			InventoryListController ilc, PartsLocationController ploc) {
		partView.registerListeners(pbc, plc, puc);
		invView.registerListeners(pbc, ilc, ploc);
		prodTempView.registerListeners(pbc);
	}
	
	public InventoryView getInvView(){
		return invView;
	}
	
	public PartsView getPartsView(){
		return partView;
	}
	
	public ProdTempView getProdTempView(){
		return prodTempView;
	}

}
