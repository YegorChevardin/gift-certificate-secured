package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.services.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Class with constants that are keeping exception messages
 */
@AllArgsConstructor
public enum ExceptionMessages {
    TAG_BY_ID_NOT_FOUND("Tag by this id %s does not exist"),
    TAB_BY_NAME_NOT_FOUND("Tag by this name %s does not exist"),
    TAG_BY_NAME_EXIST("%s tag already exist"),
    TAG_NOT_FOUND("Such tag does not exist"),
    USER_BY_ID_NOT_FOUND("User with this id %s does not exist"),
    USER_BY_USERNAME_EXIST("User with this username %s already exist"),
    USER_BY_USERNAME_NOT_FOUND("User with this username %s not found"),
    ORDER_NOT_FOUND_BY_ID("Order was not found by this id %s"),
    GIFT_CERTIFICATE_BY_ID_NOT_FOUND("Gift certificate by this id %s does not exist"),
    GIFT_CERTIFICATE_BY_NAME_EXIST("%s gift certificate already exist"),
    GIFT_CERTIFICATE_BY_NAME_DOES_NOT_FOUND(
            "Gift certificate with this name %s does not exist");
    
    @Getter
    private final String value;
}
