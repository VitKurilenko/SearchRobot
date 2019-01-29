package searchrobot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, InterruptedException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter date in the yyyy-MM-dd format:");
        String reqDate = reader.readLine();

        GatewayDB gdb = GatewayDB.getInstance();
        ResultSet rs = gdb.selectByDate(reqDate);

        ExecutorService threadPool = Executors.newCachedThreadPool();

        while (rs.next()) {
            int id = rs.getInt("id");
            String url = rs.getString("url");

            Runnable task = new Runnable() {
                @Override
                public void run() {

                    try {
                        int status = Crawler.getHttpCode(url);

                        gdb.updateStatus(id, status);

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            };

            threadPool.execute(task);
        }

        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.HOURS);

        gdb.closeConnection();

        System.out.println("Update is complete.");
    }

}
