package com.solodream.spring.vertx.req;


import com.solodream.spring.vertx.jpa.domain.ClientAccountInfoDto;

/**
 * RequestThreadLocal
 *
 * @author Young
 * @date 2015/11/21 0021
 */
public class RequestThreadLocal {


    private static final long serialVersionUID = 1L;

    private RequestThreadLocal() {
    }

    private final static RequestThreadLocal utl = new RequestThreadLocal();

    public static RequestThreadLocal getInstance() {
        return utl;
    }

    private final static ThreadLocal<ClientAccountInfoDto> _currentUser = new ThreadLocal<ClientAccountInfoDto>();

    private final static ThreadLocal<String> _currentLang = new ThreadLocal<String>();

    public ClientAccountInfoDto getUser() {
        return _currentUser.get();
    }

    public void setUser(ClientAccountInfoDto user) {
        _currentUser.set(user);
    }

    public String getCurrentLanguage() {
        String lang = _currentLang.get();
        return lang;
    }

    public void setCurrentLanguage(String url) {
        _currentLang.set(url);
    }

    public void clear() {
        _currentUser.remove();

        _currentLang.remove();
    }

}
