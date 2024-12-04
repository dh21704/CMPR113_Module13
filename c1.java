
package hw13;

import java.awt.BorderLayout;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

/**
 *
 * @author Danny
 */
public class c1 extends Application {
    
    @Override
    public void start(Stage primaryStage) throws SQLException {
        // Define the JDBC URL for the SQLite database
        String url = "jdbc:sqlite:C:/Users/Danny/OneDrive - Rancho Santiago Community College District/Documents/NetBeansProjects/HW13/coffee13"; 
        
        // SQL query to calculate the sum of the price column
        String sql = "SELECT SUM(Price) AS total_price FROM coffee13"; 

        // Establish a connection to the SQLite database
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            int count = 0;
            
            
            
            // Process the result
            if (rs.next()) {
                double totalPrice = rs.getDouble("total_price");
                System.out.println("Total Price: " + totalPrice);
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
        
        
        //now displaying using JList
        JFrame frame = new JFrame("Product List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        
        JList<String> list = new JList<>(displayList());
        
        JScrollPane scrollPane = new JScrollPane(list);
        frame.add(scrollPane, BorderLayout.CENTER);
        
        frame.setVisible(true);
    }
    
    
    private Connection connect()
    {
        Connection conn = null;
        String url = "jdbc:sqlite:C:/Users/Danny/OneDrive - Rancho Santiago Community College District/Documents/NetBeansProjects/HW13/coffee13";
        
        try 
        {
            conn = DriverManager.getConnection(url);
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        
        return conn;
    }
    
    public String[] displayList() 
    {
        List<String> descriptions = new ArrayList<>();
        
        String url = "jdbc:sqlite:C:/Users/Danny/OneDrive - Rancho Santiago Community College District/Documents/NetBeansProjects/HW13/coffee13";
        
        String query = "SELECT Description, ProdNum, PRICE FROM coffee13";
        
        try(Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query))
        {
            
            while(rs.next())
            {
                String name = rs.getString("Description");
                String prodNum = rs.getString("ProdNum");
                double price = rs.getDouble("PRICE");
                
                descriptions.add(String.format("%s: %s (Price: $%.2f)", name, prodNum, price));
            }
            
        } catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        
        return descriptions.toArray(new String[0]);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
