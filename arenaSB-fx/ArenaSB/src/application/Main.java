package application;
	
import java.net.URL;
import java.sql.*;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.Stage;
import netscape.javascript.JSObject;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.concurrent.Worker.State;

public class Main extends Application {
	
	private static String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static String DB_URL = "67.205.191.64";
	
	private static String DB_USER = "root";
	private static String DB_PASS = "arenasb";
	
	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName(DB_DRIVER);
			
			conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);
			
		}catch(SQLException se) {
		      //Handle errors for JDBC
		      se.printStackTrace();
		}catch(Exception e) {
		      //Handle errors for Class.forName
		      e.printStackTrace();
		}
		
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		WebView browser = new WebView();
		WebEngine engine = browser.getEngine();
		
		URL index = getClass().getResource("/com/application/view/index.html");
		engine.load(index.toString());
		
		VBox root = new VBox();
		root.getChildren().addAll(browser);
		
		Scene scene = new Scene(root, 1200, 600);
		primaryStage.setScene(scene);
		
		primaryStage.show();
		
		// engine.executeScript(script) // <--- Execute javascript from JavaFX code
		
		// Creates a JSObject, and can execute functions in the JavaFX Code
		engine.getLoadWorker().stateProperty().addListener(
				new ChangeListener<State>() {
		            public void changed(ObservableValue ov, State oldState, State newState) {
		                if (newState == State.SUCCEEDED) {
			                	JSObject js = (JSObject) engine.executeScript("window"); 
			            		js.setMember("app", new Bridge());
		                }
		            }
		        });
		
	}
	
	public class Bridge {
//		This function takes in data from a JS function located on Create.html
		public void submitUser(String email, String nickname, String password, String passwordConfirmation) {
			System.out.println("Email: "+email+"\nNickname: "+nickname+"\nPassword: "+password+"\nPassword Confirmation: "+passwordConfirmation);
		}
	}
}

