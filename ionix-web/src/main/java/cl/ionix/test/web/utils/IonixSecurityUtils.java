package cl.ionix.test.web.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.springframework.stereotype.Component;

import cl.ionix.test.web.exception.IonixWebException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class IonixSecurityUtils {
	
	public String cipher(String value, String key) {
		try {
			DESKeySpec keySpec = new DESKeySpec(key.getBytes("UTF8"));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(keySpec);
			byte[] clearText = value.getBytes("UTF8");
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return Base64.getEncoder().encodeToString(cipher.doFinal(clearText));
		} catch (InvalidKeySpecException ex) {
			log.error("Error de cifrado {}", ex.getMessage());
			throw new IonixWebException(ex.getMessage());
		} catch (NoSuchAlgorithmException ex) {
			log.error("Error de cifrado {}", ex.getMessage());
			throw new IonixWebException(ex.getMessage());
		} catch (InvalidKeyException ex) {
			log.error("Error de cifrado {}", ex.getMessage());
			throw new IonixWebException(ex.getMessage());
		} catch (UnsupportedEncodingException ex) {
			log.error("Error de cifrado {}", ex.getMessage());
			throw new IonixWebException(ex.getMessage());
		} catch (IllegalBlockSizeException ex) {
			log.error("Error de cifrado {}", ex.getMessage());
			throw new IonixWebException(ex.getMessage());
		} catch (BadPaddingException ex) {
			log.error("Error de cifrado {}", ex.getMessage());
			throw new IonixWebException(ex.getMessage());
		} catch (NoSuchPaddingException ex) {
			log.error("Error de cifrado {}", ex.getMessage());
			throw new IonixWebException(ex.getMessage());
		}
	}
}
