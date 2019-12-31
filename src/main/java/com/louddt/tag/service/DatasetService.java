package com.louddt.tag.service;

import com.louddt.tag.entity.vo.dataset.ColumnsQuery;
import com.louddt.tag.entity.vo.dataset.DatabaseQuery;
import com.louddt.tag.entity.vo.dataset.TableQuery;

import java.sql.SQLException;
import java.util.List;

public interface DatasetService {

    List<String> queryRemoteDatabaseNames(DatabaseQuery query) throws SQLException;

    List<String> queryRemoteTableNames(TableQuery query) throws SQLException;

    List<String> queryRemoteColumnNames(ColumnsQuery query) throws SQLException;
}
