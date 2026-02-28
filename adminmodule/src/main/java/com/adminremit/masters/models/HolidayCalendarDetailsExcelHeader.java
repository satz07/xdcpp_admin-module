package com.adminremit.masters.models;

import com.adminremit.masters.enums.ExcelHeader;
import lombok.Getter;


@Getter
public enum HolidayCalendarDetailsExcelHeader implements ExcelHeader {

    COUNTRYNAME(0,"COUNTRY"),
    TYPE(1,"TYPE"),
    DATE(2,"DATE"),
    DAY(3,"DAY"),
    DESCRIPTION(4,"DESCRIPTION");

    private final int index;
    private final String headerText;

    HolidayCalendarDetailsExcelHeader(int index, String headerText) {
        this.index = index;
        this.headerText= headerText;
    }

}
