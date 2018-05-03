package com.example.abdallahsarayrah.smscontactsh

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE), 123)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 123 && grantResults[0] == PackageManager.PERMISSION_GRANTED) showCon()
    }

    @SuppressLint("Recycle")
    private fun showCon() {

        val list = ArrayList<String>()
        val cur = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)
        cur.moveToFirst()
        while (!cur.isAfterLast) {
            list.add(cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)) + "\n" + cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)))
            cur.moveToNext()
        }
        val adp = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        lv.adapter = adp
    }
}
