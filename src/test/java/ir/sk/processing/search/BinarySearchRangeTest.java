package ir.sk.processing.search;

import ir.sk.processing.Main;
import ir.sk.processing.model.Cookie;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by sad.kayvanfar on 11/17/2021.
 */
public class BinarySearchRangeTest {

    private SearchRange<Cookie> cookieSearchRange;
    private List<Cookie> cookies;

    @Before
    public void setUp() {
        cookieSearchRange = new BinarySearchRange<>();
        cookies = Arrays.asList(new Cookie("AtY0laUfhglK3lC7", "2018-12-10T14:19:00+00:00"),
                new Cookie("SAZuXPGUrfbcn5UA", "2018-12-09T14:19:00+00:00"),
                new Cookie("5UAVanZf6UtGyKVS", "2018-12-09T14:19:00+00:00"),
                new Cookie("5UAVanZf6UtGyKVS", "2018-12-08T14:19:00+00:00"));
    }

    @After
    public void tearDown() {
    }

    @Test
    public void searchRange() {
        List<Cookie> expectedList = Arrays.asList(new Cookie("SAZuXPGUrfbcn5UA", "2018-12-09T14:19:00+00:00"),
                new Cookie("5UAVanZf6UtGyKVS", "2018-12-09T14:19:00+00:00"));
        List<Cookie> result = cookieSearchRange.searchRange(cookies, Main::dateCompare, new Cookie("", "2018-12-09"));
        Assert.assertEquals(expectedList, result);
    }
}