package Yul.Server.commands;

        import Yul.General.general.AbstractCommand;
        import Yul.Server.collection.CollectionManager;
        import Yul.Server.collection.ServerStudyGroup;
        import Yul.Server.collection.StudyGroupShower;
        import Yul.Server.connection.response.ResponseCreator;

public class ShowCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final ResponseCreator responseCreator;

    public ShowCommand(CollectionManager collectionManager, ResponseCreator responseCreator) {
        super("show", " : вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.collectionManager = collectionManager;
        this.responseCreator = responseCreator;
    }

    @Override
    public void execute(String[] args) {
        StringBuilder sb = new StringBuilder("Элементов в коллекции: " + collectionManager.getStudyGroups().size()).append("\n");
        for (ServerStudyGroup sg : collectionManager.getStudyGroups()) {
            sb.append(StudyGroupShower.toStrView(sg));
        }
        responseCreator.addToMsg(sb.toString());
    }
}