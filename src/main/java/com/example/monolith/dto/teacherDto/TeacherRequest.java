package com.example.monolith.dto.teacherDto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class TeacherRequest {

    private String name;
    private Integer age;
    private String username;
    private String password;
    private String degree;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeacherRequest that = (TeacherRequest) o;
        return name.equals(that.name) && degree.equals(that.degree);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, degree);
    }
}
