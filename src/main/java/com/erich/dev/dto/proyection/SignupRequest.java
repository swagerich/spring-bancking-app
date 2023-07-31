package com.erich.dev.dto.proyection;

public record SignupRequest(String firstName, String lastName,String userName ,String email, String password,String repeatPassword,Integer age) {
}
