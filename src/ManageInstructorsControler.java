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

public class ManageInstructorsControler implements Initializable {

    ObservableList<Instructors> listM;

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
    private TableView<Instructors> ListStdTable;

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
    private TextField StdyearFld;

    @FXML
    private Button modifyBtn;

    @FXML
    private ChoiceBox<String> ch;

    @FXML
    private TableColumn<Instructors, String> col_Id;

    @FXML
    private TableColumn<Instructors, String> col_Lname;

    @FXML
    private TableColumn<Instructors, String> col_name;

    @FXML
    private TableColumn<Instructors, String> col_year;

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

        String sql = ("update  instructor   set firstName = ?,lastName = ?,year = ?  where id = ?");
        try {
            PreparedStatement st = con.prepareStatement(sql);

            st.setString(1, StdFnameFld.getText());
            st.setString(2, StdLnameFld.getText());
            st.setString(3, StdyearFld.getText());
            st.setString(4, StdIdFld.getText());
            // st.executeUpdate();

            if (!StdFnameFld.getText().isEmpty() && !StdLnameFld.getText().isEmpty()
                    && !StdyearFld.getText().isEmpty()) {
                st.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("ID " + StdIdFld.getText() + " is modified");
                Optional<ButtonType> RegistorBtn = alert.showAndWait();
                refresh();

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

        Instructors s = ListStdTable.getSelectionModel().getSelectedItem();

        StdFnameFld.setText(String.valueOf(s.firstName));
        StdLnameFld.setText(String.valueOf(s.lastName));
        StdIdFld.setText(String.valueOf(s.id));
        StdyearFld.setText(String.valueOf(s.year));

    }

    @FXML
    void addStudOnAction(ActionEvent event) throws SQLException {
        DeletByYearHbox.setVisible(false);
        DeletByIdHbox.setVisible(false);
        AddGrid.setVisible(true);
        StdFnameFld.clear();
        StdLnameFld.clear();
        StdyearFld.clear();
        StdIdFld.clear();
        StdIdFld.setEditable(true);
        RegistorBtn.setVisible(true);
        ModifyBtnAtGrid.setVisible(false);
        refresh();

    }

    @FXML
    void searchBar(KeyEvent event) {

    }

    @FXML
    void modifyStudOnAction(ActionEvent event) {

        DeletByYearHbox.setVisible(false);
        DeletByIdHbox.setVisible(false);
        AddGrid.setVisible(true);
        StdIdFld.setEditable(false);
        ModifyBtnAtGrid.setVisible(true);
        RegistorBtn.setVisible(false);
        refresh();

    }

    int index = -1;

    public ObservableList<Instructors> getInstructors() {
        DBconnection c = new DBconnection();
        Connection con = c.getconnection();
        ObservableList<Instructors> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = con.prepareStatement("select * from instructor");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                list.add(new Instructors(rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("id"), rs.getString("year")));
            }

        } catch (Exception e) {
            // TODO: handle exception
        }

