package com.example.revpay_p2.model;

import jakarta.persistence.*;

@Entity
@Table(name = "personal_accounts")
public class PersonalAccount extends User {
    // no extra fields for now
}
