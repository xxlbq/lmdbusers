/*
 * 创建时间 2006-09-26
 */
package com.livedoor.dbm.components.treeTable;

/**
 * <p>
 * Title: 执行计划
 * </p>
 * <p>
 * Description: DbManager sql 语句执行计划
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: 英极软件开发（大连）有限公司
 * </p>
 * 
 * @author YuanBaoKun
 * @version 1.0
 */
public class PlanTabSql {

	/**
	 * 建立执行计划的表sql语句
	 */
	static String ORACLE_PLANTAB_CREATE = "create table PLAN_TABLE ( "
			+ "statement_id 	varchar2(30), " + "timestamp    	date, "
			+ "remarks      	varchar2(80), " + "operation    	varchar2(30), "
			+ "options       	varchar2(255), "
			+ "object_node  	varchar2(128), " + "object_owner 	varchar2(30), "
			+ "object_name  	varchar2(30), " + "object_instance numeric, "
			+ "object_type     varchar2(30), "
			+ "optimizer       varchar2(255), " + "search_columns  number, "
			+ "id		numeric, " + "parent_id	numeric, " + "position	numeric, "
			+ "cost		numeric, " + "cardinality	numeric, " + "bytes		numeric, "
			+ "other_tag       varchar2(255), "
			+ "partition_start varchar2(255), "
			+ "partition_stop  varchar2(255), " + "partition_id    numeric, "
			+ "other		long, " + "distribution    varchar2(30), "
			+ "cpu_cost	numeric, " + "io_cost		numeric, "
			+ "temp_space	numeric, " + "access_predicates varchar2(4000), "
			+ "filter_predicates varchar2(4000)) ";

	/**
	 * 查询执行计划的表sql语句
	 */
	static String ORACLE_PLANTAB_SELECT = "SELECT OPERATION, COST, IO_COST, CPU_COST, CARDINALITY, BYTES, "
			+ "POSITION, OBJECT_OWNER, OBJECT_NAME, OPTIONS, OBJECT_TYPE, OPTIMIZER, "
			+ "OBJECT_INSTANCE, REMARKS, OBJECT_NODE, SEARCH_COLUMNS, OTHER_TAG, "
			+ "PARTITION_START, PARTITION_STOP, PARTITION_ID, OTHER, DISTRIBUTION, TEMP_SPACE, ID, PARENT_ID "
			+ "FROM PLAN_TABLE WHERE STATEMENT_ID = '";

	/**
	 * 删除执行计划的表sql语句
	 */
	static String ORACLE_PLANTAB_DELETE = "DELETE FROM PLAN_TABLE WHERE STATEMENT_ID = '";

	/**
	 * 建立执行计划的表sql语句
	 */
	static String DB2_EXPLAIN_INSTANCE = "CREATE TABLE EXPLAIN_INSTANCE ( EXPLAIN_REQUESTER VARCHAR(128) NOT NULL,EXPLAIN_TIME      TIMESTAMP    NOT NULL,SOURCE_NAME       VARCHAR(128) NOT NULL,SOURCE_SCHEMA     VARCHAR(128) NOT NULL,SOURCE_VERSION    VARCHAR(64)  NOT NULL,EXPLAIN_OPTION    CHAR(1)      NOT NULL,SNAPSHOT_TAKEN    CHAR(1)   NOT NULL,DB2_VERSION       CHAR(7)   NOT NULL,SQL_TYPE          CHAR(1)   NOT NULL,QUERYOPT          INTEGER   NOT NULL,BLOCK             CHAR(1)   NOT NULL,ISOLATION         CHAR(2)   NOT NULL,BUFFPAGE          INTEGER   NOT NULL,AVG_APPLS         INTEGER   NOT NULL,SORTHEAP          INTEGER   NOT NULL,LOCKLIST          INTEGER   NOT NULL,MAXLOCKS          SMALLINT  NOT NULL,LOCKS_AVAIL       INTEGER   NOT NULL,CPU_SPEED         DOUBLE    NOT NULL,REMARKS           VARCHAR(254),DBHEAP            INTEGER   NOT NULL,COMM_SPEED        DOUBLE    NOT NULL,PARALLELISM       CHAR(2)   NOT NULL,DATAJOINER        CHAR(1)   NOT NULL,PRIMARY KEY (EXPLAIN_REQUESTER,             EXPLAIN_TIME,             SOURCE_NAME,             SOURCE_SCHEMA,SOURCE_VERSION))";

