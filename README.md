# codis-stress-test
Codis cluster And Redis stress test client

本项目主要是为Codis集群、Redis压力测试，提供了set和get方法的api。

使用者可以结合 abTest or Jmeter 进行压测，调用接口：

http://localhost:8080/codis/test/set?key=codisTest&value=测试缓存AAA

http://localhost:8080/codis/test/get?key=codisTest
