--/*******************************************************************************
-- *  This program is free software: you can redistribute it and/or modify it under
-- *  the terms of the GNU Lesser General Public License as published by the Free
-- *  Software Foundation, either version 3 of the License, or (at your option) any
-- *  later version. This program is distributed in the hope that it will be
-- *  useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
-- *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
-- *  Public License for more details. You should have received a copy of the GNU
-- *  Lesser General Public License along with this program. If not, see
-- *  <http://www.gnu.org/licenses/>
-- *  
-- *******************************************************************************/
SET DATABASE SQL SIZE FALSE;

SET DATABASE SQL SYNTAX ORA TRUE;

DROP TABLE IF EXISTS t;

CREATE TABLE t(a INTEGER, b BIGINT);

CREATE FUNCTION echoEM () RETURNS VARCHAR RETURN 'default';
CREATE FUNCTION echoEmpty () RETURNS VARCHAR RETURN NULL;
CREATE PROCEDURE echoEmptyProc () BEGIN ATOMIC DECLARE temp_id INTEGER; SET temp_id=1; END

CREATE FUNCTION echo (msg VARCHAR) RETURNS VARCHAR RETURN msg;

CREATE PROCEDURE echoProc (msg VARCHAR, OUT res VARCHAR) BEGIN ATOMIC SET res=msg; END

CREATE FUNCTION echoNumber (msg NUMBER) RETURNS NUMBER RETURN msg;

CREATE PROCEDURE echoNumberProc (msg NUMBER, OUT res NUMBER) BEGIN ATOMIC SET res=msg; END

CREATE FUNCTION echoDate (msg DATE) RETURNS DATE RETURN msg;

CREATE PROCEDURE echoDateProc (msg DATE, OUT res DATE) BEGIN ATOMIC SET res=msg; END

CREATE FUNCTION echoTime (msg TIME) RETURNS TIME RETURN msg;

CREATE PROCEDURE echoTimeProc (msg TIME, OUT res TIME) BEGIN ATOMIC SET res=msg; END

CREATE FUNCTION echoTimestamp (msg TIMESTAMP) RETURNS TIMESTAMP RETURN msg;

CREATE PROCEDURE echoTimestampProc (msg TIMESTAMP, OUT res TIMESTAMP) BEGIN ATOMIC SET res=msg; END

--CREATE PROCEDURE echoProcNum (msg VARCHAR,pos INTEGER, OUT res VARCHAR, OUT num INTEGER) BEGIN ATOMIC SET res=msg; SET num=pos; END