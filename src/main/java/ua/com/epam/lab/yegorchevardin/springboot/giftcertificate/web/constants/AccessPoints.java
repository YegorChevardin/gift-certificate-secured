package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum AccessPoints {
    REGISTER_POINT("/api/v1/auth/register"),
    LOGIN_POINT("/api/v1/auth/login"),
    HOME_PAGE("/"),
    API_HOME_POINT("/api/v1/");

    private final String value;

    public static String[] getPostAccessPoints() {
        return new String[]{
                REGISTER_POINT.value,
                LOGIN_POINT.value
        };
    }

    public static String[] getGetAccessPoints() {
        return new String[]{
                HOME_PAGE.value,
                API_HOME_POINT.value
        };
    }

    public static List<String> getAccessPoints() {
        return Arrays.stream(AccessPoints.values())
                .map(element -> element.value).collect(Collectors.toList());
    }
}
