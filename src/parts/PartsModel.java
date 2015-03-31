package parts;

import java.sql.Timestamp;
import java.util.Scanner;

public class PartsModel {
	private PartGateway pdg;
	private int width, height;
	private String[][] partArray = new String[12][6];
	private String[][] itemArray = new String[12][4];
	private String[][] prodTempArray = new String[12][3];
	private String[][] prodDetailArray = new String [12][3];
	private String text = "";
	private int mode = 0;
	private String unitPart = "";
	private String location = "";
	private String partid = "";
	private String templateid = "";

	public PartsModel(PartGateway pdg) {
		this.width = 0;
		this.height = 0;
		this.pdg = pdg;

		this.itemListFill();
		this.partListFill();
		this.prodTempListFill();
		this.prodDetailListFill();
	}
	
	public void prodDetailListFill(){
		int status = 0;
		int count = 0;
		if(pdg.loadProdDetail() == 1){
			return;
		}
		while(status == 0){
			prodDetailArray[count][0] = pdg.getProdDetailTempID();
			prodDetailArray[count][1] = pdg.getProdDetailPartID();
			prodDetailArray[count][2] = pdg.getProdDetailQuant();
			count++;
			status = pdg.nextRow();
		}
	}
	
	public void prodTempListFill(){
		int status = 0;
		int count = 0;
		if(pdg.loadProdTemp() == 1){
			return;
		}
		while (status == 0){
			prodTempArray[count][0] = pdg.getProdTempID();
			prodTempArray[count][1] = pdg.getProdTempNum();
			prodTempArray[count][2] = pdg.getProdTempDesc();
			count++;			
			status = pdg.nextRow();
		}
		
	}

	public void itemListFill() {
		int status = 0;
		int count = 0;
		if(pdg.loadInventory() == 1){
			return;
		}
		while (status == 0) {
			itemArray[count][0] = pdg.getItemID();
			itemArray[count][1] = pdg.getPart();
			itemArray[count][2] = pdg.getLocation();
			itemArray[count][3] = pdg.getPartAmount();
			count++;
			status = pdg.nextRow();
		}
	}

	public void partListFill() {
		int status = 0;
		int count = 0;
		if(pdg.loadParts() == 1){
			return;
		}
		while (status == 0) {
			partArray[count][0] = pdg.getPartID();
			partArray[count][1] = pdg.getPartName();
			partArray[count][2] = pdg.getPartNumber();
			partArray[count][3] = pdg.getUnit();
			partArray[count][4] = pdg.getVendor();
			partArray[count][5] = pdg.getExternalPartNumber();
			count++;
			status = pdg.nextRow();
		}
	}

	// Parts View
	public String refreshPartList(int n) {
		if (partArray[n][1] == null) {
			return " ";
		}
		if (partArray[n][4] == null && partArray[n][5] == null) {
			text = (partArray[n][0] + ", " + partArray[n][1] + ", "
					+ partArray[n][2] + ", " + partArray[n][3]);
			return this.text;
		}
		if (partArray[n][4] == null) {
			text = (partArray[n][0] + ", " + partArray[n][1] + ", "
					+ partArray[n][2] + ", " + partArray[n][3] + ", " + partArray[n][5]);
			return this.text;
		}
		if (partArray[n][5] == null) {
			text = (partArray[n][0] + ", " + partArray[n][1] + ", "
					+ partArray[n][2] + ", " + partArray[n][3] + ", " + partArray[n][4]);
			return this.text;
		} else {
			text = (partArray[n][0] + ", " + partArray[n][1] + ", "
					+ partArray[n][2] + ", " + partArray[n][3] + ", "
					+ partArray[n][4] + ", " + partArray[n][5]);
			return this.text;
		}
	}

	public void addPart(String name, String number, String vendor,
			String external) {
		for (int a = 0; a < partArray.length; a++) {
			if (partArray[a][1] == null) {
				partArray[a][1] = name;
				partArray[a][2] = number;
				partArray[a][3] = this.getUnitPart();
				if (!vendor.equalsIgnoreCase("")) {
					partArray[a][4] = vendor;
				}
				if (!external.equalsIgnoreCase("")) {
					partArray[a][5] = external;
				}
				pdg.addPartRow(partArray[a][1], partArray[a][2],
						partArray[a][3], partArray[a][4], partArray[a][5]);
				a = partArray.length;
				continue;
			}
			if (partArray[a][1].equalsIgnoreCase(name)) {
				a = partArray.length;
				System.out.println("Similar part name found, add failed.");
			}
			if (partArray[a][2].equalsIgnoreCase(number)) {
				a = partArray.length;
				System.out.println("Similar part number found, add failed.");
			}
		}
	}

