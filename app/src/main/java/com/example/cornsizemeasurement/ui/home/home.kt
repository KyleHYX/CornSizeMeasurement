package com.example.cornsizemeasurement.ui.home

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.cornsizemeasurement.R

class home : Fragment() {

    companion object {
        fun newInstance() = home()
    }

    private lateinit var viewModel: HomeViewModel

    private val BT_ENABLE_CODE: Int = 1
    private val BT_REQUEST_CODE: Int = 2;
    lateinit var btAdapter: BluetoothAdapter
    lateinit var v : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v =  inflater.inflate(R.layout.home_fragment, container, false)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        val statusBoardBtn: TextView = v.findViewById(R.id.statusBoard)
        val btIcon: ImageView = v.findViewById(R.id.bt)
        val btOnBtn: Button = v.findViewById(R.id.btOn)
        val btOffBtn: Button = v.findViewById(R.id.btOff)
        val discoverableBtn: Button = v.findViewById(R.id.discoverable)
        val pairedDeviceBtn: Button = v.findViewById(R.id.paired)
        val pairedDeviceTV: TextView = v.findViewById(R.id.pairedDevicesTV)

        //bluetooth adapter
        btAdapter = BluetoothAdapter.getDefaultAdapter()

        if(btAdapter == null) {
            statusBoardBtn.text = "Bluetooth is currently unavailable, check your phone setting"
        }
        else {
            statusBoardBtn.text = ""
        }

        //switch the bluetooth image on/off
        if(btAdapter.isEnabled) {
            btIcon.setImageResource((R.drawable.ic_baseline_bluetooth_24))
        }
        else {
            btIcon.setImageResource(R.drawable.ic_baseline_bluetooth_disabled_24)
        }

        //bluetooth on
        btOnBtn.setOnClickListener {
            if(!btAdapter.isEnabled) {
                val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(intent, BT_ENABLE_CODE)
            }
            else {
                Toast.makeText(v.context, "Bluetooth Already ON", Toast.LENGTH_LONG).show();
            }
        }

        btOffBtn.setOnClickListener {
            if(!btAdapter.isEnabled) {
                Toast.makeText(v.context, "Bluetooth Already OFF", Toast.LENGTH_LONG).show()
            }
            else {
                btAdapter.disable()
                btIcon.setImageResource(R.drawable.ic_baseline_bluetooth_disabled_24)
            }
        }

        discoverableBtn.setOnClickListener {
            if(!btAdapter.isDiscovering) {
                val intent = Intent(Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE))
                startActivityForResult(intent, BT_REQUEST_CODE)
            }
        }

        pairedDeviceBtn.setOnClickListener {
            if(btAdapter.isEnabled) {
                pairedDeviceTV.text = "Pair Devices"

                val deviceList = btAdapter.bondedDevices
                for(device in deviceList) {
                    val deviceName = device.name
                    val deviceAddress = device
                    pairedDeviceTV.append("\nDevice: $deviceName, $device")
                }
            }
            else {
                Toast.makeText(v.context, "Make sure your Bluetooth is turned on", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode) {
            BT_ENABLE_CODE ->
                if(resultCode == Activity.RESULT_OK) {
                    val btIcon: ImageView = v.findViewById(R.id.bt)
                    btIcon.setImageResource(R.drawable.ic_baseline_bluetooth_24)
                    Toast.makeText(v.context, "Bluetooth ON", Toast.LENGTH_LONG).show()
                }
                else {
                    Toast.makeText(v.context, "Couldn't turn on Bluetooth, check your settings", Toast.LENGTH_LONG).show();
                }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

}