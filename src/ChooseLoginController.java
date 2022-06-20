import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ChooseLoginController {

    @FXML
    private Button loginAsAdminBtn;

    @FXML
    private Button loginAsStudentBtn;

    @FXML
    void loginAsAdmin(ActionEvent event) {

        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("AdminLogin.fxml"));
            Stage stage = (Stage) loginAsAdminBtn.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {

            e.printStackTrace();
        }

    }

    @FXML
    void loginAsStudent(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("StudentLogin.fxml"));
            Stage stage = (Stage) loginAsAdminBtn.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
