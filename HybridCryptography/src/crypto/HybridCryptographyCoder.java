package crypto;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

public class HybridCryptographyCoder {
	public static final int KEYSIZE = 128;
	
	// Encode ip string to bytes (Encoding - UTF8)
	public static byte[] stringToBytes(String ip) {
		try {
			return ip.getBytes("UTF8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// Convert UTF8 bytes to displayable string
	public static String bytesToBase64(byte[] data) {
		return Base64.getEncoder().encodeToString(data);
	}
	
	// Convert Base64 String to UTF8 bytes
	public static byte[] base64ToBytes(String data) {
		return Base64.getDecoder().decode(data);
	}
	
	// Encode UTF 8 bytes to displayable String
	public static String bytesToString(byte[] ip) {
		return new String(ip);
	}
	
	/**
	 * Converts a large single dimensional array into smaller chunks
	 */
	public static byte[][] bytesToChunks(byte[] data) {
		int chunkSize = KEYSIZE / 8;
		int len = data.length;
		int n = ((data.length - 1) / chunkSize) + 1;
		byte chunks[][] = new byte[n][];
		int counter = 0;
		
		for (int i = 0; i < len - chunkSize + 1; i += chunkSize)
			chunks[counter++] = Arrays.copyOfRange(data, i, i + chunkSize);

		if (len % chunkSize != 0)
			chunks[counter] = Arrays.copyOfRange(data, len - len % chunkSize, len);
		
		return chunks;
	}
	
	/**
	 * Converts several small chunks into a large single dimensional array
	 */
	public static byte[] chunksToBytes(byte x[][]) {
		ArrayList<Byte> list = new ArrayList<>();
		
		for(int i = 0; i < x.length; i++) {
	        for(int j = 0; j < x[i].length; j++){
	            list.add(x[i][j]);
	        }
	    }
		
		byte data[] = new byte[list.size()];
		for(int i = 0; i < data.length; i++) 
			data[i] = list.get(i);
			
		return data;
	}

	/**
	  * Perform bitwise XOR operation on 8 bit chunks.
	  * Pads either byte array with 0's at the beginning if not of the same size.
	  */
	public static byte[] bitwiseXOR(byte[] x1, byte[] x2) {
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
	public static byte[] bitwiseCircularLeftShift(byte[] x, int n) {
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
	public static byte[] bitwiseCircularRightShift(byte[] x, int n) {
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
