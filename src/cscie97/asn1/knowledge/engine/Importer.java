/**
 * Created with IntelliJ IDEA.
 * User: dkilleffer
 * Date: 9/8/13
 * Time: 11:08 AM
 * To change this template use File | Settings | File Templates.
 */

package cscie97.asn1.knowledge.engine;

import cscie97.asn1.knowledge.engine.exception.ParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Importer {

    /**
     * Public method for importing triples from N_Triple formatted file into the KnowledgeGraph.
     * Checks for valid input file name.
     * Throws ImportException on error accessing or processing the input Triple File.
     *
     * @param filename                file with triples to load into the KnowledgeGraph
     * @throws ParseException         thrown when encountering any issues parsing the input file related to the format of the file contents
     * @throws FileNotFoundException  thrown when the supplied filename argument could not be found on the system
     * @throws IOException            thrown when thrown a system-level issue arose reading the file (perhaps a permissions issue, etc.)
     */
    //public static void importTripleFile(String filename) throws ParseException {
    public static void importTripleFile(String filename) throws ParseException, FileNotFoundException, IOException {
        try {
            KnowledgeGraph kg = KnowledgeGraph.getInstance();

            // http://stackoverflow.com/questions/2788080/reading-a-text-file-in-java

            // http://www.javapractices.com/topic/TopicAction.do?Id=42

            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line = null;

            // track the Triples we want to add to the KnowledgeEngine and load them all up once we're done reading the file
            List<Triple> triplesToAdd = new ArrayList<Triple>();

            int lineNumber = 1;  // keep track of what lineNumber we're reading in from the input file

            while ((line = reader.readLine()) != null) {

                // trim off any trailing periods from the string
                line = line.replaceAll("\\.+$", "");

                // check if we encountered an empty line, and just skip to the next one if so
                if (line.length() == 0) { continue; }

                // check if the line, once "cleaned", is a minimum of 5 characters in length
                // (5 being the minimum number of characters allowed to be valid -
                // Subject+space, Predicate+space, Object)
                String cleanedLine = kg.cleanQueryString(line);
                if ( cleanedLine == null || cleanedLine.length() < 5) { continue; }

                String[] parts = line.split("\\s");

                if (parts.length < 3) {
                    //throw new Exception("Triple line should have 3 parts, but only actually had ["+parts.length+"] parts: ["+line+"]");

                    throw new ParseException("Triple line should have 3 parts, but only actually had ["+parts.length+"] parts: ["+line+"]",
                                                line,
                                                lineNumber,
                                                filename,
                                                null);


                    } else {
                    // the first part should contain the first "Node"
                    Node subject = kg.getNode(parts[0]);  // node/subjects: Joe, Sue, Mary, etc.

                    // the second part should be the Predicate
                    Predicate predicate = kg.getPredicate(parts[1]);  // predicate: has_friend, plays, etc.

                    // last part should be the "object", also a Node
                    Node object = kg.getNode(parts[2]);  // object (also a node): Bill, Sue, Mary, Ultimate_Frisbee

                    triplesToAdd.add(new Triple(subject, predicate, object));
                }
                lineNumber++;
            }

            if (triplesToAdd.size() > 0) {
                kg.importTriples(triplesToAdd);
            }
        }
        catch (FileNotFoundException fnfe) {
            System.out.println("FileNotFoundException: " + fnfe.getMessage());
        }
        catch (IOException ioe) {
            System.out.println("IOException: " + ioe.getMessage());
        }
        catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

}
