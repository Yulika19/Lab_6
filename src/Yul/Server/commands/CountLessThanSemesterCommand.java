package Yul.Server.commands;


        import Yul.General.exceptions.EnumNotFoundException;
        import Yul.General.exceptions.InvalidFieldException;
        import Yul.General.general.AbstractCommand;
        import Yul.General.validation.StudyGroupBuilder;
        import Yul.Server.collection.CollectionManager;
        import Yul.Server.connection.response.ResponseCreator;

public class CountLessThanSemesterCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final ResponseCreator responseCreator;
    private final StudyGroupBuilder builder;

    public CountLessThanSemesterCommand(CollectionManager collectionManager, StudyGroupBuilder builder, ResponseCreator responseCreator) {
        super("count_less_than_semester", " semesterEnum : вывести количество элементов, значение поля semesterEnum которых меньше заданного");
        this.collectionManager = collectionManager;
        this.responseCreator = responseCreator;
        this.builder = builder;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            responseCreator.addToMsg("Please input argument");
            return;
        }
        try {
            collectionManager.countLessThanSemesterEnum(builder.checkSemester(args[1].trim()));
        } catch (EnumNotFoundException e) {
            responseCreator.addToMsg("There is no enum named " + args[1].trim());
        } catch (InvalidFieldException e) {
            responseCreator.addToMsg("Semester Enum can't be null");
        }
    }
}