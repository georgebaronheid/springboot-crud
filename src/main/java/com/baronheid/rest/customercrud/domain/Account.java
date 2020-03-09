package com.baronheid.rest.customercrud.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Table(name = "tb_account")
@Entity
@Data
public class Account implements Serializable {
    public static final long serialVersionUID = -165463251564612531L;

    @Id
    @Column(name = "nr_accountNumber", updatable = false)
    private Integer accountNumber;

    @Column(name = "nm_accountName")
    private String accountName;

    @Column(name = "vl_balance")
    private Double balance;

    @Column(name = "dt_openingDate")
    private LocalDate openingDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_customer", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Customer customer;

}
