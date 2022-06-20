import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class EvaluationFormController implements Initializable {

    private String[] choice = { "Strongly Agree", "Agree", "Sometimes", "Disagree", "Strongly Disagree" };
    @FXML
    private Button cancelEvaluationBtn;

    @FXML
    private Button changePasswordBtn;

    @FXML
    private TextArea commentTextArea;

    @FXML
    private ChoiceBox<String> instNameCB;

    @FXML
    private ChoiceBox<String> q1CB;

    @FXML
    private ChoiceBox<String> q2CB;

    @FXML
    private ChoiceBox<String> q3CB;

    @FXML
    private ChoiceBox<String> q4CB;

    @FXML
    private ChoiceBox<String> q5CB;

    @FXML
    private ChoiceBox<String> q6CB;

    @FXML
    private ChoiceBox<String> q7CB;

    @FXML
    private ChoiceBox<String> q8CB;

    @FXML
    private ChoiceBox<String> q9CB;

    @FXML
    private Button submitEvaluationBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    void cancelEvaluation(ActionEvent event) {
        instNameCB.setValue(null);
        q1CB.setValue(null);
        q2CB.setValue(null);
        q3CB.setValue(null);
        q4CB.setValue(null);
        q5CB.setValue(null);
        q6CB.setValue(null);
        q7CB.setValue(null);
        q8CB.setValue(null);
        q9CB.setValue(null);
        commentTextArea.clear();
    }

    @FXML
    void logout(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("ChooseLogin.fxml"));
            Stage stage = (Stage) logoutBtn.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    @FXML
    void changePassword(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("ChangePassword.fxml"));
            Stage stage = (Stage) changePasswordBtn.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {

            e.printStackTrace();
        }

    }

    ArrayList<String> instructors = UserSession.getInstructors();

    @FXML
    void submitEvaluation(ActionEvent event) {

        DBconnection database = new DBconnection();

        String instructorName = instNameCB.getValue();
        String q1 = q1CB.getValue();
        String q2 = q2CB.getValue();
        String q3 = q3CB.getValue();
        String q4 = q4CB.getValue();
        String q5 = q5CB.getValue();
        String q6 = q6CB.getValue();
        String q7 = q7CB.getValue();
        String q8 = q8CB.getValue();
        String q9 = q9CB.getValue();
        String comment = commentTextArea.getText();

        System.out.println(q1);
        System.out.println(q1.isEmpty());
        if (instNameCB.getValue() == null || q1CB.getValue() == null
                || q2CB.getValue() == null
                || q3CB.getValue() == null
                || q4CB.getValue() == null || q5CB.getValue() == null || q6CB.getValue() == null
                || q7CB.getValue() == null
                || q8CB.getValue() == null || q9CB.getValue() == null) {
            EvaluationViewController.displayalert(AlertType.ERROR, " Empty field", " please fill the form");

        } else {
            database.evaluate(instructorName, q1, q2, q3, q4, q5, q6, q7, q8, q9, comment);

            Parent root;
            try {
                instructors.remove(instNameCB.getValue());
                root = FXMLLoader.load(getClass().getResource("successfulEvaluation.fxml"));
                Stage stage = (Stage) submitEvaluationBtn.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        instNameCB.getItems().addAll(instructors);
        q1CB.getItems().addAll(choice);
        q2CB.getItems().addAll(choice);
        q3CB.getItems().addAll(choice);
        q4CB.getItems().addAll(choice);
        q5CB.getItems().addAll(choice);
        q6CB.getItems().addAll(choice);
        q7CB.getItems().addAll(choice);
        q8CB.getItems().addAll(choice);
        q9CB.getItems().addAll(choice);

    }

}
