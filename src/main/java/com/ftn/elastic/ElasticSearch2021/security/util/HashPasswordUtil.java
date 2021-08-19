package com.ftn.elastic.ElasticSearch2021.security.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashPasswordUtil {

    public static StringBuilder hashingPassword(String password)  {
        MessageDigest md = null;
        try{
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }

        md.update(password.getBytes());

        byte[] byteData = md.digest();
        StringBuilder result = new StringBuilder();

        for (byte byt : byteData) {
            result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        }
        return result;
    }
}
