package com.demo.wms.domain;

import javax.persistence.*;

/**
 * Created by toxa on 8/27/2016.
 */

@Entity
@Table(name = "user_roles", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private String name;
}
