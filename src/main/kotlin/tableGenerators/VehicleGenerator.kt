package tableGenerators

object VehicleGenerator {
    private val VEHICLE_TYPE = setOf("car", "bicycle", "scooter")
    private val STATUS_VEHICLE = setOf("on base", "courier take", "repair", "not available", "no longer available")

    fun execute(number: Int) {
        DBUtils.getConnection()

        val couriersFree: MutableList<String> = DBUtils.getKeyColumn(
            "SELECT couriers.id FROM couriers " +
                "LEFT JOIN vehicles ON couriers.id = vehicles.courier_id " +
                "WHERE vehicles.courier_id IS NULL").shuffled().toMutableList()
        val size = couriersFree.size

        for (i in 0 until number) {
            val courierId = if (i >= size) null else {
                couriersFree[0]
                couriersFree.removeAt(0)
            }
            DBUtils.update(String.format(
                "INSERT INTO vehicles(courier_id, vehicle_type, status) VALUES (%s,'%s','%s')",
                courierId, VEHICLE_TYPE.random(), STATUS_VEHICLE.random()
            ))
        }
        DBUtils.closeConnection()
    }
}