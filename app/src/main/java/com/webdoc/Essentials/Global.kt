package com.webdoc.Essentials

import android.app.Activity
import android.app.Application
import android.net.Uri
import android.util.Base64
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.webdoc.Models.PaymentModel
import com.webdoc.webviewlibaray.JsBankWallet.ResponseModels.JsBankAuthApi
import com.webdoc.webviewlibaray.JsBankWallet.ResponseModels.JsDebitInquiryResult.JsDebitInquiryResult
import com.webdoc.webviewlibaray.JsBankWallet.ResponseModels.JsDebitPaymentResponse.JsDebitPaymentResponse
import java.security.KeyFactory
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher

public class Global : Application() {


    companion object {
        fun enccriptData(strToEncrypt: String): String? {
            var encoded: String? = ""
            var encrypted: ByteArray? = null
            try {
                val publicBytes = Base64.decode(
                    "\"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiO1lWgkTZeDWQgXlDF8t92YLYZm/ENvCvKPJNuj9WZfGCF5RIUFaYolb/HAhoAHKxgYRUS81WFfHuMROT+B/d0cW+Ii/sqLzTfFjepExonCj1I8m4WLdBAdZCRlWLo+bdO39OpxfK14XaPmRMdb8+uTpZ0hZBhDzZDnXChCm4fgsn63ZT2VEHdHX8PgmKTViR4VXsvyZCkT60FiEix2JdLCuSGF+tPr9GQnlSDJK4vRCZl+/TD/IaIbeAFWcx0Y6kdLpUBBUHbxY8cXcsr/HfJ6/WMEBSlUCOvbZhrx41yC/182WMPppaqCDeDamDV2T+ufzrQkT1nU40gm9h7uoXwIDAQAB\"",
                    Base64.DEFAULT
                )
                val keySpec = X509EncodedKeySpec(publicBytes)
                val keyFactory = KeyFactory.getInstance("RSA")
                val pubKey = keyFactory.generatePublic(keySpec)
                val cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING") //or try with "RSA"
                cipher.init(Cipher.ENCRYPT_MODE, pubKey)
                encrypted = cipher.doFinal(strToEncrypt.toByteArray())
                encoded = Base64.encodeToString(encrypted, Base64.DEFAULT)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return encoded
        }
        var jsPaymentFinal: JsDebitPaymentResponse = JsDebitPaymentResponse()
        var JS_Wallet_Account_Number: kotlin.String? = null
        var newToken: String? = null
        var paymentInquiry = JsDebitInquiryResult()
        fun hideKeyboard(activity: Activity) {
            val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            //Find the currently focused view, so we can grab the correct window token from it.
            var view = activity.currentFocus
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = View(activity)
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        val price: Any = 10
        var authApiResp = JsBankAuthApi()
        var applicationContext: Activity? = null

        @JvmField
        var paymentTitle = java.util.ArrayList<PaymentModel>()
        var utils: Utils? = Utils()
        val APARTMENT_ID_KEY: String? = "wazifa_id"
        var uriArrayList = ArrayList<Uri>()
    }
}