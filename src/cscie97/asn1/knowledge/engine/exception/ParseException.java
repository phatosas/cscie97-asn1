package cscie97.asn1.knowledge.engine.exception;

/**
 * Created with IntelliJ IDEA.
 * User: dkilleffer
 * Date: 9/17/13
 * Time: 6:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class ParseException extends Exception {

    private String lineWhereFailed;

    private int lineIndexWhereFailed;

    private String filename;


    public ParseException (String msg, String line, int lineNum, String filename, Throwable cause) {
        super("ParseException occurred at line" + lineNum + " of file " + filename + " in line number [" + lineNum + "]", cause);

        this.lineWhereFailed = line;
        this.lineIndexWhereFailed = lineNum;
        this.filename = filename;
    }



    public String getLineWhereFailed() {
        return lineWhereFailed;
    }

    public void setLineWhereFailed(String lineWhereFailed) {
        this.lineWhereFailed = lineWhereFailed;
    }

    public int getLineIndexWhereFailed() {
        return lineIndexWhereFailed;
    }

    public void setLineIndexWhereFailed(int lineIndexWhereFailed) {
        this.lineIndexWhereFailed = lineIndexWhereFailed;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

}