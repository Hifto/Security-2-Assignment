# Security-2-Assignment

## Assignment Requirements
### 1. Generate key pair using RSA
– Implement method (p and q can be parameters of this
methods or class fields, by your choice)
### 2. Use the key pair to encrypt and decrypt a
message
– Implement encrypt and decrypt methods
### 3. Use key pair to generate and verify digital
signature
– Implement digital signature generation and
verification methods
### 4. Prove the homomorphic property of RSA
– E(m1)●E(m2) = E(m1 ●m2) (where E is encryption of a
message)
## Assignment Requirements (Cont’d)
• In main method: <br>
– Message can be randomly generated or chosen by
your choice <br>

## RSA Algorithm
### • To generate key pair: <br>
– Pick large primes p and q <br>
– Let n = p*q, keep p and q to yourself! <br>
– For public key, choose e that is relatively prime to
ø(n) =(p-1)(q-1), let pub = (e,n) <br>
– For private key, find d that is the multiplicative
inverse of e mod ø(n), i.e., e*d ≡1 mod ø(n), let pri
= (d,n). <br>

## How Does RSA Work?
### • Given pub = (e, n) and pri = (d, n)
– encryption: c = me mod n, m < n <br>
– decryption: m = cd mod n <br>
– signature: s = md mod n, m < n <br>
– verification: m = se mod n <br>

## Key Generation
### • Input: none
### • Computation:
– select two prime integers p, q <br>
– compute integers n = p × q
 phi = (p-1) × (q-1) <br>
– select small odd integer e such that gcd(e, phi) = 1 <br>
– compute integer d such that (e × d)%phi = 1 <br>
### • Output: n, e, and d

## Questions…
### • How to find big primes p and q?
### • How to compute gcd?
### • How to find mod inverse?
### • How to manipulate big numbers
– Java integers are of limited size

## Java BigInteger Class
### • Import java.math.BigInteger
### • Common operations
– add, subtract, multiply, divide <br>
– equals, compareTo <br>
### • Useful constants
– BigInteger.ONE <br>
– BigInteger.ZERO <br>
### • Useful Constructor
– BigInteger(int numBits, int certainty, Random rnd) <br>
– Need to generate a random number beforehand <br>
– Could use 100 for certainty <br>

## Java BigInteger Class (Cont’d)
### • More useful operations
– probablePrime(int bitLength, Random rnd) <br>
### • Could use this one instead of the constructor
– gcd(BigInteger val) <br>
– modInverse(BigInteger m) <br>
– modPow(BigInteger exponent, BigInteger m)
