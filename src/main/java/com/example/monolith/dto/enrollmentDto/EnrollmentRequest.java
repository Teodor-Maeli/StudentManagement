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
public class EnrollmentRequest {
    private Student students;
    private Course course;
    private List<Double> grades;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnrollmentRequest that = (EnrollmentRequest) o;
        return students.equals(that.students) && course.equals(that.course) && grades.equals(that.grades);
    }

    @Override
    public int hashCode() {
        return Objects.hash(students, course, grades);
    }
}
