package cscie97.asn1.knowledge.engine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: dkilleffer
 * Date: 9/8/13
 * Time: 11:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class QueryEngine {

    /**
     * Public method for executing a single query on the knowledge graph.
     * Checks for non null and well formed query string.
     * Throws QueryEngineException on error.
     *
     * @param query the query to run returning Triple(s)
     */
    public static void executeQuery(String query) {
        try {
            KnowledgeGraph kg = KnowledgeGraph.getInstance();

            Triple queryTriple = kg.getQueryTripleFromStringIdentifier(query);

            Set<Triple> queryResults = kg.executeQuery(queryTriple);

            System.out.println("QUERY: " + queryTriple.getIdentifier() );

            if (queryResults != null && queryResults.size() > 0) {
                for (Triple triple : queryResults) {
                    System.out.println(triple.getIdentifier());
                }
            }
            System.out.println();
        }
        catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    /**
     * Public method for executing a set of queries read from a file. Checks for valid file name.
     * Delegates to executeQuery for processing individual queries. Throws QueryEngineException on error.
     *
     * @param filename
     */
    public static void executeQueryFilename(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line = null;
            while ((line = reader.readLine()) != null) {
                QueryEngine.executeQuery(line);
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
