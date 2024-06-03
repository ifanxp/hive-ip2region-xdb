package NewROUTE;

/*
create temporary function get_country as 'NewROUTE.GetIP_Country' using jar 'hdfs://nameservice1/user/irsuser/ip2region/hive-udf-ip2region.jar';
select get_country('59.46.69.66');
输出：中国
 */
public class GetIP_Country extends GetIP_Info {
    public String evaluate(String ip) throws Exception{
        return this.GetInfo(ip, 0);
    }
}
