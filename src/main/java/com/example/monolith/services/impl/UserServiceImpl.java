package com.example.monolith.services.impl;

import com.example.monolith.services.UserService;
import com.example.monolith.dto.studentDto.StudentRequest;
import com.example.monolith.dto.teacherDto.TeacherRequest;
import com.example.monolith.entity.Student;
import com.example.monolith.entity.Teacher;
import com.example.monolith.utility.enums.ExceptionMessage;
import com.example.monolith.utility.Exceptions.ObjectAlreadyExistException;
import com.example.monolith.mapper.Impl.StudentMapperImpl;
import com.example.monolith.mapper.Impl.TeacherMapperImpl;
import com.example.monolith.repository.StudentRepository;
import com.example.monolith.repository.TeacherRepository;
import com.example.monolith.securityConfig.AuthUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.monolith.utility.enums.ExceptionMessage.ALREADY_REGISTERED;
import static com.example.monolith.utility.enums.ExceptionMessage.NOT_REGISTERED;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService<StudentRequest,TeacherRequest> {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    private final StudentMapperImpl studentMapper;

    private final TeacherMapperImpl teacherMapper;



    @Override
    public AuthUser loadUserByUsername(String username) throws UsernameNotFoundException {

        if (studentRepository.existsByUserName(username)) {
            Optional<Student> user = Optional.of(studentRepository.findByUserName(username).get());
            return user.map(AuthUser::new).get();
        } else if (teacherRepository.existsByUserName(username)) {
            Optional<Teacher> user = Optional.of(teacherRepository.findByUserName(username).get());
            return user.map(AuthUser::new).get();
        } else throw new UsernameNotFoundException(NOT_REGISTERED.getExceptionMessage());
    }



    public String createStudentAccount(StudentRequest student) throws ObjectAlreadyExistException {
        Student user = studentMapper.studentRequestToStudentEntity(student);
        if (!studentRepository.existsByUserName(user.getUserName()) && !studentRepository.existsByName(user.getName())) {
            studentRepository.save(user);
            return user.getUserName();
        } else throw new ObjectAlreadyExistException(ALREADY_REGISTERED.getExceptionMessage());

    }


    public String createTeacherAccount(TeacherRequest teacher) throws ObjectAlreadyExistException {
        Teacher user = teacherMapper.teacherRequestToTeacherEntity(teacher);
        if (!teacherRepository.existsByUserName(user.getUserName()) && !teacherRepository.existsByName(user.getName())) {
            teacherRepository.save(user);
            return user.getUserName();
        } else throw new ObjectAlreadyExistException(ALREADY_REGISTERED.getExceptionMessage());

    }


}
