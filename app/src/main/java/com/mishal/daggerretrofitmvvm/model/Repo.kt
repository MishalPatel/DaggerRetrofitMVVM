package com.mishal.daggerretrofitmvvm.model

import com.google.gson.annotations.SerializedName
import com.mishal.daggerretrofitmvvm.model.User

class Repo(
        val id: Long,
        val name: String,
        val description: String,
        val owner: User,
        @SerializedName("stargazers_count")
        val stars: Long,
        @SerializedName("forks_count")
        val forks: Long
)