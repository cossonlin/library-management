package com.cosson.library.librarymanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Borrow {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private Long bookId;
	@Column
	private String userId;
	@Column
	private LocalDate borrowDate;
	@Column
	private LocalDate expiryDate;
	@Column
	private LocalDate returnDate;
}
