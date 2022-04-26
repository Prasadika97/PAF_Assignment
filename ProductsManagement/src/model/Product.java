package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Product {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/product?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertProduct(String pname, String price, String pdes) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into productservice(`id`, `name`, `price`, `desct`)" + " values ( ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, pname);
			preparedStmt.setString(3, price);
			preparedStmt.setString(4, pdes);

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the Product.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	
	public String readProduct() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Product ID</th><th>Product Name</th><th>Price</th><th>Description</th></tr>";
			String query = "select * from productservice";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String pId = Integer.toString(rs.getInt("id"));
				String pName = rs.getString("name");
				String pPrice = rs.getString("price");
				String pDes = rs.getString("desct");

				output += "<tr><td>" + pId + "</td>";
				output += "<td>" + pName + "</td>";
				output += "<td>" + pPrice + "</td>";
				output += "<td>" + pDes + "</td>";

			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the product.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	
	public String updateProduct(String pId, String pname, String price, String pdes) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE productservice SET name=?,price=?,desct=? WHERE id=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values

			preparedStmt.setString(1, pname);
			preparedStmt.setString(2, price);
			preparedStmt.setString(3, pdes);
			preparedStmt.setInt(4, Integer.parseInt(pId));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the Product.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	
	public String deleteProduct(String pId) {

		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from productservice where id=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(pId));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the product.";
			System.err.println(e.getMessage());
		}

		return output;
	}

}
