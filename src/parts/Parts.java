package parts;

//CS 4743 Assignment 4 by Daniel Gardner and Stephen Leija

import java.awt.Dimension;

import javax.swing.JFrame;

public class Parts 
{
	public static void main(String[] args) 
	{
		PartGateway pdg = null;
		pdg = new PartGatewaySQL();
		
		Dimension minimumSize = new Dimension(600,600);
		PartsModel model = new PartsModel(pdg);
		ChooseView chView = new ChooseView(model);
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
	}
}