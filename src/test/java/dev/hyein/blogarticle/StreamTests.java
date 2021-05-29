package dev.hyein.blogarticle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
class StreamTests {

    @Test
    void stream() {
        /* 배열 출력 */
        String[] alphabetArr = new String[]{"A", "B", "C"};
        Arrays.stream(alphabetArr)
                .forEach(alphabet -> log.info(alphabet));
    }

    @Test
    void person() {
        List<Person> personList = new ArrayList<>();
        personList.add(new Person());

        /* 멀티 라인 */
        personList.stream().forEach(person -> {
            person.setName("아무개");
            person.setAge(13);
        });

        /* personList 중 한 요소라도 조건을 만족하면 true 반환 */
        boolean anyMatch = personList.stream()
                .anyMatch(person -> (person.getAge() == 13) && ("아무개".equals(person.getName())));

        /* personList 중 모든 요소가 조건을 만족하면 true 반환 */
        boolean allMatch = personList.stream()
                .allMatch(person -> (person.getAge() == 13) && ("아무개".equals(person.getName())));

        /* personList 중 모든 요소가 조건을 만족하지 않으면 true 반환 */
        boolean noneMatch = personList.stream()
                .noneMatch(person -> (person.getAge() == 13) && ("아무개".equals(person.getName())));

        /* personList에서 filet 조건을 첫 번째로 만족하는 요소 반환 */
        Person filterPerson = personList.stream()
                .filter(person -> "아무개".equals(person.getName()))
                .findFirst()
                .get();

        /* personList에서 age만 모아 List 로 반환 */
        List<Integer> ageList = personList.stream()
                .map(person -> person.getAge())
                .collect(Collectors.toList());

        /* personList에서 age만 모아 Set 으로 반환 */
        Set<Integer> ageSet = personList.stream()
                .map(person -> person.getAge())
                .collect(Collectors.toSet());

        /* personList에서 key가 name이고 value가 age인 Map 만들기 */
        Map<String, Integer> personMap = personList.stream()
                .collect(Collectors.toMap(Person::getName, Person::getAge));

        /* personMap에서 value가 13인 요소의 key 리스트 반환 */
        List<String> age13NameList = personMap.entrySet().stream()
                .filter(person -> person.getValue() == 13)
                .map(person -> person.getKey())
                .collect(Collectors.toList());

        /* personMap에서 value가 13인 요소의 key를 "," 로 이어붙이기 */
        String names = personMap.entrySet().stream()
                .filter(person -> person.getValue() == 13)
                .map(Map.Entry::getKey)
                .collect(Collectors.joining(","));

        /* personMap의 key인 name의 최대 글자 수 구하기 */
        int nameMaxLength = personMap.keySet().stream()
                .mapToInt(name -> name.length())
                .max()
                .getAsInt();

        /* 나이 평균 구하기 */
        double ageAvg = ageList.stream()
                .filter(Objects::nonNull)
                .mapToDouble(age -> age)
                .average()
                .getAsDouble();

        /* personList에서 짝수 인덱스 요소만 리스트로 반환 */
        List<Person> subPersonList = IntStream.range(0, personList.size())
                .filter(i -> i % 2 == 0)
                .mapToObj(personList::get)
                .collect(Collectors.toList());

        /* ageList 에서 마지막 요소 값 반환 */
        Integer lastAge = ageList.stream()
                .reduce((age1, age2) -> age2)
                .get();




    }

    @NoArgsConstructor @AllArgsConstructor @Setter @Getter
    private class Person {
        private String name;
        private Integer age;
    }

    @Test
    public void fileWalking() throws IOException {
        Path dirPath = Paths.get("/");
        /* 디렉토리의 .txt 파일 경로만 가지고 작업 */
        Files.walk(dirPath).filter(path -> path.toString().endsWith(".txt")).forEach(path ->
                doFileWork(path)
        );
    }



    private void doFileWork(Path path) {}

}
