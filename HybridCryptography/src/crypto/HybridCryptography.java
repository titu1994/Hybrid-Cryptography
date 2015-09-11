package crypto;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class HybridCryptography {
	
	public static final int KEYSIZE = 128;
	
	// Encode ip string to bytes (Encoding - UTF8)
	private byte[] stringToBytes(String ip) {
		try {
			return ip.getBytes("UTF8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// Convert UTF8 bytes to displayable string
	private String bytesToBase64(byte[] data) {
		return Base64.getEncoder().encodeToString(data);
	}
	
	// Convert Base64 String to UTF8 bytes
	private byte[] base64ToBytes(String data) {
		return Base64.getDecoder().decode(data);
	}
	
	// Encode UTF 8 bytes to displayable String
	private String bytesToString(byte[] ip) {
		return new String(ip);
	}

	/**
	  * Perform bitwise XOR operation on 8 bit chunks.
	  * Pads either byte array with 0's at the beginning if not of the same size.
	  */
	private byte[] bitwiseXOR(byte[] x1, byte[] x2) {
		byte data[] = null, tempX[] = null;
		int lenX1 = x1.length;
		int lenX2 = x2.length;
		
		if(lenX1 != lenX2) {
			int g = Math.max(lenX1, lenX2);
			tempX = new byte[g];
			
			if(lenX1 != g) {
				int offset = g - lenX1;
				System.arraycopy(x1, offset, tempX, 0, x1.length);
				x1 = tempX;
			}
			else {
				int offset = g - lenX2;
				System.arraycopy(x2, offset, tempX, 0, x2.length);
				x2 = tempX;
			}
		}
		
		data = new byte[x1.length];
		for(int i = 0; i < data.length; i++) {
			data[i] = (byte) (x1[i] ^ x2[i]);
		}
		
		return data;
	}
	
	/**
	 * Perform bitwise circular left shift operation
	 */
	private byte[] bitwiseCircularLeftShift(byte[] x, int n) {
		byte data[] = new byte[x.length];
		int length = data.length;
		
		for(int i = 0; i < length; i++) {
			data[(i + n) % length] = x[i];
		}
		
		return data;
	}
	
	/**
	 * Perform bitwise circular right shift operation
	 */
	private byte[] bitwiseCircularRightShift(byte[] x, int n) {
		byte data[] = new byte[x.length];
		int length = x.length, m;
		
		for(int i = 0; i < length; i++) {
			m = (i - n) % length;
			if(m < 0)
				m = length + m;
			
			data[m] = x[i];
		}
		
		return data;
	}
	
}
