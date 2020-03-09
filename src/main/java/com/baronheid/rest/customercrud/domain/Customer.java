package com.baronheid.rest.customercrud.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Table(name = "tb_customer")
@Entity
@Data
public class Customer implements Serializable {
    public static final long serialVersionUID = -6759774343110776659L;

    @Id
    @GeneratedValue
    @Column(name = "id_customer", updatable = false)
    private Integer customerId;

    @Column(name = "nm_name")
    private String customerName;

    @Column(name = "dt_birth", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dateofBirth;

    @Column(name = "nr_phone")
    private String phoneNumber;



}
