import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * A program fő osztálya ahol lértehozzuk a szukséges objetumokat és <br>
 * elindítjuk az interpretert.<br>
 * @author prike, johnk
 *
 */
public class fel8 {

	/**
	 * A program main függvénye.<br>
	 * Itt állítjuk elő a szükséges objektumokat. <br>
	 * Előállítunk egy scannert amivel a felhasználó inputját tudjuk fogadni.<br>
	 * {@code Scanner scanner = new Scanner(System.in);}<br>
	 * Az antlr előállitja a lexert és a parsert illetve a tokeneket.<br>
	 * {@code fel8Lexer lexer = new fel8Lexer(input);}<br>
	 * {@code CommonTokenStream tokens = new CommonTokenStream(lexer);}<br>
	 * {@code fel8Parser parser = new fel8Parser(tokens);}<br>
	 * Létrehozunk egy Interpreter objektumot és meghivjuk a megfelelő parser és tree elemekkel.<br>
	 * {@code interp.interp(parser, tree);}<br>
	 * @param args 
	 */
	public static void main(String[] args) {
		Interp interp = new Interp();
		
		if( args.length == 0){
			Scanner scanner = new Scanner(System.in);
			while(true) {
				

				System.out.print("Enter something > ");

				String inputString = scanner.nextLine();

				ANTLRInputStream input = new ANTLRInputStream(inputString);

				fel8Lexer lexer = new fel8Lexer(input);

				CommonTokenStream tokens = new CommonTokenStream(lexer);

				fel8Parser parser = new fel8Parser(tokens);
				ParseTree tree = parser.r();



				if (parser.getNumberOfSyntaxErrors() == 0) {
					try {
						interp.interp(parser, tree);

					} catch (Exception e) {

//						for (StackTraceElement ste : e.getStackTrace()) {
//							System.out.println(ste);
//						}
						System.out.println(e.getClass().getSimpleName());
						System.out.println(e.getMessage());
					}	
				}
				else
					System.out.println(parser.getNumberOfSyntaxErrors());


			}


		}

		if( args[0].equals("-o") && args.length == 2){
			System.out.println("-oban");
			try{

				
				FileReader fileReader = new FileReader(new File(args[1]));
				BufferedReader br = new BufferedReader(fileReader);
				String line = null;
				//Scanner scanner = new Scanner(new File(args[1]));
				
				
				
				while ((line = br.readLine()) != null) {
					//String line = scanner.nextLine();
					ANTLRInputStream input = new ANTLRInputStream(line);

					fel8Lexer lexer = new fel8Lexer(input);

					CommonTokenStream tokens = new CommonTokenStream(lexer);

					fel8Parser parser = new fel8Parser(tokens);
					ParseTree tree = parser.r();

					if (parser.getNumberOfSyntaxErrors() == 0) {
						try {
							interp.interp(parser, tree);

						} catch (Exception e) {

							for (StackTraceElement ste : e.getStackTrace()) {
								System.out.println(ste);
							}
							System.out.println(e.getClass().getSimpleName());
							System.out.println(e.getMessage());
						}	
					}
					else
						System.out.println(parser.getNumberOfSyntaxErrors());


				}
				

				br.close();

			}catch (Exception e) {
				e.getMessage();
			}


		}
		if(args.length > 2 || args.length == 1){
			System.out.println("usage:java -jar fordprog.jar -o filename");
		}

	} 
}