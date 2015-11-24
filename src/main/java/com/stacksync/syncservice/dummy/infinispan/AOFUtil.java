/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stacksync.syncservice.dummy.infinispan;

import com.stacksync.syncservice.db.ConnectionPool;
import com.stacksync.syncservice.db.DAOFactory;
import com.stacksync.syncservice.db.infinispan.InfinispanConnection;
import com.stacksync.syncservice.db.infinispan.DeviceDAO;
import com.stacksync.syncservice.db.infinispan.UserDAO;
import com.stacksync.syncservice.db.infinispan.WorkspaceDAO;
import com.stacksync.syncservice.db.infinispan.models.DeviceRMI;
import com.stacksync.syncservice.db.infinispan.models.UserRMI;
import com.stacksync.syncservice.db.infinispan.models.WorkspaceRMI;
import java.rmi.RemoteException;
import java.util.UUID;

/**
 *
 * @author Laura Martínez Sanahuja <lauramartinezsanahuja@gmail.com>
 */
public class AOFUtil {

    private UserDAO userDAO;
    private DeviceDAO deviceDAO;
    private WorkspaceDAO workspaceDAO;
    private InfinispanConnection connection;

    public AOFUtil(ConnectionPool pool) throws Exception {
        this.connection = (InfinispanConnection) pool.getConnection();
        DAOFactory factory = new DAOFactory("infinispan");
        this.userDAO = factory.getUserDao(connection);
        this.deviceDAO = factory.getDeviceDAO(connection);
        this.workspaceDAO = factory.getWorkspaceDao(connection);
    }

    public void setup(UUID uuid) throws RemoteException {

        System.out.println("New workspace: " + uuid.toString());
        DeviceRMI device = new DeviceRMI(uuid);

        WorkspaceRMI workspace = new WorkspaceRMI(uuid);
        workspace.addUser(uuid);
        workspace.setOwner(uuid);

        UserRMI user = new UserRMI(uuid);
        user.setEmail(uuid.toString());
        user.setName("a");
        user.setQuotaLimit(10);
        user.setQuotaUsed(0);
        user.setSwiftAccount("a");
        user.setSwiftUser("a");
        //user.addDevice(device);
        user.addWorkspace(uuid);
        userDAO.add(user);

        workspaceDAO.add(workspace);
        deviceDAO.add(device);
    }

    public InfinispanConnection getConnection() {
        return this.connection;
    }
}