	public String getPart(int index) {
		for (int g = 0; g < partArray.length; g++) {
			if (g == index) {
				text = (partArray[g][0] + " " + partArray[g][1] + " "
						+ partArray[g][2] + " " + partArray[g][3] + " "
						+ partArray[g][4] + " " + partArray[g][5]);
				return text;
			}
		}
		return "N/A";
	}

	public int editPart(String id, String name, String number, String vendor,
			String external, String copyid, String copyname, String copynumber,
			String copyvendor, String copyexternal) {
		for (int e = 0; e < partArray.length; e++) {
			if (partArray[e][1].equalsIgnoreCase(name)
					&& !name.equalsIgnoreCase(copyname)) {
				System.out.println("Similar part name found, edit failed.");
				return 1;
			}
			if (partArray[e][2].equalsIgnoreCase(number)
					&& !number.equalsIgnoreCase(copynumber)) {
				System.out.println("Similar part number found, edit failed.");
				return 1;
			}
			if (partArray[e][0].equalsIgnoreCase(copyid)
					&& partArray[e][1].equalsIgnoreCase(copyname)
					&& partArray[e][2].equalsIgnoreCase(copynumber)) {
				partArray[e][0] = id;
				partArray[e][1] = name;
				partArray[e][2] = number;
				partArray[e][3] = this.getUnitPart();
				partArray[e][4] = vendor;
				partArray[e][5] = external;
				Integer partid = Integer.parseInt(id);
				pdg.updatePartRow(partid, partArray[e][1], partArray[e][2],
						partArray[e][3], partArray[e][4], partArray[e][5]);
				return 1;
			}
		}
		return 0;
	}

	public void deletePart(String part) {
		int found = 0;
		Scanner scan = new Scanner(part);
		scan.next();
		text = scan.next();
		scan.close();
		for (int d = 0; d < partArray.length; d++) {
			if (found == 1 && d == partArray.length - 1) {
				partArray[d][0] = null;
				partArray[d][1] = null;
				partArray[d][2] = null;
				partArray[d][3] = null;
				partArray[d][4] = null;
				partArray[d][5] = null;
			} else if (found == 1 && d < partArray.length - 1) {
				partArray[d][0] = partArray[d + 1][0];
				partArray[d][1] = partArray[d + 1][1];
				partArray[d][2] = partArray[d + 1][2];
				partArray[d][3] = partArray[d + 1][3];
				partArray[d][4] = partArray[d + 1][4];
				partArray[d][5] = partArray[d + 1][5];
			} else if (partArray[d][1].equalsIgnoreCase(text)) {
				for (int c = 0; c < itemArray.length; c++) {
					if (partArray[d][1].equalsIgnoreCase(itemArray[c][1])) {
						System.out
								.println("An inventory item references this Part. Delete the Item first. Delete failed.");
						break;
					}
					if (c == itemArray.length - 1) 
					{
						for (int p = 0; p < prodDetailArray.length; p++)
						{
							if (partArray[d][0].equalsIgnoreCase(prodDetailArray[p][1]))
							{
								System.out.println("A product template detail references this Part. Delete the Template Detail first. Delete failed."); 
								break;
							}
							if (p == prodDetailArray.length - 1)
							{
								found = 1;
							}
						}
					}
				}
				if (found == 0) {
					break;
				}
				int partid = Integer.parseInt(partArray[d][0]);
				pdg.deletePartRow(partid);
				partArray[d][0] = partArray[d + 1][0];
				partArray[d][1] = partArray[d + 1][1];
				partArray[d][2] = partArray[d + 1][2];
				partArray[d][3] = partArray[d + 1][3];
				partArray[d][4] = partArray[d + 1][4];
				partArray[d][5] = partArray[d + 1][5];
			}
		}
	}

