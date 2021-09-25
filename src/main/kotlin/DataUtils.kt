import java.io.File
import java.io.IOException
import java.io.InputStream
import java.sql.Timestamp
import kotlin.random.Random

object DataUtils {

    fun readFile(str: String): Set<String> {
        val setOfLine = mutableSetOf<String>()
        try {
            val inputStream: InputStream = File(str).inputStream()
            inputStream.bufferedReader().useLines { lines -> lines.forEach { setOfLine.add(it)} }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return setOfLine
    }

    fun getRandomTime(): String {
        val year = 2021
        val month = Random.nextInt(1, 13)
        val day = when (month) {
            1,3,5,7,8,10,12 -> Random.nextInt(1, 32)
            4,6,9,11 -> Random.nextInt(1, 31)
            else -> Random.nextInt(1, 29)
        }
        val hour = Random.nextInt(0, 24)
        val minutes = Random.nextInt(0, 60)
        val seconds = Random.nextInt(0, 60)
        return String.format("%s-%s-%s %s:%s:%s", year, month, day, hour, minutes, seconds)
    }

    fun getNextTime(str: String): String {
        val prevTime = Timestamp.valueOf(str).time
        return Timestamp((prevTime + 3600000 * Random.nextDouble(0.0, 5.0)).toLong()).toString()
    }

    fun getRandomString(length: Int) : String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
}