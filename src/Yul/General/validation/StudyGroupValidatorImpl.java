package Yul.General.validation;

        import Yul.General.exceptions.InvalidFieldException;
        import Yul.General.general.FormOfEducation;
        import Yul.General.general.Semester;

public class StudyGroupValidatorImpl implements StudyGroupValidator {
    @Override
    public void validateName(String name) throws InvalidFieldException {
        if (name == null || name.equals(""))
            throw new InvalidFieldException("Invalid value for StudyGroup name");
    }

    @Override
    public void validateCoordinateX(int x) throws InvalidFieldException {
        if (x <= -393)
            throw new InvalidFieldException("Invalid value for StudyGroup Coordinate x");
    }

    @Override
    public void validateCoordinateY(Long y) throws InvalidFieldException {
        if (y == null || y <= -741)
            throw new InvalidFieldException("Invalid value for StudyGroup Coordinate y");
    }

    @Override
    public void validateStudentsCount(int studentsCount) throws InvalidFieldException {
        if (studentsCount <= 0)
            throw new InvalidFieldException("Invalid value for StudyGroup studentsCount");
    }

    @Override
    public void validateFormOfEducation(FormOfEducation formOfEducation) throws InvalidFieldException {
        if (formOfEducation == null)
            throw new InvalidFieldException("Invalid value for StudyGroup formOfEducation");

    }

    @Override
    public void validateSemester(Semester semester) throws InvalidFieldException {
        if (semester == null)
            throw new InvalidFieldException("Invalid value for StudyGroup semester");
    }

    @Override
    public void validateGAName(String name) throws InvalidFieldException {
        if (name == null || name.equals(""))
            throw new InvalidFieldException("Invalid value for StudyGroup GroupAdmin name");
    }

    @Override
    public void validateGAPassportID(String passportID) throws InvalidFieldException {
        if (passportID == null)
            throw new InvalidFieldException("Invalid value for StudyGroup GroupAdmin passportID");
    }

    @Override
    public void validateGALocationY(Long y) throws InvalidFieldException {
        if (y == null)
            throw new InvalidFieldException("Invalid value for StudyGroup GroupAdmin Location x");
    }

    @Override
    public void validateGALocationZ(Long z) throws InvalidFieldException {
        if (z == null)
            throw new InvalidFieldException("Invalid value for StudyGroup GroupAdmin Location z");
    }

    @Override
    public void validateGALocationName(String gAName) throws InvalidFieldException {
        if (gAName == null)
            throw new InvalidFieldException("Invalid value for StudyGroup GroupAdmin Location name");
    }

}