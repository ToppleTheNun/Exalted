package io.github.topplethenun.exalted.spigot.logging

import java.io.PrintWriter
import java.io.StringWriter
import java.text.MessageFormat
import java.util.Date
import java.util.Locale
import java.util.logging.Formatter
import java.util.logging.LogRecord

/**
 * [Formatter] instance that converts [LogRecord]s into standard usable formats.
 */
internal class ExaltedLoggingFormatter : Formatter() {
    private companion object {
        const val DATE_TIME_FORMAT = "{0,date} {0,time}"
    }

    private val date: Date = Date()
    private val formatter: MessageFormat by lazy { MessageFormat(DATE_TIME_FORMAT, Locale.ENGLISH) }
    private val lineSeparator: String = System.lineSeparator()

    /**
     * Attempts to format the log record into a standard usable format.
     *
     * @param record log record instance
     *
     * @return log message
     */
    override fun format(record: LogRecord): String {
        val stringBuilder = StringBuilder(getDateTime(record))
            .append(" ")
            .append(record.level.localizedName)
            .append(": ")
            .append(formatMessage(record))
            .append(lineSeparator)

        // add the exception to the message if there is one
        record.thrown?.let {
            addThrowableToStringBuilder(it, stringBuilder)
        }

        return stringBuilder.toString()
    }

    private fun addThrowableToStringBuilder(throwable: Throwable, stringBuilder: java.lang.StringBuilder) {
        try {
            StringWriter().use { stringWriter ->
                PrintWriter(stringWriter).use { printWriter ->
                    throwable.printStackTrace(printWriter)
                }
                stringBuilder.append(stringWriter.buffer)
            }
        } catch (ignored: Exception) {
            // if we get an exception while serializing an exception, just eat it
        }
    }

    private fun getDateTime(record: LogRecord): StringBuffer {
        // we want to minimize memory allocations so we reuse the `date` value from above
        date.time = record.millis

        return formatter.format(arrayOf(date), StringBuffer(), null)
    }
}
