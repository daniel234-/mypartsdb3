package parts;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

public class PartsUnitPartController implements ActionListener
{
	private PartsModel model;
	
	public PartsUnitPartController(PartsModel model)
	{
		this.model = model;
	}
	
	public void actionPerformed(ActionEvent a)
	{
        JComboBox cb = (JComboBox)a.getSource();
        String partName = (String)cb.getSelectedItem();
        model.setUnitPart(partName);
	}
}
