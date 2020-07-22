# 分布式
[分布式脑裂问题分析](https://blog.csdn.net/xinquanv1/article/details/103126372?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase)

即只有集群中超过半数节点投票才能选举出Leader。
[面试题：Zookeeper是如何解决脑裂问题](https://blog.csdn.net/u013374645/article/details/93140148)

# 分布式事务


[分布式事务解决方案汇总：2PC、3PC、消息中间件、TCC、状态机+重试+幂等（转）](https://www.cnblogs.com/myseries/p/10939355.html)

# 订单系统设计

[基于中台思想的物流系统设计（二）：构建物流订单能力](https://zhuanlan.zhihu.com/p/47812118)

> 订单C端查询&B端查询&保存
> 订单缓存设计&3万+qps
> 订单配置中心
> 订单状态机
[订单中台-3万+QPS高并发系统架构设计](https://blog.csdn.net/YaphetS__YJ/article/details/100124543)


[饿了么：业务井喷时，订单系统架构这样演进](https://mp.weixin.qq.com/s?__biz=MjM5MDE0Mjc4MA==&mid=2650993858&idx=1&sn=ce2cc36b737da8c00ba5cfb5cfe9488a&scene=1&srcid=0825qULkRUx9IU5zxzG2VJ9w&from=singlemessage&isappinstalled=0#wechat_redirect)


# Redis
[Redis高级数据结构HyperLogLog原理详解和应用场景](https://blog.csdn.net/William0318/article/details/89362255)
[详细解析Redis中的布隆过滤器及其应用](https://www.cnblogs.com/heihaozi/p/12174478.html)


先更新数据库，再删缓存
（1）更新数据库数据
（2）数据库会将操作信息写入binlog日志当中
（3）订阅程序提取出所需要的数据以及key
（4）另起一段非业务代码，获得该信息
（5）尝试删除缓存操作，发现删除失败
（6）将这些信息发送至消息队列
（7）重新从消息队列中获得该数据，重试操作。
[Redis与Mysql双写一致性方案解析](https://zhuanlan.zhihu.com/p/59167071)


1. 从节点执行 slaveof 命令
2. 从节点只是保存了 slaveof 命令中主节点的信息，并没有立即发起复制
3. 从节点内部的定时任务发现有主节点的信息，开始使用 socket 连接主节点
4. 连接建立成功后，发送 ping 命令，希望得到 pong 命令响应，否则会进行重连
5. 如果主节点设置了权限，那么就需要进行权限验证；如果验证失败，复制终止。
6. 权限验证通过后，进行数据同步，这是耗时最长的操作，主节点将把所有的数据全部发送给从节点。
7. 当主节点把当前的数据同步给从节点后，便完成了复制的建立流程。接下来，主节点就会持续的把写命令发送给从节点，保证主从数据一致性。
[深入Redis：详解 Redis主从复制的原理!](https://zhuanlan.zhihu.com/p/60239657)


Redis中实现的LRU算法
新的驱逐策略---LFU
redis的Key失效机制有两种：被动方式(passive way)和主动方式(active way)
Redis主动删除失效key的策略是：随机抽取一部分的key进行校验，如果已经失效，就删除淘汰。
在有过期时间的key集合中随机抽取20个key。
删除所有的过期key
如果过期的key超过25%，重新执行步骤1

[Redis内存回收策略和key失效机制](https://zhuanlan.zhihu.com/p/149528273)


[Redis分布式锁](https://zhuanlan.zhihu.com/p/130235036)
[Springboot分别使用乐观锁和分布式锁（基于redisson）完成高并发防超卖](https://blog.csdn.net/tianyaleixiaowu/article/details/90036180?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-8.nonecase&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-8.nonecase)

# 大量数据相关问题
[10亿个数选取重复次数最多的100个整数](https://blog.csdn.net/iteye_7408/article/details/82209473)
[在100G文件中找出出现次数最多的100个IP](https://blog.csdn.net/fycy2010/article/details/46945641?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-2.nonecase&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-2.nonecase)


# Linux
linux查看服务端口号
netstat -atunp | grep mysql