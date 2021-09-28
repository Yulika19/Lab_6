package Yul.General.general;

        import java.io.Serializable;

public enum RequestType implements Serializable {
    COMMAND_REQUEST,

    EXECUTE_COMMAND,

    ERROR_TYPE_REQUEST;

    private final static long serialVersionUID = -24388L;
}