import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AdminHomePageController {

    @FXML
    private Button manageInstructorBtn;

    @FXML
    private Button manageStudentBtn;

    @FXML
    private Button viewEvaluationBtn;

    @FXML
    private Button adminLogoutBtn;

    @FXML
    void manageInstructor(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("ManageInstructors.fxml"));
            Stage stage = (Stage) manageInstructorBtn.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void manageStudent(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("ManageStudents.fxml"));
            Stage stage = (Stage) manageStudentBtn.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            // stage.setFullScreen(true);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void viewEvaluation(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("EvaluationView.fxml"));
            Stage stage = (Stage) viewEvaluationBtn.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void adminLogout(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("ChooseLogin.fxml"));
            Stage stage = (Stage) adminLogoutBtn.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
