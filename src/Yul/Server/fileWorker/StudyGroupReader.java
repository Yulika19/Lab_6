package Yul.Server.fileWorker;

        import Yul.General.exceptions.UnsupportedFileException;
        import Yul.Server.collection.ServerStudyGroup;

public interface StudyGroupReader extends CSVFileWorker {
    ServerStudyGroup read(String[] values);

    void loadInput();

    void setFilePath(String fileName) throws UnsupportedFileException;

}