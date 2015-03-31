package parts;

import java.sql.Timestamp;

public interface PartGateway 
{
	public int loadInventory();
	public int loadParts();
	public int loadProdTemp();
	public int loadProdDetail();
	public int nextRow();
	public Timestamp prepareItemRow(int itemid);
	public void addItemRow(String text1, String text2, String text3, int number);
	public boolean updateItemRow(int number, String text1, String text2, String text3, Timestamp time);
	public boolean updateItemProductRow(int number, String text1, String text2, String text3, Timestamp time, int number2);
	public void deleteItemRow(int number);
	public void addPartRow(String text1, String text2, String text3, String text4, String text5);
	public void updatePartRow(int number, String text1, String text2, String text3, String text4, String text5);
	public void deletePartRow(int number);
	public void addProdTempRow(String text1, String text2);
	public void updateProdTempRow(int num, String text1, String text2);
	public void deleteProdTempRow(int num);
	public void addProdDetailRow(String partid, String tempid, String num);
	public void updateProdDetailRow(String partid, String tempid, String num);
	public void deleteProdDetailRow(int number);
	public String getProdDetailTempID();
	public String getProdDetailPartID();
	public String getProdDetailQuant();
	public String getItemID();
	public String getProdTempID();
	public String getProdTempNum();
	public String getProdTempDesc();
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
