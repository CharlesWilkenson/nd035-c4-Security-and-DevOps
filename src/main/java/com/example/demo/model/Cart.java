package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
//@Table(name = "cart")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString(callSuper = true)
public class Cart implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty
	@Column(updatable = false, unique = true)
	private Long id;
	
	@ManyToMany
	@JsonProperty
	@Column
    private List<Item> items;

	@OneToOne(mappedBy = "cart")
	@JsonProperty
    private User user;
	
	@Column
	@JsonProperty
	private BigDecimal total;

	public void addItem(Item item) {
		if(items == null) {
			items = new ArrayList<>();
		}
		items.add(item);
		if(total == null) {
			total = new BigDecimal(0);
		}
		total = total.add(item.getPrice());
	}
	
	public void removeItem(Item item) {
		if(items == null) {
			items = new ArrayList<>();
		}
		items.remove(item);
		if(total == null) {
			total = new BigDecimal(0);
		}
		total = total.subtract(item.getPrice());
	}
}
