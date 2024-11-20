/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classexercise10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author Danny
 */
public class C1 extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Label lblName = new Label("Name: ");
        TextField fieldName = new TextField();
        
        Label lblID = new Label("ID: ");
        TextField fieldID = new TextField();
        
        Label lblAge = new Label("Age: ");
        TextField fieldAge = new TextField();
        
        Label lblGrade = new Label("Grade: ");
        TextField fieldGrade = new TextField();
        
        primaryStage.setTitle("Student Info");
        
        GridPane pane = new GridPane();
        
        pane.add(lblName, 0, 0);
        pane.add(fieldName, 1, 0);
        
        pane.add(lblID, 0, 1);
        pane.add(fieldID, 1, 1);
        
        pane.add(lblGrade, 0, 2);
        pane.add(fieldGrade, 1, 2);
        
        pane.add(lblAge, 0, 3);
        pane.add(fieldAge, 1, 3);
        
        Button button = new Button("Submit");
        
        pane.add(button, 0, 5);
        
        button.setOnAction(e ->
        {
            String id = fieldID.getText();
            String age = fieldAge.getText();
            String grade = fieldGrade.getText();
            String name = fieldName.getText();
            
            int ageInt = Integer.parseInt(age);
            int idInt = Integer.parseInt(id);
            
            insertData(name, ageInt, idInt, grade);
            
        });
        
        Scene scene = new Scene(pane, 300, 300);
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private Connection connect()
    {
        String db = "jdbc:sqlite:C:/Users/Danny/OneDrive - Rancho Santiago Community College District/Documents/NetBeansProjects/ClassExercise10/students10.db";
        Connection conn = null;
        
        try
        {
            conn = DriverManager.getConnection(db);
        }catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    private void insertData(String name, int age, int id, String grade)
    {

         String sql = "INSERT INTO students (id, name, age, grade) VALUES (?, ?, ?, ?)";
         
         try(Connection conn = this.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql))
         {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setInt(3, age);
            pstmt.setString(4, grade);
            
            pstmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Data Inserted Successfully");
         } catch(SQLException e)
         {
             System.out.println(e.getMessage());
         }
                 
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
