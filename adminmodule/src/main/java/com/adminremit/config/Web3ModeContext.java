package com.adminremit.config;

/**
 * WEB3 migration changes - Thread-local context to store Web3 mode state
 * This allows Hibernate to access the current mode during table name resolution
 */
public class Web3ModeContext {

    private static final ThreadLocal<Boolean> web3Mode = new ThreadLocal<>();

    /**
     * Set Web3 mode for current thread
     * @param isWeb3Mode true if Web3 mode is enabled
     */
    public static void setWeb3Mode(boolean isWeb3Mode) {
        web3Mode.set(isWeb3Mode);
    }

    /**
     * Get Web3 mode for current thread
     * @return true if Web3 mode is enabled, false otherwise (defaults to false)
     */
    public static boolean isWeb3Mode() {
        Boolean mode = web3Mode.get();
        return mode != null && mode;
    }

    /**
     * Clear Web3 mode for current thread (cleanup)
     */
    public static void clear() {
        web3Mode.remove();
    }
}

