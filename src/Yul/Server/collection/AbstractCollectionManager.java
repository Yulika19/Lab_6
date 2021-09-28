package Yul.Server.collection;

        import Yul.General.exceptions.IdExistException;
        import Yul.General.general.StudyGroup;

        import java.util.HashSet;
        import java.util.LinkedList;

/**
 * Абстрактный класс Collection Manager
 * задает структуру, то что нужно реализовать
 * задает методы по управлению id
 */
public abstract class AbstractCollectionManager implements CollectionManager {
    private static HashSet<Long> idSet;
    protected LinkedList<ServerStudyGroup> studyGroups;

    protected static void initIdSet() {
        idSet = new HashSet<>();
    }

    protected static void addId(long id) {
        if (idSet.contains(id)) {
            throw new IdExistException();
        } else
            idSet.add(id);
    }

    protected static void removeId(long id) {
        idSet.remove(id);
    }

    protected final Long getId() {
        long id;
        while (idSet.contains(id = (long) (Math.random() * Integer.MAX_VALUE + 1))) ;
        return id;
    }

    public final ServerStudyGroup getServerStudyGroup(StudyGroup studyGroup) {
        long id = getId();
        addId(id);
        return new ServerStudyGroup(studyGroup, id);
    }
}