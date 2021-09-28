package Yul.Server.commands;


        import Yul.General.general.AbstractCommand;
        import Yul.Server.collection.CollectionManager;

public class ClearCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManagerImpl) {
        super("clear", " : очистить коллекцию");
        this.collectionManager = collectionManagerImpl;
    }

    @Override
    public void execute(String[] args) {
        collectionManager.clear();
    }
}