        return list;

    }

    private String[] Option = { "Delete By ID", "Delete By Year" };

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        searchID.textProperty().addListener((obs, oldText, newText) -> {
            refresh();
            search();
        });

        String fname = "", lastName = "", id = "", year = "";

        DBconnection c = new DBconnection();
        Connection con = c.getconnection();

        String sql = "select * from instructor";
        try {
            Statement statement = (Statement) con.createStatement();
            ResultSet rs = ((java.sql.Statement) statement).executeQuery(sql);

            while (rs.next()) {
                fname = rs.getString("firstName");
                lastName = rs.getString("lastName");
                id = rs.getString("id");
                year = rs.getString("year");

            }
            listM.add(new Instructors(fname, lastName, id, year));

        } catch (Exception e) {
            // TODO: handle exception
        }

        DeleteMainBtn.getItems().addAll(Option);

        col_name.setCellValueFactory(new PropertyValueFactory<Instructors, String>("firstName"));
        col_Lname.setCellValueFactory(new PropertyValueFactory<Instructors, String>("lastName"));
        col_Id.setCellValueFactory(new PropertyValueFactory<Instructors, String>("id"));
        col_year.setCellValueFactory(new PropertyValueFactory<Instructors, String>("year"));

        listM = getInstructors();
        ListStdTable.setItems(listM);

        // FilteredList<Instructors> filter = new FilteredList<>(listM, e -> true);

        // searchID.textProperty().addListener((listM, newValue, oldValue) -> {
        // filter.setPredicate((Predicate<? super Instructors>) (Instructors
        // Instructors) -> {

        // if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
        // return true;
        // }

        // String searchkey = newValue.toLowerCase();

        // if (Instructors.getFirstName().contains(searchkey)) {
        // return true;
        // }

        // Instructors std = new Instructors(col_name.getText(), col_Lname.getText(),
        // col_year.getText(),
        // col_Id.getText());

        // if (std.getFirstName().toLowerCase().indexOf(searchkey) > -1) {
        // return true;

        // }

        // else
        // return false;

        // });

        // });

        // SortedList<Instructors> sort = new SortedList<Instructors>(filter);
        // sort.comparatorProperty().bind(ListStdTable.comparatorProperty());

        // ListStdTable.setItems(sort);

    }

    public void store() {
        int r;

        try {

            DBconnection c = new DBconnection();
            Connection con = c.getconnection();

            String sql = ("insert into instructor (firstName,lastName,id,year) values(?,?,?,?)");
            String sql2 = "SELECT ROW_COUNT()";
            String sql3 = ("select count( id) from instructor where id = ?");
            String sql4 = (" select concat(firstname , lastname) as fullname   from instructor  where id ="
                    + StdIdFld.getText());

            PreparedStatement st = con.prepareStatement(sql);
            PreparedStatement ss = con.prepareStatement(sql2);
            PreparedStatement countid = con.prepareStatement(sql3);
            PreparedStatement countname = con.prepareStatement(sql4);

            countid.setString(1, StdIdFld.getText());

            st.setString(1, StdFnameFld.getText().toUpperCase());
            st.setString(2, StdLnameFld.getText().toUpperCase());
            st.setString(3, StdIdFld.getText().toUpperCase());
            st.setString(4, StdyearFld.getText().toUpperCase());
            // countname.setString(1, StdyearFld.getText()+);

            ResultSet idcounter = countid.executeQuery();
            int i = 10;
            while (idcounter.next()) {
                i = idcounter.getInt(1);

            }

            ResultSet namefinder = countname.executeQuery();

            String fName = null;
            while (namefinder.next()) {
                fName = namefinder.getString(1);

            }

            if (!StdFnameFld.getText().isEmpty() && !StdIdFld.getText().isEmpty() && !StdLnameFld.getText().isEmpty()
                    && !StdyearFld.getText().isEmpty()) {

                String fullName = StdFnameFld.getText() + StdLnameFld.getText();

                if (i == 0) {

                    st.executeUpdate();
                    ResultSet row2 = ss.executeQuery();

                    while (row2.next()) {
                        r = row2.getInt(1);

                        if (r > 0) {

                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Success");
                            alert.setContentText("Registration Successful.");
                            Optional<ButtonType> RegistorBtn = alert.showAndWait();

                        }
                    }

                } else if ((i > 0) && fullName.equalsIgnoreCase(fName)) {

                    st.executeUpdate();
                    ResultSet row2 = ss.executeQuery();

                    while (row2.next()) {
                        r = row2.getInt(1);

                        if (r > 0) {

                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Success");
                            alert.setContentText("Registration Successful.");
                            Optional<ButtonType> RegistorBtn = alert.showAndWait();

                        }
                    }

                } else {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Failure");
                    alert.setContentText(
                            " You Have Entered Different Instructor With The Same  ID \n please try again ");
                    Optional<ButtonType> RegistorBtn = alert.showAndWait();

                }

            }

        }

        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failure");
            alert.setContentText(" You Have Entered Repeated ID \n please try again ");
            Optional<ButtonType> RegistorBtn = alert.showAndWait();
            System.out.println(e);

        }

    }

    public void deleteByID() {
        int r;

        try {
            DBconnection c = new DBconnection();
            Connection con = c.getconnection();

            String sql = "delete from instructor where id = ?";
            String sql2 = "SELECT ROW_COUNT()";

            PreparedStatement st = con.prepareStatement(sql);
            PreparedStatement ss = con.prepareStatement(sql2);

            st.setString(1, DeleteStdById.getText());
            if (!DeleteStdById.getText().isEmpty()) {

                st.executeUpdate();

                ResultSet row2 = ss.executeQuery();

                while (row2.next()) {
                    r = row2.getInt(1);

                    if (r > 0) {

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setContentText("you have successfully delete ID " + DeleteStdById.getText());
                        Optional<ButtonType> DeleteBtn = alert.showAndWait();

                    }

                    if (r == 0) {

                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Failed");
                        alert.setContentText("ID " + DeleteStdById.getText() + " Does not exist");
                        Optional<ButtonType> RegistorBtn = alert.showAndWait();

                    }
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Failure");
                alert.setContentText(" Please provide ID \n please try again ");
                Optional<ButtonType> DeleteBtn = alert.showAndWait();

            }

        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    public void deleteByYear() {

        int r;
        try {

            DBconnection c = new DBconnection();
            Connection con = c.getconnection();

            String sql = "delete from instructor where year=?";
            String sql2 = "SELECT ROW_COUNT()";

            PreparedStatement st = con.prepareStatement(sql);
            PreparedStatement ss = con.prepareStatement(sql2);

            st.setString(1, DeleteStdByYear.getText());
            if (!DeleteStdByYear.getText().isEmpty()) {

                st.executeUpdate();
                ResultSet row = ss.executeQuery();
                while (row.next()) {
                    r = row.getInt(1);

                    if (r > 0) {

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setContentText("you have successfully delete  Year " + DeleteStdByYear.getText());
                        Optional<ButtonType> DeleteByYear = alert.showAndWait();

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
                alert.setTitle("Failure");
                alert.setContentText(" Please provide Year \n please try again ");
                Optional<ButtonType> DeleteByYear = alert.showAndWait();

            }

        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    @FXML
    void serachBtnOnAction(ActionEvent event) {

    }

    public void refresh() {
        ListStdTable.setItems(getInstructors());
        ListStdTable.refresh();
    }

    @FXML
    private void search() {

        String keyword = searchID.getText().toUpperCase();
        if (keyword.equals("")) {
            ListStdTable.setItems(listM);
        } else {
            ObservableList<Instructors> filteredData = FXCollections.observableArrayList();
            for (Instructors instructors : listM) {
                if (instructors.getFirstName().trim().contains(keyword)) {

                    filteredData.add(instructors);
                }
            }

            ListStdTable.setItems(filteredData);
        }

    }

}
