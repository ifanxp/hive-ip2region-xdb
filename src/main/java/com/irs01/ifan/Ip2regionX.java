package com.irs01.ifan;

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

create temporary function ip2region as 'com.irs01.ifan.Ip2regionX' using jar 'hdfs://nameservice1/user/irsuser/ip2region/hive-udf-ip2region-jar-with-dependencies.jar';
select ip2region('124.236.223.17');
 */

public class Ip2regionX extends UDF {
    private static InputStream in;

    private static byte[] data;
    private static Searcher searcher;
    static {
        //加载数据
        ByteArrayOutputStream out = null;
        try {
            // Configuration configuration = new Configuration();
            // FileSystem fileSystem = FileSystem.get(URI.create("hdfs://nameservice1/user/irsuser/ip2region/ip2region.xdb"), configuration);
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

    public String evaluate(String ip) throws Exception{
        return searcher.search(ip);
    }
}
