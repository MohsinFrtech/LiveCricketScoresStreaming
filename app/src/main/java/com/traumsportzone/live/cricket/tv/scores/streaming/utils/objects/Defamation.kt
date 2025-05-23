package com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects

import android.os.Build
import android.util.Base64
import android.util.Log
import androidx.annotation.RequiresApi
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.algoName
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.algoTypeS1
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.algoTypeS2
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.asp
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.chName
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.defaultString
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.instanceVal
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.key
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.mySecretSize
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.salt
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.transForm
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.userBase
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.userBaseDel
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.userIp
import se.simbio.encryption.Encryption
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.security.KeyPairGenerator
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.spec.KeySpec
import java.util.Base64.getDecoder
import java.util.Calendar
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec
import kotlin.experimental.and


object Defamation {

    fun calculateDifferenceBetweenDates(channelDate: Long): Long {

        val currentDate = Calendar.getInstance()
        var different: Long = currentDate.timeInMillis - channelDate
        if (different < 0) {
            try {
                val secondsInMilli: Long = 1000
                val minutesInMilli = secondsInMilli * 60
                val hoursInMilli = minutesInMilli * 60
                val daysInMilli = hoursInMilli * 24

                val elapsedDays = different / daysInMilli
                different = different % daysInMilli

                val elapsedHours = different / hoursInMilli
                different = different % hoursInMilli

                val elapsedMinutes = different / minutesInMilli
                different = different % minutesInMilli

                val elapsedSeconds = different / secondsInMilli
                println("Difference: $elapsedDays days, $elapsedHours hours, $elapsedMinutes minutes, $elapsedSeconds seconds")

                val totalTime =
                    (elapsedHours * 60 * 60 * 1000)+(elapsedMinutes * 60 * 1000)+(elapsedSeconds * 1000)

                val hours = totalTime / (60 * 60 * 1000)
                val minutes = (totalTime % (60 * 60 * 1000)) / (60 * 1000)
                val seconds = (totalTime % (60 * 1000)) / 1000
                Log.d("Difference", "msg $totalTime $hours $minutes $seconds")

                return Math.abs(totalTime)
            } catch (e: Exception) {
                return 0
            }

        } else {
            return 0
        }

    }
    fun convertDecData(strToDecrypt: String?): String {
        val iv = ByteArray(mySecretSize)
        val encryption: Encryption = Encryption.getDefault("EidGuzar1234", "abd56#90", iv)
        return encryption.decryptOrNull(strToDecrypt)
    }


    fun decryptBase6(vale:String?): String{
        // Receiving side
        val data: ByteArray = Base64.decode(vale, Base64.DEFAULT)
        return String(data, StandardCharsets.UTF_8)
    }

    fun encryptBase64(vale: String?): String {
        val data: ByteArray = vale?.toByteArray(StandardCharsets.UTF_8)!!

        return Base64.encodeToString(data, Base64.DEFAULT)
    }

