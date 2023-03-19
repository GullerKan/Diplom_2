package api.util;
import org.apache.commons.lang3.RandomStringUtils;

public class Generate {

    public static String EMAIL = RandomStringUtils.randomAlphabetic(6) + "@yandex.ru";
    public static String PASSWORD = RandomStringUtils.randomNumeric(5);
    public static String NAME = RandomStringUtils.randomAlphabetic(5);
}