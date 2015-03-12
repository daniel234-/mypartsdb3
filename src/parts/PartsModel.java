package parts;

import java.util.Scanner;

public class PartsModel {
	private PartGateway pdg;
	private int width, height;
	private String[][] partArray = new String[12][6];
	private String[][] itemArray = new String[12][4];
	private String[][] prodTempArray = new String[12][4];
	private String text = "";
	private int mode = 0;
	private String unitPart = "";
	private String location = "";

	public PartsModel(PartGateway pdg) {
		this.width = 0;
		this.height = 0;
		this.pdg = pdg;

		this.itemListFill();
		this.partListFill();
	}

	public void itemListFill() {
		int status = 0;
		int count = 0;
		pdg.loadInventory();
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
		pdg.loadParts();
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
					if (c == itemArray.length - 1) {
						found = 1;
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
		return 0;
	}

	// End Parts View

	// Inventory View
	public String refreshItemList(int n) {
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
						itemArray[a][3]);
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
			String copyid, String copypartname, String copyamount) {
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
				pdg.updateItemRow(partid, itemArray[e][1], itemArray[e][2],
						itemArray[e][3]);
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

	public String refreshProdTempList(int n){
		if(prodTempArray[n][1] == null){
			return " ";
		} else{
			text = (prodTempArray[n][0] + ", " + prodTempArray[n][1] + ","
					+prodTempArray[n][2] + ", " + prodTempArray[n][3]);
			return this.text;
		}
	}
	
	public void addProdTemp(String prodNum, String prodDesc, String amount){
		for(int a = 0; a < prodTempArray.length; a++){
			if(prodTempArray[a][1] == null){
				prodTempArray[a][1] = prodNum;
				prodTempArray[a][2] = prodDesc;
				prodTempArray[a][3] = amount;
				a = prodTempArray.length;
				continue;
			}
		}
	}
	
	public int checkProdTemp(String prodNum, String prodDesc , String amount, int mode){
		//TODO
		int found = 0;
		if(prodNum.length() > 20  || prodNum == null || prodNum.equals("")){
			System.out.println("Invalid Product Number: " + prodNum);
			return 1;
		}
		if(prodDesc.length() > 255 || prodNum == null || prodDesc.charAt(0) != 'A'){
			System.out.println("Invalid Product Description");
			return 1;
		}
		for(int i = 0;  i < prodTempArray.length; i++){
			if(prodDesc.equalsIgnoreCase(prodTempArray[i][2])){
				System.out.println("Product Description must be unique");
				return 1;
			}
		}
		if(this.checkNumber(amount, mode) == 1){
			System.out.println("Amount too low");
			return 1;
		}
		return 0;
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
