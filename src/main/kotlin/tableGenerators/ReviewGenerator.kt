package tableGenerators

import kotlin.random.Random

object ReviewGenerator {

    fun execute(number: Int) {
        DBUtils.getConnection()

        val restaurants: Set<String> = DBUtils.getKeyColumn("SELECT id FROM restaurants")
        val clients: Set<String> = DBUtils.getKeyColumn("SELECT id FROM clients")

        for (i in 0 until number) {
            val text = DataUtils.getRandomString(Random.nextInt(1, 50))
            val time = DataUtils.getRandomTime()
            val rating = Random.nextInt(0, 6)
            DBUtils.update(String.format(
                "INSERT INTO review(restaurant_id, client_id, text, review_time, rating) " +
                        "VALUES (%s, %s,'%s','%s','%s')",
                restaurants.random(), clients.random(), text, time, rating
            ))
        }
        DBUtils.closeConnection()
    }
}