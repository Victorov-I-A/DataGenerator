import tableGenerators.*

fun main() {
    ClientGenerator.execute(100)
    RestaurantGenerator.execute(15)
    DishGenerator.execute(250)
    CourierGenerator.execute(10)
    VehicleGenerator.execute(20)
    OrderGenerator.execute(50)
    ReviewGenerator.execute(100)
}