package com.example.monolith.services;

import com.example.monolith.dto.teacherDto.TeacherResponse;
import com.example.monolith.utility.Exceptions.EmptyDatabaseException;
import com.example.monolith.utility.Exceptions.ObjectAlreadyExistException;
import com.example.monolith.utility.Exceptions.ObjectNotFoundException;

import java.util.List;

public interface TeacherService<T> {

    TeacherResponse get(Long id) throws ObjectNotFoundException;
    TeacherResponse save(T type) throws ObjectAlreadyExistException;
    TeacherResponse delete(Long id) throws ObjectNotFoundException;
    List<TeacherResponse> getAll() throws EmptyDatabaseException;
    TeacherResponse updateDegree(Long id,String degree) throws ObjectNotFoundException;




}
