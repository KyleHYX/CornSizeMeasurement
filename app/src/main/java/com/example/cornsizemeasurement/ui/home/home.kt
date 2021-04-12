package com.example.cornsizemeasurement.ui.home

import android.app.Activity
import android.app.ProgressDialog
import android.bluetooth.*
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cornsizemeasurement.R
import java.io.IOException
import java.util.*


class home : Fragment() {

    companion object {
        fun newInstance() = home()
        //var uuid: UUID = UUID.fromString("00000001-0000-0000-1000-000111110000")
        lateinit var uuid: UUID
        var bluetoothSocket: BluetoothSocket? = null
        lateinit var progress: ProgressDialog
        lateinit var address: String
    }

    private lateinit var viewModel: HomeViewModel

    private val BT_ENABLE_CODE: Int = 1
    private val BT_REQUEST_CODE: Int = 2;
    lateinit var btAdapter: BluetoothAdapter
    lateinit var v : View
    lateinit var pbt: pairedBTAdapter

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
        val sendON: Button = v.findViewById(R.id.sendON);

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

        var pairedDevices = btAdapter.bondedDevices

        address = btAdapter.address

        /*if(pairedDevices.size > 0) {
            for(device in pairedDevices) {
                if(device.name == "Hongye’s MacBook Pro (2)") {
                    uuid = UUID.fromString(device.uuids[0].toString())
                    address = device.address
                    Toast.makeText(v.context, address, Toast.LENGTH_LONG).show()
                }
            }
        }

        setBluetoothSocket()
        */

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
                /*
                pairedDeviceTV.text = "Pair Devices"

                val deviceList = btAdapter.bondedDevices
                for(device in deviceList) {
                    val deviceName = device.name
                    val deviceAddress = device
                    pairedDeviceTV.append("\nDevice: $deviceName, $device")
                }*/
                val deviceList = btAdapter.bondedDevices
                showPairedList(ArrayList(deviceList));
            }
            else {
                Toast.makeText(v.context, "Make sure your Bluetooth is turned on", Toast.LENGTH_LONG).show()
            }
        }

        // recycle list of bluetooth devices
        val paired_bt_list: RecyclerView = v.findViewById(R.id.paired_bt_list)
        var bluetoothDeviceList: List<BluetoothDevice> = emptyList()

        pbt = pairedBTAdapter(bluetoothDeviceList, object : ClickListener {
            override fun onConnectClicked(position: Int) {
                var selectedBTDevice: BluetoothDevice = pairedDevices.elementAt(position)
                connectDevice(selectedBTDevice)
            }

            override fun onDisconnectClicked(position: Int) {
                disconnectDevice(position)
            }
        })

        paired_bt_list.adapter = pbt
        paired_bt_list.layoutManager = LinearLayoutManager(this.context)

        // send ON signal
        sendON.setOnClickListener {
            sendMsg("ON")
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

    private fun sendMsg(msg: String) {
        if (bluetoothSocket != null) {
            try {
                if(bluetoothSocket!!.outputStream != null) {

                    bluetoothSocket!!.outputStream.write(msg.toByteArray())
                }
            }
            catch (e: IOException){
                e.printStackTrace()
            }
        }
    }

    private fun setBluetoothSocket() {
        try {
            val device: BluetoothDevice = btAdapter.getRemoteDevice(address)
            bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(uuid)
            bluetoothSocket!!.connect()
        }
        catch(e: IOException) {
            e.printStackTrace()
        }
    }

    private fun connectDevice(selectedDevice: BluetoothDevice) {
        uuid = UUID.fromString(selectedDevice.uuids[0].toString())
        address = selectedDevice.address
        Toast.makeText(v.context, address, Toast.LENGTH_LONG).show()

        setBluetoothSocket()
    }

    private fun disconnectDevice(position: Int) {

    }

    private fun showPairedList(deviceList: List<BluetoothDevice>) {
        pbt.setData(deviceList);
    }
}