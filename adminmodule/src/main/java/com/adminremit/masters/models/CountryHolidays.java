package com.adminremit.masters.models;

import com.adminremit.common.models.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "admin_country_holidays")
@Where(clause = "is_delete=false")
@Getter
@Setter
public class CountryHolidays extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "country_id",insertable = false,updatable = false)
    private Countries countries;


    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "holiday_id",insertable = false,updatable = false)
    private Holiday holiday;

    private Long country_id;
    private Long holiday_id;
}
