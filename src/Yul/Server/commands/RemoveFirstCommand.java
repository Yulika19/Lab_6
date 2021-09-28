package Yul.Server.commands;


        import Yul.General.general.AbstractCommand;
        import Yul.Server.collection.CollectionManager;

public class RemoveFirstCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public RemoveFirstCommand(CollectionManager collectionManager) {
        super("remove_first", " : удалить первый элемент из коллекции");
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        collectionManager.removeFirstElement();
    }
}