import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment
import java.time.LocalDate
import kotlinx.serialization.*
import kotlinx.serialization.json.*

class F1 : CliktCommand() {
    override fun run() = Unit
}

class DriverCommands : CliktCommand(name = "drivers") {

    val season: Int by option(help = "Season year").int().default(LocalDate.now().year)

    override fun run() {
        FuelManager.instance.basePath = "http://ergast.com/api/f1/"
        val responseString = "$season/drivers.json".httpGet(listOf("limit" to 100)).responseString().third.get()
        val response =
            Json { ignoreUnknownKeys = true }.decodeFromString(MResponse.serializer(), responseString)
        val table = AsciiTable()
        table.addRule()
        table.addRow(null, null, "$season season drivers").setTextAlignment(TextAlignment.CENTER)
        table.addRule()
        table.addRow("Driver name", "Date birth", "Nationality").setTextAlignment(TextAlignment.CENTER)
        table.addRule()
        response.driverTable!!.drivers.forEach { driver ->
            table.addRow("${driver.givenName} ${driver.familyName}", driver.dateOfBirth, driver.nationality)
                .setTextAlignment(TextAlignment.CENTER)

            table.addRule()
        }
        println(table.render())
    }
}

class CircuitCommands : CliktCommand(name = "circuits") {

    val season: Int by option(help = "Season year").int().default(LocalDate.now().year)

    override fun run() {
        FuelManager.instance.basePath = "http://ergast.com/api/f1/"
        val table = AsciiTable()
        table.addRule()
        table.addRow(null, null, "$season season circuits").setTextAlignment(TextAlignment.CENTER)
        table.addRule()
        table.addRow("Circuit", "Location", "Country").setTextAlignment(TextAlignment.CENTER)
        table.addRule()
        val responseString = "$season/circuits.json".httpGet(listOf("limit" to 100)).responseString().third.get()
        val response =
            Json { ignoreUnknownKeys = true }.decodeFromString(MResponse.serializer(), responseString)
        response.circuitTable!!.circuits.forEach { circuit ->
            table.addRow(circuit.name, circuit.location.locality, circuit.location.country)
                .setTextAlignment(TextAlignment.CENTER)
            table.addRule()
        }
        println(table.render())
    }

}