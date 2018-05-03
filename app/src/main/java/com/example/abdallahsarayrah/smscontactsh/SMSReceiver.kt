package com.example.abdallahsarayrah.smscontactsh

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.ContactsContract
import android.telephony.SmsManager
import android.telephony.SmsMessage

/**
 * Created by abdallah.sarayrah on 12/6/2017.
 */

class SMSReceiver : BroadcastReceiver() {
    @SuppressLint("Recycle")
    override fun onReceive(context: Context, intent: Intent) {
        val pdus = intent.extras.get("pdus") as Array<*>
        var mobile = ""
        var txt = ""
        val code: String
        val name: String
        val search: String
        var result = ""
        for (index in 0 until pdus.size) {
            val sms = SmsMessage.createFromPdu(pdus[index] as ByteArray)
            mobile = sms.originatingAddress
            txt = sms.displayMessageBody
        }
        code = txt.substring(1, 4)
        name = txt.substringAfterLast("#")

        if (code == "123") {
            search = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " like '%$name%'"
            val cur = context.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, search, null, null)
            cur.moveToFirst()
            while (!cur.isAfterLast) {
                result += "\n" + cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)) + "\n" + cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                cur.moveToNext()
            }
            val sms = SmsManager.getDefault()
            sms.sendTextMessage(mobile, null, result, null, null)
        }
    }
}
