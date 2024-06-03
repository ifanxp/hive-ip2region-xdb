package NewROUTE;
/*
create temporary function get_province as 'NewROUTE.GetIP_Province' using jar 'hdfs://nameservice1/user/irsuser/ip2region/hive-udf-ip2region.jar';
select get_province('59.46.69.66');
输出：辽宁省
 */

public class GetIP_Province extends GetIP_Info {
    public String evaluate(String ip) throws Exception{
        return this.GetInfo(ip, 2);
    }
}
