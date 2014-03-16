package importer;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.xml.sax.SAXException;


/**
 * @author Joseph Karras
 */
public class Exporter {

	public static void main(String[] args) {
		Options options = new Options();
		String name = "";
		
		options.addOption("n", "names", false, "Poi must have name to be exported in csv ." );
		
		options.addOption(OptionBuilder.withLongOpt("input")
										.withDescription("file input")
										.isRequired(true)
										.hasArg()
										.withArgName("INPUT")
										.create());
		CommandLineParser parser = new BasicParser();
		CommandLine line;
		try {
			line = parser.parse( options, args );
			if(line.hasOption("input")) {
				name = line.getOptionValue("input");
			}
			
			SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
			SAXParser saxParser = saxParserFactory.newSAXParser();
			POIHandler handler = new POIHandler();
			saxParser.parse(new File("data/"+name+""), handler);
			List<POI> poiList = handler.getPOIList();
			PrintWriter writer = new PrintWriter("./data/"+name+".csv", "UTF-8");
			int entries = 0;
			for(POI poi : poiList) {
				String row = poi.cvsRow(line.hasOption("names"));
				if(row != null) {
					writer.println(row);
					entries++;
				}
			}
			writer.close();
			System.out.println("File " + name + ".csv got " + entries);
		}
		catch(org.apache.commons.cli.ParseException exp) {
			System.err.println("Try it now with --input" );
		}
		catch(SAXException | IOException | ParserConfigurationException e) {
			System.err.println("It works on my machine");
			e.printStackTrace();
		}
	}
}
