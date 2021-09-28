package Yul.Server.connection.response;

        import Yul.General.general.Response;
        import Yul.General.general.ResponseType;

public interface ResponseCreator {
    public void addToMsg(String message);

    public Response getResponse(String message, ResponseType responseType);

    public Response getResponse();
}