	/**
	 * 建立执行计划的表sql语句
	 */
	static String DB2_EXPLAIN_ARGUMENT = "CREATE TABLE EXPLAIN_ARGUMENT ( EXPLAIN_REQUESTER   VARCHAR(128)  NOT NULL,EXPLAIN_TIME        TIMESTAMP     NOT NULL,SOURCE_NAME         VARCHAR(128)  NOT NULL,SOURCE_SCHEMA       VARCHAR(128)  NOT NULL,SOURCE_VERSION      VARCHAR(64)   NOT NULL,EXPLAIN_LEVEL       CHAR(1)       NOT NULL,STMTNO              INTEGER       NOT NULL,SECTNO              INTEGER       NOT NULL,OPERATOR_ID         INTEGER       NOT NULL,ARGUMENT_TYPE       CHAR(8)       NOT NULL,ARGUMENT_VALUE      VARCHAR(1024),LONG_ARGUMENT_VALUE CLOB(2M)      NOT LOGGED,FOREIGN KEY (EXPLAIN_REQUESTER,EXPLAIN_TIME,SOURCE_NAME,SOURCE_SCHEMA,SOURCE_VERSION,EXPLAIN_LEVEL,STMTNO,SECTNO) REFERENCES EXPLAIN_STATEMENT ON DELETE CASCADE)";

	/**
	 * 建立执行计划的表sql语句
	 */
	static String DB2_EXPLAIN_OBJECT = "CREATE TABLE EXPLAIN_OBJECT ( EXPLAIN_REQUESTER    VARCHAR(128) NOT NULL,EXPLAIN_TIME         TIMESTAMP    NOT NULL,SOURCE_NAME          VARCHAR(128) NOT NULL,SOURCE_SCHEMA        VARCHAR(128) NOT NULL,SOURCE_VERSION       VARCHAR(64)  NOT NULL,EXPLAIN_LEVEL        CHAR(1)      NOT NULL,STMTNO               INTEGER      NOT NULL,SECTNO               INTEGER      NOT NULL,OBJECT_SCHEMA        VARCHAR(128) NOT NULL,OBJECT_NAME          VARCHAR(128) NOT NULL,OBJECT_TYPE          CHAR(2)      NOT NULL,CREATE_TIME          TIMESTAMP,STATISTICS_TIME      TIMESTAMP,COLUMN_COUNT         SMALLINT     NOT NULL,ROW_COUNT            BIGINT       NOT NULL,WIDTH                INTEGER      NOT NULL,PAGES                INTEGER      NOT NULL,DISTINCT             CHAR(1)      NOT NULL,TABLESPACE_NAME      VARCHAR(128),OVERHEAD             DOUBLE       NOT NULL,TRANSFER_RATE        DOUBLE       NOT NULL,PREFETCHSIZE         INTEGER      NOT NULL,EXTENTSIZE           INTEGER      NOT NULL,CLUSTER              DOUBLE       NOT NULL,NLEAF                INTEGER      NOT NULL,NLEVELS              INTEGER      NOT NULL,FULLKEYCARD          BIGINT       NOT NULL,OVERFLOW             INTEGER      NOT NULL,FIRSTKEYCARD         BIGINT       NOT NULL,FIRST2KEYCARD        BIGINT       NOT NULL,FIRST3KEYCARD        BIGINT       NOT NULL,FIRST4KEYCARD        BIGINT       NOT NULL,SEQUENTIAL_PAGES     INTEGER      NOT NULL,DENSITY              INTEGER      NOT NULL,STATS_SRC            CHAR(1)      NOT NULL,AVERAGE_SEQUENCE_GAP          DOUBLE  NOT NULL,AVERAGE_SEQUENCE_FETCH_GAP    DOUBLE  NOT NULL,AVERAGE_SEQUENCE_PAGES        DOUBLE  NOT NULL,AVERAGE_SEQUENCE_FETCH_PAGES  DOUBLE  NOT NULL,AVERAGE_RANDOM_PAGES          DOUBLE  NOT NULL,AVERAGE_RANDOM_FETCH_PAGES    DOUBLE  NOT NULL,NUMRIDS                       BIGINT  NOT NULL,NUMRIDS_DELETED               BIGINT  NOT NULL,NUM_EMPTY_LEAFS               BIGINT  NOT NULL,ACTIVE_BLOCKS                 BIGINT  NOT NULL,FOREIGN KEY (EXPLAIN_REQUESTER,EXPLAIN_TIME,SOURCE_NAME,SOURCE_SCHEMA,SOURCE_VERSION,EXPLAIN_LEVEL,STMTNO,SECTNO) REFERENCES EXPLAIN_STATEMENT ON DELETE CASCADE)";

