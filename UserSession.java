import java.util.ArrayList;

public final class UserSession {

    private static UserSession instance;
    private static String studentId;

    private static ArrayList<String> instructors;

    UserSession(String studentId, ArrayList<String> instructors) {
        this.studentId = studentId;
        this.instructors = instructors;

    }

    public static String getStudentId() {
        return studentId;
    }

    public static void setStudentId(String studentId) {
        UserSession.studentId = studentId;
    }

    public static ArrayList<String> getInstructors() {
        return instructors;
    }

    public static void setInstructors(ArrayList<String> instructors) {
        UserSession.instructors = instructors;
    }

    public static UserSession getInstace(String studentId, ArrayList<String> instructors) {
        if (instance == null) {
            instance = new UserSession(studentId, instructors);
        }
        return instance;
    }

    public void cleanUserSession() {
        studentId = "";
        instructors = null;

    }

}
