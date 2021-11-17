package ir.sk.processing.parser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sad.kayvanfar on 11/17/2021.
 */
public class CSVParserFactory {
    private static final Map<RecordType, CSVParser> PARSERS = new HashMap<>() {{
        put(RecordType.COOKIE, new CookieCsvParser());
        // etc
    }};

    public static CSVParser getParser(RecordType type) {
        CSVParser parser = PARSERS.get(type);
        if (parser == null) {
            throw new IllegalArgumentException("No such record type");
        }
        return parser;
    }
}
