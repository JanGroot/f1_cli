import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Parameters
import com.github.kittinunf.fuel.httpGet
import com.google.gson.GsonBuilder
import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment
import java.time.LocalDate

class F1 : CliktCommand() {
    override fun run() = Unit
}

class DriverCommands : CliktCommand(name = "drivers") {

    val season: Int by option(help = "Season year").int().default(LocalDate.now().year)

    override fun run() {
        FuelManager.instance.basePath ="http://ergast.com/api/f1/"
        val gson = GsonBuilder()
            .registerTypeAdapter(DriverResponse::class.java, DriverResponseDeserializer())
            .registerTypeAdapter(Driver::class.java, DriverDeserializer())
            .create()
        val responseString = "$season/drivers.json".httpGet(listOf("limit" to 100)).responseString().third.get()
        val driverResponse = gson.fromJson<DriverResponse>(responseString, DriverResponse::class.java)
        val table = AsciiTable()
        table.addRule()
        table.addRow(null, null, "$season season drivers").setTextAlignment(TextAlignment.CENTER)
        table.addRule()
        table.addRow("Driver name", "Date birth", "Nationality").setTextAlignment(TextAlignment.CENTER)
        table.addRule()
        driverResponse.drivers.forEach {driver ->
            table.addRow("${driver.givenName} ${driver.familyName}", driver.dateOfBirth, driver.nationality).setTextAlignment(TextAlignment.CENTER)

            table.addRule()
        }
        println(table.render())
    }

}

class CircuitCommands : CliktCommand(name="circuits") {

    val season: Int by option(help = "Season year").int().default(LocalDate.now().year)

    override fun run() {
        FuelManager.instance.basePath ="http://ergast.com/api/f1/"

        val gson = GsonBuilder()
            .registerTypeAdapter(CircuitResponse::class.java, CircuitResponseDeserializer())
            .registerTypeAdapter(Circuit::class.java, CircuitDeserializer())
            .create()
        val table = AsciiTable()
        table.addRule()
        table.addRow(null, null, "$season season circuits").setTextAlignment(TextAlignment.CENTER)
        table.addRule()
        table.addRow("Circuit", "Location", "Country").setTextAlignment(TextAlignment.CENTER)
        table.addRule()
        val responseString = "$season/circuits.json".httpGet(listOf("limit" to 100)).responseString().third.get()
        val circuitResponse = gson.fromJson<CircuitResponse>(responseString, CircuitResponse::class.java)
        circuitResponse.circuits.forEach {circuit ->
            table.addRow(circuit.name, circuit.location.locality, circuit.location.country).setTextAlignment(TextAlignment.CENTER)
            table.addRule()
        }
        println(table.render())
    }

}