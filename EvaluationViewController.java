import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class EvaluationViewController implements Initializable {

    @FXML
    private TextArea commentReadTextArea;

    @FXML
    private Button deleteEvaluationBtn;

    @FXML
    private Label instNameLabel;

    @FXML
    private Button nextEvaluationBtn;

    @FXML
    private Button previousEvaluationBtn;

    @FXML
    private Button backToAdminHomeBtn;

    @FXML
    private Label q1Label;

    @FXML
    private Label q2Label;

    @FXML
    private Label q3Label;

    @FXML
    private Label q4Label;

    @FXML
    private Label q5Label;

    @FXML
    private Label q6Label;

    @FXML
    private Label q7Label;

    @FXML
    private Label q8Label;

    @FXML
    private Label q9Label;

    DBconnection db = new DBconnection();

    List<Evaluation> evaluationList = db.getEvaluation();

    int index = 0;
    static int currentIndex;
    int id = evaluationList.get(currentIndex).getId();

    @FXML
    void deleteEvaluation(ActionEvent event) {
        System.out.println(id);
        String q1 = (evaluationList.get(index).getQ1());
        System.out.println(q1);

        System.out.println(index);
        System.out.println(currentIndex);

        Connection con = null;
        PreparedStatement p = null;
        con = db.getconnection();
        try {
            String deleteEvaluation = ("delete from evaluation where id ='" + id
                    + "'");
            p = con.prepareStatement(deleteEvaluation);
            p.execute();

        } catch (SQLException e) {
            System.out.println(e);
        }
        displayalert(AlertType.CONFIRMATION, "title", "Evaluation Succesfully deleted");
        evaluationList.remove(currentIndex);
        referesh();
        size = evaluationList.size();
    }

    int size = evaluationList.size();

    @FXML
    void nextEvaluation(ActionEvent event) {

        if (index < size) {
            if (currentIndex != 0) {
                previousEvaluationBtn.setDisable(false);
            }

            index++;
            if (index == size - 1) {
                nextEvaluationBtn.setDisable(true);
            }

            instNameLabel.setText(evaluationList.get(index).getInstructorName());
            q1Label.setText(evaluationList.get(index).getQ1());
            q2Label.setText(evaluationList.get(index).getQ2());
            q3Label.setText(evaluationList.get(index).getQ3());
            q4Label.setText(evaluationList.get(index).getQ4());
            q5Label.setText(evaluationList.get(index).getQ5());
            q6Label.setText(evaluationList.get(index).getQ6());
            q7Label.setText(evaluationList.get(index).getQ7());
            q8Label.setText(evaluationList.get(index).getQ8());
            q9Label.setText(evaluationList.get(index).getQ9());
            commentReadTextArea.setText(evaluationList.get(index).getComment());
            currentIndex = index;
        }

    }

    @FXML
    void previousEvaluation(ActionEvent event) {

        if (currentIndex > 0) {

            currentIndex--;

            if (currentIndex != size - 1) {
                nextEvaluationBtn.setDisable(false);
            }
            if (currentIndex == 0) {
                previousEvaluationBtn.setDisable(true);

            }

            instNameLabel.setText(evaluationList.get(currentIndex).getInstructorName());
            q1Label.setText(evaluationList.get(currentIndex).getQ1());
            q2Label.setText(evaluationList.get(currentIndex).getQ2());
            q3Label.setText(evaluationList.get(currentIndex).getQ3());
            q4Label.setText(evaluationList.get(currentIndex).getQ4());
            q5Label.setText(evaluationList.get(currentIndex).getQ5());
            q6Label.setText(evaluationList.get(currentIndex).getQ6());
            q7Label.setText(evaluationList.get(currentIndex).getQ7());
            q8Label.setText(evaluationList.get(currentIndex).getQ8());
            q9Label.setText(evaluationList.get(currentIndex).getQ9());
            commentReadTextArea.setText(evaluationList.get(currentIndex).getComment());
            index = currentIndex;
        }

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        instNameLabel.setText(evaluationList.get(0).getInstructorName());
        q1Label.setText(evaluationList.get(0).getQ1());
        q2Label.setText(evaluationList.get(0).getQ2());
        q3Label.setText(evaluationList.get(0).getQ3());
        q4Label.setText(evaluationList.get(0).getQ4());
        q5Label.setText(evaluationList.get(0).getQ5());
        q6Label.setText(evaluationList.get(0).getQ6());
        q7Label.setText(evaluationList.get(0).getQ7());
        q8Label.setText(evaluationList.get(0).getQ8());
        q9Label.setText(evaluationList.get(0).getQ9());
        commentReadTextArea.setText(evaluationList.get(0).getComment());

    }

    public static void displayalert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void backToAdminHome(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("AdminHomePage.fxml"));
            Stage stage = (Stage) backToAdminHomeBtn.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void referesh() {

        instNameLabel.setText(evaluationList.get(currentIndex).getInstructorName());
        q1Label.setText(evaluationList.get(currentIndex).getQ1());
        q2Label.setText(evaluationList.get(currentIndex).getQ2());
        q3Label.setText(evaluationList.get(currentIndex).getQ3());
        q4Label.setText(evaluationList.get(currentIndex).getQ4());
        q5Label.setText(evaluationList.get(currentIndex).getQ5());
        q6Label.setText(evaluationList.get(currentIndex).getQ6());
        q7Label.setText(evaluationList.get(currentIndex).getQ7());
        q8Label.setText(evaluationList.get(currentIndex).getQ8());
        q9Label.setText(evaluationList.get(currentIndex).getQ9());
        commentReadTextArea.setText(evaluationList.get(currentIndex).getComment());
        index = currentIndex;
    }
}
