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
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String ibsn;
	@Column
	private String name;
	@Column
	private String author;
	@Column
	private LocalDate publishDate;
	@Column
	private String summary;
}
