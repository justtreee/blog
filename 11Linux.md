# 十一、Linux命令行使用
1. **如何查找一个进程打开所有的文件**  
    - lsof -c mysql  
    备注: -c 选项将会列出所有以mysql开头的程序的文件，其实你也可以写成 lsof | grep mysql, 但是第一种方法明显比第二种方法要少打几个字符了。

    [lsof 查看进程打开那些文件 或者 查看文件给那个进程使用](https://blog.csdn.net/kozazyh/article/details/5495532)

2. **查看负载**  
    top命令能够清晰的展现出系统的状态，而且它是实时的监控

3. **nc 用法说明**  
    Netcat 或者叫 nc 是 Linux 下的一个用于调试和检查网络工具包。可用于创建 TCP/IP 连接，最大的用途就是用来处理 TCP/UDP 套接字。[8 个实用的 Linux netcat 命令示例](https://oschina.net/question/12_50469)
`nc localhost 2389`

4. **向Redis传输管道**
`(printf "PING\r\nPING\r\nPING\r\n"; sleep 1) | nc localhost 6379`

5. **[shell正则表达式求以某内容开头某内容结尾](https://blog.csdn.net/lzjsqn/article/details/53575848)**
    - 行首以^匹配字符串或字符序列
    - 行尾以$匹配字符串或字符
    - 使用句点匹配单字符
    - 使用[]匹配一个范围或集合

