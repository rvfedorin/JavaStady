/*
 * Created by Roman V. Fedorin
 */
package frv.org.streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 *
 * @author Wolf
 */
public class Streams {

    public static void main(String[] args) {
        List<String> myList = Arrays.asList("a1", "a2", "b1", "b2", "c1", "c2");

        myList
                .stream()
                .filter(e -> e.startsWith("c"))
                .map(String::toUpperCase)
                .sorted()
                .forEach(System.out::println);
        ///////////////////////////////////////////////////////

        List<Foo> foos = new ArrayList<>();

// create foos
        IntStream
                .range(1, 4)
                .forEach(i -> foos.add(new Foo("Foo" + i)));

// create bars
        foos.forEach(f
                -> IntStream
                        .range(1, 4)
                        .forEach(i -> f.bars.add(new Bar("Bar" + i + " <- " + f.name))));

        foos.stream()
                .flatMap(f -> f.bars.stream())
                .forEach(b -> System.out.println(b.name));
        ////////////////////////  OR ///////////////////////////////  
        System.out.println("////////////////////////  OR ///////////////////");
        ////////////////////////  OR /////////////////////////////// 

        IntStream.range(1, 4)
                .mapToObj(i -> new Foo("Foo" + i))
                .peek(f -> IntStream.range(1, 4)
                    .mapToObj(i -> new Bar("Bar" + i + " <-" + f.name))
                    .forEach(f.bars::add)
                    )
                .flatMap(f -> f.bars.stream())
                .forEach(b -> System.out.println(b.name));

        /////////////////////////////////////////////////
    } // main()

}

class Foo {

    String name;
    List<Bar> bars = new ArrayList<>();

    public Foo(String name) {
        this.name = name;
    }

}

class Bar {

    String name;

    public Bar(String name) {
        this.name = name;
    }

}
