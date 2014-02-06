/**
 * 
 */
package cus_stat;

import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.core.Response.StatusType;

/**
 * A demo of customising http status code
 */
public final class InvalidContent implements StatusType {
	private static final InvalidContent instance = new InvalidContent();
	
	private final int code = 460;
    private final String reason = "Invalid_Content";
    private final Family family = Family.CLIENT_ERROR;

    private InvalidContent() {
    }
    
    public static InvalidContent getInstance() {
    	return instance;
    }
    
	@Override
	public int getStatusCode() {
		return code;
	}

	@Override
	public Family getFamily() {
		return family;
	}

	@Override
	public String getReasonPhrase() {
		return reason;
	}

	@Override
	public String toString() {
		return reason;
	}
}
