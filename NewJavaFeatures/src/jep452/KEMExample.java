package jep452;

import javax.crypto.KEM;
import javax.crypto.SecretKey;
import java.security.*;

public class KEMExample {
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException {
//      Get receiver's public key
        KeyPairGenerator g = KeyPairGenerator.getInstance("RSA");
        KeyPair kp = g.generateKeyPair();
        System.out.println(kp.getPublic());


        KEM kemS = KEM.getInstance("RSA");
        PublicKey pkR = kp.getPublic();
        KEM.Encapsulator e = kemS.newEncapsulator(pkR);
        KEM.Encapsulated enc = e.encapsulate();
        SecretKey secS = enc.key();

        byte[] em = enc.key().
        KEM kemR = KEM.getInstance("ABC-KEM");
        AlgorithmParameters algParams = AlgorithmParameters.getInstance("ABC-KEM");
        algParams.init(params);
        ABCKEMParameterSpec specR = algParams.getParameterSpec(ABCKEMParameterSpec.class);
        KEM.Decapsulator d = kemR.newDecapsulator(kp.getPrivate(), specR);
        SecretKey secR = d.decapsulate(em);
    }
}
