package Yul.Server.commands;

        import Yul.General.exceptions.CommandIsNotExistException;
        import Yul.General.general.AbstractCommand;
        import Yul.General.general.Command;
        import Yul.General.general.LimitedQueue;
        import Yul.General.general.StudyGroupCommand;
        import Yul.Server.collection.ServerStudyGroup;

        import java.util.HashMap;

public class ServerCommandReaderImpl implements ServerCommandReader {
    private final HashMap<String, AbstractCommand> commandMap;
    private final HashMap<String, ServerCommand> serverCommandMap;
    private final LimitedQueue<String> history;


    public ServerCommandReaderImpl() {
        commandMap = new HashMap<>();
        serverCommandMap = new HashMap<>();
        history = new LimitedQueue<>(5);
    }

    @Override
    public LimitedQueue<String> getHistory() {
        return history;
    }

    /**
     * Метод, обеспечивающий чтение команды в строковом формате
     */
    @Override
    public void executeCommand(String userCommand, ServerStudyGroup studyGroup) throws CommandIsNotExistException {
        if (userCommand == null) {
            throw new CommandIsNotExistException("Данной команды не существует");
        }
        String[] updatedUserCommand = userCommand.trim().toLowerCase().split("\\s+", 2);
        updatedUserCommand[0] = updatedUserCommand[0].trim().toLowerCase();
        Command command = commandMap.get(updatedUserCommand[0]);
        if (command != null) {
            history.add(updatedUserCommand[0]);
            if (!command.isStudyGroupCommand()) {
                command.execute(updatedUserCommand);
            } else {
                ((StudyGroupCommand) command).execute(updatedUserCommand, studyGroup);
            }
        } else if (!updatedUserCommand[0].equals(""))
            throw new CommandIsNotExistException("Данной команды не существует");
    }

    @Override
    public void executeServerCommand(String userCommand) throws CommandIsNotExistException {
        if (userCommand == null) {
            throw new CommandIsNotExistException("Данной серверной команды не существует");
        }
        userCommand = userCommand.toLowerCase().trim();
        ServerCommand command = serverCommandMap.get(userCommand);
        if (command != null)
            command.execute();
        else
            throw new CommandIsNotExistException("Данной серверной команды не существует");
    }

    /**
     * Добавить команду
     *
     * @param commandName - название команды
     * @param command     - сама команда
     */
    @Override
    public void addCommand(String commandName, AbstractCommand command) {
        commandMap.put(commandName, command);
    }

    @Override
    public void addServerCommand(String commandName, ServerCommand command) {
        serverCommandMap.put(commandName, command);
    }

    @Override
    public HashMap<String, AbstractCommand> getCommandMap() {
        return commandMap;
    }

    @Override
    public HashMap<String, ServerCommand> getServerCommandMap() {
        return serverCommandMap;
    }

    @Override
    public boolean isStudyGroupCommand(Command command) {
        return command.isStudyGroupCommand();
    }

    @Override
    public AbstractCommand getCommandByName(String commandName) {
        return commandMap.get(commandName);
    }
}