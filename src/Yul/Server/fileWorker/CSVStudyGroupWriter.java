package Yul.Server.fileWorker;

        import Yul.General.general.IOimpl;
        import Yul.Server.collection.CollectionManager;
        import Yul.Server.collection.ServerStudyGroup;

        import java.io.BufferedWriter;
        import java.io.File;
        import java.io.FileWriter;
        import java.io.IOException;
        import java.util.LinkedList;

/**
 * Основной класс для работы с csv файлом
 */
public class CSVStudyGroupWriter implements StudyGroupWriter, IOimpl {
    private final CollectionManager manager;
    private String filePath;
    private String separator;

    public CSVStudyGroupWriter(CollectionManager collectionManager) {
        manager = collectionManager;
    }

    /**
     * Метод, обеспечивающий запись в файл, использую BufferedWriter
     */
    @Override
    public void write() {
        try {
            String[] str = parseToString(manager.getStudyGroups());
            if (filePath == null) {
                println("The path in the input variable is incorrect");
                filePath = inputFilePath();
                filePath = getFilePath();
                while (!new File(filePath).canWrite()) {
                    println("Permission denied! Unable to write to this file, please try again:");
                    filePath = inputFilePath();
                    filePath = getFilePath();
                }
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath));
            for (String s : str) {
                bufferedWriter.write(s);
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * Получить значения разделителя, который используется для CSV файла.
     *
     * @return separator разделитель.
     */
    @Override
    public String getSep() {
        return separator;
    }

    /**
     * Установить значение разделителя, которое будет использоваться для CSV файла.
     *
     * @param separator разделитель.
     */
    @Override
    public void setSep(String separator) {
        this.separator = separator;
    }

    /**
     * @return filePath
     */
    @Override
    public String getFilePath() {
        return filePath;
    }

    /**
     * Метод, обеспечивающий за строкове представление элементов коллекции в csv файле
     *
     * @param linkedList Коллекция
     * @return Строковое представление
     */
    private String[] parseToString(LinkedList<ServerStudyGroup> linkedList) {
        String[] toSave = new String[linkedList.size()];
        int cnt = 0;
        for (ServerStudyGroup sg : linkedList) {
            toSave[cnt] = sg.getName();
            toSave[cnt] += ',' + String.valueOf(sg.getCoordinates().getX());
            toSave[cnt] += ',' + String.valueOf(sg.getCoordinates().getY());
            toSave[cnt] += ',' + String.valueOf(sg.getStudentsCount());
            toSave[cnt] += ',' + sg.getFormOfEducation().getUrl();
            toSave[cnt] += ',' + sg.getSemesterEnum().getUrl();
            toSave[cnt] += ',' + sg.getGroupAdmin().getName();
            toSave[cnt] += ',' + sg.getGroupAdmin().getPassportID();
            toSave[cnt] += ',' + String.valueOf(sg.getGroupAdmin().getLocation().getX());
            toSave[cnt] += ',' + String.valueOf(sg.getGroupAdmin().getLocation().getY());
            toSave[cnt] += ',' + String.valueOf(sg.getGroupAdmin().getLocation().getZ());
            toSave[cnt] += ',' + sg.getGroupAdmin().getLocation().getName();
            toSave[cnt] += ',' + String.valueOf(sg.getId());
            toSave[cnt] += ',' + sg.getCreationDate().toString();
            cnt++;
        }
        return toSave;
    }

    @Override
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}