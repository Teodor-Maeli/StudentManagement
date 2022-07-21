package com.example.monolith.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Enrollment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @ManyToOne(targetEntity = Student.class, cascade = CascadeType.MERGE)
    @JsonIgnore
    @JoinColumn(name="student_id")
    private Student student;


    @ManyToOne(targetEntity = Course.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "course_id")
    private Course course;


    @ElementCollection
    private List<Double> grades;


}
