package com.example.monolith.dto.courseDto;

import com.example.monolith.entity.Teacher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CourseResponse {

    public Long id;
    public String name;
    public int duration;
    private Teacher teacher;


}
