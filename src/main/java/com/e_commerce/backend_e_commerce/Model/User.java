package com.e_commerce.backend_e_commerce.Model;

import java.util.List;

import org.hibernate.annotations.JoinColumnOrFormula;

import com.e_commerce.backend_e_commerce.Model.Enums.Role;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
@EqualsAndHashCode(exclude = "cart")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(max = 100)
	private String name;
	
	@Email
	@NotNull
	private String email;
	
	@NotNull
	private String password;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@OneToOne(mappedBy = "user" ,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	
	private Cart cart;
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Order> orders;

}
