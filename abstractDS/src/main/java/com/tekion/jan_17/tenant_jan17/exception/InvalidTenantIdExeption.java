package com.tekion.jan_17.tenant_jan17.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "DataSource not found for given tenant Id!")
public class InvalidTenantIdExeption extends RuntimeException  {
}