	public int checkPart(String name, String number, String external) {
		if (this.getUnitPart().equalsIgnoreCase("Unknown")) {
			System.out.println("Part unit cannot be Unknown.");
			return 1;
		}
		if (name.equalsIgnoreCase("")) {
			System.out.println("Invalid name.");
			return 1;
		}
		if (number.length() > 20) {
			System.out.println("Number too long.");
			return 1;
		}
		if (number.equalsIgnoreCase("")) {
			System.out.println("Invalid number.");
			return 1;
		}
		if (external.length() > 50) {
			System.out.println("External number too long.");
			return 1;
		}
		if (number.charAt(0) != 'P')
		{
			System.out.println("Number must start with a P.");
			return 1;
		}
		return 0;
	}

	// End Parts View

	// Inventory View
	public String refreshItemList(int n) 
	{
		if (itemArray[n][1] == null) {
			return " ";
		} else {
			text = (itemArray[n][0] + ", " + itemArray[n][1] + ", "
					+ itemArray[n][2] + ", " + itemArray[n][3]);
			return this.text;
		}
	}

	public void addItem(String partname, String amount) {
		for (int a = 0; a < itemArray.length; a++) {
			if (itemArray[a][1] == null) {
				itemArray[a][1] = partname;
				itemArray[a][2] = this.getLocation();
				itemArray[a][3] = amount;
				pdg.addItemRow(itemArray[a][1], itemArray[a][2],
						itemArray[a][3], 0);
				a = itemArray.length;
				continue;
			}
			if (itemArray[a][1].equalsIgnoreCase(partname)
					&& itemArray[a][2].equalsIgnoreCase(this.getLocation())) {
				a = itemArray.length;
				System.out
						.println("Similar part name/location found, add failed.");
			}
		}
	}
	
	public Timestamp prepareItem(int itemid)
	{
		Timestamp time = pdg.prepareItemRow(itemid);
		return time;
	}

	public String getItem(int index) {
		for (int g = 0; g < itemArray.length; g++) {
			if (g == index) {
				text = (itemArray[g][0] + " " + itemArray[g][1] + " "
						+ itemArray[g][2] + " " + itemArray[g][3]);

				return text;
			}
		}
		return "N/A";
	}

	public int editItem(String id, String partname, String amount,
			String copyid, String copypartname, String copyamount, Timestamp time) {
		for (int e = 0; e < itemArray.length; e++) {
			if (itemArray[e][1].equalsIgnoreCase(partname)
					&& !partname.equalsIgnoreCase(copypartname)) {
				if (itemArray[e][2].equalsIgnoreCase(this.getLocation())) {
					System.out
							.println("Similar part name/location found, edit failed.");
					return 1;
				}
			}
			if (itemArray[e][0].equalsIgnoreCase(copyid)
					&& itemArray[e][1].equalsIgnoreCase(copypartname)
					&& itemArray[e][3].equalsIgnoreCase(copyamount)) {
				itemArray[e][0] = id;
				itemArray[e][1] = partname;
				itemArray[e][2] = this.getLocation();
				itemArray[e][3] = amount;
				Integer partid = Integer.parseInt(id);
				boolean check = pdg.updateItemRow(partid, itemArray[e][1], itemArray[e][2],
						itemArray[e][3], time);
				if(check == false)
				{
					this.itemListFill();
				}
				return 1;
			}
		}
		return 0;
	}

	public void deleteItem(String part) {
		int found = 0;
		Scanner scan = new Scanner(part);
		scan.next();
		text = scan.next();
		scan.close();
		for (int d = 0; d < itemArray.length; d++) {
			if (found == 1 && d == itemArray.length - 1) {
				itemArray[d][0] = null;
				itemArray[d][1] = null;
				itemArray[d][2] = null;
				itemArray[d][3] = null;
			} else if (found == 1 && d < itemArray.length - 1) {
				itemArray[d][0] = itemArray[d + 1][0];
				itemArray[d][1] = itemArray[d + 1][1];
				itemArray[d][2] = itemArray[d + 1][2];
				itemArray[d][3] = itemArray[d + 1][3];

			} else if (itemArray[d][1].equalsIgnoreCase(text)) {
				if (!itemArray[d][3].equalsIgnoreCase("0")) {
					System.out.println("Quantity not zero, delete failed.");
					break;
				}
				found = 1;
				int partid = Integer.parseInt(itemArray[d][0]);
				pdg.deleteItemRow(partid);
				itemArray[d][0] = itemArray[d + 1][0];
				itemArray[d][1] = itemArray[d + 1][1];
				itemArray[d][2] = itemArray[d + 1][2];
				itemArray[d][3] = itemArray[d + 1][3];
			}
		}
	}

