package parts;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class PartGatewaySQL implements PartGateway
{
	private Connection conn = null;
	PreparedStatement sRS = null;
	private ResultSet rs = null;
	private String insertItemRow = "INSERT INTO inventory_table" 
	+ "(part_name, location_name, quantity) VALUES" 
			+ "(?, ?, ?)";
	private String updateItemRow = "UPDATE inventory_table SET " 
	+ "part_name = ?, location_name = ?, quantity = ? "
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
	private String prepareItemRow = "SELECT part_name, location_name, quantity "
	+ "FROM inventory_table "
			+ "WHERE id_number = ? FOR UPDATE ";
	
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
	
	public void loadInventory() 
	{
        try {
        	sRS = conn.prepareStatement("select * from inventory_table");
            rs = sRS.executeQuery();
            rs.first();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	public void loadParts() 
	{
        try {
        	sRS = conn.prepareStatement("select * from part_table");
            rs = sRS.executeQuery();
            rs.first();
        } catch (SQLException e) {
            e.printStackTrace();
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
	
	public void addItemRow(String partname, String location, String partamount)
	{
		try {
        	sRS = conn.prepareStatement(insertItemRow);
        	sRS.setString(1, partname);
        	sRS.setString(2, location);
        	sRS.setString(3, partamount);
			sRS.executeUpdate();
			sRS.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateItemRow(int itemid, String partname, String location, String partamount)
	{
		try {
        	sRS = conn.prepareStatement(updateItemRow);
        	sRS.setString(1, partname);
        	sRS.setString(2, location);
        	sRS.setString(3, partamount);
        	sRS.setInt(4,itemid);
			sRS.executeUpdate();
			sRS.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	public void prepareItemRow(int itemid)
	{
		int dbid = 13;
		while (itemid != dbid)
		{
			try {
				sRS = conn.prepareStatement(prepareItemRow);
				rs = sRS.executeQuery();
				rs.first();	
				dbid = rs.getInt("id_number");
				System.out.println(dbid);
				if(rs != null)
					rs.close();
				sRS.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
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
}
