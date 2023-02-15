package io.github.topplethenun.exalted

/**
 * Represents the Exalted core, for version and instance singleton handling.
 */
interface Exalted {
    companion object {
        /**
         * Singleton instance of the [Exalted] interface. An implementation will be provided by any Exalted plugins.
         */
        @JvmStatic
        var exalted: Exalted? = null
    }

    /**
     * Does any necessary setup for Exalted.
     */
    fun onEnable()

    /**
     * Does any necessary teardown for Exalted.
     */
    fun onDisable()
}