	public int checkItem(String partname, String amount, int mode) {
		int found = 0;
		if (this.getLocation().equalsIgnoreCase("Unknown")) {
			System.out.println("Location cannot be Unknown.");
			return 1;
		}
		if (partname.equalsIgnoreCase("")) {
			System.out.println("Invalid name.");
			return 1;
		}
		for (int c = 0; c < partArray.length; c++) {
			if (partname.equalsIgnoreCase(partArray[c][1])) {
				found = 1;
			}
		}
		if (found == 0) {
			System.out
					.println("Part name not found, create part name if it does not exist first.");
			return 1;
		}
		if (this.checkNumber(amount, mode) == 1) {
			System.out.println("Amount too low.");
			return 1;
		}
		return 0;
	}
	
	public int checkProduct(String productid, String description, String itemid1, String itemamount1, String itemid2, String itemamount2, String itemid3, String itemamount3)
	{
		int found = 0;
		String itemlocation = "";
		for (int i = 1; i < prodTempArray.length - 1; i++) 
		{
			if(prodTempArray[i][0] == null)
			{
				
			}
			else
			{
				if(prodTempArray[i][0].equalsIgnoreCase(productid))
				{
					if(prodTempArray[i][2].equalsIgnoreCase(description))
					{
						found = 1;
					}
					else
					{
						System.out.println("Description does not match the one referenced by this ID.");
						return 1;
					}
				}
			}
		}
		if(found != 1)
		{
			System.out.println("Product Template ID not found.");
			return 1;
		}
		found = 0;
		for (int i = 0; i < itemArray.length; i++) 
		{
			if(itemArray[i][0] == null)
			{
				
			}
			else
			{
				if(itemArray[i][0].equalsIgnoreCase(itemid1))
				{
					if(Integer.parseInt(itemArray[i][3]) >= Integer.parseInt(itemamount1))
					{
						itemlocation = itemArray[i][2];
						location = itemlocation;
						found = 1;
					}
					else
					{
						System.out.println("Not enough quantity of the first item to create this product.");
						return 1;
					}
				}
			}
		}
		if(found != 1)
		{
			System.out.println("First Item ID not found.");
			return 1;
		}
		found = 0;
		if(!itemid2.equalsIgnoreCase(""))
		{
			for (int i = 0; i < itemArray.length; i++) 
			{
				if(itemArray[i][0] == null)
				{
					
				}
				else
				{
					if(itemArray[i][0].equalsIgnoreCase(itemid2))
					{
						if(itemArray[i][2].equalsIgnoreCase(itemlocation))
						{
							if(Integer.parseInt(itemArray[i][3]) >= Integer.parseInt(itemamount2))
							{
								found = 1;
							}
							else
							{
								System.out.println("Not enough quantity of the second item to create this product.");
								return 1;
							}
						}
						else
						{
							System.out.println("Cannot get inventory from two locations to create a product.");
							return 1;
						}
					}
				}
			}
			if(found != 1)
			{
				System.out.println("Second Item ID not found.");
				return 1;
			}
			found = 0;
		}
		if(!itemid3.equalsIgnoreCase(""))
		{
			for (int i = 0; i < itemArray.length; i++) 
			{
				if(itemArray[i][0] == null)
				{
					
				}
				else
				{
					if(itemArray[i][0].equalsIgnoreCase(itemid3))
					{
						if(itemArray[i][2].equalsIgnoreCase(itemlocation))
						{
							if(Integer.parseInt(itemArray[i][3]) >= Integer.parseInt(itemamount3))
							{
								found = 1;
							}
							else
							{
								System.out.println("Not enough quantity of the third item to create this product.");
								return 1;
							}
						}
						else
						{
							System.out.println("Cannot get inventory from two locations to create a product.");
							return 1;
						}
					}
				}
			}
			if(found != 1)
			{
				System.out.println("Third Item ID not found.");
				return 1;
			}
			found = 0;
		}
		return 0;
	}
	
