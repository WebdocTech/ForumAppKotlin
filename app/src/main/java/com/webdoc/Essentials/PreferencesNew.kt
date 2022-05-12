package com.webdoc.Essentials

import android.content.Context
import android.content.SharedPreferences


class PreferencesNew(var ctx: Context) {
    var prefs: SharedPreferences
    var editor: SharedPreferences.Editor


    fun clearSharedPreferences() {
        editor.clear()
        editor.commit()
    }

    fun keyUserName(name: String) {

    }

    var keyIsLogin: Boolean?
        get() = prefs.getBoolean(
            PreferencesNew.Companion.KEY_IS_LOGIN,
            false
        )
        set(keyIsLogin) {
            editor.putBoolean(
                PreferencesNew.Companion.KEY_IS_LOGIN,
                keyIsLogin!!
            )
            editor.commit()
        }
    var keyIsRegister: Boolean?
        get() = prefs.getBoolean(
            PreferencesNew.Companion.KEY_IS_Register,
            false
        )
        set(keyIsRegister) {
            editor.putBoolean(
                PreferencesNew.Companion.KEY_IS_Register,
                keyIsRegister!!
            )
            editor.commit()
        }
    var keyIsFirstTime: Boolean?
        get() = prefs.getBoolean(
            PreferencesNew.Companion.KEY_IS_FirstTime,
            true
        )
        set(keyIsFirstTime) {
            editor.putBoolean(
                PreferencesNew.Companion.KEY_IS_FirstTime,
                keyIsFirstTime!!
            )
            editor.commit()
        }
    var keyUserName: String?
        get() = prefs.getString(
            PreferencesNew.Companion.KEY_USER_NAME,
            ""
        )
        set(FName) {
            editor.putString(
                PreferencesNew.Companion.KEY_USER_NAME,
                FName
            )
            editor.commit()
        }
    var keyUserPhone: String?
        get() = prefs.getString(
            PreferencesNew.Companion.KEY_USER_PHONE,
            ""
        )
        set(keyUserPhone) {
            editor.putString(
                PreferencesNew.Companion.KEY_USER_PHONE,
                keyUserPhone
            )
            editor.commit()
        }
    var keyUserEmail: String?
        get() = prefs.getString(
            PreferencesNew.Companion.KEY_USER_EMAIL,
            ""
        )
        set(keyEmail) {
            editor.putString(
                PreferencesNew.Companion.KEY_USER_EMAIL,
                keyEmail
            )
            editor.commit()
        }
    var kEY_USER_City: String?
        get() = prefs.getString(
            PreferencesNew.Companion.KEY_USER_IMAGE,
            ""
        )
        set(city) {
            editor.putString(
                PreferencesNew.Companion.KEY_USER_IMAGE,
                city
            )
            editor.commit()
        }
    var kEY_USER_Pin: String?
        get() = prefs.getString(
            PreferencesNew.Companion.KEY_USER_Pin,
            ""
        )
        set(pin) {
            editor.putString(PreferencesNew.Companion.KEY_USER_Pin, pin)
            editor.commit()
        }
    var kEY_ApplicationUserId: String?
        get() = prefs.getString(
            PreferencesNew.Companion.KEY_ApplicationUserId,
            ""
        )
        set(appUserId) {
            editor.putString(
                PreferencesNew.Companion.KEY_ApplicationUserId,
                appUserId
            )
            editor.commit()
        }
    var kEY_USER_Address: String?
        get() = prefs.getString(
            PreferencesNew.Companion.KEY_USER_Balance,
            ""
        )
        set(address) {
            editor.putString(
                PreferencesNew.Companion.KEY_USER_Balance,
                address
            )
            editor.commit()
        }

    companion object {
        const val KEY_IS_LOGIN = "isLogin"
        const val KEY_IS_Register = "isRegister"
        private const val KEY_IS_FirstTime = "isFirstTime"
        const val KEY_USER_EMAIL = "userEmail"
        private const val KEY_USER_Pin = "userPin"
        const val KEY_ApplicationUserId = "applicationUserId"
        const val KEY_USER_NAME = "userName"
        const val KEY_USER_PHONE = "userPhone"
        const val KEY_USER_IMAGE = "userImage"
        const val KEY_USER_Balance = "userBal"
        var mPref: PreferencesNew? = null
        fun getInstance(mContext: Context?): PreferencesNew {
            if (PreferencesNew.Companion.mPref == null) {
                PreferencesNew.Companion.mPref =
                    PreferencesNew(mContext!!)
            }
            return PreferencesNew.Companion.mPref!!
        }
    }

    init {
        prefs = ctx.getSharedPreferences("apna_darzi", Context.MODE_PRIVATE)
        editor = prefs.edit()
    }
}