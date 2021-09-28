package Yul.Server.commands;

        import Yul.General.exceptions.InvalidCommandType;
        import Yul.General.general.AbstractCommand;
        import Yul.General.general.StudyGroupCommand;
        import Yul.General.validation.InputChecker;
        import Yul.Server.collection.CollectionManager;
        import Yul.Server.collection.ServerStudyGroup;
        import Yul.Server.connection.response.ResponseCreator;

public class UpdateCommand extends AbstractCommand implements StudyGroupCommand {
    private final CollectionManager collectionManager;
    private final ResponseCreator responseCreator;

    public UpdateCommand(CollectionManager collectionManager, ResponseCreator responseCreator) {
        super("update", " id {element} обновить значение элемента коллекции, id которого равен заданному", true);
        this.collectionManager = collectionManager;
        this.responseCreator = responseCreator;
    }

    @Override
    public void execute(String[] args) {
        throw new InvalidCommandType("update command required StudyGroup instance");
    }

    @Override
    public void execute(String[] args, ServerStudyGroup serverStudyGroup) {
        try {
            long id;
            if (args.length > 1 && args[1].length() > 0 && InputChecker.checkLong(args[1].trim())) {
                id = Long.parseLong(args[1].trim());
            } else {
                responseCreator.addToMsg("Invalid format of id, can't be null or void");
                return;
            }
            collectionManager.update(id, collectionManager.getServerStudyGroup(serverStudyGroup));
        } catch (NumberFormatException e) {
            responseCreator.addToMsg(e.getMessage());
        }
    }

    @Override
    public boolean isStudyGroupCommand() {
        return true;
    }
}