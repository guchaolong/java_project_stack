redisson开源方案实现分布式锁

```
RLock lock = redisson.getLock("anyLock");
lock.lock();
run();//业务逻辑
lock.unlock();
```


