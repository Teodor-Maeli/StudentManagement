package com.example.monolith.dto.enrollmentDto;

import com.example.monolith.entity.Course;
import com.example.monolith.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class EnrollmentResponse {

    private Long id;
    private Student students;
    private Course course;
    private List<Double> grades;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnrollmentResponse that = (EnrollmentResponse) o;
        return students.equals(that.students) && course.equals(that.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(students, course);
    }
}
