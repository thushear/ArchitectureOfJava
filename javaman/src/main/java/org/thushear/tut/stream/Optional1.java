package org.thushear.tut.stream;

import java.util.Optional;

/**
 * Created by kongming on 2016/3/9.
 */
public class Optional1 {

    public static void main(String[] args) {

        Optional<String> optional = Optional.of("bam");
        System.out.println(optional.isPresent());
        System.out.println(optional.get());
        System.out.println(optional.orElse("fallback"));

        Optional emptyOptional = Optional.empty();

        System.out.println(emptyOptional.orElse("bbb"));
        System.out.println(emptyOptional.orElseGet( ()-> "aaa" ));

        optional.ifPresent((s) -> {
            System.out.println(s.charAt(0));
        });

    }
}
