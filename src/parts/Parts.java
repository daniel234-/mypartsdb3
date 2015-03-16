package parts;

//CS 4743 Assignment 3 by Daniel Gardner

import java.awt.Dimension;

import javax.swing.JFrame;

public class Parts 
{
	public static void main(String[] args) 
	{
		PartGateway pdg = null;
		//try {
		pdg = new PartGatewaySQL();
			
		/*} catch(GatewayException e) {
			System.out.println("Error creating DB connection: " + e.getMessage());
			System.exit(0);
		}*/
		
		Dimension minimumSize = new Dimension(600,600);
		PartsModel model = new PartsModel(pdg);
		ChooseView chView = new ChooseView(model);
		//PartsView view = new PartsView(model);
		//InventoryView view2 = new InventoryView(model);
		PartsButtonController controller1 = new PartsButtonController(chView.getPartsView(), chView.getInvView(), chView.getProdTempView(), 
				chView.getTempDetailView(), model);
		PartsListController controller2 = new PartsListController(chView.getPartsView(), model);
		InventoryListController controller3 = new InventoryListController(chView.getInvView(), model);
		PartsUnitPartController controller4 = new PartsUnitPartController(model);
		PartsLocationController controller5 = new PartsLocationController(model);
		TemplateListController tempListCont = new TemplateListController(chView.getProdTempView(), model);
		TemplateDetailListController tdlc = new TemplateDetailListController(chView.getTempDetailView(), model);
		chView.registerViewListeners(controller1, controller2, controller4, controller3, controller5, tempListCont, tdlc);
		
		chView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		chView.setSize(600, 600);
		chView.setMinimumSize(minimumSize);
		chView.setResizable(true);
		chView.setVisible(true);
		/*view.registerListeners(controller1,controller2,controller4);
		view2.registerListeners(controller1,controller3,controller5);
		
		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.setSize(600,600);
		view.setMinimumSize(minimumSize);
		view.setResizable(false);
		view.setVisible(true);
		
		view2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view2.setSize(600,600);
		view2.setLocation(690,0);
		view2.setMinimumSize(minimumSize);
		view2.setResizable(false);
		view2.setVisible(true);*/
	}
}