	public void addProduct(String copyproductid, String copydescription, String copyitemid1, String copyitemamount1, String copyitemid2, String copyitemamount2, String copyitemid3, String copyitemamount3) {
		for (int a = 0; a < itemArray.length; a++) {
			if (itemArray[a][1] == null) {
				int success = 1;
				success = useItems(copyitemid1, copyitemamount1, copyitemid2, copyitemamount2, copyitemid3, copyitemamount3, success);
				if(success != 0)
				{
					itemArray[a][1] = copydescription;
					itemArray[a][2] = this.getLocation();
					itemArray[a][3] = "1";
					Integer productid = Integer.parseInt(copyproductid);
					pdg.addItemRow(itemArray[a][1], itemArray[a][2],
							itemArray[a][3], productid);
					a = itemArray.length;
					continue;
				}
			}
			if (itemArray[a][1].equalsIgnoreCase(copydescription))
			{
				int success = 1;
				success = useItems(copyitemid1, copyitemamount1, copyitemid2, copyitemamount2, copyitemid3, copyitemamount3, success);
				if(success != 0)
				{
					Integer itemid = Integer.parseInt(itemArray[a][0]);
					Integer productid = Integer.parseInt(copyproductid);
					Integer oldamount = Integer.parseInt(itemArray[a][3]);
					Integer newamount = oldamount + 1;
					Timestamp time = this.prepareItem(itemid);
					itemArray[a][3] = "" + newamount;
					boolean check = pdg.updateItemProductRow(itemid, itemArray[a][1], itemArray[a][2],
							itemArray[a][3], time, productid);
					if(check == false)
					{
						itemArray[a][3] = "" + oldamount;
						this.itemListFill();
						success = 0;
					}
					a = itemArray.length;
					continue;
				}
			}
		}
	}

	// Ends Inventory View

	public int checkNumber(String text, int mode) {
		this.text = text;
		this.mode = mode;
		try {
			int number = Integer.parseInt(this.text);
			if (number < 1 && this.mode == 1) {
				return 1;
			}
			if (number < 0) {
				return 1;
			}
		} catch (NumberFormatException n) {
			return 1;
		}
		return 0;
	}
	
	public int useItems(String id1, String amount1, String id2, String amount2, String id3, String amount3, int success)
	{
		for (int b = 0; b < itemArray.length; b++)
		{
			if(itemArray[b][0] == null)
			{
				return success;
			}
			if(itemArray[b][0].equalsIgnoreCase(id1))
			{
				Integer itemid = Integer.parseInt(itemArray[b][0]);
				Integer oldamount = Integer.parseInt(itemArray[b][3]);
				Integer newamount = oldamount - Integer.parseInt(amount1);
				itemArray[b][3] = "" + newamount;
				Timestamp time = this.prepareItem(itemid);
				boolean check = pdg.updateItemRow(itemid, itemArray[b][1], itemArray[b][2],
						itemArray[b][3], time);
				if(check == false)
				{
					itemArray[b][3] = "" + oldamount;
					this.itemListFill();
					success = 0;
				}
			}
			if(itemArray[b][0].equalsIgnoreCase(id2))
			{
				Integer itemid = Integer.parseInt(itemArray[b][0]);
				Integer oldamount = Integer.parseInt(itemArray[b][3]);
				Integer newamount = oldamount - Integer.parseInt(amount2);
				itemArray[b][3] = "" + newamount;
				Timestamp time = this.prepareItem(itemid);
				boolean check = pdg.updateItemRow(itemid, itemArray[b][1], itemArray[b][2],
						itemArray[b][3], time);
				if(check == false)
				{
					itemArray[b][3] = "" + oldamount;
					this.itemListFill();
					success = 0;
				}
			}
			if(itemArray[b][0].equalsIgnoreCase(id3))
			{
				Integer itemid = Integer.parseInt(itemArray[b][0]);
				Integer oldamount = Integer.parseInt(itemArray[b][3]);
				Integer newamount = oldamount - Integer.parseInt(amount3);
				itemArray[b][3] = "" + newamount;
				Timestamp time = this.prepareItem(itemid);
				boolean check = pdg.updateItemRow(itemid, itemArray[b][1], itemArray[b][2],
						itemArray[b][3], time);
				if(check == false)
				{
					itemArray[b][3] = "" + oldamount;
					this.itemListFill();
					success = 0;
				}
			}
		}
		return success;
	}

	public String refreshProdTempList(int n) {
		if (prodTempArray[n][1] == null) {
			return " ";
		} else {
			text = (prodTempArray[n][0] + ", " + prodTempArray[n][1] + ", "
					+ prodTempArray[n][2]);// + ", " + prodTempArray[n][3]);
			return this.text;
		}
	}

