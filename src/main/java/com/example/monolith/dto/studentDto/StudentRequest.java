package com.example.monolith.dto.studentDto;

import com.example.monolith.entity.Enrollment;
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
public class StudentRequest {


    private String name;
    private Integer age;
    private String username;
    private String password;
    private List<Enrollment> enrollments;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentRequest that = (StudentRequest) o;
        return age == that.age && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
