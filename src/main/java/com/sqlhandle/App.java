package com.sqlhandle;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlInsertStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.stat.TableStat;
import com.alibaba.druid.support.opds.udf.SqlFormat;
import com.alibaba.druid.util.JdbcConstants;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        String sql = " select a.id as oor_id,(select a.dd from arnon.table_C a where a.id = b.id  order by a.dd) as dd ,b.sn,b.time,a.order_sn from table_A a inner join table_B b on a.id = b.id group by (a.id*34+b.id) order by a.id";

        DbType dbType = JdbcConstants.MYSQL;
        // 生成语法树
        // 新建 MySQL Parser
        SQLStatementParser parser = new MySqlStatementParser(sql);

        // 使用Parser解析生成AST，这里SQLStatement就是AST  是一个内部维护了树结构的类
        SQLStatement statement = parser.parseStatement();

        System.out.println(statement); 
       
        //遍历sql ast 
        MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
        statement.accept(visitor);
        //获取表名
        Map<TableStat.Name, TableStat> tables = visitor.getTables();
        Set<TableStat.Name> tableNameSet = tables.keySet();
        System.out.println(tableNameSet);
        //获取where条件
        List<TableStat.Condition> conditions = visitor.getConditions();
        System.out.println(conditions);

        //获取查询字段
        Collection<TableStat.Column> columns = visitor.getColumns();
        System.out.println(columns); 
        System.out.println(visitor.getOrderByColumns());
        System.out.println(visitor.getGroupByColumns());


       
     

    }
                
}
