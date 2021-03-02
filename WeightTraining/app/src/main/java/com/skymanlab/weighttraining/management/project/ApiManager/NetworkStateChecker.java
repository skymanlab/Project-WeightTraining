package com.skymanlab.weighttraining.management.project.ApiManager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;

import androidx.annotation.NonNull;

public class NetworkStateChecker extends ConnectivityManager.NetworkCallback {

    // instance variable
    private Context context;
    private NetworkRequest networkRequest;
    private ConnectivityManager connectivityManager;
    private ConnectivityManager.NetworkCallback networkCallback;

    // constructor
    public NetworkStateChecker(Context context, ConnectivityManager.NetworkCallback networkCallback){
        this.context = context;
        this.networkCallback = networkCallback;
        this.networkRequest = new NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .build();

        this.connectivityManager = (ConnectivityManager)this.context.getSystemService(Context.CONNECTIVITY_SERVICE);

    }


    public void register(){
        this.connectivityManager.registerNetworkCallback(networkRequest, this);
    }

    public static boolean checkActiveNetwork(Context context) {

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return manager.getActiveNetwork() != null;

    }

    @Override
    public void onAvailable(@NonNull Network network) {
        super.onAvailable(network);
    }

    @Override
    public void onLosing(@NonNull Network network, int maxMsToLive) {
        super.onLosing(network, maxMsToLive);
    }

    @Override
    public void onLost(@NonNull Network network) {
        super.onLost(network);
    }

    @Override
    public void onUnavailable() {
        super.onUnavailable();
    }

    @Override
    public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities);
    }

    @Override
    public void onLinkPropertiesChanged(@NonNull Network network, @NonNull LinkProperties linkProperties) {
        super.onLinkPropertiesChanged(network, linkProperties);
    }

    @Override
    public void onBlockedStatusChanged(@NonNull Network network, boolean blocked) {
        super.onBlockedStatusChanged(network, blocked);
    }
}
