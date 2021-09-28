package Yul.Client.commands;

        import Yul.General.exceptions.CommandIsNotExistException;
        import Yul.General.general.Command;
        import Yul.General.general.IOimpl;
        import Yul.General.general.StudyGroup;
        import Yul.General.general.StudyGroupCommand;
        import Yul.Server.collection.ServerStudyGroup;

        import java.util.HashMap;
        import java.util.HashSet;


/**
 * Класс, отвечающий за общение Client и CollectionManagerImpl
 * Является пунктом упрравления для всех команд
 */
public class ClientCommandReaderImpl implements ClientCommandReader, IOimpl {
    private final HashMap<String, Command> commandMap;
    private final HashSet<String> scriptSet;

    public ClientCommandReaderImpl() {
        commandMap = new HashMap<>();
        scriptSet = new HashSet<>();
    }


    /**
     * Метод, обеспечивающий чтение команды в строковом формате
     */
    @Override
    public void executeCommand(String userCommand, StudyGroup studyGroup) throws CommandIsNotExistException {
        if (userCommand == null)
            return;
        String[] updatedUserCommand = userCommand.trim().toLowerCase().split("\\s+", 2);
        Command command = commandMap.get(updatedUserCommand[0]);
        if (command != null) {
            if (!command.isStudyGroupCommand())
                command.execute(updatedUserCommand);
            else
                ((StudyGroupCommand) command).execute(updatedUserCommand, (ServerStudyGroup) studyGroup);
        } else if (!updatedUserCommand[0].equals(""))
            throw new CommandIsNotExistException("Данной команды не существует");
    }


    @Override
    public void addCommand(String commandName, Command command) {
        commandMap.put(commandName, command);
    }

    @Override
    public HashMap<String, Command> getCommandMap() {
        return commandMap;
    }

    public void addScript(String name) {
        scriptSet.add(name);
    }

    public void removeScript(String name) {
        scriptSet.remove(name);
    }

}