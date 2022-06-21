import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

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

public class AdminLoginController {

    @FXML
    private Button adminLoginBtn;

    @FXML
    private Button backToChooseLoginBtn;

    @FXML
    private Button adminLoginCancelBtn;

    @FXML
    private PasswordField adminPasswordField;

    @FXML
    private TextField adminUsernameTF;

    @FXML
    void adminLogin(ActionEvent event) {

        DBconnection c = new DBconnection();
        Connection con = c.getconnection();
        if (adminUsernameTF.getText() != "" && adminPasswordField.getText() != "") {

            String verify = ("select count(1) From admin where userName ='" + adminUsernameTF.getText()
                    + "' and password = '" + adminPasswordField.getText() + "'");

            try {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(verify);
                while (rs.next())
                    if (rs.getInt(1) == 1) {

                        Parent root = FXMLLoader.load(getClass().getResource("AdminHomePage.fxml"));
                        Stage stage = (Stage) adminLoginBtn.getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();

                    } else {
                        EvaluationViewController.displayalert(AlertType.ERROR, "Inccorect username or Password",
                                "Enter correct username and Password");

                    }
            } catch (Exception e) {
                System.out.print(e);
            }
        } else {
            EvaluationViewController.displayalert(AlertType.WARNING, "Empty Label", "Enter username and Password");
        }
    }

    @FXML
    void cancelAdminLogin(ActionEvent event) {
        adminPasswordField.clear();
        adminUsernameTF.clear();
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
}
