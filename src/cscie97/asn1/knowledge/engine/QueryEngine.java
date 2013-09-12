package cscie97.asn1.knowledge.engine;

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

    }

    /**
     * Public method for executing a set of queries read from a file. Checks for valid file name.
     * Delegates to executeQuery for processing individual queries. Throws QueryEngineException on error.
     *
     * @param filename
     */
    public static void executeQueryFilename(String filename) {

    }

}
