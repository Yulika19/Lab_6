package Yul.General.validation;

        import Yul.General.exceptions.InvalidFieldException;
        import Yul.General.general.FormOfEducation;
        import Yul.General.general.Semester;

public interface StudyGroupValidator {
    void validateName(String name) throws InvalidFieldException, InvalidFieldException;

    void validateCoordinateX(int x) throws InvalidFieldException;

    void validateCoordinateY(Long y) throws InvalidFieldException;

    void validateStudentsCount(int studentsCount) throws InvalidFieldException;

    void validateFormOfEducation(FormOfEducation formOfEducation) throws InvalidFieldException;

    void validateSemester(Semester semester) throws InvalidFieldException;

    void validateGAName(String name) throws InvalidFieldException;

    void validateGAPassportID(String passportID) throws InvalidFieldException;

    void validateGALocationY(Long y) throws InvalidFieldException;

    void validateGALocationZ(Long z) throws InvalidFieldException;

    void validateGALocationName(String name) throws InvalidFieldException;
}