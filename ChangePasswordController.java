import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ChangePasswordController {

    @FXML
    private Button confirmPasswordBTN;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField oldPasswordField;

    @FXML
    private Button backToEvaluationSystemBtn;

    String studentId = UserSession.getStudentId();
    static String password;
    DBconnection c = new DBconnection();
    Connection con = c.getconnection();

    @FXML
    void changePassword(ActionEvent event) throws SQLException {
        String newPassword = newPasswordField.getText();
        String oldPassword = oldPasswordField.getText();

        String selectPassword = ("select password From student where id ='" + studentId
                + "'");
        try {
            Statement statement = con.createStatement();

            ResultSet rs = statement.executeQuery(selectPassword);

            while (rs.next()) {
                password = rs.getString("password");
            }

        }

        catch (SQLException exception) {
            exception.printStackTrace();
        }

        if (oldPassword.contentEquals(password)) {
            if (newPassword.contentEquals(confirmPasswordField.getText())) {

                String updatePassword = "update student set password='" + newPassword
                        + "' where id ='" + studentId + "'";
                Statement statement = con.createStatement();
                statement.executeUpdate(updatePassword);

                // ResultSet result =
                // while (result.next()) {
                // password = result.getString("password");
                // }

                Parent root;
                try {

                    root = FXMLLoader.load(getClass().getResource("EvaluationForm.fxml"));
                    Stage stage = (Stage) confirmPasswordBTN.getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {

                    e.printStackTrace();
                }
            } else {
                EvaluationViewController.displayalert(AlertType.ERROR, "password mismatch",
                        "incorrect new password confiramtion");
            }

        } else {
            EvaluationViewController.displayalert(AlertType.ERROR, "password mismatch", "incorrect old password");

        }

    }

    @FXML
    void backToEvaluationSystem(ActionEvent event) {
        Parent root;
        try {

            root = FXMLLoader.load(getClass().getResource("EvaluationForm.fxml"));
            Stage stage = (Stage) backToEvaluationSystemBtn.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

}
