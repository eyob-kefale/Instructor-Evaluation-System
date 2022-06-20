import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SuccessfulEvaluationController {

    @FXML
    private Button evaluateOtherBtn;

    @FXML
    private Button goToHomeBtn;

    @FXML
    void goHome(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("ChooseLogin.fxml"));
            Stage stage = (Stage) goToHomeBtn.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void otherEvaluation(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("EvaluationForm.fxml"));
            Stage stage = (Stage) evaluateOtherBtn.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
