package com.jonathan.santos.marvelchallenge.util

import java.math.BigInteger
import java.security.MessageDigest

class CalculateHash {
    companion object {
        fun calculateHash(timeStamp: String,  privateKey: String, publicKey: String): String{
            val md = MessageDigest.getInstance("MD5")
            val input: String = timeStamp + privateKey + publicKey
            return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
        }
    }
}