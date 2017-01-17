package lambda;

import entity.Person;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LambdaTest {
    public static void main(String[] args){
        List<Person> list = Arrays.asList(
                new Person("George Harrison", "1"),
                new Person("John Lennon", "1"),
                new Person("Paul McCartney", "0"),
                new Person("Ringo Starr", "0"),
                new Person("The Beatles", "1"));
        String res = list.stream().map(Person::getName).collect(Collectors.joining(",", "[", "]"));
        System.out.println(res);

    }
}
