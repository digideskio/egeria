/* SPDX-License-Identifier: Apache 2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.openmetadata.adapters.repositoryservices.auditlogstore.console;


import org.odpi.openmetadata.frameworks.connectors.ffdc.ConnectorCheckedException;
import org.odpi.openmetadata.repositoryservices.connectors.stores.auditlogstore.OMRSAuditLogRecord;
import org.odpi.openmetadata.repositoryservices.connectors.stores.auditlogstore.OMRSAuditLogStoreConnectorBase;
import org.odpi.openmetadata.repositoryservices.ffdc.OMRSErrorCode;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.InvalidParameterException;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.PagingErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * ConsoleAuditLogStoreConnector provides a connector implementation for a console (stdout) audit log.
 */
public class ConsoleAuditLogStoreConnector extends OMRSAuditLogStoreConnectorBase
{
    private static final Logger log = LoggerFactory.getLogger(ConsoleAuditLogStoreConnector.class);


    /**
     * Default constructor used by the connector provider.
     */
    public ConsoleAuditLogStoreConnector()
    {
    }


    /**
     * Store the audit log record in the audit log store.
     *
     * @param logRecord  log record to store
     * @return unique identifier assigned to the log record
     * @throws InvalidParameterException indicates that the logRecord parameter is invalid.
     */
    public String storeLogRecord(OMRSAuditLogRecord logRecord) throws InvalidParameterException
    {
        final String   methodName = "storeLogRecord";

        if (logRecord == null)
        {
            OMRSErrorCode errorCode    = OMRSErrorCode.NULL_LOG_RECORD;
            String        errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage();

            throw new InvalidParameterException(errorCode.getHTTPErrorCode(),
                                                this.getClass().getName(),
                                                methodName,
                                                errorMessage,
                                                errorCode.getSystemAction(),
                                                errorCode.getUserAction());
        }

        if (logRecord.getOriginator() == null)
        {
            OMRSErrorCode errorCode    = OMRSErrorCode.NULL_LOG_RECORD_ORIGINATOR;
            String        errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage();

            throw new InvalidParameterException(errorCode.getHTTPErrorCode(),
                                                this.getClass().getName(),
                                                methodName,
                                                errorMessage,
                                                errorCode.getSystemAction(),
                                                errorCode.getUserAction());
        }

        log.debug("AuditLogRecord: " + logRecord.toString());
        System.out.println(logRecord.getTimeStamp() + " " + logRecord.getOriginator().getServerName() + " " + logRecord.getMessageId() + " " + logRecord.getMessageText());

        return null;
    }


    /**
     * Retrieve a specific audit log record.
     *
     * @param logRecordId  unique identifier for the log record
     * @return requested audit log record
     * @throws InvalidParameterException indicates that the logRecordId parameter is invalid.
     */
    public OMRSAuditLogRecord  getAuditLogRecord(String     logRecordId) throws InvalidParameterException
    {

        return null;
    }


    /**
     * Retrieve a list of log records written in a specified time period.  The offset and maximumRecords
     * parameters support a paging
     *
     * @param startDate  start of time period
     * @param endDate  end of time period
     * @param offset  offset of full collection to begin the return results
     * @param maximumRecords  maximum number of log records to return
     * @return list of log records from the specified time period
     * @throws InvalidParameterException indicates that the start and/or end date parameters are invalid.
     * @throws PagingErrorException indicates that the offset or the maximumRecords parameters are invalid.
     */
    public List<OMRSAuditLogRecord> getAuditLogRecordsByTimeStamp(Date startDate,
                                                                       Date    endDate,
                                                                       int     offset,
                                                                       int     maximumRecords) throws InvalidParameterException,
                                                                                                      PagingErrorException
    {
        return null;
    }

    /**
     * Retrieve a list of log records of a specific severity.  The offset and maximumRecords
     * parameters support a paging model.
     *
     * @param severity  the severity value of messages to return
     * @param startDate  start of time period
     * @param endDate  end of time period
     * @param offset  offset of full collection to begin the return results
     * @param maximumRecords  maximum number of log records to return
     * @return list of log records from the specified time period
     * @throws InvalidParameterException indicates that the severity, start and/or end date parameters are invalid.
     * @throws PagingErrorException indicates that the offset or the maximumRecords parameters are invalid.
     */
    public List<OMRSAuditLogRecord> getAuditLogRecordsBySeverity(String   severity,
                                                                 Date     startDate,
                                                                 Date     endDate,
                                                                 int      offset,
                                                                 int      maximumRecords) throws InvalidParameterException,
                                                                                                 PagingErrorException
    {
        return null;
    }


    /**
     * Retrieve a list of log records written by a specific component.  The offset and maximumRecords
     * parameters support a paging model.
     *
     * @param component  name of the component to retrieve events from
     * @param startDate  start of time period
     * @param endDate  end of time period
     * @param offset  offset of full collection to begin the return results
     * @param maximumRecords  maximum number of log records to return
     * @return list of log records from the specified time period
     * @throws InvalidParameterException indicates that the component, start and/or end date parameters are invalid.
     * @throws PagingErrorException indicates that the offset or the maximumRecords parameters are invalid.
     */
    public List<OMRSAuditLogRecord> getAuditLogRecordsByComponent(String component,
                                                                  Date   startDate,
                                                                  Date   endDate,
                                                                  int    offset,
                                                                  int    maximumRecords) throws InvalidParameterException,
                                                                                                PagingErrorException
    {
        return null;
    }


    /**
     * Indicates that the connector is completely configured and can begin processing.
     *
     * @throws ConnectorCheckedException there is a problem within the connector.
     */
    public void start() throws ConnectorCheckedException
    {
        super.start();
    }


    /**
     * Free up any resources held since the connector is no longer needed.
     *
     * @throws ConnectorCheckedException there is a problem within the connector.
     */
    public  void disconnect() throws ConnectorCheckedException
    {
        super.disconnect();
    }
}
