package com.nphase.service;

import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShoppingCartService {

	private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

	/*
		Normally in a web-app it could be either stored & fetched from DB or loaded from application.properties

	 */
	private static final long AMOUNT_OF_ITEMS_DISCOUNT_THRESHOLD = 3;
	private static final long DISCOUNT_PERCENTAGE = 10;

	public BigDecimal calculateTotalPrice(ShoppingCart shoppingCart) {

		Map<String, List<Product>> productsByCategory = shoppingCart.getProducts()
				.stream()
				.collect(Collectors.groupingBy(Product::getCategory));

		return productsByCategory.values()
				.stream()
				.map(this::calculateCategorySum)
				.reduce(BigDecimal::add)
				.orElse(BigDecimal.ZERO);
	}

	private BigDecimal calculateCategorySum(List<Product> products) {
		BigDecimal categorySum = products.stream()
				.map(product -> product.getPricePerUnit().multiply(BigDecimal.valueOf(product.getQuantity())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		return products.stream().mapToInt(Product::getQuantity).sum() > AMOUNT_OF_ITEMS_DISCOUNT_THRESHOLD ?
				applyDiscount(categorySum, DISCOUNT_PERCENTAGE) :
				categorySum;
	}

	private BigDecimal applyDiscount(BigDecimal sum, long percentage) {
		return sum.subtract(sum.multiply(BigDecimal.valueOf(percentage).divide(ONE_HUNDRED)))
				.setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();
	}
}
