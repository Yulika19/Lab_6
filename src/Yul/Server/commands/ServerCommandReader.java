package Yul.Server.commands;

        import Yul.General.exceptions.CommandIsNotExistException;
        import Yul.General.general.AbstractCommand;
        import Yul.General.general.Command;
        import Yul.General.general.LimitedQueue;
        import Yul.Server.collection.ServerStudyGroup;

        import java.util.HashMap;

public interface ServerCommandReader {
    LimitedQueue<String> getHistory();

    void executeCommand(String userCommand, ServerStudyGroup studyGroup) throws CommandIsNotExistException;

    void executeServerCommand(String userCommand) throws CommandIsNotExistException;

    void addCommand(String commandName, AbstractCommand command);

    void addServerCommand(String commandName, ServerCommand command);

    boolean isStudyGroupCommand(Command command);

    HashMap<String, AbstractCommand> getCommandMap();

    HashMap<String, ServerCommand> getServerCommandMap();

    AbstractCommand getCommandByName(String commandName);

}