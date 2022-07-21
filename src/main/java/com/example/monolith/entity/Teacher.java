package com.example.monolith.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Teacher implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String degree;
    private int age;
    @Column(unique = true)
    @NotNull
    private String userName;
    @NotNull
    private String password;
    private String roles;
    private boolean active;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return name.equals(teacher.name) && degree.equals(teacher.degree);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, degree);
    }
}
