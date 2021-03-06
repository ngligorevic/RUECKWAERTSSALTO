package rueckwaertssalto;

import java.util.ArrayList;
import java.util.Map;

import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.internal.ArgumentParserImpl;

public class Start {
	public static void main(String[] args) {
		ArgumentParser parser = new ArgumentParserImpl("Connect to RDBMS:",false)
		.defaultHelp(true)    
		.description("Checks arguments for connecting to a RDBMS");

		parser.addArgument("-c")
		.choices("ERD","RM")
		.setDefault("ERD")
		.help("Choice between ERD or RM");
		parser.addArgument("-h")
		.setDefault("localhost")
		.help("hostname");
		parser.addArgument("-u")// Default: User der eingeloggt ist.s
		.setDefault(System.getProperty("user.name"))
		.help("username");
		parser.addArgument("-p")
		.setDefault("")
		.help("password");
		parser.addArgument("-d").required(true)
		.help("datenbank");
		parser.addArgument("-o").required(true)
		.help("Output file");

		parser.addArgument("--help")
		.action(Arguments.help())//Hilfetext der Argumenten
		.setDefault(Arguments.SUPPRESS);

		Map<String,Object> arguments = null;
		try {
			Namespace res = parser.parseArgs(args);
			arguments =  res.getAttrs();
		} catch (ArgumentParserException e) {
			parser.handleError(e);
			System.exit(1);
		}

		Connection con = new Connection();
		con.connect(arguments.get("h").toString(), arguments.get("u").toString(), arguments.get("p").toString(),arguments.get("d").toString());
		Diagram d = new Diagram();
		if(arguments.get("c").toString().equals("RM")){
			if(d.getRM(con, arguments.get("d").toString(),arguments.get("o").toString())==true){
				System.out.println("RM saved in "+arguments.get("o").toString());
			}
		}else{
			if(d.getDotFile(con, arguments.get("d").toString(),"testinsy.dot")==true){
				d.Drawpng("testinsy.dot", "testinsy.png");
				System.out.println("ERD saved in "+arguments.get("o").toString());
				
			}
//				System.out.println("Dotfile saved in ERD.dot");
//				if(d.Drawpng("ERD.dot", arguments.get("o").toString())){
//					System.out.println("ERD saved in "+arguments.get("o").toString());
//				}
//			}else{
//				System.err.println("Something went wrong while generate ERD.dot");
//			}
		}

	}
}
