package tableGenerators

import kotlin.random.Random

object RestaurantGenerator {

    fun execute(number: Int) {
        DBUtils.getConnection()

        val listAddresses: Set<String> = DataUtils.readFile("src/main/resources/address")

        for (i in 1..number) {
            val rating = Random.nextInt(0, 6)
            DBUtils.update(String.format(
                "INSERT INTO restaurants(address, rating) VALUES ('%s','%s')",
                listAddresses.random(), rating
            ))
        }
        DBUtils.closeConnection()
    }
}