	public void addProdTemp(String prodNum, String prodDesc) {
		for (int a = 0; a < prodTempArray.length; a++) {
			if (prodTempArray[a][1] == null) {
				prodTempArray[a][1] = prodNum;
				prodTempArray[a][2] = prodDesc;
				//sprodTempArray[a][3] = amount;
				pdg.addProdTempRow(prodTempArray[a][1], prodTempArray[a][2]);
				a = prodTempArray.length;
				continue;
			}
		}
	}

	public int editTemplate(String id, String prodNum, String prodDesc,
			String copyProdNum, String copyprodDesc, String copyID) {
		for (int i = 0; i < prodTempArray.length; i++) {
			if(prodTempArray[i][1].equalsIgnoreCase(prodNum)
					&& !prodNum.equalsIgnoreCase(copyProdNum)){
				System.out.println("Cannot change Product # to an existing "
						+ "Product #");
				return 1;
			}
			if (prodTempArray[i][2].equalsIgnoreCase(prodDesc)
					&& !prodDesc.equalsIgnoreCase(copyprodDesc)) {
				return 1;
			}
			if (prodTempArray[i][0].equalsIgnoreCase(copyID) &&
					prodTempArray[i][1].equalsIgnoreCase(copyProdNum)
					&& prodTempArray[i][2].equalsIgnoreCase(copyprodDesc)){
					//&& prodTempArray[i][3].equalsIgnoreCase(copyQuantity)) {
				prodTempArray[i][0] = id;
				prodTempArray[i][1] = prodNum;
				prodTempArray[i][2] = prodDesc;
				Integer convertID = Integer.parseInt(id);
				pdg.updateProdTempRow(convertID, prodNum, prodDesc);
				//prodTempArray[i][3] = quantity;
				return 1;
			}
		}
		return 0;
	}
	
	public void deleteTemplate(String template){
		int found = 0;
		Scanner scan = new Scanner(template);
		scan.next();
		text = scan.next();
		scan.close();
		for (int i = 0; i < prodTempArray.length; i++){
			if(found == 1 && i == prodTempArray.length - 1){
				prodTempArray[i][1] = null;
				prodTempArray[i][2] = null;
				//prodTempArray[i][3] = null;
			} else if (found == 1 && i < prodTempArray.length - 1){
				prodTempArray[i][1] =  prodTempArray[i+1][1];
				prodTempArray[i][2] = prodTempArray[i+1][2];
				//prodTempArray[i][3] = prodTempArray[i+1][3];
			} else if (prodTempArray[i][1].equalsIgnoreCase(text)){
				found = 1;
				Integer convertID = Integer.parseInt(prodTempArray[i][0]);
				pdg.deleteProdTempRow(convertID);
				prodTempArray[i][1] =  prodTempArray[i+1][1];
				prodTempArray[i][2] = prodTempArray[i+1][2];
				//prodTempArray[i][3] = prodTempArray[i+1][3];
			}
		}
	}
	
	public int checkProdTemp(String prodNum, String prodDesc,
			int mode) {
		if (prodNum.length() > 20 || prodNum == null || prodNum.equals("")) {
			System.out.println("Invalid Product Number: " + prodNum);
			return 1;
		}
		if (prodDesc.length() > 255 || prodNum == null
				|| prodDesc.charAt(0) != 'A') {
			System.out.println("Invalid Product Description");
			return 1;
		}
		for (int i = 0; i < prodTempArray.length; i++) {
			if (prodDesc.equalsIgnoreCase(prodTempArray[i][2]) && mode !=2) {
				System.out.println("Product Description must be unique");
				return 1;
			}
		}
		return 0;
	}

	public String getProdTemp(int index) {
		for (int i = 1; i < prodTempArray.length; i++) {
			if (i == index) {
				String retString;
				return retString = (prodTempArray[i][0] + " "
						+ prodTempArray[i][1] + " " + prodTempArray[i][2]);// + " " + prodTempArray[i][3]);
			}
		}
		return "N/A";
	}
	
	public String refreshProdDetailList(int n){
		if(prodDetailArray[n][2] == null){
			return " ";
		} else{
			String text = prodDetailArray[n][0] + ", " + prodDetailArray[n][1] + ", " + 
					prodDetailArray[n][2] ;
			return text;
		}
	}
	
	public String getProdDetail(int index){
		for (int i = 0; i < prodDetailArray.length; i++){
			if (i == index){
				String retString;
				return retString = (prodDetailArray[i][0] + " "
						+ prodDetailArray[i][1] + " " + prodDetailArray[i][2]);
			}
		}
		return "N/A";
	}
	
