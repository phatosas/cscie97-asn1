package cscie97.asn1.knowledge.engine;

import cscie97.asn1.knowledge.engine.exception.ImportException;
import cscie97.asn1.knowledge.engine.exception.QueryEngineException;
import cscie97.asn1.knowledge.engine.exception.ParseException;
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
     * @param query            the query to run returning Triple(s)
     * @throws ParseException  thrown if there is a problem with the supplied query (such as not having 3 parts,
     *                         one each for subject, predicate, object)
     */
    //public static void executeQuery(String query) throws QueryEngineException, ParseException {
    public static void executeQuery(String query) throws ParseException {
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

    /**
     * Public method for executing a set of queries read from a file. Checks for valid file name.
     * Delegates to executeQuery for processing individual queries. Throws QueryEngineException on error.
     *
     * @param filename               the query file to read in and execute each line
     * @throws QueryEngineException  thrown when a problem occurs during reading the query file for parsing
     * @throws ParseException        thrown when there is a problem with a particular query line in the query file
     * @throws ImportException       thrown if there is a lower-level problem opening up the query file for reading
     */
    public static void executeQueryFilename(String filename) throws QueryEngineException, ParseException, ImportException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line = null;
            while ((line = reader.readLine()) != null) {
                QueryEngine.executeQuery(line);
            }
        }
        catch (FileNotFoundException fnfe) {
            throw new ImportException("Could not find query file ["+filename+"] to open for reading", 0, filename, fnfe);
        }
        catch (IOException ioe) {
            throw new ImportException("Encountered an IOException when trying to open query file ["+filename+"] for reading", 0, filename, ioe);
        }
        catch (Exception e) {
            throw new ImportException("Caught a generic Exception when attempting to read query file ["+filename+"]", 0, filename, e);
        }
    }

}