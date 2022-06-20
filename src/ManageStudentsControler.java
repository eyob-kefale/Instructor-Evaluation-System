import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import com.mysql.cj.xdevapi.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ManageStudentsControler implements Initializable {

    ObservableList<Students> listM;

    @FXML
    private GridPane AddGrid;

    @FXML
    private HBox AddStdHbox;

    @FXML
    private Button DeleteBtn;

    @FXML
    private Button DeleteByYear;

    @FXML
    private ComboBox<String> DeleteMainBtn;

    @FXML
    private TextField DeleteStdById;

    @FXML
    private HBox DeletByIdHbox;

    @FXML
    private HBox DeletByYearHbox;

    @FXML
    private TextField DeleteStdByYear;

    @FXML
    private HBox DeletetdHbox;

    @FXML
    private HBox ModifyStdHbox;

    @FXML
    private TableView<Students> ListStdTable;

    @FXML
    private Button RegistorBtn;

    @FXML
    private Button addStud;

    @FXML
    private Button ModifyBtnAtGrid;

    @FXML
    private TextField StdFnameFld;

    @FXML
    private TextField StdIdFld;

    @FXML
    private TextField StdLnameFld;

    @FXML
    private TextField StdPasswordFld;

    @FXML
    private TextField StdSexFld;

    @FXML
    private TextField StdyearFld;

    @FXML
    private Button modifyBtn;

    @FXML
    private ChoiceBox<String> ch;

    @FXML
    private TableColumn<Students, String> col_Id;

    @FXML
    private TableColumn<Students, String> col_Lname;

    @FXML
    private TableColumn<Students, String> col_name;

    @FXML
    private TableColumn<Students, String> col_password;

    @FXML
    private TableColumn<Students, String> col_sex;

    @FXML
    private TableColumn<Students, String> col_year;

    @FXML
    private TextField searchID;

    @FXML
    private Button serachBtn;

    @FXML
    private Button backToAdminHomeBtn;

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

    @FXML
    void DeleteBtnOnAction(ActionEvent event) throws Exception {

        deleteByID();
        refresh();

    }

    @FXML
    void ModifyBtnAtGridOnAction(ActionEvent event) {

        DBconnection c = new DBconnection();
        Connection con = c.getconnection();

        String sql = ("update  student   set firstName = ?,lastName = ?,password = ?,sex = ?,year = ?  where id = ?");
        try {
            PreparedStatement st = con.prepareStatement(sql);

            st.setString(1, StdFnameFld.getText());
            st.setString(2, StdLnameFld.getText());
            st.setString(3, StdPasswordFld.getText());
            st.setString(4, StdSexFld.getText());
            st.setString(5, StdyearFld.getText());
            st.setString(6, StdIdFld.getText());

            if (!StdFnameFld.getText().isEmpty() && !StdLnameFld.getText().isEmpty()
                    && !StdyearFld.getText().isEmpty()) {
                st.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("ID " + StdIdFld.getText() + " is modified");
                Optional<ButtonType> RegistorBtn = alert.showAndWait();

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("failer");
                alert.setContentText("please fill the required information");
                Optional<ButtonType> RegistorBtn = alert.showAndWait();

            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @FXML
    void DeleteByYearOnaction(ActionEvent event) throws SQLException {
        deleteByYear();
        refresh();

    }

    @FXML
    void DeleteMainBtnOnAction(ActionEvent event) {
        String op = DeleteMainBtn.getValue();

        if ((op == Option[0]) || (op == Option[1])) {

            AddGrid.setVisible(false);
            if (op == Option[0]) {
                DeletByYearHbox.setVisible(false);
                DeletByIdHbox.setVisible(true);

            } else {
                DeletByIdHbox.setVisible(false);

                DeletByYearHbox.setVisible(true);

            }
        }

    }

    @FXML
    void DeleteStdByYearOnaction(ActionEvent event) throws SQLException {

    }

    @FXML
    void RegistorBtnOnAction(ActionEvent event) {
        store();
        refresh();

    }

    @FXML
    void RegistorBtnOnActon(ActionEvent event) throws SQLException {

    }

    @FXML
    void rowClick(MouseEvent event) {

        Students s = ListStdTable.getSelectionModel().getSelectedItem();

        StdFnameFld.setText(String.valueOf(s.firstName));
        StdLnameFld.setText(String.valueOf(s.lastName));
        StdPasswordFld.setText(String.valueOf(s.password));
        StdIdFld.setText(String.valueOf(s.id));
        StdSexFld.setText(String.valueOf(s.sex));
        StdyearFld.setText(String.valueOf(s.year));

    }

    @FXML
    void addStudOnAction(ActionEvent event) throws SQLException {
        refresh();
        DeletByYearHbox.setVisible(false);
        DeletByIdHbox.setVisible(false);
        AddGrid.setVisible(true);
        StdFnameFld.clear();
        StdLnameFld.clear();
        StdPasswordFld.clear();
        StdSexFld.clear();
        StdyearFld.clear();
        StdIdFld.clear();
        RegistorBtn.setVisible(true);
        ModifyBtnAtGrid.setVisible(false);
        StdIdFld.setEditable(true);

    }

    @FXML
    void searchBar(KeyEvent event) {

    }

    @FXML
    void modifyStudOnAction(ActionEvent event) {
        refresh();

        DeletByYearHbox.setVisible(false);
        DeletByIdHbox.setVisible(false);
        AddGrid.setVisible(true);
        StdIdFld.setEditable(false);
        ModifyBtnAtGrid.setVisible(true);
        RegistorBtn.setVisible(false);

    }

    int index = -1;

    public static ObservableList<Students> getStudents() {
        DBconnection c = new DBconnection();
        Connection con = c.getconnection();
        ObservableList<Students> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = con.prepareStatement("select * from student");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                list.add(new Students(rs.getString("firstName"), rs.getString("lastName"), rs.getString("password"),
                        rs.getString("id"), rs.getString("sex"), rs.getString("year")));
            }

        } catch (Exception e) {
            // TODO: handle exception
        }

        return list;

    }

    private String[] Option = { "Delete By ID", "Delete By Year" };

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        refresh();
        searchID.textProperty().addListener((obs, oldText, newText) -> {
            search();
        });

        StdIdFld.setEditable(true);
        // ListStdTable.setItems(cellData -> cellData.getValue().crewIdProperty());

        String fname = "", lastName = "", password = "", id = "", sex = "", year = " ";

        DBconnection c = new DBconnection();
        Connection con = c.getconnection();

        String sql = "select * from student";
        try {
            Statement statement = (Statement) con.createStatement();
            ResultSet rs = ((java.sql.Statement) statement).executeQuery(sql);

            while (rs.next()) {
                fname = rs.getString("firstName");
                lastName = rs.getString("lastName");
                password = rs.getString("password");
                id = rs.getString("id");
                sex = rs.getString("sex");
                year = rs.getString("year");

            }
            listM.add(new Students(fname, lastName, password, id, sex, year));

        } catch (Exception e) {
            // TODO: handle exception
        }

        DeleteMainBtn.getItems().addAll(Option);

        col_name.setCellValueFactory(new PropertyValueFactory<Students, String>("firstName"));
        col_Lname.setCellValueFactory(new PropertyValueFactory<Students, String>("lastName"));
        col_password.setCellValueFactory(new PropertyValueFactory<Students, String>("password"));
        col_Id.setCellValueFactory(new PropertyValueFactory<Students, String>("id"));
        col_sex.setCellValueFactory(new PropertyValueFactory<Students, String>("sex"));
        col_year.setCellValueFactory(new PropertyValueFactory<Students, String>("year"));

        listM = getStudents();
        ListStdTable.setItems(listM);

        // FilteredList<Students> filter = new FilteredList<>(listM, e -> true);

        // searchID.textProperty().addListener((listM, newValue, oldValue) -> {
        // filter.setPredicate((Predicate<? super Students>) (Students students) -> {

        // if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
        // return true;
        // }

        // String searchkey = newValue.toLowerCase();

        // if (students.getFirstName().contains(searchkey)) {
        // return true;
        // }

        // Students std = new Students(col_name.getText(), col_Lname.getText(),
        // col_password.getText(),
        // col_Id.getText(), col_sex.getText(), col_year.getText());

        // if (std.getFirstName().toLowerCase().indexOf(searchkey) > -1) {
        // return true;

        // }

        // else
        // return false;

        // });

        // });

        // SortedList<Students> sort = new SortedList<Students>(filter);
        // sort.comparatorProperty().bind(ListStdTable.comparatorProperty());

        // ListStdTable.setItems(sort);

    }

    public void store() {
        int r;

        try {
            DBconnection c = new DBconnection();
            Connection con = c.getconnection();
            String sql = ("insert into student (firstName,lastName,password,id,sex,year) values(?,?,?,?,?,?)");

            PreparedStatement st = con.prepareStatement(sql);

            st.setString(1, StdFnameFld.getText().toUpperCase());
            st.setString(2, StdLnameFld.getText().toUpperCase());
            st.setString(3, StdPasswordFld.getText().toUpperCase());
            st.setString(4, StdIdFld.getText().toUpperCase());
            st.setString(5, StdSexFld.getText().toUpperCase());
            st.setString(6, StdyearFld.getText().toUpperCase());

            if (!StdFnameFld.getText().isEmpty() && !StdIdFld.getText().isEmpty() && !StdLnameFld.getText().isEmpty()
                    && !StdyearFld.getText().isEmpty()) {
                st.executeUpdate();

            }

            String sql2 = "SELECT ROW_COUNT()";
            PreparedStatement ss = con.prepareStatement(sql2);

            ResultSet row2 = ss.executeQuery();

            while (row2.next()) {
                r = row2.getInt(1);

                if (r > 0) {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setContentText("Registration Successful");
                    Optional<ButtonType> RegistorBtn = alert.showAndWait();

                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("failer");
                    alert.setContentText("Please fill the required information");
                    Optional<ButtonType> RegistorBtn = alert.showAndWait();

                }

            }

        } catch (Exception e) {
            System.out.println(e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("failer");
            alert.setContentText("Dublicated Id ");
            Optional<ButtonType> RegistorBtn = alert.showAndWait();

        }

    }

    public void deleteByID() {

        try {
            int r;

            DBconnection c = new DBconnection();
            Connection con = c.getconnection();

            String sql2 = "SELECT ROW_COUNT()";

            PreparedStatement ss = con.prepareStatement(sql2);

            String sql = "delete from student where id=?";

            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, DeleteStdById.getText());

            if (!DeleteStdById.getText().isEmpty()) {
                st.executeUpdate();
                ResultSet row2 = ss.executeQuery();
                while (row2.next()) {
                    r = row2.getInt(1);

                    if (r > 0) {

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setContentText("ID  " + DeleteStdById.getText() + " Deleted");
                        Optional<ButtonType> RegistorBtn = alert.showAndWait();

                    }

                    if (r == 0) {

                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Failed");
                        alert.setContentText("ID " + StdyearFld.getText() + " Does not exist");
                        Optional<ButtonType> RegistorBtn = alert.showAndWait();

                    }

                }

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("failer");
                alert.setContentText("Please Provide  ID");
                Optional<ButtonType> RegistorBtn = alert.showAndWait();

            }

        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    public void deleteByYear() {
        try {
            int r;
            DBconnection c = new DBconnection();
            Connection con = c.getconnection();

            String sql = "delete from student where year=?";

            String sql2 = "SELECT ROW_COUNT()";

            PreparedStatement ss = con.prepareStatement(sql2);

            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, DeleteStdByYear.getText());

            if (!DeleteStdByYear.getText().isEmpty()) {
                st.executeUpdate();
                ResultSet row2 = ss.executeQuery();
                while (row2.next()) {
                    r = row2.getInt(1);

                    System.out.println(r);

                    if (r > 0) {

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setContentText("Year " + DeleteStdByYear.getText() + " Deleted");
                        Optional<ButtonType> RegistorBtn = alert.showAndWait();

                    }

                    if (r == 0) {

                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Failed");
                        alert.setContentText("Year " + DeleteStdByYear.getText() + " Does not exist");
                        Optional<ButtonType> RegistorBtn = alert.showAndWait();

                    }

                }

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("failer");
                alert.setContentText("Please Provide  Year");
                Optional<ButtonType> RegistorBtn = alert.showAndWait();

            }

        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    @FXML
    void serachBtnOnAction(ActionEvent event) {

    }

    public void refresh() {
        ListStdTable.setItems(getStudents());
        ListStdTable.refresh();
    }

    @FXML
    private void search() {
        String keyword = searchID.getText().toUpperCase();
        if (keyword.equals("")) {
            ListStdTable.setItems(listM);
        } else {
            ObservableList<Students> filteredData = FXCollections.observableArrayList();
            for (Students students : listM) {
                if (students.getFirstName().contains(keyword))
                    filteredData.add(students);
            }

            ListStdTable.setItems(filteredData);
        }
    }
}
