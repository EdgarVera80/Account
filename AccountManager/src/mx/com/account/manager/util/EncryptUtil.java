package mx.com.account.manager.util;

import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.codec.binary.Base64.encodeBase64;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

import mx.com.account.manager.exception.EncryptException;

/**
 * 
 * @author Edgar Angel Vera del Rio.
 *
 */
public final class EncryptUtil {
	private static final Logger LOGGER = Logger.getLogger(EncryptUtil.class);
	
    // Definición del tipo de algoritmo a utilizar (AES, DES, RSA)
    private static final String ALG = "AES";
    // Definición del modo de cifrado a utilizar
    private static final String CI = "AES/CBC/PKCS5Padding";
    
    
    private static final String ERROR_TEXT="Error";
    
    private EncryptUtil() {
    	/**
    	 * Default Constructor.
    	 */
    }
    
    /**
     * Función de tipo String que recibe una llave (key), un vector de inicialización (iv)
     * y el texto que se desea cifrar
     * @param key la llave en tipo String a utilizar
     * @param iv el vector de inicialización a utilizar
     * @param cleartext el texto sin cifrar a encriptar
     * @return el texto cifrado en modo String
     * @throws Exception puede devolver excepciones de los siguientes tipos: NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException
     */
    public static String encrypt(String key, String iv, String cleartext) throws EncryptException {
    	try {
	        Cipher cipher = Cipher.getInstance(CI);
	        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), ALG);
	        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
	        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParameterSpec);
	        byte[] encrypted = cipher.doFinal(cleartext.getBytes());
	        return new String(encodeBase64(encrypted));
    	} catch (InvalidKeyException e) {
			LOGGER.error(ERROR_TEXT,e);
			throw new EncryptException(e);
		} catch (InvalidAlgorithmParameterException e) {
			LOGGER.error(ERROR_TEXT,e);
			throw new EncryptException(e);
		} catch (IllegalBlockSizeException e) {
			LOGGER.error(ERROR_TEXT,e);
			throw new EncryptException(e);
		} catch (BadPaddingException e) {
			LOGGER.error(ERROR_TEXT,e);
			throw new EncryptException(e);
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error(ERROR_TEXT,e);
			throw new EncryptException(e);
		} catch (NoSuchPaddingException e) {
			LOGGER.error(ERROR_TEXT,e);
			throw new EncryptException(e);
		}
    }
 
    /**
     * Función de tipo String que recibe una llave (key), un vector de inicialización (iv)
     * y el texto que se desea descifrar
     * @param key la llave en tipo String a utilizar
     * @param iv el vector de inicialización a utilizar
     * @param encrypted el texto cifrado en modo String
     * @return el texto desencriptado en modo String
     * @throws Exception puede devolver excepciones de los siguientes tipos: NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException
     */
    public static String decrypt(String key, String iv, String encrypted) throws EncryptException {
	    	
        try {
        	Cipher cipher = Cipher.getInstance(CI);
	        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), ALG);
	        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
	        byte[] enc = decodeBase64(encrypted);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParameterSpec);
			byte[] decrypted = cipher.doFinal(enc);
	        return new String(decrypted);
		} catch (InvalidKeyException e) {
			LOGGER.error(ERROR_TEXT,e);
			throw new EncryptException(e);
		} catch (InvalidAlgorithmParameterException e) {
			LOGGER.error(ERROR_TEXT,e);
			throw new EncryptException(e);
		} catch (IllegalBlockSizeException e) {
			LOGGER.error(ERROR_TEXT,e);
			throw new EncryptException(e);
		} catch (BadPaddingException e) {
			LOGGER.error(ERROR_TEXT,e);
			throw new EncryptException(e);
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error(ERROR_TEXT,e);
			throw new EncryptException(e);
		} catch (NoSuchPaddingException e) {
			LOGGER.error(ERROR_TEXT,e);
			throw new EncryptException(e);
		}
    }
}
