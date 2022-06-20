
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.ResultSet;
import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
import java.util.ArrayList;
import java.util.List;

public class DBconnection {
    // JDBC driver name and database URL

    private static final String url = "jdbc:mysql://localhost:3306/ies";
    private static final String user = "root";
    private static final String password = "Root-123";

    private Connection connection;
    private PreparedStatement insertEvaluationResult;
    private PreparedStatement selectInstructors;
    private PreparedStatement getEvaluation;

    DBconnection() {
        try {
            connection = DriverManager.getConnection(url, user, password);

            insertEvaluationResult = connection.prepareStatement(
                    "insert into evaluation (instName,q1,q2,q3,q4,q5,q6,q7,q8,q9,comment) values(?,?,?,?,?,?,?,?,?,?,?);");

            selectInstructors = connection
                    .prepareStatement(
                            "select  CONCAT(FirstName , ' '  , Lastname) as Name from instructor where year like ?;");
            getEvaluation = connection
                    .prepareStatement("select * from evaluation order by id desc");
            // "select instName,q1,q2,q3,q4,q5,q6,q7,q8,q9,");

        }

        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public int evaluate(String instName, String q1, String q2, String q3, String q4, String q5, String q6, String q7,
            String q8,
            String q9, String comment) {

        try {
            insertEvaluationResult.setString(1, instName);
            insertEvaluationResult.setString(2, q1);
            insertEvaluationResult.setString(3, q2);
            insertEvaluationResult.setString(4, q3);
            insertEvaluationResult.setString(5, q4);
            insertEvaluationResult.setString(6, q5);
            insertEvaluationResult.setString(7, q6);
            insertEvaluationResult.setString(8, q7);
            insertEvaluationResult.setString(9, q8);
            insertEvaluationResult.setString(10, q9);
            insertEvaluationResult.setString(11, comment);

            return insertEvaluationResult.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<String> getInstructors(String year) {
        try {
            selectInstructors.setString(1, year);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        try {
            ResultSet resultSet = selectInstructors.executeQuery();
            ArrayList<String> instructors = new ArrayList<String>();

            while (resultSet.next()) {
                String instName = resultSet.getString("Name");
                instructors.add(instName);
            }
            return instructors;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public List<Evaluation> getEvaluation() {

        try (ResultSet resultSet = getEvaluation.executeQuery()) {

            List<Evaluation> evaluationlist = new ArrayList<Evaluation>();

            while (resultSet.next()) {
                evaluationlist.add(new Evaluation(
                        resultSet.getInt("id"),
                        resultSet.getString("instname"),
                        resultSet.getString("q1"),
                        resultSet.getString("q2"),
                        resultSet.getString("q3"),
                        resultSet.getString("q4"),
                        resultSet.getString("q5"),
                        resultSet.getString("q6"),
                        resultSet.getString("q7"),
                        resultSet.getString("q8"),
                        resultSet.getString("q9"),
                        resultSet.getString("comment")

                ));
            }

            return evaluationlist;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Connection getconnection() {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return connection;
    }

}
