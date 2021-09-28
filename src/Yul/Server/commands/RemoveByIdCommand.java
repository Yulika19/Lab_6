package Yul.Server.commands;


        import Yul.General.general.AbstractCommand;
        import Yul.General.validation.InputChecker;
        import Yul.General.validation.StudyGroupBuilder;
        import Yul.Server.collection.CollectionManager;
        import Yul.Server.connection.response.ResponseCreator;

public class RemoveByIdCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final StudyGroupBuilder studyGroupBuilder;
    private final ResponseCreator responseCreator;

    public RemoveByIdCommand(CollectionManager collectionManager, StudyGroupBuilder studyGroupBuilder, ResponseCreator responseCreator) {
        super("remove_by_id", " : удалить элемент коллекции по его id");
        this.collectionManager = collectionManager;
        this.studyGroupBuilder = studyGroupBuilder;
        this.responseCreator = responseCreator;
    }

    @Override
    public void execute(String[] args) {
        Long id;
        if (args.length > 1 && args[1].length() > 0 && InputChecker.checkLong(args[1].trim())) {
            id = Long.parseLong(args[1].trim());
        } else {
            id = studyGroupBuilder.askStudyGroupId();
        }
        if (id != null && collectionManager.containsId(id))
            collectionManager.removeById(id);
        else {
            responseCreator.addToMsg("Данный id не найден");
        }
    }
}