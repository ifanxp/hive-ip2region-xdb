Hive UDF ip2region-xdb

```shell
hdfs dfs -rm /user/irsuser/ip2region/hive-udf-ip2region-jar-with-dependencies.jar
hdfs dfs -put hive-udf-ip2region-jar-with-dependencies.jar /user/irsuser/ip2region/

hive -hiveconf hive.root.logger=INFO,console

```

```hiveql
create temporary function get_country as 'NewROUTE.GetIP_Country' using jar 'hdfs://nameservice1/user/irsuser/ip2region/hive-udf-ip2region.jar';
select get_country('59.46.69.66');
-- 输出：中国

create temporary function get_province as 'NewROUTE.GetIP_Province' using jar 'hdfs://nameservice1/user/irsuser/ip2region/hive-udf-ip2region.jar';
select get_province('59.46.69.66');
-- 输出：辽宁省

create temporary function get_city as 'NewROUTE.GetIP_City' using jar 'hdfs://nameservice1/user/irsuser/ip2region/hive-udf-ip2region.jar';
select get_city('59.46.69.66');
-- 输出：沈阳市

create temporary function get_info as 'NewROUTE.GetIP_Info' using jar 'hdfs://nameservice1/user/irsuser/ip2region/hive-udf-ip2region.jar';
select get_info('59.46.69.66');
-- 输出：中国|0|辽宁省|沈阳市|电信

create temporary function get_isp as 'NewROUTE.GetIP_ISP' using jar 'hdfs://nameservice1/user/irsuser/ip2region/hive-udf-ip2region.jar';
select get_isp('59.46.69.66');
-- 输出：电信
```