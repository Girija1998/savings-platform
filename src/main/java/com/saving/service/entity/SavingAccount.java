package com.saving.service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="saving_accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SavingAccount {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="customer_name")
    private String customerName;

    @Column(name="mobile")
    private String mobile;

    @Column(name="balance")
    private double balance;
}
