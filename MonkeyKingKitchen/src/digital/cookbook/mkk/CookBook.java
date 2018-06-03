package digital.cookbook.mkk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.sql.*;

/**
 * The cookbook
 * @author Zifeng Zhang, Xinyue Shi
 * @version 2.0.1 28/5/2018
 *
 */
public class CookBook {
	
	private String cookBookTitle;
	private Map<Integer,Recipe> recipes;
	private ArrayList<User> users;
	private DBProcessor dbProcessor = new DBProcessor();
	private User currentUser;
	
	/**
	 * Constructor
	 * @param title
	 */
	public CookBook(String title) {
		this.cookBookTitle = title;
		recipes = dbProcessor.fetchRecipe();
		users = dbProcessor.fetchUserInfo();
	}
	
	/**
	 * Add recipe to the cookBook
	 * @param recipe
	 */
	public void add(Recipe recipe) {
		this.recipes.put(recipe.getRecipeId(), recipe);
		dbProcessor.insetRecipe(recipe,currentUser.getUid());
	}
	
	/**
	 * Use name to get the recipe with the same name
	 * @param name
	 * @return null if no result, otherwise return the name-matched recipes
	 */
	public Recipe getRecipe(String name) {
		Set recipeIds = recipes.keySet();
		for (Object recipeIdObj : recipeIds) {
			int id = (Integer)recipeIdObj;
			String recipeName = recipes.get(id).getName();
			if(recipeName.equals(name)) {
				return recipes.get(id);
			}
		}
		return null;
	}
	
	/**
	 * Use name to get the recipe with the same name
	 * @param name
	 * @return null if no result, otherwise return the name-matched recipes
	 */
	public Recipe searchByName(String name) {
		Set recipeIds = recipes.keySet();
		for (Object recipeIdObj : recipeIds) {
			int id = (Integer)recipeIdObj;
			String recipeName = recipes.get(id).getName();
			if(recipeName.equals(name)) {
				return recipes.get(id);
			}
		}
		return null;
	}
	
	/**
	 * Get the total recipes of the cook book.
	 * @return the recipes list
	 */
	public Map<Integer, Recipe> recipesList(){
		return this.recipes;
	}
	
	/**
	 * Change amount of preparation time, ingredients amount and cooking time according to servings
	 * @param recipeId
	 * @param Servings
	 * @return the altered recipe
	 */
	public Recipe changeServings(int recipeId, int servings) {
		Recipe recipe = recipes.get(recipeId);
		int originalServings = recipe.getServings();
		int preparationTime = servings * (recipes.get(recipeId).getPreparationTime()) / originalServings;
		int cookingTime = servings * (recipes.get(recipeId).getCookingTime()) / originalServings;
		recipe.setPreparationTime(preparationTime);
		recipe.setCookingTime(cookingTime);
		for (Object ingredientObj : recipe.getIngredients()) {
			double amount = servings * (((Ingredient)ingredientObj).getAmount()) / originalServings;
			((Ingredient)ingredientObj).setAmount(amount);
		}
		return recipe;
	}
	
	public void register(User user) {
		this.users.add(user);
		dbProcessor.insertUser(user);
	}
	
	public void login(String username,String passwd) {
		for (User user : users) {
			if(user.getName().equals(username) && user.getPasswd().equals(passwd))
				this.currentUser = user;
		}
	}
}
