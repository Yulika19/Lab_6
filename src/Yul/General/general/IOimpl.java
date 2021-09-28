package Yul.General.general;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;

public interface IOimpl {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    default String readLine() throws IOException {
        return reader.readLine();
    }

    default void println(String str) {
        System.out.println(str);
    }

    default void print(String str) {
        System.out.print(str);
    }

    default void errPrint(String str) {
        System.err.println(str);
    }

    default BufferedReader getReader() {
        return reader;
    }

}