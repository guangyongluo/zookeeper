package com.luban.client.zkclient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.io.IOException;

/**
 * *************书山有路勤为径***************
 * 鲁班学院
 * 往期资料加木兰老师  QQ: 2746251334
 * VIP课程加安其拉老师 QQ: 3164703201
 * 讲师：周瑜老师
 * *************学海无涯苦作舟***************
 */
public class ZkClientTest {

    public static void main(String[] args) throws IOException {
        ZkClient zk = new ZkClient("192.168.193.128:2181,192.168.193.128:2182,192.168.193.128:2183", 10000, 10000, new SerializableSerializer());

        zk.createPersistent("/data", "1");

        zk.subscribeDataChanges("/data", new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println("数据被改了");
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {

            }
        });

        System.in.read();

    }
}
