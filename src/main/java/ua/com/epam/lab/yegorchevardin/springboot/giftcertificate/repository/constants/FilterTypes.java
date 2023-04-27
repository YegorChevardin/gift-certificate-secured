package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.repository.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Constants that stands for filter parameters
 * @author yegorchevardin
 * @version 0.0.1
 */
@AllArgsConstructor
public enum FilterTypes {
    TAG_NAME("tag_name"),
    SORT_BY_TAG_NAME("tag_name_sort"),
    NAME("name"),
    DESCRIPTION("description"),
    DATE_SORT("date_sort"),
    NAME_SORT("name_sort");
    
    @Getter
    private final String value;
}
