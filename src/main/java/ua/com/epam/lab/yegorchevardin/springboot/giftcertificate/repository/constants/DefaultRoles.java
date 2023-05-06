package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * Enum for storing constants with default roles
 * @author yegorchevardin
 * @version 0.0.1
 */
@AllArgsConstructor
public enum DefaultRoles {
    USER_ROLE("user"),
    ADMIN_ROLE("admin");

    @Getter
    private final String value;

    /**
     * Method for getting roles in string values
     * @return list of roles in string
     */
    public static List<String> getDefaultRolesInStrings() {
        return Arrays.stream(values()).map(
                DefaultRoles::getValue
        ).toList();
    }
}
