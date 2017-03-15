package com.dream.base.common;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

/**
 * @author Wang
 * @version 1.0.0
 * @description 网络操作工具类
 * @create 2014-2-18 上午09:22:30
 * @company 北京开拓明天科技有限公司 Copyright: 版权所有 (c) 2014
 */

public class NetWorkUtil {

    public static final String NET_TYPE_NO_NETWORK = "no_network";

    /**
     * 无网络链接
     */
    public static final int NET_NO_CONNECTION = 0;

    /**
     * wifi
     */
    public static final int NET_TYPE_WIFI = 1;

    public static final int NET_TYPE_2G = 2;

    public static final int NET_TYPE_3G = 3;

    public static final int NET_TYPE_4G = 4;

    /**
     * 未知的网络类型
     */
    public static final int NET_TYPE_UNKNOWN = 5;

    private Context mContext = null;

    public NetWorkUtil(Context pContext) {
        this.mContext = pContext;
    }

    public static final String IP_DEFAULT = "0.0.0.0";

    /**
     * 判断是否连接网络
     *
     * @param pContext
     * @return
     */
    public static boolean isConnectInternet(final Context pContext) {
        final ConnectivityManager conManager = (ConnectivityManager) pContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = conManager.getActiveNetworkInfo();

        if (networkInfo != null) {
            return networkInfo.isAvailable();
        }

        return false;
    }

    /**
     * 获取网络类型
     *
     * @param context
     * @return
     */
    public static int getConnectionType(Context context) {

        int netType = NET_NO_CONNECTION;

        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

        if (networkInfo == null) {

            netType = NET_NO_CONNECTION;
        } else {

            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                netType = NET_TYPE_WIFI;
            } else {

                int networkType = networkInfo.getSubtype();

                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                        netType = NET_TYPE_2G;
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                        netType = NET_TYPE_3G;
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                        netType = NET_TYPE_4G;
                        break;
                    default:

                        String subType = networkInfo.getSubtypeName();

                        if (subType.equalsIgnoreCase("TD-SCDMA") || subType.equalsIgnoreCase("WCDMA") || subType.equalsIgnoreCase("CDMA2000")) {
                            netType = NET_TYPE_3G;
                        } else {
                            netType = NET_TYPE_UNKNOWN;
                        }

                        break;

                }
            }
        }

        return netType;
    }

    /**
     * wifi是否打开
     */
    public static boolean isWifiEnabled(Context context) {
        ConnectivityManager mgrConn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        TelephonyManager mgrTel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return ((mgrConn.getActiveNetworkInfo() != null && mgrConn.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED) || mgrTel.getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
    }

    /**
     * 连接的是否为WIFI
     *
     * @param pContext
     * @return
     */
    public static boolean isConnectWifi(final Context pContext) {
        ConnectivityManager mConnectivity = (ConnectivityManager) pContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = mConnectivity.getActiveNetworkInfo();
        // 判断网络连接类型，只有在3G或wifi里进行一些数据更新。
        int netType = -1;
        if (info != null) {
            netType = info.getType();
        }
        if (netType == ConnectivityManager.TYPE_WIFI) {
            return info.isConnected();
        } else {
            return false;
        }
    }

    /**
     * 获取wifi名
     *
     * @return
     */
    public static String getConnectWifiSSID(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo.getSSID();
    }

    /**
     * 判断当前网络是否3G网络
     *
     * @param context
     * @return boolean
     */
    public static boolean is3G(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }

    /**
     * 获取网络类型
     *
     * @param pNetType
     * @return
     */
    public static String getNetTypeName(final int pNetType) {
        switch (pNetType) {
            case 0:
                return "unknown";
            case 1:
                return "GPRS";
            case 2:
                return "EDGE";
            case 3:
                return "UMTS";
            case 4:
                return "CDMA: Either IS95A or IS95B";
            case 5:
                return "EVDO revision 0";
            case 6:
                return "EVDO revision A";
            case 7:
                return "1xRTT";
            case 8:
                return "HSDPA";
            case 9:
                return "HSUPA";
            case 10:
                return "HSPA";
            case 11:
                return "iDen";
            case 12:
                return "EVDO revision B";
            case 13:
                return "LTE";
            case 14:
                return "eHRPD";
            case 15:
                return "HSPA+";
            default:
                return "unknown";
        }
    }

    /**
     * 获取IP地址
     *
     * @return
     */
    public static String getIPAddress() {
        try {
            final Enumeration<NetworkInterface> networkInterfaceEnumeration = NetworkInterface
                    .getNetworkInterfaces();

            while (networkInterfaceEnumeration.hasMoreElements()) {
                final NetworkInterface networkInterface = networkInterfaceEnumeration
                        .nextElement();

                final Enumeration<InetAddress> inetAddressEnumeration = networkInterface
                        .getInetAddresses();

                while (inetAddressEnumeration.hasMoreElements()) {
                    final InetAddress inetAddress = inetAddressEnumeration
                            .nextElement();

                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }

            return NetWorkUtil.IP_DEFAULT;
        } catch (final SocketException e) {
            return NetWorkUtil.IP_DEFAULT;
        }
    }


    /**
     * 获取连接类型名
     *
     * @return
     */
    public String getConnTypeName() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return NET_TYPE_NO_NETWORK;
        } else {
            return networkInfo.getTypeName();
        }
    }

    /**
     * Gps是否打开
     *
     * @param context
     * @return
     */
    public static boolean isGpsEnabled(Context context) {
        LocationManager locationManager = ((LocationManager) context.getSystemService(Context.LOCATION_SERVICE));
        List<String> accessibleProviders = locationManager.getProviders(true);
        return accessibleProviders != null && accessibleProviders.size() > 0;
    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity) {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }

}
