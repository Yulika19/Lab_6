package Yul.General.general;

        import Yul.Server.collection.ServerStudyGroup;

public interface StudyGroupCommand extends Command {
    void execute(String[] args, ServerStudyGroup studyGroup);

    default boolean isStudyGroupCommand() {
        return true;
    }
}