package digital.cookbook.mkk.MyRecipeView;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.lang.ModuleLayer.Controller;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import digital.cookbook.mkk.CookBook;
import digital.cookbook.mkk.DBProcessor;
import digital.cookbook.mkk.Recipe;
import digital.cookbook.mkk.User;
import digital.cookbook.mkk.EditPage.EditViewController;
import digital.cookbook.mkk.MainPageView.MainPageController;
import digital.cookbook.mkk.RecipeView.RecipeViewController;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * Controller for my recipe view
 * @author Zhibin Xin, Zifeng Zhang
 *
 */
public class MyRecipeController implements Initializable {
	
	private ArrayList<Recipe> myRecipes = new ArrayList<>();
	private DBProcessor dBProcessor = new DBProcessor();
	private Map<Button, HBox> itemAccess = new HashMap<>();
	private Map<HBox, Recipe> recipeAccess = new HashMap<>();
	private User currentUser = CookBook.getCurrentUser();
	
	@FXML
	private Pane mainPane;
	@FXML
	private Text myFavoriteLabel;
	@FXML
	private Button deleteButton;
	@FXML
	private Button openButton;
	@FXML
	private Pane rightPane;
	@FXML
	private Label userNameLabel;
	@FXML
	private Button myRecipeButton;
	@FXML
	private Button mainPageButton;
	@FXML
	private Label mkkLogoLabel;
	@FXML
	private Button editButton;
	@FXML
	private Button createButton;
	@FXML
	private VBox listVBox;
	@FXML
	private HBox item;
	@FXML
	private Label itemName;
	@FXML
	private Label itemDesc;
	@FXML
	private Button itemOpenBtn;
	@FXML
	private Button itemDeleteBtn;
	@FXML
	private VBox buttonItem;
	@FXML
	private Button itemEditBtn;
	@FXML
	private ScrollPane scrollPane;


	/**
	 * Jump to my favorite recipe list
	 * @param event ActionEvent
	 */
	@FXML
	public void jumpToMyFavoriteEvent(ActionEvent event) {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("../MyFavoriteView/MyFavoriteView.fxml"));
		try {
			Parent parent = fxmlLoader.load();
			Scene scene = new Scene(parent);
			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			currentStage.setScene(scene);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * Jump to main page
	 * @param event ActionEvent
	 */
	@FXML
	public void jumpToMainPageEvent(ActionEvent event) {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("../MainPageView/MainPageView.fxml"));
		try {
			Parent parent = fxmlLoader.load();
			MainPageController controller = fxmlLoader.getController();
			controller.setUserTag(currentUser.getName());
			
			Scene scene = new Scene(parent);
			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			currentStage.setScene(scene);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * Jump to edit page view to create new recipe
	 * 
	 * @param event ActionEvent
	 */
	@FXML
	public void createEvent(ActionEvent event) {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("../EditPage/EditPageView.fxml"));
		try {
			Parent parent = fxmlLoader.load();
			Scene scene = new Scene(parent);
			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			currentStage.setScene(scene);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	/**
	 * setup recipe list
	 * 
	 * @param myRecipes the list of the user's recipes
	 */
	public void setMyRecipeList(ArrayList<Recipe> myRecipes) {
		
		// Add all the items(hbox = name label + description label + button) to the Map
		for (Recipe recipe : myRecipes) {
			itemName = new Label(recipe.getName());
			itemDesc = new Label(recipe.getType() + ", Cooking time: " + recipe.getCookingTime() + ", Preparing time: "
					+ recipe.getPreparationTime() + ", Servings: " + recipe.getServings());
			itemOpenBtn = new Button("OPEN");
			itemDeleteBtn = new Button("DELETE");
			itemEditBtn = new Button("EDIT");

			item = new HBox();
			item.setStyle("-fx-pref-height:85;");
			buttonItem = new VBox();
			item.getChildren().add(itemName);
			item.getChildren().add(itemDesc);
			buttonItem.getChildren().add(itemOpenBtn);
			buttonItem.getChildren().add(itemDeleteBtn);
			buttonItem.getChildren().add(itemEditBtn);
			
			item.getChildren().add(buttonItem);
			listVBox.getChildren().add(item);


			// Register the listener for all the buttons (open, edit and delete)
			// Open button
			itemOpenBtn.setOnAction(e -> {
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(getClass().getResource("../RecipeView/RecipeView.fxml"));
				CookBook.setCurrentRecipe(recipe);
				System.out.println(CookBook.getCurrentRecipe().getName());

				try {
					Scene scene = new Scene(fxmlLoader.load());
					RecipeViewController controller = fxmlLoader.getController();
					controller.setRecipeDetail(CookBook.getCurrentRecipe());
					Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
					currentStage.setScene(scene);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			});

			// Edit Button
			itemEditBtn.setOnAction(e -> {

				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(getClass().getResource("../EditPage/EditPageView.fxml"));
				CookBook.setCurrentRecipe(recipe);
				try {
					Parent parent = fxmlLoader.load();
					EditViewController controller = fxmlLoader.getController();
					controller.setRecipeDetails(recipe);
					
					Scene scene = new Scene(parent);
					Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
					currentStage.setScene(scene);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			
			itemAccess.put(itemDeleteBtn, item);
			recipeAccess.put(item, recipe);
		}
	}

	/**
	 * Grab the user's recipes
	 * listener for delete btns for all the items in the list
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//Get the user's own recipes
		Collection<Recipe> allRecipes = dBProcessor.fetchRecipe().values();
		for (Recipe recipe : allRecipes) {
			if(recipe.getUid() == currentUser.getUid())
				myRecipes.add(recipe);
		}
		
		//set up the list
		setMyRecipeList(myRecipes);
		
		//Add delete listener to the delete button
		for (Button itemDeleteBtn : itemAccess.keySet()) {
			HBox item = itemAccess.get(itemDeleteBtn);
			Recipe recipe = recipeAccess.get(item);
			itemDeleteBtn.setOnAction(e -> {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText("The recipe will be deleted. Do you want to proceed?");
				alert.setHeaderText("Warning");
				alert.showAndWait().ifPresent(response -> {
					if(response == ButtonType.OK) {
						listVBox.getChildren().remove(item);
						myRecipes.remove(recipe);
						dBProcessor.deleteRecipe(recipe.getRecipeId());
					}
				});
			});
		}
	}


}
