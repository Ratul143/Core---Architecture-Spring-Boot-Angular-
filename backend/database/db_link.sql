create
database link DBLINK_DAL_ERP
    connect to DALERP_TEST identified by dalerp2018
    using '(DESCRIPTION=
(ADDRESS=
(PROTOCOL=TCP)
(HOST=175.29.181.13)
(PORT=1521))
(CONNECT_DATA=
(SID=dcoproddb)))'