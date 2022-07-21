package com.example.monolith.services.impl;

import com.example.monolith.dto.teacherDto.TeacherRequest;
import com.example.monolith.dto.teacherDto.TeacherResponse;
import com.example.monolith.entity.Teacher;
import com.example.monolith.utility.Exceptions.EmptyDatabaseException;
import com.example.monolith.utility.Exceptions.ObjectAlreadyExistException;
import com.example.monolith.utility.Exceptions.ObjectNotFoundException;
import com.example.monolith.mapper.Impl.TeacherMapperImpl;
import com.example.monolith.repository.TeacherRepository;
import com.example.monolith.services.TeacherService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.monolith.utility.enums.ExceptionMessage.*;

@Service
@AllArgsConstructor
public class TeacherServiceImpl implements TeacherService<TeacherRequest> {

    TeacherRepository teacherRepository;
    TeacherMapperImpl teacherMapper;

    @Override
    public TeacherResponse get(Long id) throws ObjectNotFoundException {
        if (teacherRepository.existsById(id)) {
            Teacher teacher = teacherRepository.findById(id).get();
            return teacherMapper.teacherEntityToTeacherResponse(teacher);
        } else throw new ObjectNotFoundException(NOT_EXIST.getExceptionMessage());
    }

    @Override
    public List<TeacherResponse> getAll() throws EmptyDatabaseException {
        List<Teacher> teachers = teacherRepository.findAll().stream()
                .filter(teacher -> !teacher.getUserName().equals("admin"))
                .collect(Collectors.toList());
        if (teachers.size() != 0) {
            return teacherMapper.AllEntityToAllResponse(teachers);
        } else throw new EmptyDatabaseException(EMPTY.getExceptionMessage());
    }

    @Override
    public TeacherResponse delete(Long id) throws ObjectNotFoundException {
        if (teacherRepository.existsById(id)) {
            Teacher teacher = teacherRepository.findById(id).get();
            teacherRepository.delete(teacher);
            return teacherMapper.teacherEntityToTeacherResponse(teacher);
        } else throw new ObjectNotFoundException(NOT_ASSIGNED.getExceptionMessage());

    }

    @Override
    public TeacherResponse save(TeacherRequest request) throws ObjectAlreadyExistException {
        Teacher teacher = teacherMapper.teacherRequestToTeacherEntity(request);
        if (!teacherRepository.existsByNameAndAndDegree(request.getName(), request.getDegree())) {
            teacherRepository.save(teacher);
            return teacherMapper.teacherEntityToTeacherResponse(teacher);
        } else throw new ObjectAlreadyExistException(EXIST.getExceptionMessage());
    }

    @Override
    public TeacherResponse updateDegree(Long id, String degree) throws ObjectNotFoundException {
        if (teacherRepository.existsById(id)) {
            Teacher teacher = teacherRepository.findById(id).get();
            teacher.setDegree(degree);
            teacherRepository.save(teacher);
            return teacherMapper.teacherEntityToTeacherResponse(teacher);
        } else throw new ObjectNotFoundException(NOT_EXIST.getExceptionMessage());

    }


}
