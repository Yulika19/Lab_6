package Yul.General.general;

        import java.io.Serializable;

public enum ResponseType implements Serializable {
    BASIC_RESPONSE,
    STUDY_GROUP_RESPONSE,
    ERROR_RESPONSE;
    private final static long serialVersionUID = 8260L;
}