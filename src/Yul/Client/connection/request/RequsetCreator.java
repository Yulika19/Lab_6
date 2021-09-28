package Yul.Client.connection.request;

        import Yul.General.general.Request;
        import Yul.General.general.RequestType;
        import Yul.General.general.StudyGroup;

public interface RequsetCreator {
    default Request createBasicRequest(String userString) {
        userString = userString.trim();
        return new Request(RequestType.COMMAND_REQUEST, userString, null);
    }

    default Request createExecuteRequest(String userString, StudyGroup studyGroup) {
        String[] strings = userString.trim().split("\\s");
        Request request;
        if (strings.length > 1)
            request = new Request(RequestType.EXECUTE_COMMAND, strings[0], strings[0]);
        else
            request = new Request(RequestType.EXECUTE_COMMAND, strings[0], null);
        request.setStudyGroup(studyGroup);
        return request;
    }


}