package com.cosson.library.librarymanagement.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class User {
	@Id
	@Column(unique = true)
	private String userId;
	@Column
	private String userName;
}
