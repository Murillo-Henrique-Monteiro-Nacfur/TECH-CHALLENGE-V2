package org.hospital.core.infrastructure;

import org.springframework.security.core.userdetails.UserDetails;

public class ThreadLocalStorage {
    private static ThreadLocal<UserDetails> userTokenThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<String> tokenThreadLocal = new ThreadLocal<>();

    private ThreadLocalStorage() {
    }

    public static String getToken() {
        if (tokenThreadLocal.get() != null) {
            return tokenThreadLocal.get();
        }
        return "";
    }

    public static UserDetails getUserDetails() {
        if (userTokenThreadLocal.get() != null) {
            return userTokenThreadLocal.get();
        }
        return null;
    }

    public static void build(UserDetails userDetails, String token) {
        ThreadLocalStorage.userTokenThreadLocal.set(userDetails);
        ThreadLocalStorage.tokenThreadLocal.set(token);
    }

    public static void clear() {
        ThreadLocalStorage.userTokenThreadLocal.remove();
        ThreadLocalStorage.tokenThreadLocal.remove();
    }
}
