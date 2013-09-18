package cscie97.asn1.test;

/**
 * Created with IntelliJ IDEA.
 * User: dkilleffer
 * Date: 9/8/13
 * Time: 12:36 PM
 * To change this template use File | Settings | File Templates.
 */

import cscie97.asn1.knowledge.engine.*;
import cscie97.asn1.knowledge.engine.exception.ParseException;

public class TestDriver {

    /**
     * Implement a test driver class called TestDriver that implements a static main() method.
     * The main() method should accept 2 parameters, an input Triple file, and an Input Query file.
     * The main method will call the Importer.importFile() method, passing in the name of the provided triple file.
     * After loading the input triples, the main() method will invoke the executeQuery() method passing in
     * the provided query file name.
     * The TestDriver class should be defined within the package "cscie97.asn1.test".
     */
    public static void main(String[] args) {
        if (args.length == 2) {
            try {
                Importer.importTripleFile(args[0]);
                QueryEngine.executeQueryFilename(args[1]);
            }
            catch (ParseException pe) {

            }
            catch (Exception e) {

            }

        }
        else {
            System.out.println("Arguments to TestDriver should be: 1) import Triple file, and 2) input Query file");
        }
    }
}