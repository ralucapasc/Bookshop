package com.pitech.repositories;

import com.pitech.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Created by Pasc Raluca on 27.07.2017.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book>{
    Page<Book> findAllByCategoryId(Integer categoryId, Pageable page);
}
