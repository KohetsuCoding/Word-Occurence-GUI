package application;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends Application{
	
	Button button1;
	Button button2;
	
	public static void main(String[] args) throws IOException {
		launch(args);
	} //End Main

	//Methods
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Word Frequency Counter");
			button1 = new Button();
			button1.setText("Calculate Word Frequency");
			
			//This class handles button events
			button1.setOnAction(e -> {
				System.out.println("Calculating word frequency...");
				
				String text = null;
				
				try  {
					String fileName = "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm";
					URL url = new URL(fileName);
					URLConnection conn = url.openConnection();
					LineNumberReader rdr = new LineNumberReader(new InputStreamReader(conn.getInputStream()));
					StringBuilder sb1 = new StringBuilder();
					
					for (text = null; (text = rdr.readLine()) !=null;) {
						if (rdr.getLineNumber() > 11 & rdr.getLineNumber() < 140) {
							sb1.append(text).append(File.pathSeparatorChar);
						} else if (rdr.getLineNumber() > 140) {
							break;
						}
					}
					String textLC = sb1.toString().toLowerCase();
					Pattern pttrn = Pattern.compile("[a-z]+");
					Matcher mtchr = pttrn.matcher(textLC);
					TreeMap<String, Integer> freq = new TreeMap<>();
					int longest = 0;
					
					while (mtchr.find()) {
						String word = mtchr.group();
						int letters = word.length();
						
						if (letters > longest) {
							longest = letters;
						}
						if (freq.containsKey(word)) {
							freq.computeIfPresent(word,  (w, c) -> Integer.valueOf(c.intValue() +1));
						}
						else {
							freq.computeIfAbsent(word,  (w) -> Integer.valueOf(1));
						}
					} //End While Loop
					String format = "%-" + longest + "s = %2d%n";
					freq.forEach((k, v) -> System.out.printf(format,  k, v));
					rdr.close();
				
				} catch (MalformedURLException ee) {
					ee.printStackTrace();
				}
				  catch (IOException eee) {
					eee.printStackTrace();
				} 
			}); //End Button1 Action
			
			button2 = new Button();
			button2.setText("Exit Program");
			button2.setOnAction((ActionEvent event) -> {
				Platform.exit();
			});
			
			HBox layout = new HBox();
			layout.setAlignment(Pos.CENTER);
			layout.setPadding(new Insets(15, 12, 15, 12));
			layout.setSpacing(10);
			layout.setStyle("=fx-background-colo: #336699;");
			
			layout.getChildren().add(button1);
			layout.getChildren().add(button2);
			Scene scene = new Scene(layout,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
} //End Class
