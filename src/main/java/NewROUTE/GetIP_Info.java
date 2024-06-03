package NewROUTE;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.lionsoul.ip2region.xdb.Searcher;
import java.io.IOException;
import java.io.InputStream;

/*
hdfs dfs -rm /user/irsuser/ip2region/hive-udf-ip2region-jar-with-dependencies.jar
hdfs dfs -put hive-udf-ip2region-jar-with-dependencies.jar /user/irsuser/ip2region/

hive -hiveconf hive.root.logger=INFO,console

create temporary function get_info as 'NewROUTE.GetIP_Info' using jar 'hdfs://nameservice1/user/irsuser/ip2region/hive-udf-ip2region.jar';
select get_info('59.46.69.66');
输出：中国|0|辽宁省|沈阳市|电信
 */
public class GetIP_Info extends UDF {
    private static InputStream in;

    private static byte[] data;
    private static Searcher searcher;
    static {
        //加载数据
        ByteArrayOutputStream out = null;
        try {
            FileSystem fileSystem = FileSystem.get(new Configuration());
            in = fileSystem.open(new Path("hdfs://nameservice1/user/irsuser/ip2region/ip2region.xdb"));
            out = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            while (in.read(b) != -1) {
                out.write(b);
            }
            // 提高性能,将ip2region.db一次从hdfs中读取出来，缓存到data字节数组中以重用，
            // 避免每来一条数据读取一次ip2region.db
            data = out.toByteArray();
            out.close();
            in.close();

        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                if(out != null) {
                    out.close();
                }
                if(in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            searcher = Searcher.newWithBuffer(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String GetInfo(String ip, int ix) throws Exception{
        String[] addr = searcher.search(ip).split("\\|");
        return addr[ix];
    }

    public String evaluate(String ip) throws Exception{
        return searcher.search(ip);
    }
}
