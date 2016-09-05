package com.roms.library.dao;

/**
 * Implement isUnique() to use in validator
 */
public interface UniqueValidable {

    boolean isUnique(String fieldName, String value);

}
