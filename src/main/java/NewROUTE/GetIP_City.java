package NewROUTE;

/*
create temporary function get_city as 'NewROUTE.GetIP_City' using jar 'hdfs://nameservice1/user/irsuser/ip2region/hive-udf-ip2region.jar';
select get_city('59.46.69.66');
输出：沈阳市
 */
public class GetIP_City extends GetIP_Info {

    public String evaluate(String ip) throws Exception{
        try {
            return this.GetInfo(ip, 3);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
