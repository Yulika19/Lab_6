package Yul.Client.commands;

        import Yul.Client.client.ClientApp;
        import Yul.General.general.Command;

public class ClientExitCommand implements Command {
    ClientApp clientApp;

    public ClientExitCommand(ClientApp clientApp) {
        this.clientApp = clientApp;
    }

    @Override
    public void execute(String[] args) {
        clientApp.exit();
    }

    @Override
    public boolean isStudyGroupCommand() {
        return false;
    }
}