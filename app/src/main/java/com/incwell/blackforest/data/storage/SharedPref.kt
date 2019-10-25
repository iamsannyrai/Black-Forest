package com.incwell.blackforest.data.storage

import com.incwell.blackforest.data.model.UserToken
import com.orhanobut.hawk.Hawk

interface SharedPref {
    companion object {
        fun saveToken(key: String, token: UserToken) {
            Hawk.put(key, token)
        }

        fun deleteToken(key: String) {
            Hawk.delete(key)
        }

        fun checkToken(key: String): Boolean {
            return Hawk.contains(key)
        }

        fun getToken(key: String): UserToken {
            return Hawk.get(key)
        }
    }
}