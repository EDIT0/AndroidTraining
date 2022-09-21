package com.example.databindingdemo1

class Repository {
    fun getData() : Profile {
        val user = Profile("Kim", 100, "https://source.unsplash.com/user/c_v_r/1900x800", true)

        return user
    }

    fun getClickData() : Profile {
        val user = Profile("Eun", 300, "https://source.unsplash.com/user", false)

        return user
    }
}