package com.adminremit.masters.dto;

import lombok.Data;

@Data
public class HolidaysRequest {

    private String type;
    private String date;
    private String day;
    private String description;
    private String  countryName;
    private boolean publish;
}
