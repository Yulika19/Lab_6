package Yul.Client.commands;

        import Yul.General.exceptions.CommandIsNotExistException;
        import Yul.General.general.Command;
        import Yul.General.general.StudyGroup;

        import java.util.HashMap;

public interface ClientCommandReader {

    void executeCommand(String userCommand, StudyGroup studyGroup) throws CommandIsNotExistException;

    void addCommand(String commandName, Command command);

    HashMap<String, Command> getCommandMap();
}