	/**
	 * 建立执行计划的表sql语句
	 */
	static String DB2_EXPLAIN_OPERATOR = "CREATE TABLE EXPLAIN_OPERATOR ( EXPLAIN_REQUESTER VARCHAR(128) NOT NULL,EXPLAIN_TIME      TIMESTAMP    NOT NULL,SOURCE_NAME       VARCHAR(128) NOT NULL,SOURCE_SCHEMA     VARCHAR(128) NOT NULL,SOURCE_VERSION    VARCHAR(64)  NOT NULL,EXPLAIN_LEVEL     CHAR(1)      NOT NULL,STMTNO            INTEGER      NOT NULL,SECTNO            INTEGER      NOT NULL,OPERATOR_ID       INTEGER      NOT NULL,OPERATOR_TYPE     CHAR(6)      NOT NULL,TOTAL_COST        DOUBLE       NOT NULL,IO_COST           DOUBLE       NOT NULL,CPU_COST          DOUBLE       NOT NULL,FIRST_ROW_COST    DOUBLE       NOT NULL,RE_TOTAL_COST     DOUBLE       NOT NULL,RE_IO_COST        DOUBLE       NOT NULL,RE_CPU_COST       DOUBLE       NOT NULL,COMM_COST         DOUBLE       NOT NULL,FIRST_COMM_COST   DOUBLE       NOT NULL,BUFFERS           DOUBLE       NOT NULL,REMOTE_TOTAL_COST DOUBLE       NOT NULL,REMOTE_COMM_COST  DOUBLE       NOT NULL,FOREIGN KEY (EXPLAIN_REQUESTER,EXPLAIN_TIME,SOURCE_NAME,SOURCE_SCHEMA,SOURCE_VERSION,EXPLAIN_LEVEL,STMTNO,SECTNO) REFERENCES EXPLAIN_STATEMENT ON DELETE CASCADE)";

	/**
	 * 建立执行计划的表sql语句
	 */
	static String DB2_EXPLAIN_PREDICATE = "CREATE TABLE EXPLAIN_PREDICATE ( EXPLAIN_REQUESTER VARCHAR(128) NOT NULL,EXPLAIN_TIME      TIMESTAMP    NOT NULL,SOURCE_NAME       VARCHAR(128) NOT NULL,SOURCE_SCHEMA     VARCHAR(128) NOT NULL,SOURCE_VERSION    VARCHAR(64)  NOT NULL,EXPLAIN_LEVEL     CHAR(1)      NOT NULL,STMTNO            INTEGER      NOT NULL,SECTNO            INTEGER      NOT NULL,OPERATOR_ID       INTEGER      NOT NULL,PREDICATE_ID      INTEGER      NOT NULL,HOW_APPLIED       CHAR(5)      NOT NULL,WHEN_EVALUATED    CHAR(3)      NOT NULL,RELOP_TYPE        CHAR(2)      NOT NULL,SUBQUERY          CHAR(1)      NOT NULL,FILTER_FACTOR     DOUBLE       NOT NULL,PREDICATE_TEXT    CLOB(2M)     NOT LOGGED,FOREIGN KEY (EXPLAIN_REQUESTER,EXPLAIN_TIME,SOURCE_NAME,SOURCE_SCHEMA,SOURCE_VERSION,EXPLAIN_LEVEL,STMTNO,SECTNO) REFERENCES EXPLAIN_STATEMENT ON DELETE CASCADE)";

