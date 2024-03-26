package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
//@Table(name = "order")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserOrder implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty
	@Column(updatable = false, unique = true)
	private Long id;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JsonProperty
	@Column
    private List<Item> items;

	@ManyToOne
	@JoinColumn(name="user_id", nullable = false, referencedColumnName = "id")
	@JsonProperty
    private User user;
	
	@JsonProperty
	@Column
	private BigDecimal total;

	public static UserOrder createFromCart(Cart cart) {
		UserOrder userOrder = new UserOrder();
		userOrder.setItems(cart.getItems());
		userOrder.setTotal(cart.getTotal());
		userOrder.setUser(cart.getUser());
		return userOrder;
	}
	
}
