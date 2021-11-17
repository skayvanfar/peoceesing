package ir.sk.processing.parser;

import ir.sk.processing.IO;
import ir.sk.processing.model.Cookie;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;

public class CsvParserTest {

    private final static String FILENAME = "sample.csv";

    private CSVParser<Cookie> csvParser;
    private InputStream inputStream;

    @Before
    public void setUp() throws FileNotFoundException, URISyntaxException {
        csvParser = new CookieCsvParser();
        inputStream = IO.getInputStream(FILENAME);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void read() throws Exception {
        int expectedSize = 13;
        List<Cookie> cookies = csvParser.read(inputStream, 1);
        Assert.assertEquals(expectedSize, cookies.size());
    }
}