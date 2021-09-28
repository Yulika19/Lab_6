package Yul.Server.collection;

        import Yul.General.general.Semester;
        import Yul.Server.connection.response.ResponseCreator;

        import java.time.ZonedDateTime;
        import java.util.Comparator;
        import java.util.LinkedList;
        import java.util.stream.Collectors;

/**
 * Класс который хранит коллекцию и совершает действия с ней
 */
public class CollectionManagerImpl extends AbstractCollectionManager {
    private final ZonedDateTime creationDate;
    private final ResponseCreator responseCreator;


    public CollectionManagerImpl(ResponseCreator responseCreator) {
        studyGroups = new LinkedList<>();
        creationDate = ZonedDateTime.now();
        this.responseCreator = responseCreator;
        initIdSet();
    }

    /**
     * Метод отвечающий за полную очистку коллекции
     */
    @Override
    public void clear() {
        studyGroups.clear();
    }

    /**
     * Метод, отвечающий за подсчет количества всех студентов
     */
    @Override
    public void sumOfStudentsCount() {
        int id = studyGroups.stream().mapToInt(ServerStudyGroup::getStudentsCount).sum();
        responseCreator.addToMsg(Integer.toString(id));
    }

    /**
     * Метод, отвевающий за удаление первого элемента коллекции
     */
    @Override
    public void removeFirstElement() {
        removeId(studyGroups.stream().findFirst().get().getId());
        studyGroups = studyGroups.stream()
                .skip(1)
                .collect(Collectors.toCollection(LinkedList::new));
        responseCreator.addToMsg("The first element is successfully removed from collection, if it existed");
    }

    /**
     * Метод, отвечающий за удаление элементов, превышающий заданный (по studentsCount)
     */
    @Override
    public void removeGreater(ServerStudyGroup studyGroup) {
        int count = studyGroups.size();
        studyGroups.stream()
                .filter(studyGroup1 -> studyGroup1.getStudentsCount() > studyGroup.getStudentsCount())
                .forEach(studyGroup1 -> removeId(studyGroup1.getId()));

        studyGroups = studyGroups.stream()
                .filter(studyGroup1 -> studyGroup1.getStudentsCount() <= studyGroup.getStudentsCount())
                .collect(Collectors.toCollection(LinkedList::new));
        responseCreator.addToMsg(count - studyGroups.size() + " elements removed");
    }

    /**
     * Метод, отвечающий за удаление элемента коллекции по id
     *
     * @param id
     */
    @Override
    public void removeById(long id) {
        if (studyGroups.stream().anyMatch(studyGroup -> studyGroup.getId() == id))
            responseCreator.addToMsg("Element is successfully removed from collection");
        else
            responseCreator.addToMsg("Element is successfully removed from collection");
        studyGroups = studyGroups.stream()
                .filter(studyGroup -> studyGroup.getId() != id)
                .collect(Collectors.toCollection(LinkedList::new));
        removeId(id);
    }

    /**
     * Считает, сколько сколько групп учится в семестрах, ниже заданного
     *
     * @param semester
     */
    @Override
    public void countLessThanSemesterEnum(Semester semester) {
        int count = (int) studyGroups.stream()
                .filter(studyGroup -> studyGroup.getSemesterEnum().getValue() > semester.getValue())
                .count();
        responseCreator.addToMsg("Count of elements is: " + count);
    }

    /**
     * Метод, отвечающий за добавление элемента в коллекцию
     *
     * @param studyGroup
     * @return был ли добавлен элемент
     */
    @Override
    public void addElement(ServerStudyGroup studyGroup) {
        studyGroups.add(studyGroup);
    }

    /**
     * @param id         айди группы, по которому произойдет обноваление
     * @param studyGroup обновленный экземпляр
     * @return Произошло ли обновление
     */
    @Override
    public boolean update(long id, ServerStudyGroup studyGroup) {
        for (ServerStudyGroup sg : studyGroups) {
            if (sg.getId() == id) {
                sg.setName(studyGroup.getName());
                sg.setCoordinates(studyGroup.getCoordinates());
                sg.setFormOfEducation(studyGroup.getFormOfEducation());
                sg.setGroupAdmin(studyGroup.getGroupAdmin());
                sg.setStudentsCount(studyGroup.getStudentsCount());
                sg.setSemesterEnum(studyGroup.getSemesterEnum());
                responseCreator.addToMsg("Element is updated!");
                return true;
            }
        }
        responseCreator.addToMsg("Input id is not found!");
        return false;
    }

    /**
     * Метод выводит кратку информацию о классе
     */
    @Override
    public void info() {
        String info = "Время инциализации коллекции: " + getInitializationTime() + "\n" +
                "Количество элементов в коллекции: " + studyGroups.size() + "\n" +
                "Тип коллекции: " + studyGroups.getClass().getSimpleName();
        responseCreator.addToMsg(info);
    }

    /**
     * Метод возвращающий дату создания обьекта.
     *
     * @return дата создания обькта.
     */
    @Override
    public ZonedDateTime getInitializationTime() {
        return creationDate;
    }

    /**
     * Проверяет, есть ли элемент с данным id
     *
     * @param id
     * @return true - если элемент с данным id существует
     */
    @Override
    public boolean containsId(long id) {
        return studyGroups.stream().anyMatch(x -> x.getId() == id);
    }

    @Override
    public ResponseCreator getResponseCreator() {
        return responseCreator;
    }

    /**
     * @return studyGroups
     */
    @Override
    public LinkedList<ServerStudyGroup> getStudyGroups() {
        sortByCoordinates();
        return studyGroups;
    }

    /**
     * Метод, обеспечивающий сортировку коллекции
     * использует компаратор по id
     */
    private void sortById() {
        studyGroups = studyGroups.stream()
                .sorted(getComparatorById())
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Метод, обеспечивающий сортировку коллекции
     * использует компаратор по Coordinates
     */
    private void sortByCoordinates() {
        studyGroups = studyGroups.stream()
                .sorted(getComparatorByCoordinates())
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Получить Comparator сравнения по id
     *
     * @return компаратор по id
     */
    private Comparator<ServerStudyGroup> getComparatorById() {
        return (ServerStudyGroup a, ServerStudyGroup b) -> (int) (a.getId() - b.getId());
    }

    /**
     * Получить Comparator сравнения по местоположению
     *
     * @return компаратор по Coordinates
     */
    private Comparator<ServerStudyGroup> getComparatorByCoordinates() {
        return (ServerStudyGroup a, ServerStudyGroup b) -> (
                a.getCoordinates().getX() > b.getCoordinates().getX() ? 1 :
                        a.getCoordinates().getX() < b.getCoordinates().getX() ? -1 :
                                a.getCoordinates().getY() > b.getCoordinates().getY() ? 1 :
                                        a.getCoordinates().getY() == b.getCoordinates().getY()? 0 : -1
        );
    }
}