    fun stoneDel(encrypted: String, pwd: String): String? {
        try {
            val parts = encrypted.split("--").toTypedArray()
            if (parts.size != 3) return null
            val encryptedData = Base64.decode(parts[0], Base64.DEFAULT)
            val iv = Base64.decode(parts[1], Base64.DEFAULT)
            val salt = Base64.decode(parts[2], Base64.DEFAULT)
            val factory: SecretKeyFactory = SecretKeyFactory.getInstance(instanceVal)
            val spec: KeySpec = PBEKeySpec(pwd.toCharArray(), salt, 1024, 128)
            val tmp: SecretKey = factory.generateSecret(spec)
            val aesKey: SecretKey = SecretKeySpec(tmp.encoded, asp)
            val cipher: Cipher = Cipher.getInstance(transForm)
            cipher.init(Cipher.DECRYPT_MODE, aesKey, IvParameterSpec(iv))
            val result: ByteArray = cipher.doFinal(encryptedData)
            return String(result, charset(chName))
        } catch (e: Exception) {
            return ""
        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun decoupleCode(key: String, encryptedText: String): String {
        return try {
            val keyData: ByteArray = key.toByteArray(Charset.forName("UTF-8"))
            val kS =
                SecretKeySpec(keyData, "Blowfish")
            val encryptedTextToBytes: ByteArray =
                getDecoder().decode(encryptedText)
            val cipher = Cipher.getInstance("Blowfish")
            cipher.init(Cipher.DECRYPT_MODE, kS)
            val decrypted = cipher.doFinal(encryptedTextToBytes)
            String(decrypted, Charset.forName("UTF-8"))
        } catch (e: Exception) {

            ""
        }

    }

    fun decryptData(encodedBytes: ByteArray): String {

        return try {
            val kpg = KeyPairGenerator.getInstance("RSA")
            kpg.initialize(2048)
            val kp = kpg.genKeyPair()
            val privateKey = kp.private
            val cipher1 = Cipher.getInstance("RSA")
            cipher1.init(Cipher.DECRYPT_MODE, privateKey)
            val decryptedBytes = cipher1.doFinal(encodedBytes)
            val decrypted = String(decryptedBytes)
            decrypted
        } catch (e:Exception) {
            ""
        }

    }

    fun sHA2(text: String): String {
        val md = MessageDigest.getInstance(algoTypeS2)
        val textBytes = text.toByteArray(charset(algoName))
        md.update(textBytes, 0, textBytes.size)
        val sha1hash = md.digest()
        return convertToHex(sha1hash)
    }
    private fun convertToHex(data: ByteArray): String {
        val buf = StringBuilder()
        for (b in data) {
//                val v = b.toInt() and 0x0F
            var halfbyte: Int = (b.toInt().ushr(4)) and 0x0F
            var twohalfs = 0
            do {
                buf.append(
                    if (halfbyte in 0..9) {
                        ('0'.code + halfbyte).toChar()
                    } else {
                        ('a'.code + (halfbyte - 10)).toChar()
                    }
                )
                halfbyte = (b and 0x0F).toInt()
            } while (twohalfs++ < 1)
        }
        return buf.toString()
    }

    fun improveDeprecatedCode(link: String): String {
        val separated: List<String> =
            link.split(userBaseDel)
        val streamName = separated[separated.size - 2]
        val startTime = System.currentTimeMillis() / 1000
        val endTime = startTime + 77
        var sha1 = startTime.toString() + streamName + userIp + defaultString + endTime
        sha1 = sHA1(sha1)
        var userval = ""
        userval = "$userBase$sha1-$endTime-$startTime"
        userval="$userBase${sHA1(streamName + defaultString + startTime.toString() + userIp)}-$endTime-$startTime"
        return "$userBase${sHA2(streamName + defaultString + startTime.toString() + userIp)}-$endTime-$startTime"
    }

    fun encryptPRNG(seed: String): String {
        val keyGenerator = KeyGenerator.getInstance("AES")
        val secureRandom = SecureRandom.getInstance("SHA1PRNG")
        secureRandom.setSeed(seed.toByteArray())

        keyGenerator.init(128, secureRandom)
        val skey = keyGenerator.generateKey()
        val rawKey: ByteArray = skey.encoded

        val skeySpec = SecretKeySpec(rawKey, "AES")
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec)
        val byteArray = cipher.doFinal("".toByteArray())
        return byteArray.toString()
    }

    fun convertCementData(strToDecrypt: String?): String {
        val iv = ByteArray(mySecretSize)
        val encryption: Encryption = Encryption.getDefault(key, salt, iv)
        return encryption.decryptOrNull(strToDecrypt)
    }
    fun decryptPRNG(seed: String): String {
        val keyGenerator = KeyGenerator.getInstance("AES")
        val secureRandom = SecureRandom.getInstance("SHA1PRNG")
        secureRandom.setSeed(seed.toByteArray())

        keyGenerator.init(128, secureRandom)
        val skey = keyGenerator.generateKey()
        val rawKey: ByteArray = skey.encoded

        val skeySpec = SecretKeySpec(rawKey, "AES")
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, skeySpec)
        val byteArray = cipher.doFinal("".toByteArray())

        return byteArray.toString()
    }
    @Throws(
        NoSuchAlgorithmException::class,
        UnsupportedEncodingException::class
    )
    fun sHA1(text: String): String {
        val md = MessageDigest.getInstance(algoTypeS1)
        val textBytes = text.toByteArray(charset(algoName))
        md.update(textBytes, 0, textBytes.size)
        val sha1hash = md.digest()
        return convertToHex(sha1hash)
    }


}