	/**
	 * 建立执行计划的表sql语句
	 */
	static String DB2_EXPLAIN_STREAM = "CREATE TABLE EXPLAIN_STREAM ( EXPLAIN_REQUESTER VARCHAR(128) NOT NULL,EXPLAIN_TIME      TIMESTAMP    NOT NULL,SOURCE_NAME       VARCHAR(128) NOT NULL,SOURCE_SCHEMA     VARCHAR(128) NOT NULL,SOURCE_VERSION    VARCHAR(64)  NOT NULL,EXPLAIN_LEVEL     CHAR(1)      NOT NULL,STMTNO            INTEGER      NOT NULL,SECTNO            INTEGER      NOT NULL,STREAM_ID         INTEGER      NOT NULL,SOURCE_TYPE       CHAR(1)      NOT NULL,SOURCE_ID         INTEGER      NOT NULL,TARGET_TYPE       CHAR(1)      NOT NULL,TARGET_ID         INTEGER      NOT NULL,OBJECT_SCHEMA     VARCHAR(128),OBJECT_NAME       VARCHAR(128),STREAM_COUNT      DOUBLE       NOT NULL,COLUMN_COUNT      SMALLINT     NOT NULL,PREDICATE_ID      INTEGER      NOT NULL,COLUMN_NAMES      CLOB(2M)     NOT LOGGED,PMID              SMALLINT     NOT NULL,SINGLE_NODE       CHAR(5),PARTITION_COLUMNS CLOB(2M)     NOT LOGGED,FOREIGN KEY (EXPLAIN_REQUESTER,EXPLAIN_TIME,SOURCE_NAME,SOURCE_SCHEMA,SOURCE_VERSION,EXPLAIN_LEVEL,STMTNO,SECTNO) REFERENCES EXPLAIN_STATEMENT ON DELETE CASCADE)";

	/**
	 * 建立执行计划的表sql语句
	 */
	static String DB2_ADVISE_INDEX = "CREATE TABLE ADVISE_INDEX (EXPLAIN_REQUESTER VARCHAR(128)  NOT NULL WITH DEFAULT '',EXPLAIN_TIME      TIMESTAMP     NOT NULL WITH DEFAULT CURRENT TIMESTAMP,SOURCE_NAME       VARCHAR(128)  NOT NULL WITH DEFAULT '',SOURCE_SCHEMA     VARCHAR(128)  NOT NULL WITH DEFAULT '',SOURCE_VERSION    VARCHAR(64)   NOT NULL WITH DEFAULT '',EXPLAIN_LEVEL     CHAR(1)       NOT NULL WITH DEFAULT '',STMTNO            INTEGER       NOT NULL WITH DEFAULT 0,SECTNO            INTEGER       NOT NULL WITH DEFAULT 0,QUERYNO           INTEGER       NOT NULL WITH DEFAULT 0,QUERYTAG          CHAR(20)      NOT NULL WITH DEFAULT '',NAME              VARCHAR(128)  NOT NULL,CREATOR           VARCHAR(128)  NOT NULL WITH DEFAULT '',TBNAME            VARCHAR(128)  NOT NULL,TBCREATOR         VARCHAR(128)  NOT NULL WITH DEFAULT '',COLNAMES          CLOB(2M)     NOT NULL,UNIQUERULE        CHAR(1)       NOT NULL WITH DEFAULT '',COLCOUNT          SMALLINT      NOT NULL WITH DEFAULT 0,IID               SMALLINT      NOT NULL WITH DEFAULT 0,NLEAF             INTEGER       NOT NULL WITH DEFAULT 0,NLEVELS           SMALLINT      NOT NULL WITH DEFAULT 0,FIRSTKEYCARD      BIGINT        NOT NULL WITH DEFAULT 0,FULLKEYCARD       BIGINT        NOT NULL WITH DEFAULT 0,CLUSTERRATIO      SMALLINT      NOT NULL WITH DEFAULT 0,CLUSTERFACTOR     DOUBLE        NOT NULL WITH DEFAULT 0,USERDEFINED       SMALLINT      NOT NULL WITH DEFAULT 0,SYSTEM_REQUIRED   SMALLINT      NOT NULL WITH DEFAULT 0,CREATE_TIME       TIMESTAMP     NOT NULL WITH DEFAULT CURRENT TIMESTAMP,STATS_TIME        TIMESTAMP              WITH DEFAULT CURRENT TIMESTAMP,PAGE_FETCH_PAIRS  VARCHAR(254)  NOT NULL WITH DEFAULT '',REMARKS           VARCHAR(254)           WITH DEFAULT '',DEFINER           VARCHAR(128)  NOT NULL WITH DEFAULT '',CONVERTED         CHAR(1)       NOT NULL WITH DEFAULT '',SEQUENTIAL_PAGES  INTEGER       NOT NULL WITH DEFAULT 0,DENSITY           INTEGER       NOT NULL WITH DEFAULT 0,FIRST2KEYCARD     BIGINT        NOT NULL WITH DEFAULT 0,FIRST3KEYCARD     BIGINT        NOT NULL WITH DEFAULT 0,FIRST4KEYCARD     BIGINT        NOT NULL WITH DEFAULT 0,PCTFREE           SMALLINT      NOT NULL WITH DEFAULT -1,UNIQUE_COLCOUNT   SMALLINT      NOT NULL WITH DEFAULT -1,MINPCTUSED        SMALLINT      NOT NULL WITH DEFAULT 0,REVERSE_SCANS     CHAR(1)       NOT NULL WITH DEFAULT 'N',USE_INDEX         CHAR(1),CREATION_TEXT     CLOB(2M)      NOT NULL NOT LOGGED WITH DEFAULT '',PACKED_DESC       BLOB(1M)               NOT LOGGED)";

