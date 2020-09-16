redisson开源方案实现分布式锁

```java
RLock lock = redisson.getLock("anyLock");
lock.lock();
run();//业务逻辑
lock.unlock();

```


