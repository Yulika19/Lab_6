package Yul.Server.commands;


        import Yul.General.exceptions.InvalidCommandType;
        import Yul.General.general.AbstractCommand;
        import Yul.General.general.StudyGroupCommand;
        import Yul.Server.collection.CollectionManager;
        import Yul.Server.collection.ServerStudyGroup;

public class RemoveGreaterCommand extends AbstractCommand implements StudyGroupCommand {
    private final CollectionManager collectionManager;

    public RemoveGreaterCommand(CollectionManager collectionManager) {
        super("remove_greater", " : {element} удалить из коллекции все элементы, превышающие заданный", true);
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        throw new InvalidCommandType("remove greater command required Study Group instance");
    }

    @Override
    public void execute(String[] args, ServerStudyGroup studyGroup) {
        collectionManager.removeGreater(collectionManager.getServerStudyGroup(studyGroup));
    }
}