import java.math.BigInteger;
import java.util.Random;

public class RSAEncryption 
{

	private BigInteger p, q, n, phi, e, d;
	private int bitLength = 512;

	public void Generation() 
	{
		Random rnd = new Random();

		// Pick large primes p and q
		p = BigInteger.probablePrime(bitLength, rnd);
		q = BigInteger.probablePrime(bitLength, rnd);

		// Make sure p and q are not the same number
		while (p.compareTo(q) == 0) 
		{
			p = BigInteger.probablePrime(bitLength, rnd);
			q = BigInteger.probablePrime(bitLength, rnd);
		}
		
		// Let n = p * q
		n = p.multiply(q);

		System.out.println("n: " + n);

		// Let phi = (p-1) x (q-1)
		phi = ((p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE)));

		// Small odd int e such that gcd(e, phi) = 1
		e = new BigInteger("3");

		while (e.gcd(phi).intValue() > 1) 
		{
			e = e.add(new BigInteger("2"));
		}

		System.out.println("e: " + e);

		// Compute d such that (e * d) % phi = 1 (d = multiplicative inverse of
		// e mod phi)
		d = e.modInverse(phi);

		System.out.println("d: " + d);
	}

	// c = m^e mod n, m < n
	public BigInteger Encryption(BigInteger m) 
	{
		// m.compareTo(n): m < n = -1, m == n = 0, m > n = 1
		if (m.compareTo(n) == -1) 
		{
			BigInteger c;

			c = m.modPow(e, n);

			return c;
		} 
		else 
		{
			return null;
		}
	}

	// m = c^d mod n
	public BigInteger Decryption(BigInteger c) 
	{
		BigInteger m;

		m = c.modPow(d, n);

		return m;
	}

	// s = m^d mod n, m < n
	public BigInteger Signature(BigInteger m) 
	{
		// m.compareTo(n): m < n = -1, m == n = 0, m > n = 1
		if (m.compareTo(n) == -1) 
		{
			BigInteger s;

			s = m.modPow(d, n);

			return s;
		} 
		else 
		{
			return null;
		}
	}

	// m = s^e mod n
	public BigInteger Verification(BigInteger s) 
	{
		BigInteger m;

		m = s.modPow(e, n);

		return m;
	}

	public static void main(String[] args) 
	{
		Random rnd = new Random();
		
		// Creating message options
		String message [] = new String [5];
		
		message [0] = "This is the first message";
		message [1] = "This is another message";
		message [2] = "This message is secret";
		message [3] = "Watch out for this message";
		message [4] = "Messages everywhere!";
		
		// Generate random number to decide which message to encrypt
		int messageChoice = rnd.nextInt(5);

		RSAEncryption rsa = new RSAEncryption();

		// Generates keys
		rsa.Generation();

		System.out.println();

		// Turns message into a BigInteger
		BigInteger messageBigInt = new BigInteger(message[messageChoice].getBytes());
		BigInteger messageVerify = new BigInteger(message[messageChoice].getBytes());

		// Displays original message
		System.out.println("Original message: " + message[messageChoice]);

		// Encrypts the message
		BigInteger encrypted = rsa.Encryption(messageBigInt);
		System.out.println("Encrypted: " + encrypted);
		
		// Decrypts the message
		messageBigInt = rsa.Decryption(encrypted);
		System.out.println("Decrypted: " + messageBigInt);
		
		// Turns BigInteger message back to text
		String backToMessage = new String(messageBigInt.toByteArray());
		System.out.println("Decrypted text: " + backToMessage);

		System.out.println();

		// Generates signature
		BigInteger signature = rsa.Signature(messageVerify);
		System.out.println("Signature: " + signature);

		// Verifies
		messageVerify = rsa.Verification(signature);
		System.out.println("Verification: " + messageVerify);
		
		// Turns verified message to text
		String backToVerifiedMessage = new String(messageVerify.toByteArray());
		System.out.println("Decrypted text: " + backToVerifiedMessage);

		System.out.println();
		
		// Displays message of verification
		if (messageVerify.compareTo(messageBigInt) == 0) 
		{
			System.out.println("This message is verified!");
		} 
		else 
		{
			System.out.println("This message is not verified!");
		}
		
		System.out.println();
		
		//Homomorphic property states that E(m1) * E(m2) = E(m1 * m2) (where E is the encryption of a message)
		System.out.println("Homomorphic Property");
		
		// Generate choice for message 1
		int messageChoiceOne = rnd.nextInt(5);
		
		// Generate message 1 (m1)
		BigInteger messageOne = new BigInteger(message[messageChoiceOne].getBytes());
		System.out.println("Message 1: " + message[messageChoiceOne]);
		
		// Generate choice for message 2
		int messageChoiceTwo = rnd.nextInt(5);
		
		// Makes sure that message 2 is different from message 1
		while (messageChoiceTwo == messageChoiceOne)
		{
			messageChoiceTwo = rnd.nextInt(5);
		}
		
		// Generate message 2 (m2)
		BigInteger messageTwo = new BigInteger(message[messageChoiceTwo].getBytes());
		System.out.println("Message 2: " + message[messageChoiceTwo]);
		
		// Encrypt message 1
		BigInteger encryptedOne = rsa.Encryption(messageOne);
		System.out.println("Message 1 Encrypted: " + encryptedOne);
		
		// Encrypt message 2
		BigInteger encryptedTwo = rsa.Encryption(messageTwo);
		System.out.println("Message 2 Encrypted: " + encryptedTwo);
		
		// Multiply E(m1) * E(m2)
		BigInteger finalEncryptionFirst = encryptedOne.multiply(encryptedTwo);
		
		// Multiply message 1 and message 2 (m1 * m2)
		BigInteger multiMessages = messageOne.multiply(messageTwo);
		
		// Encrypt the the multiplied messages (E(m1 * m2))
		BigInteger finalEncryptionSecond = rsa.Encryption(multiMessages);
		
		// Display the two encryption methods (E(m1) * E(m2) & E(m1 * m2))
		System.out.println("Encryption of message 1 multiplied by encryption of message 2: " + finalEncryptionFirst);
		System.out.println("Encryption of message 1 multiplied by message 2: " + finalEncryptionSecond);
		
		//Decrypts message 1
		BigInteger messageOneDecrypt = rsa.Decryption(finalEncryptionFirst);
		System.out.println("Decryption of message 1: " + messageOneDecrypt);
		
		//Decrypts message 2
		BigInteger messageTwoDecrypt = rsa.Decryption(finalEncryptionSecond);
		System.out.println("Decryption of message 2: " + messageTwoDecrypt);
		
		// Compare the two. Does E(m1) * E(m2) = E(m1 * m2)?
		if (messageOneDecrypt.compareTo(messageTwoDecrypt) == 0)
		{
			System.out.println("The two messages are the same. Homomorphic property proven.");
		}
		else
		{
			System.out.println("The two messages are not the same. Homomorphic property fails.");
		}
	}
}