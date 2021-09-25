package tableGenerators

import java.util.*
import kotlin.random.Random

object OrderGenerator {
    private val STATUS_ORDER = setOf("new", "confirmed", "not confirmed", "delivery", "canceled",
        "return", "pay_wait", "delivered")
    private val PAY_METHOD = setOf("online card", "offline card", "cash")

    fun execute(number: Int) {
        DBUtils.getConnection()

        val restaurants: Set<String> = DBUtils.getKeyColumn("SELECT id FROM restaurants")
        val clients: Set<String> = DBUtils.getKeyColumn("SELECT id FROM clients")
        val couriers: Set<String> = DBUtils.getKeyColumn("SELECT id FROM couriers")

        for (i in 0 until number) {
            //restaurant
            var restaurant: String
            var dishesMap: Map<String, Double>
            do {
                restaurant = restaurants.random()
                dishesMap = DBUtils.getColumnsPair(
                    "SELECT id, price FROM dishes WHERE restaurant_id = $restaurant"
                ).toMutableMap()
            } while (dishesMap.isEmpty())
            //price and content table block
            val contentMap = mutableMapOf<String, Int>()
            var price = 0.0
            dishesMap.forEach {
                if (Random.nextBoolean() || price == 0.0) {
                    val numOfDuplicates = Random.nextInt(1, 5)
                    contentMap[it.key] = numOfDuplicates
                    price += it.value * numOfDuplicates
                }
            }
            //order info block
            val orderTime = DataUtils.getRandomTime()
            val status = STATUS_ORDER.random()
            val payMethod = PAY_METHOD.random()
            var deliveryTime = "null"
            var courier = "null"
            if (status != "new" && status != "confirmed" && status != "not confirmed") {
                courier = couriers.random()
                if (status != "delivery" && status != "canceled" && status != "return" && status != "pay_wait")
                    deliveryTime = "'${DataUtils.getNextTime(orderTime)}'"
            }
            //update db block
            DBUtils.update(String.format(
                Locale.ROOT,
                "INSERT INTO \"order\"(" +
                        "client_id, restaurant_id, courier_id, price, time, status, delivery_time, pay_method" +
                        ") VALUES (%s, %s, %s, %.2f, '%s', '%s', %s,'%s')",
                clients.random(), restaurant, courier, price, orderTime, status, deliveryTime, payMethod
            ))
            generateContent(contentMap)
        }
        DBUtils.closeConnection()
    }

    private fun generateContent(map: MutableMap<String, Int>) {
        val lastOrder = DBUtils.getKeyColumn(
            "SELECT id FROM \"order\" WHERE id = (SELECT MAX(id) FROM \"order\")"
        ).last()
        map.forEach{
            DBUtils.update(String.format(
                "INSERT INTO order_content(" +
                        "order_id, dish_id, number" +
                        ") VALUES ('%s', '%s', %d)",
                lastOrder, it.key, it.value
            ))
        }
    }
}