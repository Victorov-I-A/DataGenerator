import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException


object DBUtils {
    private lateinit var connection: Connection
    private object DBCredentials {
        const val USER = "postgres"
        const val PASSWORD = "********"
        const val URL = "jdbc:postgresql://localhost:5432/fooddelivery"
    }

    fun getConnection() {
        try {
            connection = DriverManager.getConnection(
                DBCredentials.URL,
                DBCredentials.USER,
                DBCredentials.PASSWORD)
        } catch (e: SQLException) {
            throw e
        }
    }

    fun getKeyColumn(sql: String): MutableSet<String> {
        try {
            val set = mutableSetOf<String>()
            val statement = connection.createStatement()
            val rs: ResultSet = statement.executeQuery(sql)
            while (rs.next()) {
                if (rs.getObject(1) != null)
                set.add(rs.getString(1))
            }
            return set
        } catch (e: SQLException) {
            throw e
        }
    }

    fun getColumnsPair(sql: String): MutableMap<String, Double> {
        try {
            val map = mutableMapOf<String, Double>()
            val statement = connection.createStatement()
            val rs: ResultSet = statement.executeQuery(sql)
            while (rs.next()) {
                if (rs.getObject(1) != null && rs.getObject(2) != null)
                    map[rs.getString(1)] = rs.getDouble(2)
            }
            return map
        } catch (e: SQLException) {
            throw e
        }
    }

    fun update(str: String) {
        try {
            val statement = connection.createStatement()
            statement?.executeUpdate(str)
        } catch (e: SQLException) {
            throw e
        }
    }

    fun closeConnection() {
        try {
            connection.close()
        } catch (e: SQLException) {
            throw e
        }
    }
}