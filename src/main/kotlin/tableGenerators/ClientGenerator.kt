package tableGenerators

import DBUtils
import DataUtils
import kotlin.random.Random


object ClientGenerator {

    fun execute(number: Int) {
        DBUtils.getConnection()

        val setOfLogin: MutableSet<String> = DBUtils.getKeyColumn("SELECT login FROM clients")
        val setOfPhone: MutableSet<String> = DBUtils.getKeyColumn("SELECT phone FROM clients")

        val listAddresses: Set<String> = DataUtils.readFile("src/main/resources/address")

        for (i in 0 until number) {
            val login = generateUnique(setOfLogin) {
                DataUtils.getRandomString(Random.nextInt(8, 16))
            }
            val phone = generateUnique(setOfPhone) {
                Random.nextLong(79000000000, 79999999999).toString()
            }
            val password = DataUtils.getRandomString(Random.nextInt(8, 16))

            DBUtils.update(String.format(
                "INSERT INTO clients(login, password, address, phone) VALUES ('%s','%s','%s','%s')",
                    login, password, listAddresses.random(), phone
            ))
        }
        DBUtils.closeConnection()
    }

    private fun generateUnique(set: MutableSet<String>, func: () -> String): String {
        val str = func()
        return if (set.contains(str)) generateUnique(set, func)
        else {
            set.add(str)
            str
        }
    }
}
