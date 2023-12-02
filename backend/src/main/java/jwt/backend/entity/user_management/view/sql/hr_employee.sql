create
or replace view HR_EMPLOYEE_VIEW as
select he.EMPLOYEE_ID                                               AS "employeeId",
       he.AUTO_FORMATED_ID                                          AS "autoFormattedId",
       he.WORK_EMAIL                                                AS "workMail",
       he.FIRST_NAME || '' || he.MIDDLE_NAME || ' ' || he.LAST_NAME AS "fullName",
       he.WORK_PHONE                                                AS "workPhone"
from HRM_EMPLOYEE@DBLINK_DAL_ERP he;