package com.lotterychecker.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * <pre>
 * Author         : Paulo Franklim, paulofranklim@hotmail.com
 * Purpose        : <Purpose>
 * Input files    : N/A
 * Log File       : N/A
 * Output file    : N/A
 *
 * Copyright 2020 github.com/paulofranklim
 * </pre>
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = { "password" })
@EqualsAndHashCode
@Table(name = "USERS")
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long    id;
    private String  name;
    private String  mail;
    private String  userName;
    private String  password;
    private boolean active;

}
