package Yul.General.validation;

        import Yul.General.exceptions.EnumNotFoundException;
        import Yul.General.exceptions.InvalidFieldException;
        import Yul.General.general.FormOfEducation;
        import Yul.General.general.Location;
        import Yul.General.general.Semester;
        import Yul.General.general.StudyGroup;

public interface StudyGroupBuilder {
    void setName(String name) throws InvalidFieldException, InvalidFieldException;

    void setCoordinateX(int x) throws InvalidFieldException;

    void setCoordinateY(Long y) throws InvalidFieldException;

    void setStudentsCount(int studentsCount) throws InvalidFieldException;

    void setFormOfEducation(FormOfEducation formOfEducation) throws InvalidFieldException;

    void setSemester(Semester semester) throws InvalidFieldException;

    void setGAName(String name) throws InvalidFieldException;

    void setGAPassportID(String passportID) throws InvalidFieldException;

    void setGALocation(Location location);

    void setGALocationX(long x);

    void setGALocationY(Long y) throws InvalidFieldException;

    void setGALocationZ(Long z) throws InvalidFieldException;

    void setGALocationName(String name) throws InvalidFieldException;

    void setCreationDate();

    StudyGroup getStudyGroup();

    Semester checkSemester(String str) throws InvalidFieldException, EnumNotFoundException;

    FormOfEducation checkFormOfEducation(String str) throws InvalidFieldException, EnumNotFoundException;

    Long askStudyGroupId();

    void inputFieldsFile();

    void askName();

    void askCoordinateX();

    void askCoordinateY();

    void askStudentsCount();

    void askFormOfEducation();

    void askSemester();

    void askGAName();

    void askGAPassportID();

    void askGALocationX();

    void askGALocationY();

    void askGALocationZ();

    void askGALocationName();

    StudyGroup askStudyGroup();
}