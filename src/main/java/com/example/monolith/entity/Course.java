package com.example.monolith.entity;


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
public class Course implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String name;
    private int duration;

    @ManyToOne(targetEntity = Teacher.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "Name")
    private Teacher teacher;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return duration == course.duration && name.equals(course.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, duration);
    }
}
