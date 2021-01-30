package dao;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T, PK> {
    // добавление в таблицу
    void add(T entity) throws SQLException;
    // обновление записи в таблице
    void update(T entity) throws SQLException;
    // удаление по первичному ключу
    void removeByPK(PK id) throws SQLException;
    // получение по первичному ключу
    T getByPK(PK id) throws SQLException;
    // получение всех записей
    List<T> getAll() throws SQLException;
}