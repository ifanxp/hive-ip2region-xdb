package NewROUTE;

/*
create temporary function get_isp as 'NewROUTE.GetIP_ISP' using jar 'hdfs://nameservice1/user/irsuser/ip2region/hive-udf-ip2region.jar';
select get_isp('59.46.69.66');
输出：电信
 */

public class GetIP_ISP extends GetIP_Info {
    public String evaluate(String ip) throws Exception{
        try {
            return this.GetInfo(ip, 4);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
