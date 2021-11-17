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
import java.util.List;

public class Main {

    private final static String FILE_FLAG = "-f";
    private final static String DATE_FLAG = "-d";
    private final static int SKIP_LINES = 1;

    public static void main(String[] args) {
        CSVParser<Cookie> csvParser = CSVParserFactory.getParser(RecordType.COOKIE);
        InputStream inputStream = null;
        PrintStream printStream = System.out;
        try {
            List<Option> options = getOptions(args);
            inputStream = IO.getInputStream(getOpt(options, FILE_FLAG));

            List<Cookie> cookies = csvParser.read(inputStream, SKIP_LINES);
            SearchRange<Cookie> SearchRange = new BinarySearchRange<>();

            // O(Log n)
            List<Cookie> searchedCookies = SearchRange.searchRange(cookies, Main::dateCompare, new Cookie("", getOpt(options, DATE_FLAG)));
            if (searchedCookies.isEmpty()) {
                IO.write(printStream, "there isn't cookies for the specified day");
                System.exit(0);
            }

            ListMapper<Cookie> listMapper = new MostFrequentListMapper();
            List<Cookie> finalCookies = listMapper.apply(searchedCookies);
            finalCookies.forEach(cookie -> IO.write(printStream, cookie.getName()));
        } catch (NotEnoughArgumentException | URISyntaxException | IOException e) {
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

    private static class Option {
        String flag, opt;

        Option(String flag, String opt) {
            this.flag = flag;
            this.opt = opt;
        }
    }


    private static String getOpt(List<Option> options,String flag) {
        return options.stream().filter(option -> option.flag.equals(flag)).findFirst().get().opt;
    }

    private static List<Option> getOptions(String[] args) {
        if (args.length < 4)
            throw new NotEnoughArgumentException(" not valid arguments -f (filename) -d (day)");
        List<Option> optsList = new ArrayList<>();

        for (int i = 0; i < args.length; i++) {
            if (args[i].charAt(0) == '-') {
                if (args[i].length() < 2)
                    throw new IllegalArgumentException("Not a valid argument: " + args[i]);
                else {
                    if (args.length - 1 == i)
                        throw new IllegalArgumentException("Expected arg after: " + args[i]);
                    // -opt
                    optsList.add(new Option(args[i], args[i + 1]));
                    i++;
                }
            }
        }
        return optsList;
    }
}
