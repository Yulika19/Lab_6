package Yul.Server.collection;

        import Yul.General.general.StudyGroup;

public class ServerStudyGroup extends StudyGroup {
    public ServerStudyGroup(StudyGroup sg, long id) {
        super(sg.getName(), sg.getCoordinates(), sg.getStudentsCount(), sg.getFormOfEducation(), sg.getSemesterEnum(), sg.getGroupAdmin());
        setId(id);
    }

    public long getId() {
        return id;
    }

    private void setId(long id) {
        this.id = id;
    }
}