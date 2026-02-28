package com.adminremit.masters.models;

import com.adminremit.common.models.BaseEntity;
import com.adminremit.masters.enums.Days;
import com.adminremit.masters.enums.HolidayType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;


@Entity
@Table(name = "admin_holiday_calendar")
@Where(clause = "is_delete=false")
@Getter
@Setter
public class Holiday extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "day")
    @Enumerated(EnumType.STRING)
    private Days day;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private HolidayType type;

    @Column(name = "date")
    @JsonFormat(pattern="dd/MM/yyyy")
    private String date;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "country")
    private Countries country;
}
