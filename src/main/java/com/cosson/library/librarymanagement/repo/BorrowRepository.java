package com.cosson.library.librarymanagement.repo;

import com.cosson.library.librarymanagement.entity.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, Long> {
	int countByUserIdAndReturnDateNull(String userId);

	boolean existsByBookIdAndReturnDateNull(long bookId);

	Optional<Borrow> findTopByBookIdAndUserIdAndReturnDateNull(long bookId, String userId);
}
