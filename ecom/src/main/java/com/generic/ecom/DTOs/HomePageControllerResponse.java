package com.generic.ecom.DTOs;

public record HomePageControllerResponse<T>(boolean success,String message, T data) {
}