/**
 * Created with IntelliJ IDEA.
 * User: dkilleffer
 * Date: 9/8/13
 * Time: 11:08 AM
 * To change this template use File | Settings | File Templates.
 */

package cscie97.asn1.knowledge.engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Importer {

    /**
     * Public method for importing triples from N_Triple formatted file into the KnowledgeGraph.
     * Checks for valid input file name.
     * Throws ImportException on error accessing or processing the input Triple File.
     *
     * @param filename  file with triples to load into the KnowledgeGraph
     */
    public static void importTripleFile(String filename) {

        try {

            KnowledgeGraph kg = KnowledgeGraph.getInstance();


            // http://stackoverflow.com/questions/2788080/reading-a-text-file-in-java

            // http://www.javapractices.com/topic/TopicAction.do?Id=42

            //BufferedReader reader = new BufferedReader(new FileReader("/path/to/file.txt"));
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line = null;

            while ((line = reader.readLine()) != null) {
                // trim off any trailing periods from the string
                line = line.replaceAll("\\.+$", "");

                String[] parts = line.split("\\s");

                if (parts.length < 3) {
                    throw new Exception("Triple line should have 3 parts, but only actually had ["+parts.length+"] parts: ["+line+"]");
                } else {

                    Node subject = kg.getNode(parts[0]);
                    Predicate predicate = kg.getPredicate(parts[1]);
                    Node object = kg.getNode(parts[2]);

                    Triple triple = kg.getTriple(subject, predicate, object);

                    //parts[0]; // node/subjects: Joe, Sue, Mary, etc.
                    //parts[1]; // predicate: has_friend, plays, etc.
                    //parts[2]; // object: Bill, Sue, Mary, Ultimate_Frisbee


                }

                // ...

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
