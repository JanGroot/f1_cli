import com.github.ajalt.clikt.core.subcommands

fun main(args: Array<String>) {
    F1().subcommands(DriverCommands(), CircuitCommands()).main(args)
}