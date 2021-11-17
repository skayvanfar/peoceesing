package ir.sk.processing.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public interface CSVParser<T> {
     List<T> read(InputStream inputStream, int skipLines, UnaryOperator<T> ...unaryOperators) throws IOException;
}
