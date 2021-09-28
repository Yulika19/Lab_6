package Yul.Server.commands;


        import Yul.Server.collection.CollectionManager;
        import Yul.Server.fileWorker.StudyGroupWriter;

public class SaveCommand implements ServerCommand {
    private final StudyGroupWriter writer;

    public SaveCommand(StudyGroupWriter writer) {
        this.writer = writer;
    }

    @Override
    public void execute() {
        writer.write();
        System.out.println("The collection is saved successfully");
    }
}