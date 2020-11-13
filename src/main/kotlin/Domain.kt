import kotlinx.serialization.*

@Serializable
data class MResponse(val series: String?, val limit: Int, val offset:Int, val total:Int, val driverTable: DriverResponse?, val circuitTable: CircuitResponse?)
@Serializable
data class Driver(val driverId: String, val code: String?, val url: String? = "", val givenName: String, val familyName: String, val dateOfBirth: String?, val nationality: String?)
@Serializable
data class DriverResponse(val season: Int, val drivers: List<Driver>)
@Serializable
data class Location(val lat: Float?, val lon: Float?, val locality: String?, val country: String?)
@Serializable
data class Circuit(val circuitId: String, val url: String?, val name: String, val location: Location)
@Serializable
data class CircuitResponse(val season: Int, val circuits: List<Circuit>)
