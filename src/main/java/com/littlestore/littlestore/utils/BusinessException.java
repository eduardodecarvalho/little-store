package com.littlestore.littlestore.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BusinessException extends RuntimeException {

    public static final String CLIENT_NOT_FOUND = "client_not_found";
    public static final String PRODUCT_NOT_FOUND = "product_not_found";
    public static final String CANNOT_DELETE_PRODUCT_IN_STOCK = "cannot_delete_product_in_stock";

    public BusinessException(final String message) {
        super(message);
    }
}
