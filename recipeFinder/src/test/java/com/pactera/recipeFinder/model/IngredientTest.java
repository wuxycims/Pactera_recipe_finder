package com.pactera.recipeFinder.model;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

public class IngredientTest {

	@Test
	public void test() {
		final String item = "MyIngredient";

		final int amount = 220;

		final Unit unit = Unit.slices;

		final Date useBy = new Date();

		Ingredient Ingredient = new Ingredient(item, amount, unit, useBy);

		Assert.assertEquals(item, Ingredient.getItem());
		Assert.assertEquals(amount, Ingredient.getAmount());
		Assert.assertEquals(unit, Ingredient.getUnit());
		Assert.assertEquals(useBy, Ingredient.getUseBy());
	}

}
