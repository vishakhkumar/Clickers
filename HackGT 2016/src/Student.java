import java.util.ArrayList;

/**
 * Created by Will on 9/24/2016.
 */
public class Student extends User {
    private Server host;
    private String name;
    private int id;
    private Classroom currentClass;
    private boolean authenticated;

    public Student(Server host, String name, int id) {
        super(host, name, id);
        this.currentClass = null;
    }

    public void joinClass(final Classroom room) {
        this.currentClass = room;
        Thread newT = new Thread() {
            public void run() {
                ArrayList<String> nearbyIds = Student.this.getNearbyIds();
                while (!room.classAuthenticated()) {
                    room.attemptJoin(Student.this, nearbyIds);
                }
            }
        };
        newT.start();
    }


    public ArrayList<String> getNearbyIds() {
        return null;
    }

    public int getNewRandomId() {
        return (int) (id * Math.random());
    }
}
