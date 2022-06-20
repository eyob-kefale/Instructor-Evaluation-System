import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class StudentLoginController {

    @FXML
    private Button studentLoginBtn;

    @FXML
    private Button studentLoginCancelBtn;

    @FXML
    private PasswordField studentPasswordField;

    @FXML
    private TextField studentUsernameTF;

    @FXML
    private Button backToChooseLoginBtn;

    @FXML
    void cancelStudentLogin(ActionEvent event) {
        studentUsernameTF.clear();
        studentPasswordField.clear();
    }

    @FXML
    void backToChooseLogin(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("ChooseLogin.fxml"));
            Stage stage = (Stage) backToChooseLoginBtn.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String stdYear;
    public String studentId;

    public String getYear(String studentId) {
        DBconnection c = new DBconnection();
        Connection con = c.getconnection();
        String selectYear = ("select year From student where id ='" + studentId
                + "'");
        try {

            Statement statement = con.createStatement();
            // ResultSet resultSet =
            ResultSet rs = statement.executeQuery(selectYear);

            while (rs.next()) {
                stdYear = rs.getString("year");
            }
            return stdYear;
        }

        catch (SQLException exception) {

        }
        return null;
    }

    @FXML
    void studentLogin(ActionEvent event) {
        StudentLoginController stdc = new StudentLoginController();
        DBconnection c = new DBconnection();
        Connection con = c.getconnection();
        if (studentUsernameTF.getText() != "" && studentPasswordField.getText() != "") {

            String verify = ("select count(1) From student where id ='" + studentUsernameTF.getText()
                    + "' and password = '" + studentPasswordField.getText() + "'");

            try {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(verify);

                while (rs.next())
                    if (rs.getInt(1) == 1) {

                        studentId = studentUsernameTF.getText();

                        String year = stdc.getYear(studentId);

                        ArrayList<String> instructors = c.getInstructors(year);

                        UserSession user = new UserSession(studentId, instructors);

                        Parent root;
                        try {
                            root = FXMLLoader
                                    .load(getClass().getResource("EvaluationForm.fxml"));
                            Stage stage = (Stage) studentLoginBtn.getScene().getWindow();
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                            stage.show();

                        } catch (IOException e) {

                            e.printStackTrace();
                        }

                    } else {
                        EvaluationViewController.displayalert(AlertType.ERROR, "incorrect pasword or student id",
                                "Enter correct student id and password");
                    }
            } catch (SQLException e) {
                System.out.print(e);
            }
        } else {
            EvaluationViewController.displayalert(AlertType.WARNING, "Empty studentId or password",
                    "Enter id and password");
        }
    }
}