	public void addProdDetail(String partNum, String template, String quantity){
		for (int i = 0; i < prodDetailArray.length; i++) {
			if (prodDetailArray[i][1] != null) {
				if (prodDetailArray[i][1].equalsIgnoreCase(partid)
						&& templateid.equalsIgnoreCase(prodDetailArray[i][0])) {
					System.out.println("keys must form a unique index");
					return;
				}
			}
			if (partid.equalsIgnoreCase(prodDetailArray[i][1])) {
				if (!templateid.equalsIgnoreCase(prodDetailArray[i][0])) {
					System.out.println("Part must be unique to product");
					return;
				}
			}
			if (templateid.equalsIgnoreCase(prodDetailArray[i][0])) {
				if (!partid.equalsIgnoreCase(prodDetailArray[i][1])) {
					System.out.println("Product must be unique with this part");
					return;
				}
			}
			if (prodDetailArray[i][2] == null) {
				prodDetailArray[i][0] = templateid;
				prodDetailArray[i][1] = partid;
				prodDetailArray[i][2] = quantity;
				pdg.addProdDetailRow(templateid, partid, quantity);
				i = prodDetailArray.length;
				continue;
			}
		}		
		partid = "";
		templateid = "";
	}
	
	public int editProdDetail(String tempid, String otherpartid, String quantity, String copyQuant){
		for (int i = 0; i < prodDetailArray.length; i++) {
			if (prodDetailArray[i][2].equalsIgnoreCase(copyQuant)) {
				prodDetailArray[i][0] = tempid;
				prodDetailArray[i][1] = otherpartid;
				prodDetailArray[i][2] = quantity;
				pdg.updateProdDetailRow(otherpartid, tempid, quantity);
				return 1;
			}
		}
		partid = "";
		templateid = "";
		return 0;
	}
	
	public int checkProdDetail(String partNum, String template, String quantity, int mode){
		if(this.checkNumber(quantity, mode) == 1){
			return 1;
		}
		if(mode != 2){
		int flag = 0;
		for (int i = 0; i < partArray.length; i++){
			if (partNum.equalsIgnoreCase(partArray[i][2])){
				partid = partArray[i][0];
				flag = 1;
				break;
			}
		}
		if(flag == 0){
			System.out.println("The part specified has not been added to Parts Inventory");
			return 1;
		}
		for (int i = 0; i < prodTempArray.length; i++){
			if (template.equalsIgnoreCase(prodTempArray[i][2])){
				templateid = prodTempArray[i][0];
				flag = 1;
				break;
			}
		}
		if (flag == 0){
			System.out.println("The template specified has not been added to Product Template Inventory");
		}
		}
		return 0;		
	}
	
	public void deleteProdDetail(String detail){
		int found = 0;
		Scanner scan = new Scanner(detail);
		//scan.next();
		text = scan.next();
		scan.close();
		for (int i = 0; i < prodDetailArray.length; i++){
			if(found == 1 && i == prodDetailArray.length - 1){
				prodDetailArray[i][0] = null;
				prodDetailArray[i][1] = null;
				prodDetailArray[i][2] = null;
				//prodTempArray[i][3] = null;
			} else if (found == 1 && i < prodDetailArray.length - 1){
				prodDetailArray[i][0] = prodDetailArray[i+1][0];
				prodDetailArray[i][1] =  prodDetailArray[i+1][1];
				prodDetailArray[i][2] = prodDetailArray[i+1][2];
				//prodTempArray[i][3] = prodTempArray[i+1][3];
			} else if (prodDetailArray[i][0].equalsIgnoreCase(text)){
				found = 1;
				Integer convertID = Integer.parseInt(prodDetailArray[i][0]);
				pdg.deleteProdDetailRow(convertID);
				prodDetailArray[i][0] = prodDetailArray[i+1][0];
				prodDetailArray[i][1] =  prodDetailArray[i+1][1];
				prodDetailArray[i][2] = prodDetailArray[i+1][2];
				//prodTempArray[i][3] = prodTempArray[i+1][3];
			}
		}
	}

	public String getUnitPart() {
		return this.unitPart;
	}

	public void setUnitPart(String part) {
		this.unitPart = part;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public void setSize(int length1, int length2) {
		this.width = length1;
		this.height = length2;
	}
}
