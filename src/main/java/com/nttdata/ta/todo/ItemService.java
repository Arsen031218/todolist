package com.nttdata.ta.todo;



import java.util.List;

public interface ItemService<T>{
    Boolean addItem(T item);
    List<T> getAllItem();
    T getItem(Long id);

    Boolean deleteItem(T item);
    Boolean updateItem(T item);

}