package Yul.Server.commands;

        import Yul.General.general.AbstractCommand;
        import Yul.Server.collection.CollectionManager;

public class SumOfStudentsCountCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public SumOfStudentsCountCommand(CollectionManager collectionManager) {
        super("sum_of_students_count", " : вывести сумму значений поля studentsCount для всех элементов коллекции");
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        collectionManager.sumOfStudentsCount();
    }
}