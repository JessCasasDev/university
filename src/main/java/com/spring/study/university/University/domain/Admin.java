package com.spring.study.university.University.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name="admins")
@Entity
public class Admin extends Person {
}
