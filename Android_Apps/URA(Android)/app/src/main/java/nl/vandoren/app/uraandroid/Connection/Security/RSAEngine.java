package nl.vandoren.app.uraandroid.Connection.Security;

import android.util.Base64;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by Alex on 5/26/2015.
 */
public final class RSAEngine
{
    //public  String serviceSessionID = null;
    public String Encrypt (String RSApublickKeyXML, String plainText, String[] serviceSessionID) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
    {
        try{
            //Parsing XML string
            String privateKey = null;
            String privateKeyExponent = null;

            XmlPullParserFactory myparser = XmlPullParserFactory.newInstance();
            myparser.setNamespaceAware(true);
            XmlPullParser xpp = myparser.newPullParser();

            xpp.setInput( new StringReader( RSApublickKeyXML ) );
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_DOCUMENT) {
                    eventType = xpp.next();
                    continue;
                }
                else if(eventType == XmlPullParser.START_TAG)
                {
                    if(xpp.getName().equals("Modulus"))
                    {
                        xpp.next();
                        privateKey = xpp.getText();
                    }
                    else if(xpp.getName().equals("Exponent"))
                    {
                        xpp.next();
                        privateKeyExponent = xpp.getText();
                    }
                    else if(xpp.getName().equals("id"))
                    {
                        xpp.next();
                        serviceSessionID[0] = xpp.getText();
                    }
                }
                eventType = xpp.next();
            }

            byte[] modulusBytes = Base64.decode(privateKey, Base64.NO_WRAP);
            byte[] exponentBytes = Base64.decode(privateKeyExponent,0);
            BigInteger modulus = new BigInteger(1, modulusBytes );
            BigInteger exponent = new BigInteger(1, exponentBytes);

            RSAPublicKeySpec rsaPubKey = new RSAPublicKeySpec(modulus, exponent);
            KeyFactory fact = KeyFactory.getInstance("RSA");
            PublicKey pubKey = fact.generatePublic(rsaPubKey);

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);

            byte[] plainBytes = new String(plainText).getBytes("UTF-8");
            byte[] cipherData = cipher.doFinal(plainBytes);
            String encryptedString = Base64.encodeToString(cipherData,Base64.NO_WRAP);
            return encryptedString;
        }
        catch(Exception e){
            return null;
        }
    }
}

