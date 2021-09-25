package tableGenerators

import kotlin.random.Random

object CourierGenerator {

    fun execute(number: Int) {
        DBUtils.getConnection()

        val listAddresses: Set<String> = DataUtils.readFile("src/main/resources/address")

        for (i in 1..number) {
            val salary = Random.nextInt(30000, 55000)
            DBUtils.update(String.format(
                "INSERT INTO couriers(address, salary) VALUES ('%s','%s')",
                listAddresses.random(), salary
            ))
        }
        DBUtils.closeConnection()
    }
}