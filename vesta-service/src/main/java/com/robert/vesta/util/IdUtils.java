package com.robert.vesta.util;

import com.robert.vesta.service.impl.IdServiceImpl;
import com.robert.vesta.service.impl.provider.PropertyMachineIdProvider;
import com.robert.vesta.service.intf.IdService;

/**
 * Created with IntelliJ IDEA.
 * User: yujianfu (mr.vencnet@gmail.com)
 * Date: 2018/7/13
 * Time: 上午10:40
 */
public class IdUtils {

    private static long machineId = -1;
    private static IdService idService = null;


    public static long genId() {

        if (machineId == -1) {
            String hostIp = IpUtils.getHostIp();
            String[] split = hostIp.split("\\.");
            for (String v : split) {
                machineId += Long.valueOf(v);
            }

            machineId = machineId % 1000;
        }

        if (idService == null) {
            PropertyMachineIdProvider propertyMachineIdProvider = new PropertyMachineIdProvider();
            propertyMachineIdProvider.setMachineId(machineId);

            IdServiceImpl idServiceImpl = new IdServiceImpl();
            idServiceImpl.setMachineIdProvider(propertyMachineIdProvider);
            idServiceImpl.init();
            idService = idServiceImpl;
            return idServiceImpl.genId();
        }

        return idService.genId();
    }
}
