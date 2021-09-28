package Yul.Server.collection;

public interface StudyGroupShower {
    static String toStrView(ServerStudyGroup st) {
        return "StudyGroup{id = " + st.getId() +
                ", name = '" + st.getName() +
                "', Coordinates{x = " +
                st.getCoordinates().getX() + ", y = " +
                st.getCoordinates().getY() + "}, creationDate = " +
                st.getCreationDate().toLocalDate().toString()+
                ", studentsCount = " + st.getStudentsCount() +
                ", formOfEducation = " + st.getFormOfEducation() +
                ", semesterEnum = " + st.getSemesterEnum() +
                ", Person{name='" + st.getGroupAdmin().getName() + "', passportID='" + st.getGroupAdmin().getPassportID() + "', Location{x=" + st.getGroupAdmin().getLocation().getX() + ", y=" + st.getGroupAdmin().getLocation().getY() + ", z=" + st.getGroupAdmin().getLocation().getZ() + ", name = '" + st.getGroupAdmin().getLocation().getName() + "'}}}\n";
    }
}