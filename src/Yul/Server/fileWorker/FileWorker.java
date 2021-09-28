package Yul.Server.fileWorker;

        import java.io.File;
        import java.util.Scanner;

public interface FileWorker {
    String getFilePath();

    default String inputFilePath() {
        String filePath;
        System.out.println("Enter the path to file:");
        Scanner scan = new Scanner(System.in);
        filePath = scan.nextLine().trim();
        File file = new File(filePath);
        while (!file.exists() || file.isDirectory() || !file.canRead()) {
            if (!file.exists())
                System.out.println("File isn't exist, please try again:");
            else if (!file.canRead())
                System.out.println("Permission denied! File can't be read, please try again:");
            else
                System.out.println("This is the Directory! Please try again:");
            filePath = scan.nextLine();
            file = new File(filePath.trim());
        }
        System.out.println("The path to the file is successfully entered");
        return filePath;
    }
}