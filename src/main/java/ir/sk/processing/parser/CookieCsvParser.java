package ir.sk.processing.parser;

import ir.sk.processing.model.Cookie;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class CookieCsvParser extends AbstractCSVParser<Cookie> {

    private static final String SEPARATOR = ",";

    // Strategy design pattern
    @Override
    protected Optional<Cookie> buildObject(String str, UnaryOperator<Cookie> ...unaryOperators) {
        String[] splitted = str.split(SEPARATOR);
        Cookie cookie = new Cookie(splitted[0],  splitted[1]);

        for (UnaryOperator<Cookie> pokemonUnaryOperator : unaryOperators) {
            if (cookie != null)
                cookie = pokemonUnaryOperator.apply(cookie);
            else
                break;
        }
        return Optional.ofNullable(cookie);
    }

}
