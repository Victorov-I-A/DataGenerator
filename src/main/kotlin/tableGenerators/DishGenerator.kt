package tableGenerators

import java.util.*
import kotlin.random.Random

object DishGenerator {

    fun execute(number: Int) {
        DBUtils.getConnection()

        val restaurants: Set<String> = DBUtils.getKeyColumn("SELECT id FROM restaurants")

        for (i in 0 until number) {
            val name = DataUtils.getRandomString(Random.nextInt(2, 19))
            val price = Random.nextDouble(100.0, 5001.0)
            val rating = Random.nextInt(0, 6)
            DBUtils.update(String.format(
                Locale.ROOT,
                "INSERT INTO dishes(name, restaurant_id, price, rating) VALUES ('%s', %s, %.2f, '%s')",
                name, restaurants.random(), price, rating
            ))
        }
        DBUtils.closeConnection()
    }
}