package com.roms.library.dao;

import java.util.List;

public interface GenericDaoInterface<T> {

    T save(T emp);
    Boolean delete(T emp);
    T edit(T emp);
    T find(Long empId);
    List<T> findAll();

}
