package com.pactera.recipeFinder;

import java.io.File;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.pactera.recipeFinder.model.Recipe;
import com.pactera.recipeFinder.parser.RecipeParser;

public class MatchResultTest {

	@Test
	public void testMatchResult() {
		List<Recipe> recipes = RecipeParser.produceRecipes(new File(getClass().getResource("/recipe.json").getFile()));
		Assert.assertNotNull(recipes);
		Assert.assertEquals(2, recipes.size());

		MatchResult matchResult = new MatchResult(recipes.get(1));
		Assert.assertNotNull(matchResult.getRecipe());
		Assert.assertEquals("salad sandwich", matchResult.getRecipe().getName());

		matchResult.addExpiredTime(4200L);
		matchResult.addExpiredTime(0L);
		matchResult.addExpiredTime(4565323L);
		matchResult.addExpiredTime(12L);

		Assert.assertEquals(4, matchResult.getExpiredTimes().size());
		Assert.assertEquals(4565323L, matchResult.getExpiredTimes().get(2).longValue());

		matchResult.sortExpiredTimes();
		Assert.assertEquals(4200L, matchResult.getExpiredTimes().get(2).longValue());
	}

}
