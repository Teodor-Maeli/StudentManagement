package com.example.monolith.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Student implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String name;
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

    @OneToMany(mappedBy = "student")
    @JsonIgnore
    private List<Enrollment> enrollments;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return age == student.age && name.equals(student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
