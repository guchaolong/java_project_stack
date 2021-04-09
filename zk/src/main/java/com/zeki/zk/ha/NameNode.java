package com.zeki.zk.ha;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Description:
 *
 * @author GUCHAOLONG
 * @date 2020/11/27 23:47
 */
public class NameNode implements Watcher, Runnable {

    private ZooKeeper zk;


    // 存储active namenode的父级znode节点
    private static final String ACTIVE_PARENT = "/namenode_active";
    // 存储standby namenode的父级znode节点
    private static final String STANBY_PARENT = "/namenode_standbys";
    // 锁节点
    private static final String LOCK_ZNODE = "/namenode_lock";

    private String lockName;

    private String nodeName;

    private NameNode(String nodeName) {
        this.nodeName = nodeName;

        try {
            zk = new ZooKeeper("localhost", 2000, this);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入当前节点名：");
        String nodeName = scanner.next();
        new NameNode(nodeName).run();
    }

    public void process(WatchedEvent event) {

        System.out.println(event.getPath());
        if (event.getPath() == null) {
            try {
                if (zk.exists(ACTIVE_PARENT, false) == null) {
                    zk.create(ACTIVE_PARENT, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                }

                if (zk.exists(STANBY_PARENT, false) == null) {
                    zk.create(STANBY_PARENT, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                }

                if (zk.exists(LOCK_ZNODE, false) == null) {
                    zk.create(LOCK_ZNODE, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                }


                String lockNode = zk.create(LOCK_ZNODE + "/node_", nodeName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

                lockName = lockNode.replace(LOCK_ZNODE + "/", "");

            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        try {
            List<String> actives = zk.getChildren(ACTIVE_PARENT, false);

            //获取锁
            boolean lock = getLock();

            if (actives.size() == 0) {

                if (lock) {
                    System.out.println("当前没有active状态的NameNode," + nodeName + "竞争成为active");
                    String s = zk.create(ACTIVE_PARENT + "/" + nodeName, nodeName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
                    if (s != null) {
                        Stat exists2 = zk.exists(STANBY_PARENT + "/" + nodeName, false);
                        if (exists2 != null) {
                            zk.delete(STANBY_PARENT + "/" + nodeName, -1);
                        }
                    }
                }

            } else {
                if (zk.exists(STANBY_PARENT + "/" + nodeName, false) == null) {
                    System.out.println("当前active状态的节点为：" + actives);
                    System.out.println("当前节点设置备用状态：" + nodeName);
                    zk.create(STANBY_PARENT + "/" + nodeName, nodeName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

                }


            }

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private boolean getLock() {
        try {
            List<String> lock = zk.getChildren(LOCK_ZNODE, false);

            Collections.sort(lock);
            System.out.println(lock);

            if (lockName.equals(lock.get(0))) {
                System.out.println("我是最小的:" + lockName + "，获取到锁");
                return true;
            } else {
                int i = lock.indexOf(lockName);
                String lastNode = LOCK_ZNODE + "/" + lock.get(i - 1);
                System.out.println("我不是最小的:" + lockName + "，监控前一个:" + lastNode);

                zk.exists(lastNode, true);
                return false;
            }

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void run() {
        while (true) {

        }
    }
}

