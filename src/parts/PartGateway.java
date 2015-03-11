package parts;

public interface PartGateway 
{
	public void loadInventory();
	public void loadParts();
	public int nextRow();
	public void addItemRow(String text1, String text2, String text3);
	public void updateItemRow(int number, String text1, String text2, String text3);
	public void deleteItemRow(int number);
	public void addPartRow(String text1, String text2, String text3, String text4, String text5);
	public void updatePartRow(int number, String text1, String text2, String text3, String text4, String text5);
	public void deletePartRow(int number);
	public String getItemID();
	public String getPart();
	public String getLocation();
	public String getPartAmount();
	public String getPartID();
	public String getPartNumber();
	public String getPartName();
	public String getUnit();
	public String getVendor();
	public String getExternalPartNumber();
}
