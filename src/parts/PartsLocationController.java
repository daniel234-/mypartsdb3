package parts;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

public class PartsLocationController implements ActionListener
{
	private PartsModel model;
	
	public PartsLocationController(PartsModel model)
	{
		this.model = model;
	}
	
	public void actionPerformed(ActionEvent a)
	{
        JComboBox cb = (JComboBox)a.getSource();
        String location = (String)cb.getSelectedItem();
        model.setLocation(location);
	}
}