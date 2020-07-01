package com.codingapi.txlcn.tc.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author lorne
 * @date 2020/7/1
 * @description
 */
public interface TransactionJdbcEvent {

    String type();

    TransactionJdbcState state();

    Object execute(Connection connection, Object param) throws SQLException;
}
