package com.example.monolith.mapper.Impl;

import com.example.monolith.dto.teacherDto.TeacherRequest;
import com.example.monolith.dto.teacherDto.TeacherResponse;
import com.example.monolith.entity.Teacher;
import com.example.monolith.mapper.TeacherMapper;
import com.example.monolith.repository.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.monolith.utility.enums.Authorities.ADMIN;
import static com.example.monolith.utility.enums.Authorities.TEACHER;

@Component
@AllArgsConstructor
public class TeacherMapperImpl extends BaseMapper implements TeacherMapper<Teacher, TeacherRequest>, CommandLineRunner {

    TeacherRepository teacherRepository;

    @Override
    public TeacherResponse teacherEntityToTeacherResponse(Teacher teacher) {
        return TeacherResponse.builder()
                .id(teacher.getId())
                .degree(teacher.getDegree())
                .name(teacher.getName())
                .age(teacher.getAge())
                .build();
    }


    public List<TeacherResponse> AllEntityToAllResponse(List<Teacher> teachers) {
        List<TeacherResponse> responses = teachers.stream().map(this::teacherEntityToTeacherResponse).toList();
        return responses;
    }

    @Override
    public Teacher teacherRequestToTeacherEntity(TeacherRequest teacher) {
        return Teacher.builder()
                .name(teacher.getName())
                .degree(teacher.getDegree())
                .age(teacher.getAge())
                .userName(teacher.getUsername())
                .password(passwordEncoder().encode(teacher.getPassword()))
                .active(true)
                .roles(TEACHER.name())
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .build();

    }


    @Override
    public void run(String... args) throws Exception {
        if(teacherRepository.findAll().isEmpty()){
            teacherRepository.save(Teacher.builder()
                    .name("admin")
                    .degree("admin")
                    .age(999)
                    .userName("admin")
                    .password(passwordEncoder().encode("admin"))
                    .active(true)
                    .roles(ADMIN.name())
                    .isAccountNonExpired(true)
                    .isAccountNonLocked(true)
                    .isCredentialsNonExpired(true)
                    .isEnabled(true)
                    .build());
        }
    }
}