	/**
	 * 建立执行计划的表sql语句
	 */
	static String DB2_ADVISE_WORKLOAD = "CREATE TABLE ADVISE_WORKLOAD (WORKLOAD_NAME     CHAR(128)    NOT NULL WITH DEFAULT 'WK0',STATEMENT_NO      INTEGER      NOT NULL WITH DEFAULT 1,STATEMENT_TEXT    CLOB(2M)     NOT NULL NOT LOGGED,STATEMENT_TAG     VARCHAR(256) NOT NULL WITH DEFAULT '' ,FREQUENCY         INTEGER      NOT NULL WITH DEFAULT 1,IMPORTANCE        DOUBLE       NOT NULL WITH DEFAULT 1,WEIGHT            DOUBLE       NOT NULL WITH DEFAULT 1,COST_BEFORE       DOUBLE,COST_AFTER        DOUBLE,COMPILABLE        CHAR(17))";

	/**
	 * 建立执行计划的表sql语句
	 */
	static String DB2_EXPLAIN_STATEMENT = "CREATE TABLE EXPLAIN_STATEMENT ( EXPLAIN_REQUESTER VARCHAR(128) NOT NULL,EXPLAIN_TIME      TIMESTAMP    NOT NULL,SOURCE_NAME       VARCHAR(128) NOT NULL,SOURCE_SCHEMA     VARCHAR(128) NOT NULL,SOURCE_VERSION    VARCHAR(64)  NOT NULL,EXPLAIN_LEVEL     CHAR(1)      NOT NULL,STMTNO            INTEGER      NOT NULL,SECTNO            INTEGER      NOT NULL,QUERYNO           INTEGER      NOT NULL,QUERYTAG          CHAR(20)     NOT NULL,STATEMENT_TYPE    CHAR(2)      NOT NULL,UPDATABLE         CHAR(1)      NOT NULL,DELETABLE         CHAR(1)      NOT NULL,TOTAL_COST        DOUBLE       NOT NULL,STATEMENT_TEXT    CLOB(2M)     NOT NULL NOT LOGGED,SNAPSHOT          BLOB(10M)    NOT LOGGED,QUERY_DEGREE      INTEGER      NOT NULL,PRIMARY KEY (EXPLAIN_REQUESTER,EXPLAIN_TIME,SOURCE_NAME,SOURCE_SCHEMA,SOURCE_VERSION,EXPLAIN_LEVEL,STMTNO,SECTNO),FOREIGN KEY (EXPLAIN_REQUESTER,EXPLAIN_TIME,SOURCE_NAME,SOURCE_SCHEMA,SOURCE_VERSION) REFERENCES EXPLAIN_INSTANCE ON DELETE CASCADE)";

