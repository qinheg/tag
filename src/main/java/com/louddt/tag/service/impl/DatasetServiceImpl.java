package com.louddt.tag.service.impl;

import com.louddt.tag.entity.vo.dataset.ColumnsQuery;
import com.louddt.tag.entity.vo.dataset.DatabaseQuery;
import com.louddt.tag.entity.vo.dataset.TableQuery;
import com.louddt.tag.service.DatasetService;
import com.louddt.tag.utils.DBUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class DatasetServiceImpl implements DatasetService {

    @Override
    public List<String> queryRemoteDatabaseNames(DatabaseQuery query) throws SQLException{
        if(DBUtil.DB_TYPE_MYSQL.equals(query.getDbType())){
            try (Connection con = DBUtil.getMySqlConnection(query.getIp(),query.getPort(),null,query.getUsername(),query.getPassword())) {
                return DBUtil.queryDatabaseNames(con);
            }
        }
        return null;
    }

    @Override
    public List<String> queryRemoteTableNames(TableQuery query) throws SQLException{
        if(DBUtil.DB_TYPE_MYSQL.equals(query.getDbType())){
            try (Connection con = DBUtil.getMySqlConnection(query.getIp(),query.getPort(),query.getDatabaseName(),query.getUsername(),query.getPassword())) {
                return DBUtil.queryTableNames(con);
            }
        }
        return null;
    }

    @Override
    public List<String> queryRemoteColumnNames(ColumnsQuery query) throws SQLException{
        try (Connection con = DBUtil.getMySqlConnection(query.getIp(),query.getPort(),query.getDatabaseName(),query.getUsername(),query.getPassword())) {
            return DBUtil.queryColumnNames(query.getDbType(),con,query.getTableName());
        }
    }
}
