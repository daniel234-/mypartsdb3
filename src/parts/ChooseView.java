package parts;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

public class ChooseView extends JFrame {

	private JTabbedPane tabPane;
	private PartsView partView;
	private InventoryView invView;
	private ProdTempView prodTempView;
	private ProdTempDetailView prodTempDetailView;
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem chooseUserTom, chooseUserSue, chooseUserRagnar;
	private JMenuItem logout;
	private Session session;

	public ChooseView(PartsModel otherModel, Session otherSession, String s) {
		super(s);
		session = otherSession;
		tabPane = new JTabbedPane();

		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		logout = new JMenuItem("Log Out");
		/*chooseUserTom = new JMenuItem("Tom Jones");
		fileMenu.add(chooseUserTom);
		chooseUserSue = new JMenuItem("Sue Smith");
		fileMenu.add(chooseUserSue);
		chooseUserRagnar = new JMenuItem("Ragnar Nelson");
		fileMenu.add(chooseUserRagnar);*/
		fileMenu.add(logout);
		menuBar.add(fileMenu);
		
		//tabPane.add(menuBar);
		invView = new InventoryView(otherModel);
		partView = new PartsView(otherModel);
		prodTempView = new ProdTempView(otherModel);
		prodTempDetailView = new ProdTempDetailView(otherModel);
		
		tabPane.addTab("View Inventory", invView.getInvPanel());
		tabPane.addTab("View Parts", partView.getPartPanel());
		if (session.isCanViewProdTemp()){
			tabPane.addTab("View Product Templates",
				prodTempView.getProdTempPanel());
			tabPane.addTab("View Product Template Part Detail",
				prodTempDetailView.getTempDetailPanel());
		}
		add(tabPane);
		setJMenuBar(menuBar);
		//tabPane.add(menuBar);
	}

	public void registerViewListeners(PartsButtonController pbc,
			PartsListController plc, PartsUnitPartController puc,
			InventoryListController ilc, PartsLocationController ploc,
			TemplateListController tlc, TemplateDetailListController tldc){ //TabbedController tbc) {
		partView.registerListeners(pbc, plc, puc);
		invView.registerListeners(pbc, ilc, ploc);
		prodTempView.registerListeners(pbc, tlc);
		prodTempDetailView.registerListeners(pbc,tldc);
		logout.addActionListener(pbc);
		this.addWindowListener(pbc);
		//tabPane.addChangeListener(tbc);
	}
	/*
	public void registerActionListeners(){
		chooseUserTom.addActionListener();
		chooseUserSue.addActionListener();
		chooseUserRagnar.addActionListener();
	}*/

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
	
	public JTabbedPane getTabbedPane(){
		return this.tabPane;
	}

}


