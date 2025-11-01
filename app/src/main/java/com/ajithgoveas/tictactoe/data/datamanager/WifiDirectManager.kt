package com.ajithgoveas.tictactoe.data.datamanager // Your package
//
//import android.Manifest
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import android.content.IntentFilter
//import android.content.pm.PackageManager
//import android.net.NetworkInfo
//import android.net.wifi.p2p.WifiP2pConfig
//import android.net.wifi.p2p.WifiP2pDevice
//import android.net.wifi.p2p.WifiP2pDeviceList
//import android.net.wifi.p2p.WifiP2pInfo
//import android.net.wifi.p2p.WifiP2pManager
//import android.os.Build
//import android.os.Looper
//import android.util.Log
//import androidx.core.app.ActivityCompat
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.Job
//import kotlinx.coroutines.isActive
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//import java.io.IOException
//import java.io.InputStream
//import java.io.OutputStream
//import java.net.InetAddress
//import java.net.InetSocketAddress
//import java.net.ServerSocket
//import java.net.Socket
//
//private const val TAG = "WifiDirectManager"
//private const val SERVER_PORT = 8888 // Choose a port for your game
//
//class WifiDirectManager(private val context: Context) {
//
//    private val wifiP2pManager: WifiP2pManager? by lazy(LazyThreadSafetyMode.NONE) {
//        context.getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager?
//    }
//    private var channel: WifiP2pManager.Channel? = null
//    private var receiver: WiFiDirectBroadcastReceiver? = null
//    private val intentFilter = IntentFilter()
//
//    private var serverSocket: ServerSocket? = null
//    private var clientSocket: Socket? = null
//    private var inputStream: InputStream? = null
//    private var outputStream: OutputStream? = null
//
//    private val coroutineScope = CoroutineScope(Dispatchers.IO + Job())
//
//    var onPeersChanged: ((List<WifiP2pDevice>) -> Unit)? = null
//    var onConnectionInfoAvailable: ((WifiP2pInfo) -> Unit)? = null
//    var onDeviceAddressAvailable: ((String?) -> Unit)? = null
//    var onDataReceived: ((String) -> Unit)? = null
//    var onStatusChanged: ((String) -> Unit)? = null // For UI updates
//
//    init {
//        channel = wifiP2pManager?.initialize(context, Looper.getMainLooper(), null)
//        setupIntentFilter()
//    }
//
//    private fun setupIntentFilter() {
//        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
//        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
//        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
//        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)
//    }
//
//    fun registerReceiver() {
//        if (receiver == null) {
//            receiver = WiFiDirectBroadcastReceiver()
//            context.registerReceiver(receiver, intentFilter)
//            onStatusChanged?.invoke("BroadcastReceiver registered.")
//        }
//    }
//
//    fun unregisterReceiver() {
//        try {
//            if (receiver != null) {
//                context.unregisterReceiver(receiver)
//                receiver = null
//                onStatusChanged?.invoke("BroadcastReceiver unregistered.")
//            }
//        } catch (e: IllegalArgumentException) {
//            Log.e(TAG, "Receiver not registered or already unregistered: ${e.message}")
//        }
//    }
//
//    fun discoverPeers() {
//        if (!checkPermissions()) {
//            onStatusChanged?.invoke("Required permissions missing for discovery.")
//            return
//        }
//        wifiP2pManager?.discoverPeers(channel, object : WifiP2pManager.ActionListener {
//            override fun onSuccess() {
//                onStatusChanged?.invoke("Peer discovery initiated.")
//                Log.d(TAG, "Peer discovery initiated")
//            }
//
//            override fun onFailure(reasonCode: Int) {
//                onStatusChanged?.invoke("Peer discovery failed: ${errorReasonToString(reasonCode)}")
//                Log.e(TAG, "Peer discovery failed: $reasonCode")
//            }
//        })
//    }
//
//    fun stopPeerDiscovery() {
//        wifiP2pManager?.stopPeerDiscovery(channel, object : WifiP2pManager.ActionListener {
//            override fun onSuccess() {
//                onStatusChanged?.invoke("Peer discovery stopped.")
//                Log.d(TAG, "Peer discovery stopped")
//            }
//
//            override fun onFailure(reasonCode: Int) {
//                onStatusChanged?.invoke(
//                    "Stopping peer discovery failed: ${
//                        errorReasonToString(
//                            reasonCode
//                        )
//                    }"
//                )
//                Log.e(TAG, "Stopping peer discovery failed: $reasonCode")
//            }
//        })
//    }
//
//    fun createGroup() {
//        if (!checkPermissions()) {
//            onStatusChanged?.invoke("Permissions missing for creating group.")
//            return
//        }
//        wifiP2pManager?.createGroup(channel, object : WifiP2pManager.ActionListener {
//            override fun onSuccess() {
//                onStatusChanged?.invoke("Group creation initiated. Waiting for connection info...")
//                Log.d(TAG, "Group created successfully.")
//                // Connection info will be available via WIFI_P2P_CONNECTION_CHANGED_ACTION
//            }
//
//            override fun onFailure(reasonCode: Int) {
//                onStatusChanged?.invoke("Group creation failed: ${errorReasonToString(reasonCode)}")
//                Log.e(TAG, "Group creation failed: $reasonCode")
//            }
//        })
//    }
//
//    fun removeGroup() {
//        wifiP2pManager?.removeGroup(channel, object : WifiP2pManager.ActionListener {
//            override fun onSuccess() {
//                onStatusChanged?.invoke("Group removed.")
//                Log.d(TAG, "Group removed successfully.")
//            }
//
//            override fun onFailure(reasonCode: Int) {
//                onStatusChanged?.invoke("Failed to remove group: ${errorReasonToString(reasonCode)}")
//                Log.e(TAG, "Failed to remove group: $reasonCode")
//            }
//        })
//    }
//
//
//    fun connect(device: WifiP2pDevice) {
//        if (!checkPermissions()) {
//            onStatusChanged?.invoke("Permissions missing for connecting.")
//            return
//        }
//        val config = WifiP2pConfig().apply {
//            deviceAddress = device.deviceAddress
//            // groupOwnerIntent = 0 // Optional: Set to 0 to be a client, 15 to be a group owner
//        }
//        wifiP2pManager?.connect(channel, config, object : WifiP2pManager.ActionListener {
//            override fun onSuccess() {
//                onStatusChanged?.invoke("Connection to ${device.deviceName} initiated.")
//                Log.d(TAG, "Connection initiated to ${device.deviceName}")
//                // Connection info will be available via WIFI_P2P_CONNECTION_CHANGED_ACTION
//            }
//
//            override fun onFailure(reasonCode: Int) {
//                onStatusChanged?.invoke(
//                    "Connection to ${device.deviceName} failed: ${
//                        errorReasonToString(
//                            reasonCode
//                        )
//                    }"
//                )
//                Log.e(TAG, "Connection to ${device.deviceName} failed: $reasonCode")
//            }
//        })
//    }
//
//    fun disconnect() {
//        wifiP2pManager?.cancelConnect(channel, object : WifiP2pManager.ActionListener {
//            override fun onSuccess() {
//                onStatusChanged?.invoke("Disconnect initiated.")
//            }
//
//            override fun onFailure(reason: Int) {
//                onStatusChanged?.invoke("Disconnect failed: ${errorReasonToString(reason)}")
//            }
//        })
//        // Also remove group if this device is the owner
//        wifiP2pManager?.requestGroupInfo(channel) { group ->
//            if (group != null && group.isGroupOwner) {
//                removeGroup()
//            }
//        }
//        closeSockets()
//    }
//
//
//    private fun startServerSocket() {
//        coroutineScope.launch {
//            try {
//                serverSocket = ServerSocket(SERVER_PORT)
//                onStatusChanged?.invoke("Host: Server socket started on port $SERVER_PORT. Waiting for client...")
//                Log.d(TAG, "Server socket started, waiting for client connection.")
//
//                clientSocket = serverSocket?.accept() // Blocking call
//                onStatusChanged?.invoke("Host: Client connected: ${clientSocket?.inetAddress?.hostAddress}")
//                Log.d(TAG, "Client connected: ${clientSocket?.inetAddress}")
//
//                inputStream = clientSocket?.getInputStream()
//                outputStream = clientSocket?.getOutputStream()
//                listenForData() // Start listening for incoming data from client
//            } catch (e: IOException) {
//                Log.e(TAG, "Error starting server socket or accepting client: ${e.message}", e)
//                onStatusChanged?.invoke("Host: Error starting server: ${e.message}")
//                closeSockets()
//            } finally {
//                serverSocket?.close() // Ensure server socket is closed if accept fails or loop ends
//            }
//        }
//    }
//
//    private fun connectToHost(hostAddress: InetAddress) {
//        coroutineScope.launch {
//            try {
//                clientSocket = Socket()
//                onStatusChanged?.invoke("Client: Connecting to host: $hostAddress")
//                clientSocket?.connect(
//                    InetSocketAddress(hostAddress, SERVER_PORT),
//                    5000
//                ) // 5 sec timeout
//                onStatusChanged?.invoke("Client: Connected to host.")
//                Log.d(TAG, "Connected to host: $hostAddress")
//
//                inputStream = clientSocket?.getInputStream()
//                outputStream = clientSocket?.getOutputStream()
//                listenForData() // Start listening for incoming data from host
//            } catch (e: IOException) {
//                Log.e(TAG, "Error connecting to host: ${e.message}", e)
//                onStatusChanged?.invoke("Client: Error connecting to host: ${e.message}")
//                closeSockets()
//            }
//        }
//    }
//
//    private fun listenForData() {
//        coroutineScope.launch {
//            val buffer = ByteArray(1024)
//            var bytes: Int
//
//            while (isActive && inputStream != null) {
//                try {
//                    bytes = inputStream?.read(buffer) ?: -1
//                    if (bytes != -1) {
//                        val receivedMessage = String(buffer, 0, bytes)
//                        Log.d(TAG, "Data received: $receivedMessage")
//                        withContext(Dispatchers.Main) { // Switch to main thread for UI updates
//                            onDataReceived?.invoke(receivedMessage)
//                        }
//                    } else {
//                        // End of stream, connection likely closed
//                        Log.d(TAG, "Input stream ended, connection closed by peer.")
//                        onStatusChanged?.invoke("Connection closed by peer.")
//                        closeSockets()
//                        break
//                    }
//                } catch (e: IOException) {
//                    Log.e(TAG, "Error reading data: ${e.message}", e)
//                    onStatusChanged?.invoke("Error reading data: ${e.message}. Connection lost.")
//                    closeSockets()
//                    break // Exit loop on error
//                }
//            }
//            Log.d(TAG, "Stopped listening for data.")
//        }
//    }
//
//    fun sendData(data: String) {
//        coroutineScope.launch {
//            try {
//                outputStream?.write(data.toByteArray())
//                outputStream?.flush()
//                Log.d(TAG, "Data sent: $data")
//            } catch (e: IOException) {
//                Log.e(TAG, "Error sending data: ${e.message}", e)
//                onStatusChanged?.invoke("Error sending data: ${e.message}")
//                closeSockets()
//            }
//        }
//    }
//
//    private fun closeSockets() {
//        try {
//            inputStream?.close()
//            outputStream?.close()
//            clientSocket?.close()
//            serverSocket?.close()
//            inputStream = null
//            outputStream = null
//            clientSocket = null
//            serverSocket = null
//            onStatusChanged?.invoke("Sockets closed.")
//            Log.d(TAG, "Sockets closed.")
//        } catch (e: IOException) {
//            Log.e(TAG, "Error closing sockets: ${e.message}", e)
//        }
//    }
//
//    fun cleanup() {
//        unregisterReceiver()
//        disconnect() // This will also attempt to close sockets
//        coroutineScope.coroutineContext.cancelChildren() // Cancel any ongoing coroutines
//        onStatusChanged?.invoke("Cleanup complete.")
//    }
//
//    private fun checkPermissions(): Boolean {
//        val requiredPermissions = mutableListOf(
//            Manifest.permission.ACCESS_FINE_LOCATION,
//            Manifest.permission.ACCESS_WIFI_STATE,
//            Manifest.permission.CHANGE_WIFI_STATE,
//            Manifest.permission.INTERNET
//        )
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            requiredPermissions.add(Manifest.permission.NEARBY_WIFI_DEVICES)
//        }
//
//        return requiredPermissions.all {
//            ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
//        }
//    }
//
//    private fun errorReasonToString(reasonCode: Int): String {
//        return when (reasonCode) {
//            WifiP2pManager.P2P_UNSUPPORTED -> "P2P Unsupported"
//            WifiP2pManager.ERROR -> "Error"
//            WifiP2pManager.BUSY -> "Busy"
//            else -> "Unknown Error"
//        }
//    }
//
//
//    inner class WiFiDirectBroadcastReceiver : BroadcastReceiver() {
//        override fun onReceive(context: Context, intent: Intent) {
//            when (intent.action) {
//                WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION -> {
//                    val state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1)
//                    if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
//                        onStatusChanged?.invoke("Wi-Fi Direct is enabled.")
//                        Log.d(TAG, "Wi-Fi P2P is enabled.")
//                    } else {
//                        onStatusChanged?.invoke("Wi-Fi Direct is not enabled.")
//                        Log.d(TAG, "Wi-Fi P2P is not enabled.")
//                        // You might want to prompt the user to enable Wi-Fi P2P
//                    }
//                }
//
//                WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION -> {
//                    if (!checkPermissions()) return
//                    wifiP2pManager?.requestPeers(channel, peerListListener)
//                    Log.d(TAG, "P2P peers changed.")
//                }
//
//                WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION -> {
//                    val networkInfo =
//                        intent.getParcelableExtra<NetworkInfo>(WifiP2pManager.EXTRA_NETWORK_INFO)
//                    if (networkInfo?.isConnected == true) {
//                        wifiP2pManager?.requestConnectionInfo(channel, connectionInfoListener)
//                        onStatusChanged?.invoke("Connected to peer.")
//                        Log.d(TAG, "Connected to peer.")
//                    } else {
//                        onStatusChanged?.invoke("Disconnected from peer.")
//                        Log.d(TAG, "Disconnected from peer.")
//                        closeSockets() // Ensure sockets are closed on Wi-Fi P2P disconnect
//                        // Potentially try to rediscover or allow user to reconnect
//                    }
//                }
//
//                WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION -> {
//                    val thisDevice =
//                        intent.getParcelableExtra<WifiP2pDevice>(WifiP2pManager.EXTRA_WIFI_P2P_DEVICE)
//                    onDeviceAddressAvailable?.invoke(thisDevice?.deviceAddress)
//                    Log.d(
//                        TAG,
//                        "This device details changed: ${thisDevice?.deviceName} - ${thisDevice?.deviceAddress}"
//                    )
//                    onStatusChanged?.invoke("My device: ${thisDevice?.deviceName}")
//                }
//            }
//        }
//    }
//
//    private val peerListListener =
//        WifiP2pManager.PeerListListener { peersList: WifiP2pDeviceList? ->
//            val refreshedPeers = peersList?.deviceList?.toList() ?: emptyList()
//            onPeersChanged?.invoke(refreshedPeers)
//            if (refreshedPeers.isEmpty()) {
//                Log.d(TAG, "No peers found.")
//                // onStatusChanged?.invoke("No peers found yet.") // Can be noisy
//            } else {
//                Log.d(TAG, "Peers found: ${refreshedPeers.joinToString { it.deviceName }}")
//            }
//        }
//
//    private val connectionInfoListener =
//        WifiP2pManager.ConnectionInfoListener { info: WifiP2pInfo? ->
//            if (info != null) {
//                onConnectionInfoAvailable?.invoke(info)
//                if (info.groupFormed && info.isGroupOwner) {
//                    onStatusChanged?.invoke("I am the Group Owner (Host). Starting server...")
//                    Log.d(
//                        TAG,
//                        "I am the group owner. Group host address: ${info.groupOwnerAddress}"
//                    )
//                    startServerSocket()
//                } else if (info.groupFormed) {
//                    onStatusChanged?.invoke("I am a Client. Connecting to host...")
//                    Log.d(TAG, "I am a client. Group host address: ${info.groupOwnerAddress}")
//                    info.groupOwnerAddress?.let {
//                        connectToHost(it)
//                    } ?: run {
//                        onStatusChanged?.invoke("Host address not available.")
//                        Log.e(TAG, "Host address is null, cannot connect.")
//                    }
//                }
//            } else {
//                onStatusChanged?.invoke("Connection info is null.")
//                Log.e(TAG, "Connection info is null.")
//            }
//        }
//}
