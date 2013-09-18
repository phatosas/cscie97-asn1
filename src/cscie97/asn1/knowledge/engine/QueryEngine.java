package cscie97.asn1.knowledge.engine;

import cscie97.asn1.knowledge.engine.exception.ImportException;
import cscie97.asn1.knowledge.engine.exception.ParseException;
import cscie97.asn1.knowledge.engine.exception.QueryEngineException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

/**
 * Used to execute queries against the KnowledgeGraph for matching Triples.
 *
 * @author David Killeffer <rayden7@gmail.com>
 * @version 1.0
 * @see Triple
 * @see KnowledgeGraph
 */
public class QueryEngine {

    /**
     * Public method for executing a single query on the knowledge graph.  Checks for non-null and well
     * formed query string.  Throws QueryEngineException on error.
     *
     * @param query the query to run returning Triple(s)
     */
    public static void executeQuery(String query) throws QueryEngineException {
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
     * @param filename  the query file to read in and execute each line
     * @throws QueryEngineException  thrown when a problem occurs during reading the query file for parsing
     */
    public static void executeQueryFilename(String filename) throws QueryEngineException {
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