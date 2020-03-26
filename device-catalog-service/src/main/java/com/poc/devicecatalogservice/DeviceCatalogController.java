package com.poc.devicecatalogservice;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class DeviceCatalogController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EurekaClient eurekaClient;

    @GetMapping(path = "/devices")
    ResponseEntity<List<Device>> getDeviceList() {
        if (!checkToken("token123456")) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        List<Device> deviceList = new ArrayList<>();
        deviceList.add(new Device("1", "device1", "type1"));
        deviceList.add(new Device("2", "device2", "type2"));
        deviceList.add(new Device("3", "device3", "type3"));
        deviceList.add(new Device("4", "device4", "type4"));
        deviceList.add(new Device("5", "device5", "type5"));
        return new ResponseEntity<>(deviceList, HttpStatus.OK);
    }

    @GetMapping(path = "/devices/{deviceId}")
    ResponseEntity<Device> getDeviceDetails(@PathVariable("deviceId") String deviceId) {
        if (!checkToken("token123456")) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        
        if (deviceId.equals("1")
                || deviceId.equals("2")
                || deviceId.equals("3")
                || deviceId.equals("4")
                || deviceId.equals("5")) {
            Device device = new Device(deviceId, "device" + deviceId, "type" + deviceId);
            return new ResponseEntity<>(device, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    Boolean checkToken(String token) {
        Application application = eurekaClient.getApplication("auth-service");
        InstanceInfo instanceInfo = application.getInstances().get(0);
        String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/" + "auth/checkToken?token=" + token;
        System.out.println("URL" + url);
        HttpEntity<String> entity = new HttpEntity<>("parameters", null);
        ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);
        if (result.getStatusCode() == HttpStatus.OK) {
            return true;
        }
        return false;
    }
}
