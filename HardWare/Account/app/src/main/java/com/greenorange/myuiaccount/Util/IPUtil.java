package com.greenorange.myuiaccount.Util;

import android.text.TextUtils;

import com.greenorange.myuiaccount.Log;

import org.apache.http.conn.util.InetAddressUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by JasWorkSpace on 15/10/27.
 */
public class IPUtil {

    private static final String IP_NULL = "";
    private static final String IP_PATTERN = "\\b([1-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])){3}\\b";
    private static final String [] IP_ADDRS = {
            "http://www.qingcheng.com/plugin/getip",
            "http://ip.taobao.com/service/getIpInfo2.php?ip=myip",
            "http://city.ip138.com/ip2city.asp"};

    public  static String getIpFromNetWork(){
        for(String url : IP_ADDRS){
            String ipaddress = getNetIP(url);
            if(!TextUtils.isEmpty(ipaddress)
                    && (InetAddressUtils.isIPv4Address(ipaddress) || InetAddressUtils.isIPv6Address(ipaddress))){
                return ipaddress;
            }
        }
        return "";
    }

    public static String getNetIP(String ipaddr) {
        URL infoUrl = null;
        InputStream inStream = null;
        try {
            infoUrl = new URL(ipaddr);
            URLConnection connection = infoUrl.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inStream = httpConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "gb2312"));
                StringBuilder strber = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    strber.append(line + "\n");
                }
                inStream.close();
                Log.i("GetNetIp(" + ipaddr + ") == " + strber.toString());
                Pattern pattern = Pattern.compile(IP_PATTERN);
                Matcher matcher = pattern.matcher(strber);
                if (matcher.find()) {
                    String matcherIp = matcher.group();
                    Log.i("matcher.find() true matched ip = " + matcherIp);
                    if (!isInnerIP(matcherIp)) {
                        return matcherIp;
                    }
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return IP_NULL;
    }

    public static boolean isInnerIP(String ipAddress) {
        boolean isInnerIp = false;
        long ipNum = getIpNum(ipAddress);
        // 私有IP：A类 10.0.0.0-10.255.255.255 B类 172.16.0.0-172.31.255.255 C类 192.168.0.0-192.168.255.255 当然，还有127这个网段是环回地址
        long aBegin = getIpNum("10.0.0.0");
        long aEnd = getIpNum("10.255.255.255");
        long bBegin = getIpNum("172.16.0.0");
        long bEnd = getIpNum("172.31.255.255");
        long cBegin = getIpNum("192.168.0.0");
        long cEnd = getIpNum("192.168.255.255");
        isInnerIp = isInner(ipNum, aBegin, aEnd) || isInner(ipNum, bBegin, bEnd) || isInner(ipNum, cBegin, cEnd) || ipAddress.equals("127.0.0.1");
        Log.i(ipAddress + " is inner ip ? " + isInnerIp);
        return isInnerIp;
    }
    private static long getIpNum(String ipAddress) {
        String[] ip = ipAddress.split("\\.");
        long a = Integer.parseInt(ip[0]);
        long b = Integer.parseInt(ip[1]);
        long c = Integer.parseInt(ip[2]);
        long d = Integer.parseInt(ip[3]);

        long ipNum = a * 256 * 256 * 256 + b * 256 * 256 + c * 256 + d;
        return ipNum;
    }

    private static boolean isInner(long userIp, long begin, long end) {
        return (userIp >= begin) && (userIp <= end);
    }
}
