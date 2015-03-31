package parts;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Properties;

import javax.sql.DataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class PartGatewaySQL implements PartGateway
{
	private Connection conn = null;
	PreparedStatement sRS = null;
	private ResultSet rs = null;
	private final int QUERY_TIMEOUT = 10;//query timeout threshold in seconds
	
	private String insertItemRow = "INSERT INTO inventory_table" 
	+ "(part_name, location_name, quantity, product_template_id) VALUES" 
			+ "(?, ?, ?, ?)";
	private String updateItemRow = "UPDATE inventory_table SET " 
			+ "part_name = ?, location_name = ?, quantity = ? , last_modified = ? "
					+ "WHERE id_number = ?";
	private String deleteItemRow = "DELETE FROM inventory_table WHERE id_number = ?";
	private String insertPartRow = "INSERT INTO part_table" 
	+ "(part_number, part_name, vendor_name, unit_of_quantity, external_part_number) VALUES" 
			+ "(?, ?, ?, ?, ?)";
	private String updatePartRow = "UPDATE part_table SET " 
	+ "part_number = ?, part_name = ?, vendor_name = ?, unit_of_quantity = ?, external_part_number = ? "
			+ "WHERE id_number = ?";
	private String deletePartRow = "DELETE FROM part_table WHERE id_number = ?";
	private String insertProdTempRow = "INSERT INTO product_table"
			+ "(product_number, product_description) VALUES"
			+ "(?, ?)";
	private String editProdTempRow = "UPDATE product_table SET "
			+ "product_number = ?, product_description = ? "
			+ "WHERE id = ?";
	private String deleteProdTempRow = "DELETE FROM product_table WHERE id = ?";
	private String insertProdDetailRow = "INSERT INTO product_detail"
			+ "(product_id, part_id, quantity) VALUES"
			+ "(?, ?, ?)";
	private String editProdDetailRow;
	private String deleteProdDetailRow = "DELETE FROM product_detail WHERE product_id = ?";
	
	public PartGatewaySQL()
	{
		DataSource ds = getDataSource();
		if(ds == null) {
        	try {
				throw new GatewayException("Datasource is null!");
			} catch (GatewayException g) {
				g.printStackTrace();
			}
        }
		try {
        	conn = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				throw new GatewayException("SQL Error: " + e.getMessage());
			} catch (GatewayException g) {
				g.printStackTrace();
			}
		}
	}
	
	public DataSource getDataSource() {
		Properties props = new Properties();
		FileInputStream fis = null;
        try {
        	fis = new FileInputStream("db.properties");
            props.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        try {
        	MysqlDataSource mysqlDS = new MysqlDataSource();
        	mysqlDS.setURL(props.getProperty("MYSQL_DB_URL"));
        	mysqlDS.setUser(props.getProperty("MYSQL_DB_USER"));
        	mysqlDS.setPassword(props.getProperty("MYSQL_DB_PW"));
        	return mysqlDS;
        } catch(RuntimeException e) {
            e.printStackTrace();
            return null;
        }
	}
	
	public int loadProdDetail(){
		try{
			sRS = conn.prepareStatement("select * from product_detail");
			rs = sRS.executeQuery();
			if(rs.first()== true){
				return 0;
			} return 1;
		} catch (SQLException e){
			e.printStackTrace();
			return 1;
		}
	}
	
	public int loadProdTemp(){
		try {
			sRS = conn.prepareStatement("select * from product_table");
			rs = sRS.executeQuery();
			if(rs.first() == true){
				return 0;
			} return 1;
		} catch(SQLException e){
			e.printStackTrace();
			return 1;
		}
	}
	
	public int loadInventory() 
	{
        try {
        	sRS = conn.prepareStatement("select * from inventory_table");
            rs = sRS.executeQuery();
            if(rs.first() == true){
            	return 0;
            } return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 1;
        }
	}
	
	public int loadParts() 
	{
        try {
        	sRS = conn.prepareStatement("select * from part_table");
            rs = sRS.executeQuery();
            if(rs.first() == true){
            	return 0;
            } return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 1;
        }
	}
	
	public int nextRow()
	{
		try {
			if(rs.isLast())
			{
				return 1;
			}
			else
			{
				rs.next();
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
	}
	
	public void addItemRow(String partname, String location, String partamount, int productid)
	{
		try {
        	sRS = conn.prepareStatement(insertItemRow);
        	sRS.setString(1, partname);
        	sRS.setString(2, location);
        	sRS.setString(3, partamount);
        	sRS.setInt(4, productid);
			sRS.executeUpdate();
			sRS.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean updateItemRow(int itemid, String partname, String location, String partamount, Timestamp time)
	{
		try {
			Timestamp lasttime = this.prepareItemRow(itemid);
			if(lasttime.toString().equalsIgnoreCase(time.toString()))
			{
				System.out.println("Entry checks out.");
				sRS = conn.prepareStatement(updateItemRow);
				sRS.setString(1, partname);
				sRS.setString(2, location);
				sRS.setString(3, partamount);
				sRS.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
				sRS.setInt(5,itemid);
				sRS.executeUpdate();
				sRS.close();
			}
			else
			{
				System.out.println("Entry was editted prior to your edit, please check the revised entry.");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean updateItemProductRow(int itemid, String partname, String location, String partamount, Timestamp time, int productid)
	{
		try {
			Timestamp lasttime = this.prepareItemRow(itemid);
			if(lasttime.toString().equalsIgnoreCase(time.toString()))
			{
				System.out.println("Entry checks out.");
				sRS = conn.prepareStatement("UPDATE inventory_table SET " 
						+ "part_name = ?, location_name = ?, quantity = ? , last_modified = ?, product_template_id = ? "
						+ "WHERE id_number = ?");
				sRS.setString(1, partname);
				sRS.setString(2, location);
				sRS.setString(3, partamount);
				sRS.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
				sRS.setInt(5,productid);
				sRS.setInt(6,itemid);
				sRS.executeUpdate();
				sRS.close();
			}
			else
			{
				System.out.println("Entry was editted prior to your edit, please check the revised entry.");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	public Timestamp prepareItemRow(int itemid)
	{
		Timestamp time = null;
		int dbid = 0;
		int status = 0;
		try {
			sRS = conn.prepareStatement("select * from inventory_table");
			sRS.setQueryTimeout(QUERY_TIMEOUT);
			rs = sRS.executeQuery();
			while (dbid != itemid && status == 0)
			{
				status = this.nextRow();
				dbid = rs.getInt("id_number");
			}
			if(rs != null)
				time = this.getTimestamp();
				rs.close();
			sRS.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return time;
	}
	
	
	
	public void deleteItemRow(int partid)
	{
		try {
        	sRS = conn.prepareStatement(deleteItemRow);
        	sRS.setInt(1,partid);
			sRS.executeUpdate();
			sRS.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addPartRow(String partname, String partnumber, String partunit, String vendor, String externalnumber)
	{
		try {
        	sRS = conn.prepareStatement(insertPartRow);
        	sRS.setString(1, partnumber);
        	sRS.setString(2, partname);
        	sRS.setString(3, vendor);
        	sRS.setString(4, partunit);
        	sRS.setString(5, externalnumber);
			sRS.executeUpdate();
			sRS.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updatePartRow(int partid, String partname, String partnumber, String partunit, String vendor, String externalnumber)
	{
		try {
        	sRS = conn.prepareStatement(updatePartRow);
        	sRS.setString(1, partnumber);
        	sRS.setString(2, partname);
        	sRS.setString(3, vendor);
        	sRS.setString(4, partunit);
        	sRS.setString(5, externalnumber);
        	sRS.setInt(6,partid);
			sRS.executeUpdate();
			sRS.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deletePartRow(int partid)
	{
		try {
        	sRS = conn.prepareStatement(deletePartRow);
        	sRS.setInt(1,partid);
			sRS.executeUpdate();
			sRS.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addProdTempRow(String partNum, String partDesc){
		try{
			sRS = conn.prepareStatement(insertProdTempRow);
			sRS.setString(1, partNum);
			sRS.setString(2, partDesc);
			sRS.executeUpdate();
			sRS.close();
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void updateProdTempRow(int id, String partNum, String partDesc){
		try {
			sRS = conn.prepareStatement(editProdTempRow);
			sRS.setString(1, partNum);
			sRS.setString(2, partDesc);
			sRS.setInt(3, id);
			sRS.executeUpdate();
			sRS.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	} 
	
	public void deleteProdTempRow(int id){
		try{ 
			sRS = conn.prepareStatement(deleteProdTempRow);
			sRS.setInt(1, id);
			sRS.executeUpdate();
			sRS.close();
		} catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	public void deleteProdDetailRow(int num){
		try{
			sRS = conn.prepareStatement(deleteProdDetailRow);
			sRS.setInt(1, num);
			sRS.executeUpdate();
			sRS.close();
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void addProdDetailRow(String partid, String tempid, String quantity){
		int convertpartID = Integer.valueOf(partid);
		int converttempID = Integer.valueOf(tempid);
		int convertInt = Integer.valueOf(quantity);
		try {
			sRS = conn.prepareStatement(insertProdDetailRow);
			sRS.setInt(1, convertpartID);
			sRS.setInt(2, converttempID);
			sRS.setInt(3, convertInt);
			sRS.executeUpdate();
			sRS.close();
		} catch (SQLException e) {
			System.out.println("This part may only reference one template");
		} catch (Exception e){
			System.out.println("This part may only reference one template");
		}
		
	}
	
	public void updateProdDetailRow(String partid, String tempid, String quantity){
		int convertpartID = Integer.valueOf(partid);
		int converttempID = Integer.valueOf(tempid);
		int convertInt = Integer.valueOf(quantity);
		editProdDetailRow = "UPDATE product_detail SET "
		+ "quantity = ? WHERE product_id = ? AND part_id = ?";
		try {
			sRS = conn.prepareStatement(editProdDetailRow);
			sRS.setInt(1, convertInt);
			sRS.setInt(2, converttempID);
			sRS.setInt(3, convertpartID);
			sRS.executeUpdate();
			sRS.close();
		} catch (SQLException e) {
			System.out.println("error");
			e.printStackTrace();
		}
	}
	
	public String getProdDetailTempID(){
		int idnumber = 0;
		try {
			if(rs != null)
				idnumber = rs.getInt("product_id");
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
		Integer number = idnumber;
		String text = number.toString();
		return text;
	}
	
	public String getProdDetailPartID(){
		int idnumber = 0;
		try {
			if(rs != null)
				idnumber = rs.getInt("part_id");
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
		Integer number = idnumber;
		String text = number.toString();
		return text;
	}
	
	public String getProdDetailQuant(){
		int idnumber = 0;
		try {
			if(rs != null)
				idnumber = rs.getInt("quantity");
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
		Integer number = idnumber;
		String text = number.toString();
		return text;
	}

	public String getItemID()
	{
		int idnumber = 0;
		try {
			if(rs != null)
				idnumber = rs.getInt("id_number");
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
		Integer number = idnumber;
		String text = number.toString();
		return text;
	}
	
	public String getProdTempID()
	{
		int prodTempNum = 0;
		try{
			if (rs != null)
				prodTempNum = rs.getInt("id");
		} catch (SQLException e){
			//throw new RuntimeException(e.getMessage());
			return null;
		}
		Integer number = prodTempNum;
		String text = number.toString();
		return text;
	}
	public String getPart()
	{
		String partname = "";
		try {
			if(rs != null)
				partname = rs.getString("part_name");
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
		return partname;
	}
	
	public String getLocation()
	{
		String location = "";
		try {
			if(rs != null)
				location = rs.getString("location_name");
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
		return location;
	}
	
	public String getPartAmount()
	{
		int amount = 0;
		try {
			if(rs != null)
				amount = rs.getInt("quantity");
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
		Integer number = amount;
		String text = number.toString();
		return text;
	}
	
	public String getPartID()
	{
		int idnumber = 0;
		try {
			if(rs != null)
				idnumber = rs.getInt("id_number");
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
		Integer number = idnumber;
		String text = number.toString();
		return text;
	}
	
	public String getPartNumber()
	{
		String partnumber = "";
		try {
			if(rs != null)
				partnumber = rs.getString("part_number");
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
		return partnumber;
	}
	
	public String getProdTempNum(){
		String tempNum = "";
		try {
			if (rs != null)
				tempNum = rs.getString("product_number");
		} catch (SQLException e){
			//throw new RuntimeException(e.getMessage());
			return null;
		}
		return tempNum;
	}
	
	public String getPartName()
	{
		String partname = "";
		try {
			if(rs != null)
				partname = rs.getString("part_name");
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
		return partname;
	}
	
	public String getProdTempDesc(){
		String prodDesc = "";
		try {
			if (rs != null)
				prodDesc = rs.getString("product_description");
		} catch (SQLException e){
			//throw new RuntimeException(e.getMessage());
			return null;
		}
		return prodDesc;
	}
	
	public String getVendor()
	{
		String vendor = "";
		try {
			if(rs != null)
				vendor = rs.getString("vendor_name");
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
		return vendor;
	}
	
	public String getUnit()
	{
		String unitname = "";
		try {
			if(rs != null)
				unitname = rs.getString("unit_of_quantity");
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
		return unitname;
	}
	
	public String getExternalPartNumber()
	{
		String partnumber = "";
		try {
			if(rs != null)
				partnumber = rs.getString("external_part_number");
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
		return partnumber;
	}
	
	public Timestamp getTimestamp()
	{
		Timestamp time = null;
		try {
			time = rs.getTimestamp("last_modified");
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
		return time;
	}
}
