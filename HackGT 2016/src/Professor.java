/**
 * Created by Will on 9/24/2016.
 */


public class Professor extends User {

    private Server host;
    private String name;
    private int id;
    private boolean authenticated;

    public Professor(Server host, String name, int id) {
        super(host, name, id);
    }

    public void startClass(String className, double attendanceTolerance) {
        host.addClass(new Classroom(className, this, host, attendanceTolerance));
    }

}
