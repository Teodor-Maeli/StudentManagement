package com.example.monolith.dto.courseDto;

import com.example.monolith.entity.Teacher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CourseRequest {

    public String name;
    private int duration;
    private Teacher teacher;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseRequest that = (CourseRequest) o;
        return duration == that.duration && name.equals(that.name) && teacher.equals(that.teacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, duration, teacher);
    }
}
