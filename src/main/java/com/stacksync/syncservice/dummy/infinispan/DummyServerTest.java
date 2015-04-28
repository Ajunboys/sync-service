/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stacksync.syncservice.dummy.infinispan;

import com.stacksync.syncservice.db.Connection;
import java.util.UUID;

import com.stacksync.syncservice.db.ConnectionPool;
import com.stacksync.syncservice.db.ConnectionPoolFactory;
import com.stacksync.syncservice.util.Config;

public class DummyServerTest {

    /**
     *
     * @param args commitsMinute, numUsers, minutes
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String configPath = "config.properties";

        if ((args.length != 2) && (args.length != 4)) {

            System.err.println("Usage: numUsers fileName [commitsPerMinute minutes]");
            System.exit(0);
        }

        int numUsers = Integer.parseInt(args[0]);
        String fileName = args[1];
        int commitsPerMinute = 0;
        int minutes = 0;
        if (args.length == 4) {
            commitsPerMinute = Integer.parseInt(args[2]);
            minutes = Integer.parseInt(args[3]);
        }
        int numThreads = 2;

        // Load properties
        Config.loadProperties(configPath);
        String datasource = Config.getDatasource();
        ConnectionPool pool = ConnectionPoolFactory.getConnectionPool(datasource);

        // it will try to connect to the DB, throws exception if not
        // possible.
        Connection conn = pool.getConnection();
        conn.close();

        ServerDummy[] dummies = new ServerDummy[numThreads];

        for (int i = 0; i < numThreads; i++) {
            // Crear un nou thread
            dummies[i] = new ServerDummy(pool, numUsers, fileName, commitsPerMinute, minutes);
        }

        // executar a la senyal
        // TODO provar només amb un únic thread
        for (int i = 0; i < numThreads; i++) {
            ServerDummy dummy = dummies[i];
            dummy.start();
        }

        int numLines = 0;
        // Wait all the threads
        for (ServerDummy dummy : dummies) {
            dummy.join();
            dummy.getConnection().close();
            numLines = dummy.getItemsCount();
        }

        System.out.println("END - Commits made: " + numThreads * numLines);

        System.exit(0);
    }
}
