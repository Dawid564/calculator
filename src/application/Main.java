package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;


public class Main extends Application {
	private double MIN_BUTTON_HEIGHT = 35.0;
	private double MIN_BUTTON_WIDTH = 45.0;
	private double MIN_DISPLAY_HEIGHT = 40.0;
	private double MIN_DISPLAY_WIDTH = 240.0;
	
	private Button btn[][] = new Button[4][4];
	private Button textButton = new Button();
	private Double tempCarryFirstNumber = 0.0;
	private Double tempCarrySecondNumber = 0.0;
	private String tempCarrySymbol = null;
	
	private solver solv = new solver();
	
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Kalulator");
			GridPane root = new GridPane();
			BorderPane displayCorector = new BorderPane();
			GridPane rootMain = new GridPane();
			
			Scene scene = new Scene(rootMain,280,295);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			createBtn();
			root.setHgap(20);
			root.setVgap(20);

			textButton.setDisable(true);
			textButton.setMinHeight(MIN_DISPLAY_HEIGHT);
			textButton.setMinWidth(MIN_DISPLAY_WIDTH);
			displayCorector.setTop(textButton);
			BorderPane.setMargin(textButton, new Insets(15,0,0,20));
			
			GridPane.setConstraints(root, 0, 1);
			GridPane.setConstraints(displayCorector, 0, 0);
			
			rootMain.getChildren().addAll(displayCorector, root);
			
			for(int i=0; i<4; i++){
				for(int j=0; j<4; j++){
					//j - columns
					//i - rows
					GridPane.setConstraints(btn[j][i], j+1, i+1);
					root.getChildren().add(btn[j][i]);
				}
			}
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private void createBtn(){
		String[] symbolsRight = {"*","/","+","-"};
		String[] symbolsBottom = {"C","0","="};
		
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
				if(i==0){
					btn[j][i] = new Button("" + (j + 1));
					btn[j][i].setMinHeight(MIN_BUTTON_HEIGHT);
					btn[j][i].setMinWidth(MIN_BUTTON_WIDTH);
					BtnLogic(btn[j][i], j + 1);
				}else if(i==1){
					btn[j][i] = new Button("" + (j + 4));
					btn[j][i].setMinHeight(MIN_BUTTON_HEIGHT);
					btn[j][i].setMinWidth(MIN_BUTTON_WIDTH);
					BtnLogic(btn[j][i], j + 4);
				}else{
					btn[j][i] = new Button("" + (j + 7));
					btn[j][i].setMinHeight(MIN_BUTTON_HEIGHT);
					btn[j][i].setMinWidth(MIN_BUTTON_WIDTH);
					BtnLogic(btn[j][i], j + 7);
				}
			}
		}
		
		for(int k=0; k<4; k++){
			btn[3][k].setText(symbolsRight[k]);
			btnOperatorLogic(btn[3][k], symbolsRight[k]);
		}
		
		for(int l=0; l<3; l++){
			btn[l][3].setText(symbolsBottom[l]);
			setBottomOperator(btn[l][3], symbolsBottom[l]);
		}
	}
	
	private void BtnLogic(Button btn, int var){
		btn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				textButton.setText("" + var);
				tempCarryFirstNumber = (double)var;
			}
		});
	}
	
	private void btnOperatorLogic(Button btn, String var){
		btn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				textButton.setText("" + var);
				tempCarrySecondNumber = tempCarryFirstNumber;
				tempCarrySymbol = var;
			}
		});
	}
	
	private void setBottomOperator(Button btn, String var){
		btn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				switch(var){
				case "C":
					textButton.setText("CLEAR");
					tempCarryFirstNumber = 0.0;
					tempCarrySecondNumber = 0.0;
					tempCarrySymbol = null;
					break;
				
				case "0":
					BtnLogic(btn, 0);
					break;
				
				case "=":
					if(tempCarrySymbol.equals("+")){
						textButton.setText("" + solv.add(tempCarryFirstNumber, tempCarrySecondNumber));
					}else if(tempCarrySymbol.equals("-")){
						textButton.setText("" + solv.substract(tempCarryFirstNumber, tempCarrySecondNumber));
					}else if(tempCarrySymbol.equals("*")){
						textButton.setText("" + solv.multiply(tempCarryFirstNumber, tempCarrySecondNumber));
					}else{
						textButton.setText("" + solv.divide(tempCarryFirstNumber, tempCarrySecondNumber));
					}
					
					break;
				
				default:
					break;
				}
				
			}
		});
	}
	
}
