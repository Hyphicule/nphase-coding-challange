package com.nphase.service;


import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

public class ShoppingCartServiceTest {
	private final ShoppingCartService service = new ShoppingCartService();

	@Test
	public void calculatesPriceForBasicScenario()  {
		ShoppingCart cart = new ShoppingCart(Arrays.asList(
				new Product("Tea", BigDecimal.valueOf(5.0), 2, "Drink"),
				new Product("Coffee", BigDecimal.valueOf(6.5), 1, "Drink")
		));

		BigDecimal result = service.calculateTotalPrice(cart);

		Assertions.assertEquals(result, BigDecimal.valueOf(16.5));
	}

	// Task 3
	@Test
	public void calculatesPriceWhenMoreThan3ItemsFromGivenCategoryAreInShoppingCart()  {

		ShoppingCart cart = new ShoppingCart(Arrays.asList(
				new Product("tea", BigDecimal.valueOf(5.3), 2, "drinks"),
				new Product("coffee", BigDecimal.valueOf(3.5), 2, "drinks"),
				new Product("cheese", BigDecimal.valueOf(8), 2, "food")));

		BigDecimal result = service.calculateTotalPrice(cart);

		Assertions.assertEquals(result, BigDecimal.valueOf(31.84));
	}
}
