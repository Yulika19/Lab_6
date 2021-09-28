package Yul.Server.collection;

        import Yul.General.general.Semester;
        import Yul.General.general.StudyGroup;
        import Yul.Server.connection.response.ResponseCreator;

        import java.time.ZonedDateTime;
        import java.util.LinkedList;

public interface CollectionManager {
    void clear();

    void sumOfStudentsCount();

    void removeFirstElement();

    void removeGreater(ServerStudyGroup studyGroup);

    void removeById(long id);

    void countLessThanSemesterEnum(Semester semester);

    void addElement(ServerStudyGroup studyGroup);

    boolean update(long id, ServerStudyGroup studyGroup);

    void info();

    ZonedDateTime getInitializationTime();

    boolean containsId(long id);

    LinkedList<ServerStudyGroup> getStudyGroups();

    ServerStudyGroup getServerStudyGroup(StudyGroup studyGroup);

    ResponseCreator getResponseCreator();


}