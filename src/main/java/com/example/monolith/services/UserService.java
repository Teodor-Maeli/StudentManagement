package com.example.monolith.services;

import com.example.monolith.utility.Exceptions.ObjectAlreadyExistException;

public interface UserService<T,T1> {

    String createStudentAccount(T type) throws ObjectAlreadyExistException;
    String createTeacherAccount(T1 type) throws ObjectAlreadyExistException;
}
