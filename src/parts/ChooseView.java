package parts;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class ChooseView extends JFrame {

	private JTabbedPane tabPane;
	private PartsView partView;
	private InventoryView invView;
	private ProdTempView prodTempView;
	private ProdTempDetailView prodTempDetailView;

	public ChooseView(PartsModel otherModel) {
		tabPane = new JTabbedPane();

		invView = new InventoryView(otherModel);
		partView = new PartsView(otherModel);
		prodTempView = new ProdTempView(otherModel);
		prodTempDetailView = new ProdTempDetailView(otherModel);

		tabPane.addTab("View Inventory", invView.getInvPanel());
		tabPane.addTab("View Parts", partView.getPartPanel());
		tabPane.addTab("View Product Templates",
				prodTempView.getProdTempPanel());
		tabPane.addTab("View Product Template Part Detail",
				prodTempDetailView.getTempDetailPanel());

		add(tabPane);
	}

	public void registerViewListeners(PartsButtonController pbc,
			PartsListController plc, PartsUnitPartController puc,
			InventoryListController ilc, PartsLocationController ploc,
			TemplateListController tlc, TemplateDetailListController tldc) {
		partView.registerListeners(pbc, plc, puc);
		invView.registerListeners(pbc, ilc, ploc);
		prodTempView.registerListeners(pbc, tlc);
		prodTempDetailView.registerListeners(pbc,tldc);
	}

	public InventoryView getInvView() {
		return invView;
	}

	public PartsView getPartsView() {
		return partView;
	}

	public ProdTempView getProdTempView() {
		return prodTempView;
	}
	
	public ProdTempDetailView getTempDetailView(){
		return prodTempDetailView;
	}

}


