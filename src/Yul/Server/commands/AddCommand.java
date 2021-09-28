package Yul.Server.commands;

        import Yul.General.exceptions.InvalidCommandType;
        import Yul.General.general.AbstractCommand;
        import Yul.General.general.StudyGroupCommand;
        import Yul.Server.collection.CollectionManager;
        import Yul.Server.collection.ServerStudyGroup;


public class AddCommand extends AbstractCommand implements StudyGroupCommand {
    private final CollectionManager collectionManagerImpl;

    public AddCommand(CollectionManager collectionManager) {
        super("add", " {element} : добавить новый элемент в коллекцию", true);
        this.collectionManagerImpl = collectionManager;
    }


    @Override
    public void execute(String[] args) {
        throw new InvalidCommandType("add command required Study Group instance");
    }

    @Override
    public void execute(String[] args, ServerStudyGroup studyGroup) {
        collectionManagerImpl.addElement(studyGroup);
    }

    @Override
    public boolean isStudyGroupCommand() {
        return true;
    }
}