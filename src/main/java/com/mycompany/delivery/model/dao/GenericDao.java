package com.mycompany.delivery.model.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T, ID> extends AutoCloseable {
    void save(T entity);
    Optional<T> findById(ID id);
    List<T> findAll();
    List<T> findPaginated(int page, int pageSize, String sortField, String sortDirection);
    ID getNumberOfRows();
    void update(T entity);
    void delete(ID id);
    void close();
}
