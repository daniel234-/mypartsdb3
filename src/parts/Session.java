package parts;

public class Session {

	private boolean canViewProdTemp;
	private boolean canAddProdTemp;
	private boolean canDelProdTemp;
	private boolean canCreateProdTemp;
	private boolean canCreateProducts;
	private boolean canViewInventory;
	private boolean canAddInventory;
	private boolean canViewParts;
	private boolean canAddParts;
	private boolean canDeleteParts;
	private boolean canDeleteInventory;
	private boolean inUse;

	public Session() {
		canViewInventory = true;
		canViewParts = true;
		inUse = false;
	}

	public void getSession(String user) {
		inUse = true;
		if (user.equalsIgnoreCase("Tom Jones")) {
			canViewProdTemp = true;
			canAddProdTemp = true;
			canDelProdTemp = true;
			canCreateProducts = true;
			canCreateProdTemp = true;
			canAddInventory = false;
			canAddParts = false;
			canDeleteParts = false;
			canDeleteInventory = false;
		} else if(user.equalsIgnoreCase("Sue Smith")){
			canAddInventory = true;
			canAddParts = true;
			canViewProdTemp = false;
			canAddProdTemp = false;
			canDelProdTemp = false;
			canCreateProdTemp = false;
			canCreateProducts = false;
			canDeleteParts = false;
			canDeleteInventory = false;
		} else if(user.equalsIgnoreCase("Ragnar Nelson")){
			canAddInventory = true;
			canAddParts = true;
			canViewProdTemp = true;
			canAddProdTemp = true;
			canDelProdTemp = true;
			canCreateProdTemp = true;
			canCreateProducts = true;
			canDeleteParts = true;
			canDeleteInventory = true;
		}		
	}
	
	public void sessionLogOut(){
		inUse = false;
	}
	
	public boolean getSessionStatus(){
		return inUse;
	}

	public boolean isCanViewProdTemp() {
		return canViewProdTemp;
	}

	public boolean isCanAddProdTemp() {
		return canAddProdTemp;
	}

	public boolean isCanDelProdTemp() {
		return canDelProdTemp;
	}

	public boolean isCanCreateProdTemp() {
		return canCreateProdTemp;
	}

	public boolean isCanCreateProducts() {
		return canCreateProducts;
	}

	public boolean isCanViewInventory() {
		return canViewInventory;
	}

	public boolean isCanAddInventory() {
		return canAddInventory;
	}

	public boolean isCanViewParts() {
		return canViewParts;
	}

	public boolean isCanAddParts() {
		return canAddParts;
	}

	public boolean isCanDeleteParts() {
		return canDeleteParts;
	}

	public boolean isCanDeleteInventory() {
		return canDeleteInventory;
	}

}
