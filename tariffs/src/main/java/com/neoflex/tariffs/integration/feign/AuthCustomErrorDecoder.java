package com.neoflex.tariffs.integration.feign;

import com.neoflex.tariffs.exception.AccessException;
import com.neoflex.tariffs.exception.AuthorizationException;
import com.neoflex.tariffs.exception.UserNotFoundException;
import com.neoflex.tariffs.exception.ValidationException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class AuthCustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        return switch (response.status()) {
            case 400 -> new ValidationException("Auth - проверка данных не пройдена");
            case 403 -> new AccessException("Auth - доступ запрещен");
            case 404 -> new UserNotFoundException("Auth - юзер не найден");
            case 409 -> new AuthorizationException("Auth - токен не прошел проверку");
            default -> new Exception("Tariff");
        };
    }
}