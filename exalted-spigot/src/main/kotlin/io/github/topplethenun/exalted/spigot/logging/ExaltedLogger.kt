package io.github.topplethenun.exalted.spigot.logging

import io.pixeloutlaw.kindling.Log
import io.pixeloutlaw.kindling.LogRecord
import io.pixeloutlaw.kindling.Logger
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter
import java.util.Locale
import java.util.logging.FileHandler
import java.util.logging.Level

/**
 * Implementation of [Logger] for use within Exalted for Spigot.
 */
internal class ExaltedLogger(
    private val dataDirectory: File,
    private val pluginName: String,
    override val minimumLogLevel: Log.Level
) : Logger() {
    private companion object {
        const val FIVE = 5
        const val TWO_HUNDRED_FIFTY_SIX = 256
    }

    private val javaLogger: java.util.logging.Logger =
        java.util.logging.Logger.getLogger(javaClass.canonicalName).apply {
            level = Level.ALL
            addHandler(
                FileHandler(
                    String.format(Locale.ENGLISH, "%s/%s.log", dataDirectory.absolutePath, pluginName),
                    true
                ).apply {
                    level = Level.ALL
                    formatter = ExaltedLoggingFormatter()
                }
            )
            useParentHandlers = false
        }

    override fun print(logRecord: LogRecord) {
        val (level, tag, message, throwable) = logRecord

        val trace = Exception().stackTrace[FIVE]
        var logTag = tag
        if (tag.isEmpty()) {
            logTag = getTraceTag(trace)
        }

        var fullMessage = "$message"
        throwable?.let { fullMessage = "$fullMessage\n${it.stackTraceString}" }
        val msg = "$logTag: $fullMessage"

        when (level) {
            Log.Level.VERBOSE -> javaLogger.finest(msg)
            Log.Level.DEBUG -> javaLogger.fine(msg)
            Log.Level.INFO -> javaLogger.info(msg)
            Log.Level.WARNING -> javaLogger.warning(msg)
            Log.Level.ERROR -> javaLogger.severe(msg)
            Log.Level.ASSERT -> javaLogger.severe(msg)
        }
    }

    private val Throwable.stackTraceString
        get(): String {
            val sw = StringWriter(TWO_HUNDRED_FIFTY_SIX)
            val pw = PrintWriter(sw, false)
            printStackTrace(pw)
            pw.flush()
            return sw.toString()
        }

    private fun getTraceTag(trace: StackTraceElement): String {
        val className = trace.className.split(".").last()
        return "$className.${trace.methodName}"
    }
}
