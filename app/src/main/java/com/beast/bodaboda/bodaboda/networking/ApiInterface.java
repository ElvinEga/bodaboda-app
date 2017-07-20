package com.beast.bodaboda.bodaboda.networking;

import com.beast.bodaboda.bodaboda.model.request.LoginRequest;
import com.beast.bodaboda.bodaboda.model.request.RegisterCustomerRequest;
import com.beast.bodaboda.bodaboda.model.request.RegisterRequest;
import com.beast.bodaboda.bodaboda.model.response.Login;
import com.beast.bodaboda.bodaboda.model.response.Register;
import com.beast.bodaboda.bodaboda.model.response.RegisterCustomer;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by beast on 7/17/17.
 */

public interface ApiInterface {
    //url points

    //login
    @POST("/login")
    Call<Login> postLogin(@Body LoginRequest loginRequest);

    //register user
    @POST("/register")
    Call<Register> postRegister(@Body RegisterRequest registerRequest);

    //register Customer'
    @POST("/registerCustomer")
    Call<RegisterCustomer> postRegisterCustomer(@Body RegisterCustomerRequest registerCustomerRequest);

    }

