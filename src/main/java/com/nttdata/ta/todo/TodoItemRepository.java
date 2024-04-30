package com.nttdata.ta.todo;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
public interface TodoItemRepository extends JpaRepository<TodoItem,Long> {

}