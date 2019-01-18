package com.rootlol.chatrootlol;

import java.util.List;

import com.rootlol.chatrootlol.objMess.bodyMess;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    @GET("load")
    Call<List<bodyMess>> messages();

}