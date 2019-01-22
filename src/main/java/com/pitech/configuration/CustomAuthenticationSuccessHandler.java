package com.pitech.configuration;

import com.pitech.dtos.ShoppingCartDto;
import com.pitech.utils.CookieUtils;
import com.pitech.utils.EncodeDecodeCookieUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Getter
@Setter
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();



    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        try {

            ShoppingCartDto cookieShoppingCart = EncodeDecodeCookieUtils.decode(CookieUtils.getCookie(request).getValue(), ShoppingCartDto.class);
//            cartServiceAuthenticated.addItemsFromCookieInShoppingCart(cookieShoppingCart);
            CookieUtils.setCookie(response, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
