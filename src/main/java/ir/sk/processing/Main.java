package ir.sk.processing;

import ir.sk.processing.exception.NotEnoughArgumentException;
import ir.sk.processing.mapper.ListMapper;
import ir.sk.processing.mapper.MostFrequentListMapper;
import ir.sk.processing.model.Cookie;
import ir.sk.processing.parser.CSVParser;
import ir.sk.processing.parser.CSVParserFactory;
import ir.sk.processing.parser.RecordType;
import ir.sk.processing.search.BinarySearchRange;
import ir.sk.processing.search.SearchRange;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    private final static String FILE_FLAG = "f";
    private final static String DATE_FLAG = "d";
    private final static int SKIP_LINES = 1;

    public static void main(String[] args) {
        CSVParser<Cookie> csvParser = CSVParserFactory.getParser(RecordType.COOKIE);
        InputStream inputStream = null;
        PrintStream printStream = System.out;
        try {
            Map<String, List<String>> arguments = getOptions(args);

            List<String> fileOptions = arguments.get(FILE_FLAG);

            inputStream = IO.getInputStream(fileOptions.get(0));

            List<Cookie> cookies = csvParser.read(inputStream, SKIP_LINES);
            SearchRange<Cookie> SearchRange = new BinarySearchRange<>();

            // O(Log n)
            List<String> dateOption = arguments.get(DATE_FLAG);
            List<Cookie> searchedCookies = SearchRange.searchRange(cookies, Main::dateCompare, new Cookie("", dateOption.get(0)));
            if (searchedCookies.isEmpty()) {
                IO.write(printStream, "there isn't cookies for the specified day");
                System.exit(0);
            }

            ListMapper<Cookie> listMapper = new MostFrequentListMapper();
            List<Cookie> finalCookies = listMapper.apply(searchedCookies);
            finalCookies.forEach(cookie -> IO.write(printStream, cookie.getName()));
        } catch (NotEnoughArgumentException | IOException | URISyntaxException e) {
            IO.write(printStream, e.getMessage());
        } catch (DateTimeParseException e) {
            IO.write(printStream, "Invalid date format");
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static int dateCompare(Cookie cookie, Cookie targetCookie) {
        LocalDate localDate = LocalDate.parse(cookie.getTimestamp());
        LocalDate targetLocalDate = LocalDate.parse(targetCookie.getTimestamp().substring(0, 10));
        if (localDate.equals(targetLocalDate))
            return 0;
        else if (localDate.isBefore(targetLocalDate))
            return -1;
        else
            return 1;
    }

    private static Map<String, List<String>> getOptions(String[] args) {
        final Map<String, List<String>> params = new HashMap<>();

        List<String> options = null;
        for (int i = 0; i < args.length; i++) {
            final String a = args[i];

            if (a.charAt(0) == '-') {
                if (a.length() < 2) {
                    throw new IllegalArgumentException("Not a valid argument: " + a);
                }

                options = new ArrayList<>();
                params.put(a.substring(1), options);
            }
            else if (options != null) {
                options.add(a);
            }
            else {
                throw new IllegalArgumentException("Illegal parameter usage: " + a);
            }
        }
        return params;
    }
}
