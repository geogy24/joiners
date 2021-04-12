package com.joiner.main.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception when language level is not found
 *
 * @author Jorge Díaz
 * @version 1.0.0
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Language level Not Found")
public class LanguageLevelNotFoundException extends Exception {

}