package ir.sk.processing.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;

// template design patten
public abstract class AbstractCSVParser<T> implements CSVParser<T> {

    protected final static String SEPARATOR = ",";

    @Override
    public List<T> read(InputStream inputStream, int skipLines, UnaryOperator<T> ...unaryOperators) throws IOException {
        List<T> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String str;
            int counter = 0;
            while ((str = br.readLine()) != null) {
                if (counter++ < skipLines)
                    continue;
                Optional<T> optional = buildObject(str, unaryOperators);
                optional.ifPresent(list::add);
            }
        }
        return list;
    }

    protected abstract Optional<T> buildObject(String str, UnaryOperator<T> ...unaryOperators);
}
