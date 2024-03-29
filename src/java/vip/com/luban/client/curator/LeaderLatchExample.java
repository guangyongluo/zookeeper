package com.luban.client.curator;

import com.google.common.collect.Lists;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.retry.RetryNTimes;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * *************书山有路勤为径***************
 * 鲁班学院
 * 往期资料加木兰老师  QQ: 2746251334
 * VIP课程加安其拉老师 QQ: 3164703201
 * 讲师：周瑜老师
 * *************学海无涯苦作舟***************
 */
public class LeaderLatchExample {

    public static void main(String[] args) throws Exception {

        List<CuratorFramework> clients = Lists.newArrayList();

        List<LeaderLatch> leaderLatches = Lists.newArrayList();

        for(int i=0; i<10; i++) {
            CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.193.128:2181,192.168.193.128:2182,192.168.193.128:2183", new RetryNTimes(1,1000));
            clients.add(client);
            client.start();

            LeaderLatch leaderLatch = new LeaderLatch(client, "/LeaderLatch", "client#"+i);

            leaderLatches.add(leaderLatch);
            leaderLatch.start();
        }

        TimeUnit.SECONDS.sleep(20);

        for (LeaderLatch leaderLatch: leaderLatches) {
            if (leaderLatch.hasLeadership()) {
                System.out.println("当前Leader是"+ leaderLatch.getId());
                break;
            }
        }

        System.in.read();

        for (CuratorFramework curatorFramework: clients) {
            curatorFramework.close();
        }

    }
}
