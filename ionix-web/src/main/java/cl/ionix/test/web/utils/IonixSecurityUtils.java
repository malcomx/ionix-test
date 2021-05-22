package cl.ionix.test.web.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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
	
	private Charset charset = StandardCharsets.UTF_8;
	
	private enum Algorithms { AES, DES }
	
	public String cipher(String value, String key) {
		Algorithms algorithm = Algorithms.DES;
		try {
			if(null == value) throw new IonixWebException("Value is null");
			if(null == key) throw new IonixWebException("Key is null");
			DESKeySpec keySpec = new DESKeySpec(key.getBytes(charset.name()));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm.name());
			SecretKey secretKey = keyFactory.generateSecret(keySpec);
			byte[] bytes = value.getBytes(charset.name());
			Cipher cipher = Cipher.getInstance(algorithm.name());
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return Base64.getEncoder().encodeToString(cipher.doFinal(bytes));
		} catch (InvalidKeySpecException ex) {
			log.error("Especicficacion de la llave inválido (KeySpec) - {}", ex.getMessage());
			throw new IonixWebException(ex.getMessage());
		} catch (NoSuchAlgorithmException ex) {
			log.error("Algoritmo inválido ({}) - {}", algorithm.name(), ex.getMessage());
			throw new IonixWebException(ex.getMessage());
		} catch (InvalidKeyException ex) {
			log.error("Llave Inválida (key) {}", ex.getMessage());
			throw new IonixWebException(ex.getMessage());
		} catch (UnsupportedEncodingException ex) {
			log.error("Encoding no soportado ({}) - {}", charset.name(), ex.getMessage());
			throw new IonixWebException(ex.getMessage());
		} catch (IllegalBlockSizeException ex) {
			log.error("Bloque del value/key a cifrar es ilegal {}", ex.getMessage());
			throw new IonixWebException(ex.getMessage());
		} catch (BadPaddingException ex) {
			log.error("Padding del value/key es incorrecto{}", ex.getMessage());
			throw new IonixWebException(ex.getMessage());
		} catch (NoSuchPaddingException ex) {
			log.error("Esquema del Padding no esta disponible {}", ex.getMessage());
			throw new IonixWebException(ex.getMessage());
		}
	}
}
