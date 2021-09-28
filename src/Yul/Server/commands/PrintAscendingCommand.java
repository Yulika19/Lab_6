package Yul.Server.commands;


        import Yul.General.general.AbstractCommand;
        import Yul.Server.collection.CollectionManager;
        import Yul.Server.collection.StudyGroupShower;
        import Yul.Server.connection.response.ResponseCreator;

public class PrintAscendingCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final ResponseCreator responseCreator;

    public PrintAscendingCommand(CollectionManager collectionManager, ResponseCreator responseCreator) {
        super("print_ascending", " : вывести элементы коллекции в порядке возрастания");
        this.collectionManager = collectionManager;
        this.responseCreator = responseCreator;
    }

    @Override
    public void execute(String[] args) {
        StringBuilder sb = new StringBuilder("Элементов в коллекции: " + collectionManager.getStudyGroups().size()).append("\n");
        collectionManager.getStudyGroups()
                .stream()
                .forEach(x -> sb.append(StudyGroupShower.toStrView(x)));
        responseCreator.addToMsg(sb.toString());
    }
}