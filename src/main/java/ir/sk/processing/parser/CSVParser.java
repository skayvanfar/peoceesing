package ir.sk.processing.parser;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.function.UnaryOperator;

public interface CSVParser<T> {
     List<T> read(Reader reader, int skipLines, UnaryOperator<T> ...unaryOperators) throws IOException;
}
