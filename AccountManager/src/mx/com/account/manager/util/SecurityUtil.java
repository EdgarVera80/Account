package mx.com.account.manager.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import org.apache.log4j.Logger;

import mx.com.account.manager.exception.SecurityExceptionHandler;

/**
 * 
 * @author Edgar Angel Vera del Rio.
 *
 */
public final class SecurityUtil {
	private static final Logger LOGGER = Logger.getLogger(SecurityUtil.class);
	
	private SecurityUtil(){
		/**
		 * Default Constructor
		 */
	}
	
	public static String hashGenerator(String text) throws SecurityExceptionHandler {
	    MessageDigest md=null;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(text.getBytes());
			byte[] digest = md.digest();
			return DatatypeConverter.printHexBinary(digest).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("Error",e);
			throw new SecurityExceptionHandler(1,e.getMessage());
		}
	}
}
