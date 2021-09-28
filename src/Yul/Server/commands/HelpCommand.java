package Yul.Server.commands;

        import Yul.General.general.AbstractCommand;
        import Yul.Server.connection.response.ResponseCreator;

        import java.util.Formatter;
        import java.util.HashMap;

public class HelpCommand extends AbstractCommand {
    private final HashMap<String, AbstractCommand> commandMap;
    private final ResponseCreator responseCreator;

    public HelpCommand(HashMap<String, AbstractCommand> commandMap, ResponseCreator responseCreator) {
        super("help", " : вывести справку по доступным командам");
        this.commandMap = commandMap;
        this.responseCreator = responseCreator;
    }

    @Override
    public void execute(String[] args) {
        for (String it : commandMap.keySet()) {
            Formatter formatter = new Formatter();
            formatter.format("%-24s", commandMap.get(it).getName());
            formatter.format("%s", " " + commandMap.get(it).getDescription());
            responseCreator.addToMsg(formatter.toString());
        }
    }
}