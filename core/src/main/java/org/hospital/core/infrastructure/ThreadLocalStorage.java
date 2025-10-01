package org.hospital.core.infrastructure;

import io.grpc.Context;
import org.hospital.core.domain.dto.UserAuthenticatedDTO;
import org.springframework.security.core.userdetails.UserDetails;

public class ThreadLocalStorage {
    private static ThreadLocal<String> tokenThreadLocal = new ThreadLocal<>();
    public static final Context.Key<UserAuthenticatedDTO> USER_AUTHENTICATION_DTO_CONTEXT_KEY = Context.key("threadLocalUserAuthenticatedDTO");


    private ThreadLocalStorage() {
    }

    public static String getToken() {
        if (tokenThreadLocal.get() != null) {
            return tokenThreadLocal.get();
        }
        return "";
    }

    public static UserAuthenticatedDTO getUserAuthenticatedDTO() {
        if (USER_AUTHENTICATION_DTO_CONTEXT_KEY.get() != null) {
            return USER_AUTHENTICATION_DTO_CONTEXT_KEY.get();
        }
        return null;
    }

    public static void setTokenThreadLocal(String token) {
        ThreadLocalStorage.tokenThreadLocal.set(token);
    }

    public static void clear() {
        ThreadLocalStorage.tokenThreadLocal.remove();
    }
}
