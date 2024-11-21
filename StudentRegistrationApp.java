import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


import java.sql.*;


public class StudentRegistrationApp extends Application {


    private TextField studentIdField, lastNameField, firstNameField, ageField;
    private ComboBox<String> primaryCourseBox, secondaryCourseBox;


    @Override
    public void start(Stage primaryStage) {
        // Create UI elements
        Label studentIdLabel = new Label("Student ID:");
        Label lastNameLabel = new Label("Last Name:");
        Label firstNameLabel = new Label("First Name:");
        Label ageLabel = new Label("Age:");
        Label primaryCourseLabel = new Label("Primary Course:");
        Label secondaryCourseLabel = new Label("Secondary Course:");


        studentIdField = new TextField();
        lastNameField = new TextField();
        firstNameField = new TextField();
        ageField = new TextField();


        primaryCourseBox = new ComboBox<>();
        primaryCourseBox.getItems().addAll("Computer Science", "Math");
        
//The lambda expression (observable, oldValue, newValue) 
//-> { ... } defines the actions to be taken when the selected item changes.
//The lambda expression receives three arguments: 
//observable: The observable object (the ComboBox's selection model).
//oldValue: The previous value of the selected item.
//newValue: The new value of the selected item.
//In Java, a lambda expression is a concise way to represent an anonymous function. 
//It's particularly useful for functional interfaces, which are interfaces 
//with a single abstract method.
        primaryCourseBox.getSelectionModel().selectedItemProperty().addListener((
                observable, oldValue, newValue) -> {
            secondaryCourseBox.getItems().clear();
            if (newValue.equals("Computer Science")) {
                secondaryCourseBox.getItems().addAll("CMPR112", "CMPR113", "CMPR114");
            } else if (newValue.equals("Math")) {
                secondaryCourseBox.getItems().addAll("MATH101", "MATH102", "MATH103");
            }
        });


        secondaryCourseBox = new ComboBox<>();


        Button submitButton = new Button("Submit");


        // Create layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);


        gridPane.add(studentIdLabel, 0, 0);
        gridPane.add(studentIdField, 1, 0);
        gridPane.add(lastNameLabel, 0, 1);
        gridPane.add(lastNameField, 1, 1);
        gridPane.add(firstNameLabel, 0, 2);
        gridPane.add(firstNameField, 1, 2);
        gridPane.add(ageLabel, 0, 3);
        gridPane.add(ageField, 1, 3);
        gridPane.add(primaryCourseLabel, 0, 4);
        gridPane.add(primaryCourseBox, 1, 4);
        gridPane.add(secondaryCourseLabel, 0, 5);
        gridPane.add(secondaryCourseBox, 1, 5);
        gridPane.add(submitButton, 1, 6);


        // Set up the scene and stage
        Scene scene = new Scene(gridPane, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Student Registration");
        primaryStage.show();


        // Handle the submit button click
        submitButton.setOnAction(event -> {
            // Get user input
            String studentId = studentIdField.getText();
            String lastName = lastNameField.getText();
            String firstName = firstNameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String primaryCourse = primaryCourseBox.getValue();
            String secondaryCourse = secondaryCourseBox.getValue();


            // Connect to the SQLite database and insert data (replace with your database connection details)
            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:students.db");
                 PreparedStatement stmt = conn.prepareStatement("INSERT INTO students (studentId, lastName, firstName, age, primaryCourse, secondaryCourse) VALUES (?, ?, ?, ?, ?, ?)")) {
                stmt.setString(1, studentId);
                stmt.setString(2, lastName);
                stmt.setString(3, firstName);
                stmt.setInt(4, age);
                stmt.setString(5, primaryCourse);
                stmt.setString(6, secondaryCourse);
                stmt.executeUpdate();


                // Display a success message or handle errors appropriately
                System.out.println("Student data inserted successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the exception, e.g., display an error message to the user
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
