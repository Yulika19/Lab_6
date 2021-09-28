package Yul.Server.fileWorker;

public interface StudyGroupWriter extends CSVFileWorker {

    void write();

    void setFilePath(String filePath);

}