	/**
	 * 查询执行计划的表sql语句
	 */
	static String DB2_PLANTAB_SELECT = "SELECT STMT.QUERYNO, QUERYTAG, OPERATOR_TYPE, STMT.EXPLAIN_REQUESTER, "
			+ "STMT.EXPLAIN_TIME, O.SOURCE_NAME, O.SOURCE_SCHEMA, O.EXPLAIN_LEVEL, O.STMTNO, O.SECTNO, S.STREAM_ID, "
			+ "S.SOURCE_TYPE, S.TARGET_TYPE, OBJ.OBJECT_SCHEMA, OBJ.OBJECT_NAME, S.STREAM_COUNT, "
			+ "S.COLUMN_COUNT, S.PREDICATE_ID, S.COLUMN_NAMES, S.PMID, S.SINGLE_NODE, S.PARTITION_COLUMNS, "
			+ "OPERATOR_ID, O.TOTAL_COST, IO_COST, CPU_COST, FIRST_ROW_COST, RE_TOTAL_COST, RE_IO_COST, "
			+ "RE_CPU_COST, COMM_COST, REMOTE_TOTAL_COST, FIRST_COMM_COST, BUFFERS,  REMOTE_COMM_COST, S.SOURCE_ID, S.TARGET_ID "
			+ "FROM ((( EXPLAIN_OPERATOR O JOIN EXPLAIN_STATEMENT STMT ON "
			+ "O.STMTNO = STMT.STMTNO AND O.EXPLAIN_REQUESTER = STMT.EXPLAIN_REQUESTER AND "
			+ "O.EXPLAIN_TIME = STMT.EXPLAIN_TIME AND O.SOURCE_NAME = STMT.SOURCE_NAME AND "
			+ "O.SOURCE_SCHEMA = STMT.SOURCE_SCHEMA AND O.EXPLAIN_LEVEL = STMT.EXPLAIN_LEVEL AND "
			+ "O.STMTNO = STMT.STMTNO AND O.SECTNO = STMT.SECTNO AND "
			+ "STMT.EXPLAIN_LEVEL = ''P'' AND "
			+ "STMT.QUERYTAG = ''{0}'') "
			+ "LEFT JOIN EXPLAIN_STREAM S ON O.OPERATOR_ID = S.SOURCE_ID AND O.STMTNO = S.STMTNO AND "
			+ "O.EXPLAIN_REQUESTER = S.EXPLAIN_REQUESTER AND O.EXPLAIN_TIME = S.EXPLAIN_TIME AND "
			+ "O.SOURCE_NAME = S.SOURCE_NAME AND O.SOURCE_SCHEMA = S.SOURCE_SCHEMA AND "
			+ "O.EXPLAIN_LEVEL = S.EXPLAIN_LEVEL AND O.STMTNO = S.STMTNO AND O.SECTNO = S.SECTNO ) "
			+ "LEFT JOIN EXPLAIN_STREAM OBJ ON S.SOURCE_ID = OBJ.TARGET_ID AND "
			+ "OBJ.SOURCE_ID = -1 AND S.EXPLAIN_REQUESTER = OBJ.EXPLAIN_REQUESTER AND "
			+ "S.EXPLAIN_TIME = OBJ.EXPLAIN_TIME AND S.SOURCE_NAME = OBJ.SOURCE_NAME AND "
			+ "S.SOURCE_SCHEMA = OBJ.SOURCE_SCHEMA AND S.EXPLAIN_LEVEL = OBJ.EXPLAIN_LEVEL AND "
			+ "S.STMTNO = OBJ.STMTNO AND S.SECTNO = OBJ.SECTNO ) ORDER BY OPERATOR_ID ASC";

	/**
	 * 删除执行计划的表sql语句
	 */
	static String DB2_EXPLAIN_DELETE = "DELETE FROM EXPLAIN_INSTANCE A WHERE EXISTS "
			+ "( SELECT B.QUERYTAG FROM EXPLAIN_STATEMENT B WHERE "
			+ "A.EXPLAIN_REQUESTER = B.EXPLAIN_REQUESTER AND "
			+ "A.EXPLAIN_TIME = B.EXPLAIN_TIME AND "
			+ "A.SOURCE_NAME = B.SOURCE_NAME AND "
			+ "A.SOURCE_SCHEMA = B.SOURCE_SCHEMA 	AND "
			+ "A.SOURCE_VERSION = B.SOURCE_VERSION AND B.QUERYTAG = '";

}