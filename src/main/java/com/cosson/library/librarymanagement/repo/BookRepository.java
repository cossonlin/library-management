package com.cosson.library.librarymanagement.repo;

import com.cosson.library.librarymanagement.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
}
